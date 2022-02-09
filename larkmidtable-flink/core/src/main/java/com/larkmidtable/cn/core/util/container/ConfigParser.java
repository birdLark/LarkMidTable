package com.larkmidtable.cn.core.util.container;

import com.larkmidtable.cn.core.util.container.container.CoreConstant;
import com.larkmidtable.common.exception.LarkMidTableException;
import com.larkmidtable.common.util.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ConfigParser {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigParser.class);

	/**
	 * 指定Job配置路径，ConfigParser会解析Job、Plugin、Core全部信息，并以Configuration返回
	 */
	public static Configuration parse(final String jobPath) {
		Configuration configuration = ConfigParser.parseJobConfig(jobPath);

		configuration.merge(ConfigParser.parseCoreConfig(CoreConstant.FLINK_CONF_PATH), false);
		// todo config优化，只捕获需要的plugin
		String readerPluginName = configuration.getString(CoreConstant.FLINK_JOB_CONTENT_READER_NAME);
		String writerPluginName = configuration.getString(CoreConstant.FLINK_JOB_CONTENT_WRITER_NAME);

		String preHandlerName = configuration.getString(CoreConstant.FLINK_JOB_PREHANDLER_PLUGINNAME);

		String postHandlerName = configuration.getString(CoreConstant.FLINK_JOB_POSTHANDLER_PLUGINNAME);

		Set<String> pluginList = new HashSet<String>();
		pluginList.add(readerPluginName);
		pluginList.add(writerPluginName);

		if (StringUtils.isNotEmpty(preHandlerName)) {
			pluginList.add(preHandlerName);
		}
		if (StringUtils.isNotEmpty(postHandlerName)) {
			pluginList.add(postHandlerName);
		}
		try {
			configuration.merge(parsePluginConfig(new ArrayList<String>(pluginList)), false);
		} catch (Exception e) {
			//吞掉异常，保持log干净。这里message足够。
			LOG.warn(String.format("插件[%s,%s]加载失败，1s后重试... Exception:%s ", readerPluginName, writerPluginName,
					e.getMessage()));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				//
			}
			configuration.merge(parsePluginConfig(new ArrayList<String>(pluginList)), false);
		}

		return configuration;
	}

	public static Configuration parsePluginConfig(List<String> wantPluginNames) {
		Configuration configuration = Configuration.newDefault();

		Set<String> replicaCheckPluginSet = new HashSet<String>();
		int complete = 0;
		for (final String each : ConfigParser
				.getDirAsList(CoreConstant.FLINK_PLUGIN_READER_HOME)) {
			Configuration eachReaderConfig = ConfigParser.parseOnePluginConfig(each, "reader", replicaCheckPluginSet, wantPluginNames);
			if(eachReaderConfig!=null) {
				configuration.merge(eachReaderConfig, true);
				complete += 1;
			}
		}

		for (final String each : ConfigParser
				.getDirAsList(CoreConstant.FLINK_PLUGIN_WRITER_HOME)) {
			Configuration eachWriterConfig = ConfigParser.parseOnePluginConfig(each, "writer", replicaCheckPluginSet, wantPluginNames);
			if(eachWriterConfig!=null) {
				configuration.merge(eachWriterConfig, true);
				complete += 1;
			}
		}

		if (wantPluginNames != null && wantPluginNames.size() > 0 && wantPluginNames.size() != complete) {
			throw LarkMidTableException.asLarkMidTableException(FrameworkErrorCode.PLUGIN_INIT_ERROR, "插件加载失败，未完成指定插件加载:" + wantPluginNames);
		}

		return configuration;
	}

	public static Configuration parseJobConfig(final String path) {
		String jobContent = getJobContent(path);
		Configuration config = Configuration.from(jobContent);

		return config;
	}

	public static Configuration parseOnePluginConfig(final String path,
			final String type,
			Set<String> pluginSet, List<String> wantPluginNames) {
		String filePath = path + File.separator + "plugin.json";
		Configuration configuration = Configuration.from(new File(filePath));

		String pluginPath = configuration.getString("path");
		String pluginName = configuration.getString("name");
		if(!pluginSet.contains(pluginName)) {
			pluginSet.add(pluginName);
		} else {
			throw LarkMidTableException.asLarkMidTableException(FrameworkErrorCode.PLUGIN_INIT_ERROR, "插件加载失败,存在重复插件:" + filePath);
		}

		//不是想要的插件，返回null
		if (wantPluginNames != null && wantPluginNames.size() > 0 && !wantPluginNames.contains(pluginName)) {
			return null;
		}

		boolean isDefaultPath = StringUtils.isBlank(pluginPath);
		if (isDefaultPath) {
			configuration.set("path", path);
		}

		Configuration result = Configuration.newDefault();

		result.set(
				String.format("plugin.%s.%s", type, pluginName),
				configuration.getInternal());

		return result;
	}

	private static String getJobContent(String jobResource) {
		String jobContent;
		try {
			jobContent = FileUtils.readFileToString(new File(jobResource));
		} catch (IOException e) {
			throw LarkMidTableException
					.asLarkMidTableException(FrameworkErrorCode.CONFIG_ERROR, "获取作业配置信息失败:" + jobResource, e);
		}

		if (jobContent == null) {
			throw LarkMidTableException
					.asLarkMidTableException(FrameworkErrorCode.CONFIG_ERROR, "获取作业配置信息失败:" + jobResource);
		}
		return jobContent;
	}

	private static Configuration parseCoreConfig(final String path) {
		return Configuration.from(new File(path));
	}

	private static List<String> getDirAsList(String path) {
		List<String> result = new ArrayList<String>();

		String[] paths = new File(path).list();
		if (null == paths) {
			return result;
		}

		for (final String each : paths) {
			result.add(path + File.separator + each);
		}

		return result;
	}

}

package com.larkmidtable.cn.core;

import com.larkmidtable.cn.core.util.container.ConfigParser;
import com.larkmidtable.cn.core.util.container.ExceptionTracker;
import com.larkmidtable.cn.core.util.container.FrameworkErrorCode;
import com.larkmidtable.common.exception.LarkMidTableException;
import com.larkmidtable.common.spi.ErrorCode;
import com.larkmidtable.common.statistics.VMInfo;
import com.larkmidtable.common.util.Configuration;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 *
 * @Date: 2022/2/9 19:25
 * @Description:
 **/
public class Engine {
	private static final Logger LOG = LoggerFactory.getLogger(Engine.class);
	private static String RUNTIME_MODE;


	public static void main(String[] args) throws Exception {
		int exitCode = 0;
		try {
			Engine.entry(args);
		} catch (Throwable e) {
			exitCode = 1;
			LOG.error("\n\n经 LarkMidTable 智能分析,该任务最可能的错误原因是:\n" + ExceptionTracker.trace(e));
			if (e instanceof LarkMidTableException) {
				LarkMidTableException tempException = (LarkMidTableException) e;
				ErrorCode errorCode = tempException.getErrorCode();
				if (errorCode instanceof FrameworkErrorCode) {
					FrameworkErrorCode tempErrorCode = (FrameworkErrorCode) errorCode;
					exitCode = tempErrorCode.toExitValue();
				}
			}
			System.exit(exitCode);
		}
		System.exit(exitCode);
	}
	public static void entry(final String[] args) throws Throwable {
		// 1.解析参数
		Options options = new Options();
		options.addOption("job", true, "Job config.");
		options.addOption("mode", true, "Job runtime mode.");
		BasicParser parser = new BasicParser();
		CommandLine cl = parser.parse(options, args);
		String jobPath = cl.getOptionValue("job");
		RUNTIME_MODE = cl.getOptionValue("mode");
		Configuration configuration = ConfigParser.parse(jobPath);

		//打印vmInfo
		VMInfo vmInfo = VMInfo.getVmInfo();
		if (vmInfo != null) {
			LOG.info(vmInfo.toString());
		}

		Engine engine = new Engine();
		engine.start(configuration);
	}

	public void start(Configuration allConf) {

		//2.反射得到reader

		//3.反射得到writer

		//4.将流关闭

	}
}

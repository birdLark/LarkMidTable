package com.guoliang.flinkx.executor.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author maokeluo
 * @description 多隆镇楼，bug退散🙏🙏🙏
 * 系统工具
 * @date 2020/1/7
 */
public class SystemUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);

    private static String DATAX_HOME;

    private SystemUtils() {
    }

    /**
     * 获取环境变量中的Datax路径
     *
     * @return
     */
    public static String getDataXHomePath() {
        if (StringUtils.isNotEmpty(DATAX_HOME)) return DATAX_HOME;
        String dataXHome = System.getenv("DATAX_HOME");
        if (StringUtils.isBlank(dataXHome)) {
            LOGGER.warn("DATAX_HOME 环境变量为NULL");
            return null;
        }
        DATAX_HOME = dataXHome.endsWith(File.separator) ? dataXHome : dataXHome.concat(File.separator);
        LOGGER.info("DATAX_HOME:{}", DATAX_HOME);
        return DATAX_HOME;
    }

    public static  void executeShell(String command) {
		BufferedReader reader = null;
		try {
			command = "sh /home/hadoop/data/install.sh";
			System.out.println(command);
			Runtime runtime = Runtime.getRuntime();
			Process pro = runtime.exec(command);
			int status = pro.waitFor();
			if (status != 0) {
				System.out.println("Failed to call shell's command ");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			StringBuffer strbr = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				strbr.append(line).append("\n");
			}
			String result = strbr.toString();
			System.out.println(result);
		} catch (IOException ec) {
			ec.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}

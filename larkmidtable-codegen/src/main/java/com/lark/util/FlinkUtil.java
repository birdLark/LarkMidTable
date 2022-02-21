package com.lark.util;

/**
 *
 *
 * @Date: 2021/4/2 11:44
 * @Description:
 * @Author: LarkMidTable
 **/
public class FlinkUtil {

	//Flink's environment variables
	public static String streamExecuteEnv = "StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();";
	public static String envExecute ="env.execute(\"$className\");";
	//How Flink is submitted
	public static String standaloneCommit = "flink run -p 1 ";
	public static String yarnCommit = "flink run -m yarn-cluster -yn 1 -yjm 1024 -ytm 1024 ";

	public static String getEnvExecute(String className) {
		envExecute =  envExecute.replace("$className",className);
		return envExecute;
	}
}

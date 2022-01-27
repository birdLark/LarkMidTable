package com.lark.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 *
 *
 * @Date: 2022/1/15 10:45
 * @Description:
 * @Author: LarkMidTable
 **/
public class FlinkCDCSQL {

	public static void main(String[] args) throws Exception {
		// 1.设置流的环境
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//		// 2.设置并行度
		env.setParallelism(1);
		StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

		//2.创建 Flink-MySQL-CDC 的 Source
		tableEnv.executeSql("CREATE TABLE student2 (" +
				" id STRING," +
				" name STRING," +
				" address STRING" +
				") WITH (" +
				" 'connector' = 'mysql-cdc'," +
				" 'scan.startup.mode' = 'latest-offset'," +
				" 'scan.incremental.snapshot.enabled' = 'false'," +
				" 'hostname' = 'localhost'," +
				" 'port' = '3306'," +
				" 'username' = 'root'," +
				" 'password' = 'root'," +
				" 'database-name' = 'test'," +
				" 'table-name' = 'student'" +
				")");
		TableResult tableResult = tableEnv.executeSql("select * from student2");
		// FlinkSQL
		tableEnv.executeSql("CREATE TABLE output_kafka (\n"
				+ " id STRING,\n"
				+ " name STRING,\n"
				+ " address STRING\n"
				+ " )  WITH (\n"
				+ "  'connector' = 'kafka',\n"
				+ "  'topic' = 'user_behavior',\n"
				+ "  'properties.bootstrap.servers' = '192.168.1.204:9092',\n"
				+ "  'properties.group.id' = 'testGroup',\n"
				+ "  'scan.startup.mode' = 'earliest-offset',\n"
				+ "  'value.format' = 'debezium-json'\n"
				+ ")"
		);
		tableEnv.executeSql("insert into output_kafka select * from student2");  //从结果表查数据，转存到输出表
	}
}

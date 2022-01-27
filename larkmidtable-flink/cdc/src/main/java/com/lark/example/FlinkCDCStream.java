package com.lark.example;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import com.ververica.cdc.debezium.StringDebeziumDeserializationSchema;

/**
 *
 *
 * @Date: 2022/1/15 10:45
 * @Description:
 * @Author: LarkMidTable
 **/
public class FlinkCDCStream {

	public static void main(String[] args) throws Exception {
		// 1.设置流的环境
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//		// 2.设置并行度
//		env.setParallelism(1);
//		// 3.1状态信息保存到CK中，进行断点续传，从checkpoint和savepoint开始
//		env.enableCheckpointing(5000L);
//		// 3.2 设置仅一次的语义
//		env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
//		//3.3 设置任务关闭的时候保留最后一次 CK 数据
//		env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
//		//3.4 指定从 CK 自动重启策略
//		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 2000L));
//		//3.5 设置状态后端
//		env.setStateBackend(new FsStateBackend("hdfs://192.168.1.204:9000/flinkCDC"));
//		//3.6 设置访问 HDFS 的用户名
//		System.setProperty("HADOOP_USER_NAME", "hadoop");
		//4   获取mysql的数据源
		DebeziumSourceFunction<String> sourceFunction = MySqlSource.<String>builder()
				.hostname("localhost")
				.port(3306)
				.username("root")
				.password("root")
				.databaseList("test")
				.serverTimeZone("UTC")
				//                .tableList("cdc_test.user_info")
				.deserializer(new StringDebeziumDeserializationSchema())
				.startupOptions(StartupOptions.initial())
				.build();
		DataStreamSource<String> dataStreamSource = env.addSource(sourceFunction);

		//5.数据打印
		dataStreamSource.print();

		//6.启动任务
		env.execute("FlinkCDC");
	}
}

package com.lark.example;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.data.RowData;
import org.apache.iceberg.flink.TableLoader;
import org.apache.iceberg.flink.sink.FlinkSink;
import org.apache.iceberg.flink.source.FlinkSource;

/**
 *
 *
 * @Date: 2022/1/29 12:52
 * @Description:
 **/
public class FlinkIceBergStream {
	public static void main(String[] args) throws Exception{
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		TableLoader tableLoader = TableLoader.fromHadoopTable("");
		appendingData(env,tableLoader);
		env.execute();

	}

	public static  void appendingData(StreamExecutionEnvironment env,TableLoader tableLoader) {
		DataStream<RowData> batch = FlinkSource.forRowData().env(env).tableLoader(tableLoader).streaming(false).build();
		TableLoader table2 = TableLoader.fromHadoopTable("");
		FlinkSink.forRowData(batch).tableLoader(table2).build();
	}
}

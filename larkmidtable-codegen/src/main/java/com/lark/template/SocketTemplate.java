package com.lark.template;

import com.lark.util.FlinkUtil;

/**
 *
 *
 * @Date: 2021/3/31 15:16
 * @Description:
 * @Author: LarkMidTable
 **/
public class SocketTemplate {


	public static String getSocketCode() {
		String socketCode = "\t\t"+ FlinkUtil.streamExecuteEnv+"\n"
				+ "\t\tDataStream<Tuple2<String, Integer>> dataStream = env\n"
				+ "\t\t\t\t.socketTextStream(\"192.168.1.204\", 9000)\n"
				+ "\t\t\t\t.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {\n" + "\n"
				+ "\t\t\t\t\t@Override\n"
				+ "\t\t\t\t\tpublic void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {\n"
				+ "\t\t\t\t\t\tfor (String word: sentence.split(\" \")) {\n"
				+ "\t\t\t\t\t\t\tout.collect(new Tuple2<String, Integer>(word, 1));\n" + "\t\t\t\t\t\t}\n"
				+ "\t\t\t\t\t}\n" + "\t\t\t\t})\n" + "\t\t\t\t.keyBy(0)\n"
				+ "\t\t\t\t.timeWindow(Time.days(10),Time.seconds(5))\n" + "\t\t\t\t.sum(1);\n" + "\n"
				+ "\t\tdataStream.print();";
		return socketCode;
	}

	public String getMapFunction(){
		return "\tpublic static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {\n"
				+ "\t\t@Override\n"
				+ "\t\tpublic void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {\n"
				+ "\t\t\tfor (String word: sentence.split(\" \")) {\n"
				+ "\t\t\t\tout.collect(new Tuple2<String, Integer>(word, 1));\n"
				+ "\t\t\t}\n"
				+ "\t\t}\n";
	}
}

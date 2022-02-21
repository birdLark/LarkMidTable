package com.lark.util;

import com.lark.template.SocketTemplate;

/**
 *
 *
 * @Date: 2021/4/2 11:12
 * @Description:
 * @Author: LarkMidTable
 **/
public class ClassUtil {


	public static String classFrameworkCode = "public class $className {\n"
			+ "\tpublic static void main(String[] args) throws Exception {\n"
			+ "$body"
			+ "\n" + "\t\t"+FlinkUtil.getEnvExecute("$className")
			+ "\n\t}\n"
			+ "}";




	public static String getClassCode(String className,String bodyCode){
		String classFrame = classFrameworkCode.replace("$className", className);
		String classCode = classFrame.replace("$body", bodyCode);
		return classCode;
	}

	public static String getImportCode() {
		String importCode =
				"package GenCode;\n"+
						"import org.apache.flink.api.common.functions.*;\n"
						+ "import org.apache.flink.api.java.tuple.Tuple;\n"
						+ "import org.apache.flink.api.java.tuple.Tuple2;\n"
						+ "import org.apache.flink.streaming.api.TimeCharacteristic;\n"
						+ "import org.apache.flink.streaming.api.datastream.DataStream;\n"
						+ "import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;\n"
						+ "import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;\n"
						+ "import org.apache.flink.streaming.api.functions.windowing.WindowFunction;\n"
						+ "import org.apache.flink.streaming.api.watermark.Watermark;\n"
						+ "import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;\n"
						+ "import org.apache.flink.streaming.api.windowing.time.Time;\n"
						+ "import org.apache.flink.streaming.api.windowing.windows.TimeWindow;\n"
						+ "import org.apache.flink.util.Collector;\n"
						+ "\n"
						+ "import javax.annotation.Nullable;\n"
						+ "import java.text.DateFormat;\n"
						+ "import java.text.SimpleDateFormat;\n"
						+ "import java.util.*;\n\n";
		return importCode;
	}


	public static String getInputCode(String inputType) {
		switch(inputType){
			case "socket" :
				break;
			case "" :
				break;
			default :
		}
		return SocketTemplate.getSocketCode();
	}

	public static String getOutputCode(String outputType) {
		switch(outputType){
			case "socket" :
				break;
			case "" :
				break;
			default :
		}
		return "";
	}

	public static String getTransferCode(String sql) {
//		switch(sql){
//			case "socket" :
//				break;
//			case "" :
//				break;
//			default :
//		}
		return "";
	}
}

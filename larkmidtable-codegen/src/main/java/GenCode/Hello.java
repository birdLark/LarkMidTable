package GenCode;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Hello {
	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		DataStream<Tuple2<String, Integer>> dataStream = env
				.socketTextStream("192.168.1.204", 9000)
				.flatMap(new FlatMapFunction<String, Tuple2<String,Integer>>() {

					@Override
					public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
						for (String word: sentence.split(" ")) {
							out.collect(new Tuple2<String, Integer>(word, 1));
						}
					}
				})
				.keyBy(0)
				.timeWindow(Time.days(10),Time.seconds(5))
				.sum(1);

		dataStream.print();
		env.execute("Hello");
	}
}
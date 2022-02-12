package com.larkmidtable.cn.core;

import com.larkmidtable.cn.core.util.container.ExceptionTracker;
import com.larkmidtable.cn.core.util.container.FrameworkErrorCode;
import com.larkmidtable.cn.core.util.container.JarLoader;
import com.larkmidtable.common.exception.LarkMidTableException;
import com.larkmidtable.common.spi.ErrorCode;
import com.larkmidtable.common.statistics.VMInfo;
import com.larkmidtable.common.util.Configuration;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


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
		Configuration configuration = null;
		//		Configuration configuration = ConfigParser.parse(jobPath);

		//打印vmInfo
		VMInfo vmInfo = VMInfo.getVmInfo();
		if (vmInfo != null) {
			LOG.info(vmInfo.toString());
		}

		Engine engine = new Engine();
		engine.start(configuration);
	}

	public void test() {
		System.out.println("haha");
	}

	public void start(Configuration allConf) throws Exception {

		/*JDBCInputFormat input = new JDBCInputFormat.JDBCInputFormatBuilder()
				.setDrivername("com.mysql.cj.jdbc.Driver")
				.setDBUrl("jdbc:mysql://localhost:3306/test?user=root&password=root&serverTimezone=UTC")
				.setQuery("select * from student")
				//设置获取的数据的类型
				.setRowTypeInfo(new RowTypeInfo(BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO))
				.finish();

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSource<Row> input1 = env.createInput(input);

		input1.map(new MapFunction<Row, String>() {
			@Override
			public String map(Row row) throws Exception {
				return row.toString();
			}
		}).print();*/
	}
}

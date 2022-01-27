package com.lark.cn.sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.SqlParserException;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import com.lark.cn.common.SqlCommandParser;
import com.lark.cn.common.SqlCommandParser.SqlCommandCall;

/**
 *
 * @author fei
 * @date 2022-01-17
 * @version flink 1.10
 **/
public class StreamJobExecutor {

	private StreamTableEnvironment streamTableEnvironment;
	private StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

	public StreamJobExecutor() {
		init();
	}

	private void init() {
		streamTableEnvironment = StreamTableEnvironment.create(env);
	}

	public void invoke(String sql) {
		if (streamTableEnvironment == null) {
			throw new RuntimeException("StreamTableEnvironment is not init");
		}

		if (StringUtils.isEmpty(sql) || sql.split(";").length < 1) {
			throw new RuntimeException("SQL is not standard");
		}
		List<String> sqlSegment = Arrays.asList(sql.split(";"));

		for (String segment : sqlSegment) {
			Optional<SqlCommandCall> call = SqlCommandParser.parse(segment);
			callCommand(call.get());
		}
		try {
			env.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void callCommand(SqlCommandParser.SqlCommandCall cmdCall) {
		switch (cmdCall.command) {
		case SELECT:
			callSelect(cmdCall);
			break;
		case CREATE_TABLE:
			callCreateTable(cmdCall);
			break;
		case INSERT_INTO:
			callInsertInto(cmdCall);
			break;
		default:
			throw new RuntimeException("Unsupported command: " + cmdCall.command);
		}
	}

	private void callCreateTable(SqlCommandParser.SqlCommandCall cmdCall) {
		String ddl = cmdCall.operands[0];
		try {
			streamTableEnvironment.sqlUpdate(ddl);
		} catch (SqlParserException e) {
			throw new RuntimeException("SQL parse failed:\n" + ddl + "\n", e);
		}
	}

	private void callInsertInto(SqlCommandParser.SqlCommandCall cmdCall) {
		String dml = cmdCall.operands[0];
		try {
			streamTableEnvironment.sqlUpdate(dml);
		} catch (SqlParserException e) {
			throw new RuntimeException("SQL parse failed:\n" + dml + "\n", e);
		}
	}

	private void callSelect(SqlCommandParser.SqlCommandCall cmdCall) {
		String dql = cmdCall.operands[0];
		try {
			Table table = streamTableEnvironment.sqlQuery(dql);
			table.printSchema();
		} catch (SqlParserException e) {
			throw new RuntimeException("SQL parse failed:\n" + dql + "\n", e);
		}
	}

}

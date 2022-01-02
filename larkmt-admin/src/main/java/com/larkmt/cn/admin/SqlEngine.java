package com.larkmt.cn.admin;

import com.larkmt.cn.executor.sql.SqlExecutor;
import com.larkmt.cn.executor.util.SystemUtils;
import com.larkmt.cn.util.SqlCommandParser;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Date: 2022/1/3 6:28
 * @Description:
 * @Author: LarkMidTable
 * 后台应用: 读取Flink的SQL文件
 **/
public class SqlEngine {
	private static TableEnvironment tEnv;
	private SqlExecutor sqlExecutor = new SqlExecutor();

	public static void main(String[] args) {
		SqlEngine sqlEngine = new SqlEngine();
		try {
			sqlEngine.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {
		EnvironmentSettings settings = EnvironmentSettings.newInstance()
				.useBlinkPlanner()
				.inStreamingMode()
				.build();
		this.tEnv = TableEnvironment.create(settings);
		List<String> sql = Files.readAllLines(Paths.get(SystemUtils.getFlinkxHomePath()));
		List<SqlCommandParser.SqlCommandCall> calls = SqlCommandParser.parse(sql);
		for (SqlCommandParser.SqlCommandCall call : calls) {
			sqlExecutor.callCommand(call,tEnv);
		}
		tEnv.execute("SQL Job");
	}
}

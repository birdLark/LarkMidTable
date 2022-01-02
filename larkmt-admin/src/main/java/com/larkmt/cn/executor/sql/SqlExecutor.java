package com.larkmt.cn.executor.sql;

import com.larkmt.cn.util.SqlCommandParser;
import org.apache.flink.table.api.SqlParserException;
import org.apache.flink.table.api.TableEnvironment;

/**
 * @Date: 2022/1/3 6:56
 * @Description:
 * @Author: LarkMidTable
 **/
public class SqlExecutor {
	public void callCommand(SqlCommandParser.SqlCommandCall cmdCall, TableEnvironment tEnv) {
		switch (cmdCall.command) {
			case SET:
				callSet(cmdCall,tEnv);
				break;
			case CREATE_TABLE:
				callCreateTable(cmdCall,tEnv);
				break;
			case INSERT_INTO:
				callInsertInto(cmdCall,tEnv);
				break;
			default:
				throw new RuntimeException("Unsupported command: " + cmdCall.command);
		}
	}

	private void callSet(SqlCommandParser.SqlCommandCall cmdCall, TableEnvironment tEnv) {
		String key = cmdCall.operands[0];
		String value = cmdCall.operands[1];
		tEnv.getConfig().getConfiguration().setString(key, value);
	}

	private void callCreateTable(SqlCommandParser.SqlCommandCall cmdCall, TableEnvironment tEnv) {
		String ddl = cmdCall.operands[0];
		try {
			tEnv.sqlUpdate(ddl);
		} catch (SqlParserException e) {
			throw new RuntimeException("SQL parse failed:\n" + ddl + "\n", e);
		}
	}

	private void callInsertInto(SqlCommandParser.SqlCommandCall cmdCall, TableEnvironment tEnv) {
		String dml = cmdCall.operands[0];
		try {
			tEnv.sqlUpdate(dml);
		} catch (SqlParserException e) {
			throw new RuntimeException("SQL parse failed:\n" + dml + "\n", e);
		}
	}
}

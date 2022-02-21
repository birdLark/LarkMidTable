package com.lark.util;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @Date: 2021/4/7 17:03
 * @Description:
 * @Author: LarkMidTable
 **/
public class SQLUtil {
	private SQLStatement statement ;
	private MySqlSchemaStatVisitor visitor;

	public SQLUtil(String sql) {
		String dbType = JdbcConstants.MYSQL;
		statement = SQLUtils.parseStatements(sql, dbType).get(0);
		visitor = new MySqlSchemaStatVisitor();
		statement.accept(visitor);
	}

	public String getSearchType(){
		return visitor.getCurrentTable();
	}

	public String getInsertType(String sql) {
		return sql.substring(sql.toLowerCase().indexOf("into")+4,sql.toLowerCase().indexOf("(")).trim();

	}
}

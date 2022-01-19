package com.lark.cn.sql;

/**
 *
 * @author fei
 * @date 2022-01-18
 * 
 **/
public class Main {
	public static void main(String[] args) {

		StreamJobExecutor exe = new StreamJobExecutor();
		String splitOper = ";";
		String sql = MysqlSampleSql.mysql_source + splitOper + MysqlSampleSql.mysql_select + splitOper
				+ MysqlSampleSql.mysql_sink + splitOper + MysqlSampleSql.msyql_insert + splitOper;
		System.out.println(sql);
		exe.invoke(sql);

	}
}

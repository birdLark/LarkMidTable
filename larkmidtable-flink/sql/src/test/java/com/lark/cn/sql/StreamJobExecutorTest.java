package com.lark.cn.sql;
/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
public class StreamJobExecutorTest {
	
	public static void main(String[] args) {

		StreamJobExecutor exe = new StreamJobExecutor();
		String splitOper = ";";
		String sql = MysqlDemoSql.MYSQL_SOURCE + splitOper + MysqlDemoSql.MYSQL_SELECT + splitOper
				+ MysqlDemoSql.MYSQL_SINK + splitOper + MysqlDemoSql.MSYQL_INSERT + splitOper;
		System.out.println(sql);
		exe.invoke(sql);

	}

}

package com.larkmidtable.admin.service.impl;

import java.sql.*;

/**
 *
 *
 * @Date: 2022/1/27 9:04
 * @Description:
 **/
public class APIDBQueryServiceImpl {
	private static  Connection conn = null;//表示数据库连接的对象
	private static  Statement stmt = null;//表示数据库更新操作
	private static ResultSet result = null;//表示接受数据库查询到的结果

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		//组装SQL
		//执行查询返回结果
		APIDBQueryServiceImpl.getResult("select * from student","mysql");
	}

	public static   ResultSet getResult(String sql,String type)  throws ClassNotFoundException, SQLException  {
		Class.forName("com.mysql.jdbc.Driver");//使用class类加载驱动程序
		switch (type) {
			case "mysql" :
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
				break;
			case "postgresql" :
				//语句
				conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
				break;
			default :
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		}

		stmt = conn.createStatement();

		result = stmt.executeQuery(sql);

		while(result.next()){
			String name = result.getString("name");
			System.out.println(name);
		}
		result.close();
		stmt.close();
		conn.close();
		return result;
	}
}

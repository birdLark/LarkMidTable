package com.wugui.flinkx;

import com.guoliang.flinkx.admin.util.AESUtil;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Test {
	public static void main(String[] args) {
		//这里默认使用 hikari 数据源
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setJdbcUrl("jdbc:mysql://192.168.1.204:3306/flinkx_web");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setMaximumPoolSize(1);
		dataSource.setMinimumIdle(0);
		dataSource.setConnectionTimeout(30000);
		DataSource datasource2 = dataSource;
		try {
			datasource2.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

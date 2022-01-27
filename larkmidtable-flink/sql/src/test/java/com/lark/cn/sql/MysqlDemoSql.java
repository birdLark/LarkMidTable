package com.lark.cn.sql;
/**
 *
 * @author fei
 * @date 2022-01-18
 * 
 **/
public interface MysqlDemoSql {
	String MYSQL_SOURCE = "CREATE TABLE city1 ( "
			+ "NAME STRING, "
			+ "ID INT,"
			+ "COUNTRYCODE STRING,"
			+ "DISTRICT STRING"
			+ " ) "
			+ "WITH ("
			+ "'connector.type' = 'jdbc',"
			+ "'connector.driver' = 'com.mysql.cj.jdbc.Driver',"
			+ "'connector.url' = 'jdbc:mysql://localhost:3306/world?tinyInt1isBit=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true',"
			+ "'connector.table' = 'city',"
			+ "'connector.username' = 'root',"
			+ "'connector.password' = '123abc'"
			+ ")";
	
	String MYSQL_SELECT = "SELECT ID,NAME from city1";
	
	String MYSQL_SINK = "CREATE TABLE city2 ( "
			+ "NAME STRING, "
			+ "ID INT,"
			+ "COUNTRYCODE STRING,"
			+ "DISTRICT STRING"
			+ " ) "
			+ "WITH ("
			+ "'connector.type' = 'jdbc',"
			+ "'connector.driver' = 'com.mysql.cj.jdbc.Driver',"
			+ "'connector.url' = 'jdbc:mysql://localhost:3306/world?tinyInt1isBit=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true',"
			+ "'connector.table' = 'city2',"
			+ "'connector.username' = 'root',"
			+ "'connector.password' = '123abc'"
			+ ")";
	
	String MSYQL_INSERT = "INSERT INTO city2 select * from city1";
	
	
}

package com.lark.cn.sql;
/**
 *
 * @author fei
 * @date 2022-01-18
 * 
 **/
public interface MysqlSampleSql {
	String mysql_source = "CREATE TABLE city1 ( "
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
	
	String mysql_select = "SELECT ID,NAME from city1";
	
	String mysql_sink = "CREATE TABLE city2 ( "
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
	
	String msyql_insert = "INSERT INTO city2 select * from city1";
	
	
}

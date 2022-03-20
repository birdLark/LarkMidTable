package com.larkmidtable.admin.service;
/**
 *
 * @author fei
 * @date 2022-01-18
 * 
 **/
public interface MysqlDemo {
	String MYSQL_SOURCE = "CREATE TABLE city1 ( "
			+ "NAME STRING, "
			+ "ID INT,"
			+ "COUNTRYCODE STRING,"
			+ "DISTRICT STRING"
			+ " ) "
			+ "WITH ("
			+ "'connector.type' = 'jdbc',"
			+ "'connector.driver' = 'com.mysql.cj.jdbc.Driver',"
			+ "'connector.url' = 'jdbc:mysql://192.168.220.248:3306/world?tinyInt1isBit=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true',"
			+ "'connector.table' = 'city1',"
			+ "'connector.username' = '111',"
			+ "'connector.password' = '111'"
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
			+ "'connector.url' = 'jdbc:mysql://192.168.220.12:3306/world?tinyInt1isBit=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true',"
			+ "'connector.table' = 'city2',"
			+ "'connector.username' = '111',"
			+ "'connector.password' = '111'"
			+ ")";
	
	String MSYQL_INSERT = "INSERT INTO city2 select * from city1";
	
	
}

package com.larkmidtable.admin.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.larkmidtable.admin.entity.DevTask;

/**
 *
 * @author fei
 * @date 2022-03-20
 * 
 **/
@SpringBootTest
public class DevSQLTest implements MysqlDemo {
	private static final String SEG = ";";

	@Autowired
	private DevSQLService devSQLService;

	@Test
	public void exeFlinkSql() {
		DevTask devTask = new DevTask();

		String sql = MYSQL_SOURCE + SEG + MYSQL_SINK + SEG + MSYQL_INSERT;
		devTask.setSql_text(sql);

		devSQLService.exeFlinkSql(devTask);
	}
}

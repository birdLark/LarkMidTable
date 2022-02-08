package com.larkmidtable.admin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.larkmidtable.admin.dto.FlinkSqlDto;

/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlinkSqlTest {
	
	@Autowired
	private FlinkSqlService flinkSqlService;
	
	@Test
	public void test1() {
		System.out.println("test start");
		FlinkSqlDto flinkSqlDto = new FlinkSqlDto();
		String splitOper = ";";
		String sql = MysqlDemo.MYSQL_SOURCE + splitOper + MysqlDemo.MYSQL_SELECT + splitOper
				+ MysqlDemo.MYSQL_SINK + splitOper + MysqlDemo.MSYQL_INSERT + splitOper;
		
		flinkSqlDto.setSqlStr(sql);
		flinkSqlService.exeFlinkSql(flinkSqlDto);
	}
}

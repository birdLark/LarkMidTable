package com.larkmidtable.admin.service;

import com.larkmidtable.admin.dto.FlinkSqlDto;

/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
public interface FlinkSqlService {

	/**
	 * flink sql 执行
	 * 
	 * @param flinkSqlDto
	 */
	void exeFlinkSql(FlinkSqlDto flinkSqlDto);
}

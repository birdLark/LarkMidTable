package com.larkmidtable.admin.service.impl;

import org.springframework.stereotype.Service;

import com.larkmidtable.admin.dto.FlinkSqlDto;
import com.larkmidtable.admin.service.FlinkSqlService;

/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
@Service
public class FlinkSqlServiceImpl implements FlinkSqlService {

	@Override
	public void exeFlinkSql(FlinkSqlDto flinkSqlDto) {
//		StreamJobExecutor exe = new StreamJobExecutor();
//		// TODO 对sql做格式校验
//		exe.invoke(flinkSqlDto.getSqlStr());
	}

}

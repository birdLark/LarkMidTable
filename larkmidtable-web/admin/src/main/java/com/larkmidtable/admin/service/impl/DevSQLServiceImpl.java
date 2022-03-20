package com.larkmidtable.admin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lark.cn.sql.StreamJobExecutor;
import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.mapper.DevSQLMapper;
import com.larkmidtable.admin.service.DevSQLService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("devSQLService")
public class DevSQLServiceImpl extends ServiceImpl<DevSQLMapper, DevTask> implements DevSQLService {

	@Override
	public void exeFlinkSql(DevTask entity) {
		StreamJobExecutor streamJobExecutor = new StreamJobExecutor();
		String sql = entity.getSql_text();
		log.info("执行的 Flink SQL : {}", sql);
		streamJobExecutor.invoke(sql);
	}

}

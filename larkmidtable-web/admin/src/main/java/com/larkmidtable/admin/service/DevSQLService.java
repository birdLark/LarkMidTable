package com.larkmidtable.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.larkmidtable.admin.entity.DevTask;

public interface DevSQLService extends IService<DevTask> {
	/**
	 * 执行Flink SQL
	 * 
	 * @param entity
	 */
	void exeFlinkSql(DevTask entity);

}

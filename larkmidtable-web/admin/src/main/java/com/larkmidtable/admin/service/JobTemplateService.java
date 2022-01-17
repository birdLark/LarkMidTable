package com.larkmidtable.admin.service;


import com.larkmidtable.core.biz.model.ReturnT;
import com.larkmidtable.admin.entity.JobTemplate;

import java.util.Map;

/**
 * core job action for flinkx-web
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface JobTemplateService {

	/**
	 * page list
	 *
	 * @param start
	 * @param length
	 * @param jobGroup
	 * @param jobDesc
	 * @param executorHandler
	 * @param userId
	 * @return
	 */
	Map<String, Object> pageList(int start, int length, int jobGroup, String jobDesc, String executorHandler, int userId,Integer[] projectIds);
	/**
	 * add job
	 *
	 * @param jobTemplate
	 * @return
	 */
	ReturnT<String> add(JobTemplate jobTemplate);

	/**
	 * update job
	 *
	 * @param jobTemplate
	 * @return
	 */
	ReturnT<String> update(JobTemplate jobTemplate);

	/**
	 * remove job
	 * 	 *
	 * @param id
	 * @return
	 */
	ReturnT<String> remove(int id);
}

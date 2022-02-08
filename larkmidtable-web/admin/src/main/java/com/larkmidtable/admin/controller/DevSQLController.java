package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.entity.APIAuth;
import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.mapper.DevJarMapper;
import com.larkmidtable.admin.mapper.DevSQLMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description: 数据开发-SQL作业开发
 *
 * */
@RestController
@RequestMapping("/api/devSQL")
@Api(tags = "数据开发JAR方式的增删改查")
public class DevSQLController extends BaseController {

	@Resource
	private DevSQLMapper devSQLMapper;

	@ApiOperation("新增数据")
	@PostMapping("/add")
	public ReturnT<String> insert(HttpServletRequest request, @RequestBody DevTask entity) {
		entity.setCreate_time(new Date().toString());
		this.devSQLMapper.save(entity);
		return ReturnT.SUCCESS;
	}

	@ApiOperation("修改数据")
	@PostMapping(value = "/update")
	public ReturnT<String> update(@RequestBody DevTask entity) {
		entity.setUpdate_time(new Date().toString());
		devSQLMapper.update(entity);
		return ReturnT.SUCCESS;
	}
}

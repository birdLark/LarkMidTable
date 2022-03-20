package com.larkmidtable.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.mapper.DevSQLMapper;
import com.larkmidtable.admin.service.DevSQLService;
import com.larkmidtable.core.biz.model.ReturnT;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/devSQL")
@Api(tags = "数据开发-SQL作业开发")
public class DevSQLController extends BaseController {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private DevSQLMapper devSQLMapper;

	@Autowired
	private DevSQLService devSQLService;

	@ApiOperation(value = "Flink SQL 执行器")
	@PostMapping("/exeFlinkSql")
	public R<String> exeFlinkSql(@RequestBody DevTask entity) {
		devSQLService.exeFlinkSql(entity);
		return R.ok("执行成功");
	}

	@ApiOperation("新增数据")
	@PostMapping("/add")
	public ReturnT<String> insert(HttpServletRequest request, @RequestBody DevTask entity) {
		entity.setCreate_time(sdf.format(new Date()));
		this.devSQLMapper.save(entity);
		return ReturnT.SUCCESS;
	}

	@ApiOperation("修改数据")
	@PostMapping(value = "/update")
	public ReturnT<String> update(@RequestBody DevTask entity) {
		entity.setUpdate_time(sdf.format(new Date()));
		devSQLMapper.update(entity);
		return ReturnT.SUCCESS;
	}
}

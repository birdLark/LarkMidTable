package com.larkmidtable.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.dto.FlinkSqlDto;
import com.larkmidtable.admin.service.FlinkSqlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
@Api(tags = { "Flink SQL 控制器" })
@RestController
@RequestMapping("/fs")
public class FlinkSqlController extends BaseController {

	@Autowired
	private FlinkSqlService flinkSqlService;

	@ApiOperation(value = "Flink SQL 执行器")
	@PostMapping("/exeFlinkSql")
	public R<String> exeFlinkSql(@RequestBody FlinkSqlDto flinkSqlDto) {
		flinkSqlService.exeFlinkSql(flinkSqlDto);
		return R.ok("执行成功");
	}

}

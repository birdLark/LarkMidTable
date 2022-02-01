package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.mapper.DevJarMapper;
import com.larkmidtable.admin.mapper.DevSQLMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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


	// 文件上传

}

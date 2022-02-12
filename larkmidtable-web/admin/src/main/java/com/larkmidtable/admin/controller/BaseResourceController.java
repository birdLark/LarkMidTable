package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.entity.BaseResource;
import com.larkmidtable.admin.mapper.BaseResourceMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/base/resource")
@Api(tags = "基础建设-资源管理")
public class BaseResourceController {

	@Resource
	private BaseResourceMapper baseResourceMapper;

	@ApiOperation("获取所有数据")
	@GetMapping("/list")
	public ReturnT<Map<String, Object>> selectList(
			@RequestParam(value = "current", required = false, defaultValue = "1") int current,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "name", required = false) String name) {
		// page list
		List<BaseResource> list = baseResourceMapper.findList((current - 1) * size,size,name);
		Map<String, Object> maps = new HashMap<>();
		maps.put("recordsFiltered", list.size());    // 过滤后的总记录数
		maps.put("data", list);                    // 分页列表
		return new ReturnT<>(maps);
	}

	@ApiOperation("新增数据")
	@PostMapping("/add")
	public ReturnT<String> insert(HttpServletRequest request, @RequestBody BaseResource entity) {
		entity.setUpdate_time(new Date().toString());
		this.baseResourceMapper.save(entity);
		return ReturnT.SUCCESS;
	}

	@ApiOperation("修改数据")
	@PostMapping(value = "/update")
	public ReturnT<String> update(@RequestBody BaseResource entity) {
		entity.setUpdate_time(new Date().toString());
		baseResourceMapper.update(entity);
		return ReturnT.SUCCESS;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ApiOperation("删除数据")
	public ReturnT<String> delete(int id) {
		int result = baseResourceMapper.delete(id);
		return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
	}
}

package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.entity.APIAuth;
import com.larkmidtable.admin.mapper.APIAuthMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/apiAuth")
@Api(tags = "数据服务- 权限控制")
public class APIAuthController extends BaseController {

	@Resource
	private APIAuthMapper apiAuthMapper;

    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    public ReturnT<List<APIAuth>> selectList() {
		// page list
		List<APIAuth> list = apiAuthMapper.findAll();
		return new ReturnT<> (list);
    }

    @ApiOperation("新增数据")
	@PostMapping("/add")
    public ReturnT<String> insert(HttpServletRequest request, @RequestBody APIAuth entity) {
		entity.setUpdate_time(new Date().toString());
        this.apiAuthMapper.save(entity);
		return ReturnT.SUCCESS;
    }

    @ApiOperation("修改数据")
	@PostMapping(value = "/update")
    public ReturnT<String> update(@RequestBody APIAuth entity) {
		APIAuth project = apiAuthMapper.getById(entity.getId());
		project.setGroup_id(entity.getGroup_id());
		project.setToken_id(entity.getToken_id());
        project.setUpdate_time(new Date().toString());
		apiAuthMapper.update(project);
		return ReturnT.SUCCESS;
    }

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation("删除数据")
    public ReturnT<String> delete(int id) {
		int result = apiAuthMapper.delete(id);
		return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
    }
}

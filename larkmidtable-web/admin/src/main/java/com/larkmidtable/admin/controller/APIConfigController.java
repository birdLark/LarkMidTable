package com.larkmidtable.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.entity.APIAuth;
import com.larkmidtable.admin.entity.APIConfig;
import com.larkmidtable.admin.mapper.APIAuthMapper;
import com.larkmidtable.admin.mapper.APIConfigMapper;
import com.larkmidtable.admin.service.APIConfigService;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Author:
 * @Date:
 * @Description:
 **/
@RestController
@RequestMapping("/api/apiConfig")
@Api(tags = "OpenAPI的操作")
public class APIConfigController extends BaseController {

    @Autowired
    private APIConfigService apiConfigService;

	@Resource
	private APIConfigMapper apiConfigMapper;


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("分页查询所有数据")
    public R<IPage<APIConfig>> selectAll(@RequestParam(value = "searchVal", required = false) String searchVal,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNo") Integer pageNo) {

        return success(apiConfigService.getAPIConfigListPaging(pageSize, pageNo, searchVal));
    }

    /**
     * Get all project
     *
     * @return
     */
	@ApiOperation("获取所有数据")
	@GetMapping("/list")
	public ReturnT<List<APIConfig>> selectList() {
		// page list
		List<APIConfig> list = apiConfigMapper.findAll();
		return new ReturnT<> (list);
	}

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public R<APIConfig> selectOne(@PathVariable Serializable id) {
        return success(this.apiConfigService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param entity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
	@PostMapping("/add")
    public ReturnT<String> insert(HttpServletRequest request, @RequestBody APIConfig entity) {
		entity.setName(entity.getName());
		entity.setParams(entity.getParams());
		entity.setCreate_time(new Date().toString());
		entity.setDatasource_id(entity.getDatasource_id());
		entity.setDescribe(entity.getDescribe());
		entity.setPath(entity.getPath());
		entity.setGroup_id(entity.getGroup_id());
        this.apiConfigMapper.save(entity);
		return ReturnT.SUCCESS;
    }

    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改数据")
	@PostMapping(value = "/update")
	public ReturnT<String> update(@RequestBody APIConfig entity) {
		APIConfig project = apiConfigMapper.getById(entity.getId());
        project.setName(entity.getName());
        project.setParams(entity.getParams());
        project.setUpdate_time(new Date().toString());
        project.setDatasource_id(entity.getDatasource_id());
        project.setDescribe(entity.getDescribe());
        project.setPath(entity.getPath());
        project.setGroup_id(entity.getGroup_id());
		apiConfigMapper.update(project);
		return ReturnT.SUCCESS;
    }

    /**
     * 删除数据
     *
     * @param id 主键结合
     * @return 删除结果
     */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ApiOperation("删除数据")
	public ReturnT<String> delete(int id) {
		int result = apiConfigMapper.delete(id);
		return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
	}
}

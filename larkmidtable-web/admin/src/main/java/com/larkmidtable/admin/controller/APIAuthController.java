package com.larkmidtable.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.entity.APIAuth;
import com.larkmidtable.admin.service.APIAuthService;
import com.larkmidtable.admin.service.APIAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/apiAuth")
@Api(tags = "OpenAPI的权限操作")
public class APIAuthController extends BaseController {

    @Autowired
    private APIAuthService apiAuthService;


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("分页查询所有数据")
    public R<IPage<APIAuth>> selectAll(@RequestParam(value = "searchVal", required = false) String searchVal,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNo") Integer pageNo) {

        return success(apiAuthService.getAPIConfigListPaging(pageSize, pageNo, searchVal));
    }

    /**
     * Get all project
     *
     * @return
     */
    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    public R<List<APIAuth>> selectList() {
        QueryWrapper<APIAuth> query = new QueryWrapper();
        query.eq("flag", true);
        return success(apiAuthService.list(query));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public R<APIAuth> selectOne(@PathVariable Serializable id) {
        return success(this.apiAuthService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param entity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public R<Boolean> insert(HttpServletRequest request, @RequestBody APIAuth entity) {
        entity.setGroup_id(entity.getGroup_id());
        entity.setToken_id(entity.getToken_id());
		entity.setUpdateTime(new Date().toString());
        return success(this.apiAuthService.save(entity));
    }

    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改数据")
    public R<Boolean> update(@RequestBody APIAuth entity) {
		APIAuth project = apiAuthService.getById(entity.getId());
		project.setGroup_id(entity.getGroup_id());
		project.setToken_id(entity.getToken_id());
        project.setUpdateTime(new Date().toString());
        return success(this.apiAuthService.updateById(entity));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除数据")
    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return success(this.apiAuthService.removeByIds(idList));
    }
}

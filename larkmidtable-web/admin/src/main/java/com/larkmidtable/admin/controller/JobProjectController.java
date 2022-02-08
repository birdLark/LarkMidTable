package com.larkmidtable.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.service.JobProjectService;
import com.larkmidtable.admin.entity.JobProject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/jobProject")
@Api(tags = "数据集成-项目管理模块")
public class JobProjectController extends BaseController {

    @Autowired
    private JobProjectService jobProjectService;

    @GetMapping
    @ApiOperation("分页查询所有数据")
    public R<IPage<JobProject>> selectAll(@RequestParam(value = "searchVal", required = false) String searchVal,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNo") Integer pageNo) {

        return success(jobProjectService.getProjectListPaging(pageSize, pageNo, searchVal));
    }

    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    public R<List<JobProject>> selectList() {
        QueryWrapper<JobProject> query = new QueryWrapper();
        query.eq("flag", true);
        return success(jobProjectService.list(query));
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public R<JobProject> selectOne(@PathVariable Serializable id) {
        return success(this.jobProjectService.getById(id));
    }

    @ApiOperation("新增数据")
    @PostMapping
    public R<Boolean> insert(HttpServletRequest request, @RequestBody JobProject entity) {
        entity.setUserId(getCurrentUserId(request));
        entity.setCreateTime(new Date());
        return success(this.jobProjectService.save(entity));
    }

    @PutMapping
    @ApiOperation("修改数据")
    public R<Boolean> update(@RequestBody JobProject entity) {
        JobProject project = jobProjectService.getById(entity.getId());
        project.setName(entity.getName());
        project.setDescription(entity.getDescription());
        project.setUpdateTime(new Date());
        return success(this.jobProjectService.updateById(entity));
    }

    @DeleteMapping
    @ApiOperation("删除数据")
    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return success(this.jobProjectService.removeByIds(idList));
    }
}

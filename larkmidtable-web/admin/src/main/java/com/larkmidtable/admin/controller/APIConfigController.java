package com.larkmidtable.admin.controller;

import com.alibaba.fastjson.JSON;
import com.larkmidtable.admin.entity.APIConfig;
import com.larkmidtable.admin.entity.JobDatasource;
import com.larkmidtable.admin.entity.ResponseData;
import com.larkmidtable.admin.mapper.APIAuthMapper;
import com.larkmidtable.admin.mapper.APIConfigMapper;
import com.larkmidtable.admin.service.JobDatasourceService;
import com.larkmidtable.admin.util.DruidDataSource;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/apiConfig")
@Api(tags = "数据服务-可视化API构建")
public class APIConfigController extends BaseController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private JobDatasourceService jobJdbcDatasourceService;

    @Resource
    private APIConfigMapper apiConfigMapper;

    @Resource
    private APIAuthMapper apiAuthMapper;


    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    public ReturnT<Map<String, Object>> selectList(@RequestParam(value = "current", required = false, defaultValue = "1") int current,
                                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                                   @RequestParam(value = "name", required = false) String name) {
        // page list
        List<APIConfig> list = apiConfigMapper.findList((current - 1) * size, size, name);
        Map<String, Object> maps = new HashMap<>();
        maps.put("recordsTotal", list.size());    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return new ReturnT<>(maps);
    }

    @ApiOperation("获取下拉框的")
    @GetMapping("/listDataSourceName")
    public ReturnT<Map<String, Object>> findDataSourceName() {
        // page list
        List<JobDatasource> list = apiConfigMapper.findDataSourceName();
        Map<String, Object> maps = new HashMap<>();
        maps.put("data", list);
        return new ReturnT<>(maps);
    }

    @ApiOperation("新增数据")
    @PostMapping("/add")
    public ReturnT<String> insert(HttpServletRequest request, @RequestBody APIConfig entity) {
        entity.setCreate_time(new Date().toString());
        this.apiConfigMapper.save(entity);
        return ReturnT.SUCCESS;
    }

    @ApiOperation("修改数据")
    @PostMapping(value = "/update")
    public ReturnT<String> update(@RequestBody APIConfig entity) {
        APIConfig project = apiConfigMapper.getById(entity.getId());
        project.setName(entity.getName());
        project.setParams(entity.getParams());
        project.setUpdate_time(sdf.format(new Date()));
        project.setDatasource_id(entity.getDatasource_id());
        project.setDescribe(entity.getDescribe());
        project.setPath(entity.getPath());
        project.setGroup_id(entity.getGroup_id());
        project.setSql_text(entity.getSql_text());
        apiConfigMapper.update(project);
        return ReturnT.SUCCESS;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation("删除数据")
    public ReturnT<String> delete(int id) {
        int result = apiConfigMapper.delete(id);
        return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
    }


    @PostMapping(value = "/execute")
    @ApiOperation("执行API的查询")
    public ResponseData executeSql(@RequestBody APIConfig apiConfig) {
        try {
            if (apiConfig.getId() == 0) {
                String params = apiConfig.getParams();
                String datasourceId = apiConfig.getDatasource_id();
                String sql_text = apiConfig.getSql_text();
                Map<String, Object> paramsMap = JSON.parseObject(params, LinkedHashMap.class);

                JobDatasource datasource = this.jobJdbcDatasourceService.getById(datasourceId);
                Object result = DruidDataSource.executeSql(datasource, sql_text, paramsMap);
                return ResponseData.successWithData(result);
            }else {
                apiAuthMapper.getAuthByConfigId(apiConfig.getId());
                // 确认是否有权限
                return ResponseData.fail("没有访问权限接口，请添加权限后再进行尝试！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail(e.getMessage());
        }
    }
}

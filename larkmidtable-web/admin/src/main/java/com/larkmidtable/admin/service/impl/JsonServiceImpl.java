package com.larkmidtable.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.larkmidtable.admin.dto.FlinkXJsonBuildDto;
import com.larkmidtable.admin.entity.JobDatasource;
import com.larkmidtable.admin.service.JsonService;
import com.larkmidtable.admin.service.JobDatasourceService;
import com.larkmidtable.admin.tool.flinkx.FlinkxJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @Author: LarkMidTable
 * @Date: 2020/9/16 11:14
 * @Description:  JSON构建实现类
 **/
@Service
public class JsonServiceImpl implements JsonService {

    @Autowired
    private JobDatasourceService jobJdbcDatasourceService;

    @Override
    public String buildJobFlinkxJson(FlinkXJsonBuildDto FlinkXJsonBuildDto) {
        FlinkxJsonHelper flinkxJsonHelper = new FlinkxJsonHelper();
        // reader
        JobDatasource readerDatasource = jobJdbcDatasourceService.getById(FlinkXJsonBuildDto.getReaderDatasourceId());
        flinkxJsonHelper.initReader(FlinkXJsonBuildDto, readerDatasource);
        // writer
        JobDatasource writerDatasource = jobJdbcDatasourceService.getById(FlinkXJsonBuildDto.getWriterDatasourceId());
        flinkxJsonHelper.initWriter(FlinkXJsonBuildDto, writerDatasource);

        return JSON.toJSONString(flinkxJsonHelper.buildJob());
    }

    @Override
    public String buildJobDataxJson(FlinkXJsonBuildDto dto) {
        return null;
    }

    @Override
    public String buildJobSeatunnelJson(FlinkXJsonBuildDto dto) {
        return null;
    }
}

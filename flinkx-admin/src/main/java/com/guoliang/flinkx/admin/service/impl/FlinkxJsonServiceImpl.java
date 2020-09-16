package com.guoliang.flinkx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.guoliang.flinkx.admin.dto.FlinkXJsonBuildDto;
import com.guoliang.flinkx.admin.entity.JobDatasource;
import com.guoliang.flinkx.admin.service.FlinkxJsonService;
import com.guoliang.flinkx.admin.service.JobDatasourceService;
import com.guoliang.flinkx.admin.tool.flinkx.FlinkxJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * com.wugui.flinkx json构建实现类
 *
 * @author jingwk
 * @ClassName FlinkxJsonServiceImpl
 * @Version 2.0
 * @since 2020/01/11 17:15
 */
@Service
public class FlinkxJsonServiceImpl implements FlinkxJsonService {

    @Autowired
    private JobDatasourceService jobJdbcDatasourceService;

    @Override
    public String buildJobJson(FlinkXJsonBuildDto dataXJsonBuildDto) {
        FlinkxJsonHelper flinkxJsonHelper = new FlinkxJsonHelper();
        // reader
        JobDatasource readerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getReaderDatasourceId());
        // reader plugin init
        flinkxJsonHelper.initReader(dataXJsonBuildDto, readerDatasource);
        JobDatasource writerDatasource = jobJdbcDatasourceService.getById(dataXJsonBuildDto.getWriterDatasourceId());
        flinkxJsonHelper.initWriter(dataXJsonBuildDto, writerDatasource);

        return JSON.toJSONString(flinkxJsonHelper.buildJob());
    }
}
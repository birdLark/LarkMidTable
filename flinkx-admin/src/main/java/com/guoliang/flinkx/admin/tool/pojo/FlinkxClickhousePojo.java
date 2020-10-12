package com.guoliang.flinkx.admin.tool.pojo;

import com.guoliang.flinkx.admin.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用于传参，构建json
 *
 * @author gavin
 * @ClassName FlinkxClickhousePojo
 * @Version 2.0
 * @Date: 2020/9/30
 */
@Data
public class FlinkxClickhousePojo {
    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    /**
     * clickhouse列名
     */
    private List<Map<String,Object>> columns;
    /**
     * clickhouse连接
     */
    private List<Map<String, Object>> connection;

    private String username;
    private String password;
}

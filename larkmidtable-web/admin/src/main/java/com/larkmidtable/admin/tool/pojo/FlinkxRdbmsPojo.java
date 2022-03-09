package com.larkmidtable.admin.tool.pojo;


import com.alibaba.fastjson.JSONObject;
import com.larkmidtable.admin.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用于传参，构建json
 *
 * @author jingwk
 * @ClassName FlinkxRdbmsPojo
 * @Version 2.0
 * @since 2020/01/11 15:19
 */
@Data
public class FlinkxRdbmsPojo {

    /**
     * 表名
     */
    private List<String> tables;

    /**
     * 列名
     */
    private Object rdbmsColumns;

    /**
     * 数据源信息
     */
    private JobDatasource jobDatasource;

    /**
     * querySql 属性，如果指定了，则优先于columns参数
     */
    private String querySql;

    /**
     * preSql 属性
     */
    private String preSql;

    /**
     * postSql 属性
     */
    private String postSql;

    /**
     * 切分主键
     */
    private String splitPk;

    /**
     * where
     */
    private String whereParam;
}

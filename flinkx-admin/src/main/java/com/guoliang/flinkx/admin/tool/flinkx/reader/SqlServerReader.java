package com.guoliang.flinkx.admin.tool.flinkx.reader;

import java.util.Map;

/**
 * sqlserver reader 构建类
 *
 * @author zhouhongfa@gz-yibo.com
 * @version 1.0
 * @since 2019/8/2
 */
public class SqlServerReader extends BaseReaderPlugin implements FlinkxReaderInterface {
    @Override
    public String getName() {
        return "sqlserverreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}

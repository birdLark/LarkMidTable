package com.guoliang.flinkx.admin.tool.flinkx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象实现类
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName BaseFlinkxPlugin
 * @Version 1.0
 * @since 2019/7/31 9:45
 */
public abstract class BaseFlinkxPlugin implements FlinkxPluginInterface {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}

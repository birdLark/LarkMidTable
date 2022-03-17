package com.larkmidtable.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.larkmidtable.admin.entity.JobRegistry;

/**
 * jdbc数据源配置表服务接口
 *
 * @author jingwk
 * @version v2.1.1
 * @since 2020-03-15
 */
public interface JobRegistryService extends IService<JobRegistry> {
    IPage<JobRegistry> selectAll(Integer size, Integer current);
}

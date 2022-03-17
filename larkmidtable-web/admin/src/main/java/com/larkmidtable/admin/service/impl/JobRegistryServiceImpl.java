package com.larkmidtable.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.larkmidtable.admin.entity.JobProject;
import com.larkmidtable.admin.mapper.JobProjectMapper;
import com.larkmidtable.admin.mapper.JobRegistryMapper;
import com.larkmidtable.admin.service.JobRegistryService;
import com.larkmidtable.admin.entity.JobRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JobRegistryServiceImpl
 * @author jingwk
 * @since 2019-03-15
 * @version v2.1.1
 */
@Service("jobRegistryService")
public class JobRegistryServiceImpl extends ServiceImpl<JobRegistryMapper, JobRegistry> implements JobRegistryService {

    @Autowired
    private JobRegistryMapper jobRegistryMapper;

    @Override
    public IPage<JobRegistry> selectAll(Integer size, Integer current) {
        Page<JobProject> page = new Page(current, size);
        return jobRegistryMapper.selectAll(page);
    }
}

package com.larkmidtable.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.larkmidtable.admin.entity.APIConfig;
import com.larkmidtable.admin.mapper.APIConfigMapper;
import com.larkmidtable.admin.service.APIConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("apiConfigService")
public class APIConfigServiceImpl extends ServiceImpl<APIConfigMapper, APIConfig> implements APIConfigService {

    @Autowired
    private APIConfigMapper apiConfigMapper;
}

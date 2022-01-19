package com.larkmidtable.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.larkmidtable.admin.mapper.DevEnvSettingMapper;
import com.larkmidtable.admin.service.DevEnvSettingService;
import com.larkmidtable.admin.entity.DevEnvSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("devEnvSettingService")
public class DevEnvSettingServiceImpl extends ServiceImpl<DevEnvSettingMapper, DevEnvSetting> implements DevEnvSettingService {

    @Autowired
    private DevEnvSettingMapper devEnvSettingMapper;

    @Override
    public IPage<DevEnvSetting> getDevEnvSettingListPaging(Integer pageSize, Integer pageNo, String searchName) {
        Page<DevEnvSetting> page = new Page(pageNo, pageSize);
        return devEnvSettingMapper.getDevEnvSettingListPaging(page, searchName);
    }
}
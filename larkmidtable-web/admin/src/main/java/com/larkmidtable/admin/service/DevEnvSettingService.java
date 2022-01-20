package com.larkmidtable.admin.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.larkmidtable.admin.entity.DevEnvSetting;
public interface DevEnvSettingService extends IService<DevEnvSetting>{
    IPage<DevEnvSetting> getDevEnvSettingListPaging(Integer pageSize, Integer pageNo, String searchName);
    
}

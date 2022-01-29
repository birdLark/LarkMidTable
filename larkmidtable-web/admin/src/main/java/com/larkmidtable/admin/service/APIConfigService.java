package com.larkmidtable.admin.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.larkmidtable.admin.entity.APIConfig;
import com.larkmidtable.admin.entity.DevEnvSetting;

public interface APIConfigService extends IService<APIConfig>{
    IPage<APIConfig> getDevEnvSettiAPIConfigServicengListPaging(Integer pageSize, Integer pageNo, String searchName);

}

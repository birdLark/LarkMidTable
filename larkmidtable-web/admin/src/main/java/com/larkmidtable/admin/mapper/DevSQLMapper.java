package com.larkmidtable.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.larkmidtable.admin.entity.DevTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DevSQLMapper extends BaseMapper<DevTask>{


	int save(DevTask devJar);

	int update(DevTask entity);

	DevTask getById(int id);
}

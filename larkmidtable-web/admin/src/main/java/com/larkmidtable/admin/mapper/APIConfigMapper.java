package com.larkmidtable.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.larkmidtable.admin.entity.APIConfig;
import com.larkmidtable.admin.entity.JobDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface APIConfigMapper extends BaseMapper<APIConfig>{

	int delete(@Param("id") int id);

	List<APIConfig> findList(@Param("offset") int offset,
			@Param("pagesize") int pagesize,
			@Param("name") String name);

	int save(APIConfig apiConfig);

	int update(APIConfig project);

	APIConfig getById(int id);

	List<JobDatasource> findDataSourceName();
}

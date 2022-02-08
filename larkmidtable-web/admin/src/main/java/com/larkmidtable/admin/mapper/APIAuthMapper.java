package com.larkmidtable.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.larkmidtable.admin.entity.APIAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface APIAuthMapper extends BaseMapper<APIAuth>{

	int delete(@Param("id") int id);

	List<APIAuth> findAll();

	int save(APIAuth apiAuth);

	int update(APIAuth entity);

	APIAuth getById(int id);
}

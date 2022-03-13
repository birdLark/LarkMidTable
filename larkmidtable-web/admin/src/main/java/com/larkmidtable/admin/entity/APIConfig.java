package com.larkmidtable.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 */
@Data
public class APIConfig {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("API分组")
    private String group_id;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("数据源ID")
    private String datasource_id;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("创建时间")
    private String create_time;

	@ApiModelProperty("更新时间")
	private String update_time;

	@ApiModelProperty("执行的SQL语句")
	private String sql_text;
}

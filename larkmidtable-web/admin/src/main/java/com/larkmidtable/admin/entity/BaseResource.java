package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class BaseResource {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("资源名称")
    private String name;

	@ApiModelProperty("资源地址")
	private String resource_address;

	@ApiModelProperty("更新时间")
	private String update_time;
}

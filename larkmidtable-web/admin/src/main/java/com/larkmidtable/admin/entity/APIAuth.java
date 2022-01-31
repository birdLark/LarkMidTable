package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class APIAuth {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("请求token的ID")
    private String token_id;

    @ApiModelProperty("请求分组的ID")
    private String group_id;

	@ApiModelProperty("更新时间")
	private String update_time;

}

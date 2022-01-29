package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class APIToken {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("请求Token")
    private String token;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("过期时间")
    private String expire;

    @ApiModelProperty("创建时间")
    private String create_time;

}

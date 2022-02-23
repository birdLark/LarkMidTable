package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class APIAuthConfig {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("权限的id")
    private String auth_id;

    @ApiModelProperty("配置的id")
    private String config_id;
}

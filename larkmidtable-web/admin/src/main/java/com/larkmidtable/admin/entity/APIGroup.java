package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class APIGroup {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("分组名称")
    private String name;
}

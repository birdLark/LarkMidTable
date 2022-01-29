package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class APISQL {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("api的Id")
    private String api_id;

    @ApiModelProperty("api的执行SQL")
    private String sql_text;
}

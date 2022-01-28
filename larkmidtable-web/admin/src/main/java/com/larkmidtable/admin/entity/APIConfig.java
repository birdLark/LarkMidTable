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

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性值")
    private String propValue;

    @ApiModelProperty("属性描述")
    private String description;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("标记")
    private Boolean flag;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist=false)
    private String userName;

}

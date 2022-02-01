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
public class DevTask {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务类型")
    private String tasktype;

    @ApiModelProperty("运行类型")
    private String runtype;

    @ApiModelProperty("运行参数")
    private String run_param;

    @ApiModelProperty("JAR包路径")
    private String jarpath;

	@ApiModelProperty("任务的SQL")
	private String sql_text;

    @ApiModelProperty("任务描述")
    private String task_describe;

	@ApiModelProperty("创建时间")
	private String create_time;

	@ApiModelProperty("更新时间")
	private String update_time;
}

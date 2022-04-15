package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class DeployTask {

	@ApiModelProperty("作业ID")
	private String jid;

    @ApiModelProperty("作业名称")
    private String name;

    @ApiModelProperty("开始时间")
    private String begintime;

    @ApiModelProperty("持续时间")
    private String duration;

	@ApiModelProperty("结束时间")
	private String endtime;

	@ApiModelProperty("任务数")
	private String tasknumber;

	@ApiModelProperty("状态")
	private String status;
}

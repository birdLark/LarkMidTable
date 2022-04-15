package com.larkmidtable.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class  OperLog {
	private long id;

	@ApiModelProperty("操作")
	private String operate;
	@ApiModelProperty("用户名")
	private String user;
	@ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("创建时间")
	private String createtime;
}

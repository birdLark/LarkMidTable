package com.larkmidtable.admin.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 *
 * @author fei
 * @date 2022-01-27
 * 
 **/
@Data
public class FlinkSqlDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "SQL 字符串必传！")
	private String sqlStr;

}

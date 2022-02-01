package com.larkmidtable.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *
 *
 * @Date: 2022/2/1 12:34
 * @Description:
 **/
public class ResponseData {
	String msg;
	boolean success;

	@JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
	Object data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static ResponseData apiSuccess(Object data) {
		ResponseData dto = new ResponseData();
		dto.setData(data);
		dto.setSuccess(true);
		//        dto.setMsg("Api access succeeded");
		return dto;

	}

	public static ResponseData successWithMsg(String msg) {
		ResponseData dto = new ResponseData();
		dto.setData(null);
		dto.setSuccess(true);
		dto.setMsg(msg);
		return dto;
	}

	public static ResponseData successWithData(Object data) {
		ResponseData dto = new ResponseData();
		dto.setData(data);
		dto.setSuccess(true);
		return dto;
	}

	public static ResponseData fail(String msg) {
		ResponseData dto = new ResponseData();
		dto.setSuccess(false);
		dto.setMsg(msg);
		return dto;
	}
}

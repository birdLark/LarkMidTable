package com.larkmidtable.rpc.util;

/**
 *
 * @Author: LarkMidTable
 * @Date: 2020/9/16 11:14
 * @Description: 自定义异常类
 **/
public class XxlRpcException extends RuntimeException {

	private static final long serialVersionUID = 42L;

	public XxlRpcException(String msg) {
		super(msg);
	}

	public XxlRpcException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public XxlRpcException(Throwable cause) {
		super(cause);
	}

}

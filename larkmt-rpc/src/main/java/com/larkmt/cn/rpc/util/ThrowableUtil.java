package com.larkmt.cn.rpc.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @Author: LarkMidTable
 * @Date: 2020/9/16 11:14
 * @Description: 将异常转换为String
 **/
public class ThrowableUtil {

	public static String toString(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		String errorMsg = stringWriter.toString();
		return errorMsg;
	}

}

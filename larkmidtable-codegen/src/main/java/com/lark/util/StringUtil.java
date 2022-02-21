package com.lark.util;

/**
 *
 *
 * @Date: 2021/4/2 11:36
 * @Description:
 * @Author: LarkMidTable
 **/
public class StringUtil {

	public static String strContat(String... args) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			stringBuffer.append(args[i]);
		}
		return stringBuffer.toString();
	}
}

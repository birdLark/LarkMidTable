package com.larkmidtable.cn.core.util.container;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 *
 * @Date: 2022/2/9 20:58
 * @Description:
 **/
public class ExceptionTracker {
	public static final int STRING_BUFFER = 4096;

	public static String trace(Throwable ex) {
		StringWriter sw = new StringWriter(STRING_BUFFER);
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}

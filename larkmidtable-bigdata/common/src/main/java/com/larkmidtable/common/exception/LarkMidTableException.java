package com.larkmidtable.common.exception;

import com.larkmidtable.common.spi.ErrorCode;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 *
 * @Date: 2022/2/9 21:05
 * @Description:
 **/
public class LarkMidTableException  extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	public LarkMidTableException(ErrorCode errorCode, String errorMessage) {
		super(errorCode.toString() + " - " + errorMessage);
		this.errorCode = errorCode;
	}

	private LarkMidTableException(ErrorCode errorCode, String errorMessage, Throwable cause) {
		super(errorCode.toString() + " - " + getMessage(errorMessage) + " - " + getMessage(cause), cause);

		this.errorCode = errorCode;
	}

	public static LarkMidTableException asLarkMidTableException(ErrorCode errorCode, String message) {
		return new LarkMidTableException(errorCode, message);
	}

	public static LarkMidTableException asLarkMidTableException(ErrorCode errorCode, String message, Throwable cause) {
		if (cause instanceof LarkMidTableException) {
			return (LarkMidTableException) cause;
		}
		return new LarkMidTableException(errorCode, message, cause);
	}

	public static LarkMidTableException asLarkMidTableException(ErrorCode errorCode, Throwable cause) {
		if (cause instanceof LarkMidTableException) {
			return (LarkMidTableException) cause;
		}
		return new LarkMidTableException(errorCode, getMessage(cause), cause);
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}

	private static String getMessage(Object obj) {
		if (obj == null) {
			return "";
		}

		if (obj instanceof Throwable) {
			StringWriter str = new StringWriter();
			PrintWriter pw = new PrintWriter(str);
			((Throwable) obj).printStackTrace(pw);
			return str.toString();
			// return ((Throwable) obj).getMessage();
		} else {
			return obj.toString();
		}
	}
}

package com.lark.template;

import com.lark.core.Engine;
import com.lark.util.ClassUtil;
import com.lark.util.ParamUtil;
import com.lark.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @Date: 2021/4/7 15:31
 * @Description:
 * @Author: LarkMidTable
 **/
public class JavaFileTemplate {

	private static final Logger logger = LoggerFactory.getLogger(Engine.class);

	public static String getJavaFileContent(ParamUtil param) {
		//		select * from socket.stream into kafka.stream
		logger.info("Resolving SQL to generate a Link task was successful!!!");
		//import code
		String importCode = ClassUtil.getImportCode();
		//input code
		String inputCode = ClassUtil.getInputCode(param.getInputType());
		//transfer code
		String transferCode = ClassUtil.getTransferCode(param.getSql());
		//output code
		String outputCode = ClassUtil.getOutputCode(param.getOutputType());
		//body code
		String bodyCode =StringUtil.strContat(inputCode,transferCode,outputCode);
		//class code
		String classCode = ClassUtil.getClassCode(param.getClassName(), bodyCode);
		//java File
		String javaFileContent = StringUtil.strContat(importCode, classCode);
		return javaFileContent;
	}
}

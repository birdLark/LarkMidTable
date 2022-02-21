package com.lark.util;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 *
 * @Date: 2021/4/7 14:58
 * @Description:
 * @Author: LarkMidTable
 **/
public class ParamUtil {

	private String model ;
	private String sql;
	private String className;
	private String inputType;
	private String inputFiled;
	private String inputTable;
	private String outputType;
	private String outputFiled;
	private String outputTable;

	public static ParamUtil getParam(String[] args) throws ParseException {
		Options options = new Options();

		options.addOption("model", true, "Flink running model.");
		options.addOption("sql", true, "Flink task sql.");
		BasicParser parser = new BasicParser();
		CommandLine cl = parser.parse(options, args);
		ParamUtil paramUtil = new ParamUtil();
		paramUtil.setModel(cl.getOptionValue("model"));
		SQLUtil sqlUtil = new SQLUtil(cl.getOptionValue("sql"));
		String getSearchType = sqlUtil.getSearchType();
		String getInsertType = sqlUtil.getInsertType(cl.getOptionValue("sql"));
		paramUtil.setClassName("Hello");
		paramUtil.setInputType(getSearchType.split("\\.")[0]);
		paramUtil.setInputTable(getSearchType.split("\\.")[1]);
		paramUtil.setOutputType(getInsertType.split("\\.")[0]);
		paramUtil.setOutputTable(getInsertType.split("\\.")[1]);
		return paramUtil;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getInputFiled() {
		return inputFiled;
	}

	public void setInputFiled(String inputFiled) {
		this.inputFiled = inputFiled;
	}

	public String getInputTable() {
		return inputTable;
	}

	public void setInputTable(String inputTable) {
		this.inputTable = inputTable;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public String getOutputFiled() {
		return outputFiled;
	}

	public void setOutputFiled(String outputFiled) {
		this.outputFiled = outputFiled;
	}

	public String getOutputTable() {
		return outputTable;
	}

	public void setOutputTable(String outputTable) {
		this.outputTable = outputTable;
	}
}

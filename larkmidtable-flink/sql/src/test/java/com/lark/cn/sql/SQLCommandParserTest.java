package com.lark.cn.sql;

import com.lark.cn.common.SqlCommandParser;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 * @Date: 2022/2/4 12:47
 * @Description:
 **/
public class SQLCommandParserTest {
	public static void main(String[] args) {
		String sql = "";
		Optional<SqlCommandParser.SqlCommandCall> calls = SqlCommandParser.parse(sql);
	}
}

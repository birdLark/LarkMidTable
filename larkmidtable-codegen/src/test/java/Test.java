import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.lark.util.SQLUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 *
 *
 * @Date: 2021/4/7 16:39
 * @Description:
 * @Author: LarkMidTable
 **/
public class Test {
	public static void main(String[] args) {
		String sql = "INSERT INTO xxx.table2 (xx,xx)";
//		String s = sql.toLowerCase();
//		s.substring(sql.indexOf("into"),sql.indexOf("("));

//		TableStat select = visitor.getTableStat("Select");
		////		String s = select.toString();
		////		System.out.println(s);
		SQLUtil sqlUtil = new SQLUtil(sql);
		System.out.println(sqlUtil.getInsertType(sql));

	}
}

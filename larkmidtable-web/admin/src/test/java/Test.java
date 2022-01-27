import com.larkmidtable.admin.core.cron.CronExpression;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 *
 * @Date: 2022/1/21 9:32
 * @Description:
 **/
public class Test {

	public static void main(String[] args) throws ParseException, IOException {
		String command = "C:/Users/test2/Desktop/flinkx/flinkx.py";
		String params = "world";
		String[] cmd = new String[]{"python",command,params};
		Process process = Runtime.getRuntime().exec(cmd);
	}
}

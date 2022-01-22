import com.larkmidtable.admin.core.cron.CronExpression;
import com.larkmidtable.core.util.ProcessUtil;

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
//		CronExpression cronExpression = new CronExpression("00 * * * * ? *");
//		Date lastTime = new Date();
//		System.out.println(lastTime);
//		lastTime = cronExpression.getNextValidTimeAfter(lastTime);
//		System.out.println(lastTime);
		final Process process = Runtime.getRuntime().exec("python C:\\Users\\guoliang\\Desktop\\flinkx\\flinkx.py");
		String prcsId = ProcessUtil.getProcessId(process);
		System.out.println(prcsId);
	}
}

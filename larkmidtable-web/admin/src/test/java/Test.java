import com.larkmidtable.admin.core.cron.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 *
 *
 * @Date: 2022/1/21 9:32
 * @Description:
 **/
public class Test {

	public static void main(String[] args) throws ParseException {
		CronExpression cronExpression = new CronExpression("00 * * * * ? *");
		Date lastTime = new Date();
		System.out.println(lastTime);
		lastTime = cronExpression.getNextValidTimeAfter(lastTime);
		System.out.println(lastTime);
	}
}

package com.larkmidtable.admin.core.thread;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.larkmidtable.admin.core.conf.ExcecutorConfig;
import com.larkmidtable.admin.core.conf.JobAdminConfig;
import com.larkmidtable.admin.core.trigger.JobTrigger;
import com.larkmidtable.admin.core.trigger.TriggerTypeEnum;
import com.larkmidtable.admin.entity.JobInfo;
import com.larkmidtable.core.log.JobLogger;
import com.larkmidtable.core.util.ProcessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * job trigger thread pool helper
 *
 * @author xuxueli 2018-07-03 21:08:07
 */

public class JobTriggerPoolHelper {
	public static final ConcurrentMap<String, String> jobTmpFiles = new ConcurrentHashMap<>();


	//	private String flinkXShPath;

	// ---------------------- trigger pool ----------------------
	private static Logger logger = LoggerFactory.getLogger(JobTriggerPoolHelper.class);
	private static JobTriggerPoolHelper helper = new JobTriggerPoolHelper();
	// fast/slow thread pool
	private ThreadPoolExecutor fastTriggerPool = null;
	private ThreadPoolExecutor slowTriggerPool = null;
	// job timeout count
	private volatile long minTim = System.currentTimeMillis() / 60000;     // ms > min
	private volatile ConcurrentMap<Integer, AtomicInteger> jobTimeoutCountMap = new ConcurrentHashMap<>();

	public static void toStart() {
		helper.start();
	}

	public static void toStop() {
		helper.stop();
	}


	// ---------------------- helper ----------------------

	public static String[] buildFlinkXExecutorCmd(String flinkXShPath, String tmpFilePath) {
		List<String> cmdArr = new ArrayList<>();
		cmdArr.add("python");
		cmdArr.add(flinkXShPath);
		cmdArr.add(tmpFilePath);

		logger.info(cmdArr + " " + flinkXShPath + " " + tmpFilePath);
		return cmdArr.toArray(new String[cmdArr.size()]);
	}

	public static void runJob(int jobId) {
		try {
			JobInfo jobInfo = JobAdminConfig.getAdminConfig().getJobInfoMapper().loadById(jobId);
			String tmpFilePath = generateTemJsonFile(jobInfo.getJobJson());
			String flinkxHome = ExcecutorConfig.getExcecutorConfig().getFlinkxHome();
			String cmdstr = "";
			String[] cmdarrayFinal = buildFlinkXExecutorCmd(flinkxHome, tmpFilePath);
			for (int j = 0; j < cmdarrayFinal.length; j++) {
				cmdstr += cmdarrayFinal[j] + " ";
			}
			System.out.println("-------job will run----------");


			final Process process = Runtime.getRuntime().exec(cmdstr);
			String prcsId = ProcessUtil.getProcessId(process);
			JobLogger.log("------------------FlinkX process id: " + prcsId);
			// 运行完后删除文件
			if (FileUtil.exist(tmpFilePath)) {
//				FileUtil.del(new File(tmpFilePath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runJobWithJson(String  jobJson) {
		try {
			String tmpFilePath = generateTemJsonFile(jobJson);
			String flinkxHome = ExcecutorConfig.getExcecutorConfig().getFlinkxHome();
			String cmdstr = "";
			String[] cmdarrayFinal = buildFlinkXExecutorCmd(flinkxHome, tmpFilePath);
			for (int j = 0; j < cmdarrayFinal.length; j++) {
				cmdstr += cmdarrayFinal[j] + " ";
			}
			System.out.println("-------job will run----------");
			final Process process = Runtime.getRuntime().exec(cmdstr);
			String prcsId = ProcessUtil.getProcessId(process);
			JobLogger.log("------------------FlinkX process id: " + prcsId);
			// 运行完后删除文件
			if (FileUtil.exist(tmpFilePath)) {
//				FileUtil.del(new File(tmpFilePath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String generateTemJsonFile(String jobJson) {
		String jsonPath = ExcecutorConfig.getExcecutorConfig().getJsonPath();
		if (!FileUtil.exist(jsonPath)) {
			FileUtil.mkdir(jsonPath);
		}
		String tmpFilePath = jsonPath + "jobTmp-" + IdUtil.simpleUUID() + ".json";
		// 根据json写入到临时本地文件
		try (PrintWriter writer = new PrintWriter(tmpFilePath, "UTF-8")) {
			writer.println(jobJson);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			JobLogger.log("JSON 临时文件写入异常：" + e.getMessage());
		}
		return tmpFilePath;
	}

	public void start() {
		fastTriggerPool = new ThreadPoolExecutor(10, JobAdminConfig.getAdminConfig().getTriggerPoolFastMax(), 60L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000), new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "web, admin JobTriggerPoolHelper-fastTriggerPool-" + r.hashCode());
			}
		});

		slowTriggerPool = new ThreadPoolExecutor(10, JobAdminConfig.getAdminConfig().getTriggerPoolSlowMax(), 60L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000), new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "web, admin JobTriggerPoolHelper-slowTriggerPool-" + r.hashCode());
			}
		});
	}

	public void stop() {
		//triggerPool.shutdown();
		fastTriggerPool.shutdownNow();
		slowTriggerPool.shutdownNow();
		logger.info(">>>>>>>>> web trigger thread pool shutdown success.");
	}


}

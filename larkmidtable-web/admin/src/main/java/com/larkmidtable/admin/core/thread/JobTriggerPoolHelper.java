package com.larkmidtable.admin.core.thread;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.larkmidtable.admin.core.conf.ExcecutorConfig;
import com.larkmidtable.admin.core.conf.JobAdminConfig;
import com.larkmidtable.admin.core.trigger.JobTrigger;
import com.larkmidtable.admin.core.trigger.TriggerTypeEnum;
import com.larkmidtable.admin.entity.JobInfo;
import com.larkmidtable.admin.entity.JobLog;
import com.larkmidtable.core.biz.model.ReturnT;
import com.larkmidtable.core.glue.GlueTypeEnum;
import com.larkmidtable.core.log.JobLogger;
import com.larkmidtable.core.util.Constants;
import com.larkmidtable.core.util.ProcessUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	public static String[] buildFlinkXExecutorCmd(String flinkXShPath, String tmpFilePath,int jobId) {
		long timestamp = System.currentTimeMillis();
		List<String> cmdArr = new ArrayList<>();
		if(JobTriggerPoolHelper.isWindows()) {
			cmdArr.add(Constants.CMDWINDOW);
			cmdArr.add(flinkXShPath);
			cmdArr.add(tmpFilePath);
		} else {
			cmdArr.add(Constants.CMDLINUX);
			cmdArr.add(flinkXShPath);
			cmdArr.add(tmpFilePath);
		}
		cmdArr.add(jobId+""+timestamp+".out");
		logger.info(cmdArr + " " + flinkXShPath + " " + tmpFilePath);
		return cmdArr.toArray(new String[cmdArr.size()]);
	}

	public static String[] buildDataXExecutorCmd(String dataXShPath,
												 String tmpFilePath,
												 String dataxHome,
												 int jobId) {
		List<String> cmdArr = new ArrayList<>();
		long timestamp = System.currentTimeMillis();
		if(JobTriggerPoolHelper.isWindows()) {
			cmdArr.add(Constants.CMDWINDOW);
		} else {
			cmdArr.add(Constants.CMDLINUX);

		}
		cmdArr.add(dataXShPath);
		cmdArr.add(tmpFilePath);
		cmdArr.add(dataxHome);
		cmdArr.add(jobId+""+timestamp+".out");
		logger.info(JSONObject.toJSONString(cmdArr));
		return cmdArr.toArray(new String[cmdArr.size()]);
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}

	public static void runJob(int jobId) {
		try {
			JobInfo jobInfo = JobAdminConfig.getAdminConfig().getJobInfoMapper().loadById(jobId);
			String tmpFilePath = generateTemJsonFile(jobInfo.getJobJson());
			String flinkxHome = ExcecutorConfig.getExcecutorConfig().getFlinkxHome();
			String cmdstr = "";
			String[] cmdarrayFinal = null;
			switch (jobInfo.getGlueType()){
				case "datax":
					cmdarrayFinal = buildDataXExecutorCmd(ExcecutorConfig.getExcecutorConfig().getDataxPyHome(),
							tmpFilePath,ExcecutorConfig.getExcecutorConfig().getDataxHome(),
							jobId
							);
				    break;
				case "flinkx":
					cmdarrayFinal = buildFlinkXExecutorCmd(flinkxHome, tmpFilePath,jobId);
					break;
				default:
					throw new RuntimeException("配置的执行类型["+jobInfo.getGlueType()+"]没有配置执行方法");
			}

			for (int j = 0; j < cmdarrayFinal.length; j++) {
				cmdstr += cmdarrayFinal[j] + " ";
			}
			final Process process = Runtime.getRuntime().exec(cmdstr);
			String prcsId = ProcessUtil.getProcessId(process);
			JobLogger.log("Execute: " + cmdstr);
			JobLogger.log("process id: " + prcsId);
			if (FileUtil.exist(tmpFilePath)) {
//				FileUtil.del(new File(tmpFilePath));
			}
			// 记录日志
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.MILLISECOND, 0);
			Date triggerTime = calendar.getTime();
			JobLog jobLog = new JobLog();
			jobLog.setJobGroup(jobInfo.getJobGroup());
			jobLog.setJobId(jobInfo.getId());
			jobLog.setTriggerTime(triggerTime);
			jobLog.setJobDesc(jobInfo.getJobDesc());
			jobLog.setHandleTime(triggerTime);
			jobLog.setTriggerCode(ReturnT.SUCCESS_CODE);
			jobLog.setHandleCode(0);
			jobLog.setProcessId(prcsId);
			// 设置job的执行路径
			jobLog.setExecutorAddress(cmdarrayFinal[3]);
			JobAdminConfig.getAdminConfig().getJobLogMapper().save(jobLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runJobWithJson(String  jobJson,String type,int jobid,JobInfo jobInfo) {
		try {
			String tmpFilePath = generateTemJsonFile(jobJson);
			String flinkxHome = ExcecutorConfig.getExcecutorConfig().getFlinkxHome();
			String cmdstr = "";
			String[] cmdarrayFinal = null;
			switch (type){
				case "datax":
					cmdarrayFinal = buildDataXExecutorCmd(ExcecutorConfig.getExcecutorConfig().getDataxPyHome(),
							tmpFilePath,ExcecutorConfig.getExcecutorConfig().getDataxHome(),
							jobid
					);
					break;
				case "flinkx":
					cmdarrayFinal = buildFlinkXExecutorCmd(flinkxHome, tmpFilePath,jobid);
					break;
				default:
					throw new RuntimeException("配置的执行类型["+type+"]没有配置执行方法");
			}

			for (int j = 0; j < cmdarrayFinal.length; j++) {
				cmdstr += cmdarrayFinal[j] + " ";
			}
			System.out.println("-------job will run----------");
			logger.info("cmdStr:{}",cmdstr);
			final Process process = Runtime.getRuntime().exec(cmdstr);
			String prcsId = ProcessUtil.getProcessId(process);
			JobLogger.log("------------------FlinkX process id: " + prcsId);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = null;
			while ((line = reader.readLine()) != null){
				logger.info(line);
			}
			process.waitFor();
			int exitVal =process.exitValue();
			logger.info(exitVal == 0 ? "成功" : "失败");
			// 运行完后删除文件
			if (FileUtil.exist(tmpFilePath)) {
//				FileUtil.del(new File(tmpFilePath));
			}
			// 记录日志
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.MILLISECOND, 0);
			Date triggerTime = calendar.getTime();
			JobLog jobLog = new JobLog();
			jobLog.setJobGroup(jobInfo.getJobGroup());
			jobLog.setJobId(jobInfo.getId());
			jobLog.setTriggerTime(triggerTime);
			jobLog.setJobDesc(jobInfo.getJobDesc());
			jobLog.setHandleTime(triggerTime);
			jobLog.setTriggerCode(ReturnT.SUCCESS_CODE);
			jobLog.setHandleCode(0);
			jobLog.setProcessId(prcsId);
			// 设置job的执行路径
			jobLog.setExecutorAddress(cmdarrayFinal[3]);
			JobAdminConfig.getAdminConfig().getJobLogMapper().save(jobLog);
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

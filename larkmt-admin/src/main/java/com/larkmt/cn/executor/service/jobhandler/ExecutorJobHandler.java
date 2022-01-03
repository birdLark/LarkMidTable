package com.larkmt.cn.executor.service.jobhandler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.larkmt.cn.core.biz.model.HandleProcessCallbackParam;
import com.larkmt.cn.core.biz.model.ReturnT;
import com.larkmt.cn.core.biz.model.TriggerParam;
import com.larkmt.cn.core.handler.IJobHandler;
import com.larkmt.cn.core.handler.annotation.JobHandler;
import com.larkmt.cn.core.log.JobLogger;
import com.larkmt.cn.core.util.ProcessUtil;
import com.larkmt.cn.executor.service.command.BuildCommand;
import com.larkmt.cn.executor.service.logparse.AnalysisStatistics;
import com.larkmt.cn.executor.service.logparse.LogStatistics;
import com.larkmt.cn.executor.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.FutureTask;


@JobHandler(value = "executorJobHandler")
@Component
public class ExecutorJobHandler extends IJobHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorJobHandler.class);

	@Override
	public ReturnT<String> execute(TriggerParam trigger) {

		int exitValue = -1;
		Thread errThread = null;
		LogStatistics logStatistics = null;
		// Generate JSON temporary file
		String tmpFilePath = generateTemJsonFile(trigger.getJobJson());
		String jobCommitPath = generateJobCommitPath();

		try {
			String[] cmdarrayFinal = BuildCommand.buildFlinkXExecutorCmd(trigger, tmpFilePath, jobCommitPath);
			String cmdstr = "";
			for (int i = 0; i < cmdarrayFinal.length; i++) {
				cmdstr += cmdarrayFinal[i] + " ";
			}
			//在window上面运行
			final Process process = Runtime.getRuntime().exec(cmdstr);
			String prcsId = ProcessUtil.getProcessId(process);
			JobLogger.log("------------------FlinkX process id: " + prcsId);
			jobTmpFiles.put(prcsId, tmpFilePath);
			// update flinkx process id
			HandleProcessCallbackParam prcs = new HandleProcessCallbackParam(trigger.getLogId(),
					trigger.getLogDateTime(), prcsId);
//			ProcessCallbackThread.pushCallBack(prcs);
			// log-thread
			Thread futureThread = null;
			FutureTask<LogStatistics> futureTask = new FutureTask<>(
					() -> AnalysisStatistics.analysisStatisticsLog(new BufferedInputStream(process.getInputStream())));
			futureThread = new Thread(futureTask);
			futureThread.start();

			errThread = new Thread(() -> {
				try {
					AnalysisStatistics.analysisStatisticsLog(new BufferedInputStream(process.getErrorStream()));
				} catch (IOException e) {
					JobLogger.log(e);
				}
			});

			logStatistics = futureTask.get();
			errThread.start();
			// process-wait
			exitValue = process.waitFor(); // exit code: 0=success, 1=error
			// log-thread join
			errThread.join();
		} catch (Exception e) {
			JobLogger.log(e);
		} finally {
			if (errThread != null && errThread.isAlive()) {
				errThread.interrupt();
			}
			// 删除临时文件
			if (FileUtil.exist(tmpFilePath)) {
				FileUtil.del(new File(tmpFilePath));
			}
		}
		if (exitValue == 0) {
			return new ReturnT<>(200, logStatistics.toString());
		} else {
			return new ReturnT<>(IJobHandler.FAIL.getCode(), "command exit value(" + exitValue + ") is failed");
		}
	}

	private String generateJobCommitPath() {
		File directory = new File("bin");
		return directory.getAbsolutePath();
	}


	private String generateTemJsonFile(String jobJson) {
		String tmpFilePath;
		String jsonPath = SystemUtils.getFlinkxHomePath();
		tmpFilePath = jsonPath + "jobTmp-" + IdUtil.simpleUUID() + ".conf";
		// 根据json写入到临时本地文件
		try (PrintWriter writer = new PrintWriter(tmpFilePath, "UTF-8")) {
			writer.println(jobJson);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			JobLogger.log("JSON 临时文件写入异常：" + e.getMessage());
		}
		return tmpFilePath;
	}

}

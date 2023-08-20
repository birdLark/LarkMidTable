package com.larkmt.cn.admin.core.kill;

import com.larkmt.cn.admin.core.thread.JobTriggerPoolHelper;
import com.larkmt.core.biz.model.ReturnT;
import com.larkmt.core.biz.model.TriggerParam;
import com.larkmt.core.enums.ExecutorBlockStrategyEnum;
import com.larkmt.core.glue.GlueTypeEnum;
import com.larkmt.cn.admin.core.trigger.JobTrigger;
import com.larkmt.core.util.Constants;
import com.larkmt.core.util.ProcessUtil;

import java.util.Date;

/**
 * flinkx-job trigger
 * Created by jingwk on 2019/12/15.
 */
public class KillJob {

    /**
     * @param logId
     * @param address
     * @param processId
     */
	public static ReturnT<String> trigger(String processId) {
		ReturnT<String> triggerResult = null;
		try {
			//将作业杀掉
			String cmdstr="";
			if(JobTriggerPoolHelper.isWindows()){
				cmdstr= Constants.CMDWINDOWTASKKILL+processId;
			}else {
				cmdstr=Constants.CMDLINUXTASKKILL+processId;
			}
			final Process process = Runtime.getRuntime().exec(cmdstr);
			String prcsId = ProcessUtil.getProcessId(process);
			triggerResult = new ReturnT<>(ReturnT.SUCCESS_CODE, "成功停止作业 !!!");
		}catch (Exception e) {
			triggerResult = new ReturnT<>(ReturnT.FAIL_CODE, null);
		}
		return triggerResult;
	}

}

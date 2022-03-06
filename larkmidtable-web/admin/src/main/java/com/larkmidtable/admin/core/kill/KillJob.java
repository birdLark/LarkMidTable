package com.larkmidtable.admin.core.kill;

import com.larkmidtable.admin.core.thread.JobTriggerPoolHelper;
import com.larkmidtable.core.biz.model.ReturnT;
import com.larkmidtable.core.biz.model.TriggerParam;
import com.larkmidtable.core.enums.ExecutorBlockStrategyEnum;
import com.larkmidtable.core.glue.GlueTypeEnum;
import com.larkmidtable.admin.core.trigger.JobTrigger;
import com.larkmidtable.core.log.JobLogger;
import com.larkmidtable.core.util.Constants;
import com.larkmidtable.core.util.ProcessUtil;

import java.util.Date;

/**
 * job trigger
 * Created by jingwk on 2019/12/15.
 */
public class KillJob {

    /**
     *
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
            JobLogger.log("Execute: " + cmdstr);
            JobLogger.log("process id: " + prcsId);
            triggerResult = new ReturnT<>(ReturnT.SUCCESS_CODE, "成功停止作业 !!!");
        }catch (Exception e) {
            triggerResult = new ReturnT<>(ReturnT.FAIL_CODE, null);
        }
        return triggerResult;
    }
}

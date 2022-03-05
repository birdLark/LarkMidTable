package com.larkmidtable.admin.core.kill;

import com.larkmidtable.core.biz.model.ReturnT;
import com.larkmidtable.core.biz.model.TriggerParam;
import com.larkmidtable.core.enums.ExecutorBlockStrategyEnum;
import com.larkmidtable.core.glue.GlueTypeEnum;
import com.larkmidtable.admin.core.trigger.JobTrigger;

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
            triggerResult = new ReturnT<>(ReturnT.SUCCESS_CODE, "成功停止作业 !!!");
        }catch (Exception e) {
            triggerResult = new ReturnT<>(ReturnT.FAIL_CODE, null);
        }
        return triggerResult;
    }
}

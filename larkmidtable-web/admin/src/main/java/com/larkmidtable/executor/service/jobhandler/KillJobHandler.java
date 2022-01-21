package com.larkmidtable.executor.service.jobhandler;


import cn.hutool.core.io.FileUtil;
import com.larkmidtable.core.biz.model.ReturnT;
import com.larkmidtable.core.biz.model.TriggerParam;
import com.larkmidtable.core.handler.IJobHandler;
import com.larkmidtable.core.handler.annotation.JobHandler;
import com.larkmidtable.core.util.ProcessUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;

/**
 * FlinkX任务终止
 *
 * @author jingwk 2019-12-16
 */

@JobHandler(value = "killJobHandler")
@Component
public class KillJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(TriggerParam tgParam) {
        String processId = tgParam.getProcessId();
        boolean result = ProcessUtil.killProcessByPid(processId);
        //  删除临时文件
        if (!CollectionUtils.isEmpty(jobTmpFiles)) {
            String pathname = jobTmpFiles.get(processId);
            if (pathname != null) {
                FileUtil.del(new File(pathname));
                jobTmpFiles.remove(processId);
            }
        }
        return result ? IJobHandler.SUCCESS : IJobHandler.FAIL;
    }
}

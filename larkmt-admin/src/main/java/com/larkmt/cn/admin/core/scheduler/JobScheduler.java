package com.larkmt.cn.admin.core.scheduler;

import com.larkmt.cn.admin.core.util.I18nUtil;
import com.larkmt.cn.core.enums.ExecutorBlockStrategyEnum;
import com.larkmt.cn.admin.core.thread.JobFailMonitorHelper;
import com.larkmt.cn.admin.core.thread.JobLogReportHelper;
import com.larkmt.cn.admin.core.thread.JobRegistryMonitorHelper;
import com.larkmt.cn.admin.core.thread.JobScheduleHelper;
import com.larkmt.cn.admin.core.thread.JobTriggerPoolHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author xuxueli 2018-10-28 00:18:17
 */

public class JobScheduler {
    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);


    public void init() throws Exception {
        // init i18n
        initI18n();

        // admin registry monitor run
        JobRegistryMonitorHelper.getInstance().start();

        // admin monitor run
        JobFailMonitorHelper.getInstance().start();

        // admin trigger pool start
        JobTriggerPoolHelper.toStart();

        // admin log report start
        JobLogReportHelper.getInstance().start();

        // start-schedule
        JobScheduleHelper.getInstance().start();

        logger.info(">>>>>>>>> init LarkMidTable admin success.");
    }


    public void destroy() throws Exception {

        // stop-schedule
        JobScheduleHelper.getInstance().toStop();

        // admin log report stop
        JobLogReportHelper.getInstance().toStop();

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();

        // admin monitor stop
        JobFailMonitorHelper.getInstance().toStop();

        // admin registry stop
        JobRegistryMonitorHelper.getInstance().toStop();

    }

    // ---------------------- I18n ----------------------

    private void initI18n() {
        for (ExecutorBlockStrategyEnum item : ExecutorBlockStrategyEnum.values()) {
            item.setTitle(I18nUtil.getString("jobconf_block_".concat(item.name())));
        }
    }

}

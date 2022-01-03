package com.larkmt.cn.executor.core.config;

import com.larkmt.cn.executor.util.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Configuration
public class FlinkXConfig {
    private Logger logger = LoggerFactory.getLogger(FlinkXConfig.class);

    private static final String DEFAULT_LOG_PATH = "log/executor/jobhandler";

    @Value("${larkmt.job.admin.addresses}")
    private String adminAddresses;

    @Value("${larkmt.job.executor.appname}")
    private String appName;

    @Value("${larkmt.job.executor.ip}")
    private String ip;

    @Value("${larkmt.job.executor.port}")
    private int port;

    @Value("${larkmt.job.accessToken}")
    private String accessToken;

    @Value("${larkmt.job.executor.logpath}")
    private String logPath;

    @Value("${larkmt.job.executor.logretentiondays}")
    private int logRetentionDays;



}
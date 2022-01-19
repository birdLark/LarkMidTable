package com.larkmidtable.executor.core.config;

import com.larkmidtable.core.executor.impl.JobSpringExecutor;
import com.larkmidtable.executor.util.SystemUtils;
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

    @Value("${flinkx.job.admin.addresses}")
    private String adminAddresses;

    @Value("${flinkx.job.executor.appname}")
    private String appName;

    @Value("${flinkx.job.executor.ip}")
    private String ip;

    @Value("${flinkx.job.executor.port}")
    private int port;

    @Value("${flinkx.job.accessToken}")
    private String accessToken;

    @Value("${flinkx.job.executor.logpath}")
    private String logPath;

    @Value("${flinkx.job.executor.logretentiondays}")
    private int logRetentionDays;


    @Bean
    public JobSpringExecutor JobExecutor() {
        logger.info(">>>>>>>>>>> web config init.");
        JobSpringExecutor jobSpringExecutor = new JobSpringExecutor();
        jobSpringExecutor.setAdminAddresses(adminAddresses);
        jobSpringExecutor.setAppName(appName);
        jobSpringExecutor.setIp(ip);
        jobSpringExecutor.setPort(port);
        jobSpringExecutor.setAccessToken(accessToken);
        String flinkXHomePath = SystemUtils.getFlinkxHomePath();
        if (StringUtils.isEmpty(logPath)) {
            logPath = flinkXHomePath + DEFAULT_LOG_PATH;
        }
        jobSpringExecutor.setLogPath(logPath);
        jobSpringExecutor.setLogRetentionDays(logRetentionDays);

        return jobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}

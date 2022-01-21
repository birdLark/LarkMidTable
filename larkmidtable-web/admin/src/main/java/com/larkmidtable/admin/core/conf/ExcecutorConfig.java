package com.larkmidtable.admin.core.conf;

import com.larkmidtable.admin.core.scheduler.JobScheduler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @Date: 2022/1/20 22:52
 * @Description:
 **/
@Component
public class ExcecutorConfig  implements InitializingBean, DisposableBean {

	private static ExcecutorConfig excecutorConfig = null;

	public static ExcecutorConfig getExcecutorConfig() {
		return excecutorConfig;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		excecutorConfig = this;
	}

	@Override
	public void destroy() throws Exception {
	}

	@Value("${flinkx.executor.jsonPath}")
	private  String jsonPath;

	@Value("${flinkx.executor.flinkxHome}")
	private  String flinkxHome;

	@Value("${flinkx.executor.logHome}")
	private  String logHome;


	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public String getFlinkxHome() {
		return flinkxHome;
	}

	public void setFlinkxHome(String flinkxHome) {
		this.flinkxHome = flinkxHome;
	}

	public String getLogHome() {
		return logHome;
	}

	public void setLogHome(String logHome) {
		this.logHome = logHome;
	}
}

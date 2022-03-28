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

	@Value("${flinkx.executor.flinkxHome}")
	private  String flinkxHome;

	@Value("${flinkx.executor.flinkxjsonPath}")
	private  String flinkxjsonPath;

	@Value("${flinkx.executor.flinkxlogHome}")
	private  String flinkxlogHome;

	@Value("${flinkx.executor.dataxHome}")
	private String dataxHome;

	@Value("${flinkx.executor.dataxjsonPath}")
	private String dataxjsonPath;

	@Value("${flinkx.executor.dataxlogHome}")
	private String dataxlogHome;

	public static void setExcecutorConfig(ExcecutorConfig excecutorConfig) {
		ExcecutorConfig.excecutorConfig = excecutorConfig;
	}

	public String getFlinkxHome() {
		return flinkxHome;
	}

	public void setFlinkxHome(String flinkxHome) {
		this.flinkxHome = flinkxHome;
	}

	public String getFlinkxjsonPath() {
		return flinkxjsonPath;
	}

	public void setFlinkxjsonPath(String flinkxjsonPath) {
		this.flinkxjsonPath = flinkxjsonPath;
	}

	public String getFlinkxlogHome() {
		return flinkxlogHome;
	}

	public void setFlinkxlogHome(String flinkxlogHome) {
		this.flinkxlogHome = flinkxlogHome;
	}

	public String getDataxHome() {
		return dataxHome;
	}

	public void setDataxHome(String dataxHome) {
		this.dataxHome = dataxHome;
	}

	public String getDataxjsonPath() {
		return dataxjsonPath;
	}

	public void setDataxjsonPath(String dataxjsonPath) {
		this.dataxjsonPath = dataxjsonPath;
	}

	public String getDataxlogHome() {
		return dataxlogHome;
	}

	public void setDataxlogHome(String dataxlogHome) {
		this.dataxlogHome = dataxlogHome;
	}
}

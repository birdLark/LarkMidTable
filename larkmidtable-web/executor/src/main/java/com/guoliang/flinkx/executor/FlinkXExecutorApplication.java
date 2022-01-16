package com.guoliang.flinkx.executor;

import com.guoliang.flinkx.executor.nettyservice.LogServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class FlinkXExecutorApplication {

	public static void main(String[] args) {
		try {
			InetAddress addr =InetAddress.getLocalHost();
			String hostAddress = addr.getHostAddress();
			LogServiceImpl.startServer(hostAddress, 8990);
			SpringApplication.run(FlinkXExecutorApplication.class, args);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}

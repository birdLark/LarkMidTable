package com.guoliang.flinkx.admin.nettyclient;

/**
 *
 *
 * @Date: 2022/1/12 21:41
 * @Description:
 * @Author: LarkMidTable
 **/
public class ClientBootstrap {
	public static final String providerName = "UserService#sayHello#";

	public static void main(String[] args) throws InterruptedException {
		RpcConsumer consumer = new RpcConsumer();
		// 创建一个代理对象
		LogService service = (LogService) consumer.createProxy(LogService.class, providerName);
		System.out.println(service.getExecuteLog("userGuid.md"));
		Runtime.getRuntime().exit(0);

	}
}

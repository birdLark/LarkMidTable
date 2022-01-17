package com.larkmidtable.executor.nettyservice;

/**
 *
 *
 * @Date: 2022/1/13 14:12
 * @Description:
 * @Author: LarkMidTable
 **/
public class ServiceBootstrap {
	public static void main(String[] args) {
		LogServiceImpl.startServer("localhost", 8990);
	}
}

package com.guoliang.flinkx.admin.nettyclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 *
 *
 * @Date: 2022/1/12 21:40
 * @Description:
 * @Author: LarkMidTable
 **/
public class LogClientHandler extends ChannelInboundHandlerAdapter implements Callable {
	private ChannelHandlerContext context;
	private String result;
	private String para;

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		context = ctx;
	}

	/**
	 * 收到服务端数据，唤醒等待线程
	 */
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) {
		result = msg.toString();
		notify();
	}

	/**
	 * 写出数据，开始等待唤醒
	 */
	@Override
	public synchronized Object call() throws InterruptedException {
		context.writeAndFlush(para);
		wait();
		return result;
	}

	void setPara(String para) {
		this.para = para;
	}

}

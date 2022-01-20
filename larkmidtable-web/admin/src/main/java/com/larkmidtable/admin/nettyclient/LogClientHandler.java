package com.larkmidtable.admin.nettyclient;

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
	public static String result;
	private volatile boolean isComplete = false;
	private ChannelHandlerContext context;
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
		result += msg.toString();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		ctx.close();
		isComplete = true;
	}

	/**
	 * 写出数据，开始等待唤醒
	 */
	@Override
	public synchronized Object call() throws InterruptedException {
		context.writeAndFlush(para);
		while (!isComplete) {
			wait(1000);
		}
		return result;
	}

	void setPara(String para) {
		this.para = para;
	}

}

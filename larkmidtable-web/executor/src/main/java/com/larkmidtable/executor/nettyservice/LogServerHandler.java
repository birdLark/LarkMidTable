package com.larkmidtable.executor.nettyservice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 *
 * @Date: 2022/1/12 21:37
 * @Description:
 * @Author: LarkMidTable
 **/
public class LogServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 如何符合约定，则调用本地方法，返回数据
		if (msg.toString().startsWith("UserService")) {
			byte[] executeLog = new LogServiceImpl()
					.getExecuteLog(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
			//保存数据到mysql
			String logstr = new String(executeLog);
			ctx.writeAndFlush(logstr);
		}
	}
}

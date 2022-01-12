package nettyservice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 *
 * @Date: 2022/1/12 21:37
 * @Description:
 * @Author: LarkMidTable
 **/
public class UserServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 如何符合约定，则调用本地方法，返回数据
		if (msg.toString().startsWith("UserService")) {
			String result = new UserServiceImpl()
					.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
			ctx.writeAndFlush(result);
		}
	}
}

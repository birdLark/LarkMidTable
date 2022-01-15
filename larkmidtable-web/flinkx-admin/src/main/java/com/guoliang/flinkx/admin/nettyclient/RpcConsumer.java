package com.guoliang.flinkx.admin.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @Date: 2022/1/12 21:39
 * @Description:
 * @Author: LarkMidTable
 **/
public class RpcConsumer {
	private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private static LogClientHandler client;

	/**
	 * 创建一个代理对象
	 */
	public Object createProxy(final Class<?> serviceClass, final String providerName) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class<?>[]{serviceClass}, (proxy, method, args) -> {
					if (client == null) {
						initClient();
					}
					// 设置参数
					client.setPara(providerName + args[0]);
					return executor.submit(client).get();
				});

	}

	/**
	 * 初始化客户端
	 */
	private static void initClient() {
		client = new LogClientHandler();
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new StringDecoder());
						p.addLast(new StringEncoder());
						p.addLast(client);
					}
				});
		try {
			b.connect("localhost", 8990).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

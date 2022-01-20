package com.larkmidtable.executor.nettyservice;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 *
 * @Date: 2022/1/12 21:34
 * @Description:
 * @Author: LarkMidTable
 **/
public class LogServiceImpl implements LogService {
	@Override
	public byte[] getExecuteLog(String jobId) {
		// 通过流的方式读取
		byte[] fileContentBytes = getFileContentBytes(
				"/home/hadoop/flinkx-1.8_release/"+jobId);
		return fileContentBytes;
	}
	public static void startServer(String hostName, int port) {
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
			bootstrap.group(eventLoopGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new StringDecoder());
							p.addLast(new StringEncoder());
							p.addLast(new LogServerHandler());
						}
					});
			bootstrap.bind(hostName, port).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private byte[] getFileContentBytes(String filePath) {
		try (InputStream in = new FileInputStream(filePath);
				ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			byte[] logdetail = bos.toByteArray();
			if(bos !=null) {
				bos.close();
			}
			if(in != null) {
				in.close();
			}
			return logdetail;
		} catch (IOException e) {
//			logger.error("get file bytes error", e);
		}
		return new byte[0];
	}
}

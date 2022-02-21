package com.lark.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 *
 *
 * @Date: 2021/3/31 15:20
 * @Description:
 * @Author: LarkMidTable
 **/
public class RemoteExecuteUtil {
	// 字符编码默认是utf-8
	private static String DEFAULTCHART = "UTF-8";
	private Connection conn;
	private String ip;
	private String userName;
	private String userPwd;

	public RemoteExecuteUtil(String ip, String userName, String userPwd) {
		this.ip = ip;
		this.userName = userName;
		this.userPwd = userPwd;
	}


	public RemoteExecuteUtil() {

	}

	/**
	 * 远程登录linux主机
	 * @since V0.1
	 * @return 登录成功返回true，否则返回false
	 * @throws Exception
	 */
	public Boolean login() throws Exception {
		boolean flg = false;
		try {
			conn = new Connection(ip);
			// 连接
			conn.connect();
			// 认证
			flg = conn.authenticateWithPassword(userName, userPwd);
		} catch (IOException e) {
			throw new Exception("远程连接服务器失败", e);
		}
		return flg;
	}

	/**
	 * 远程执行shll脚本或者命令
	 * @param cmd 即将执行的命令
	 * @return 命令执行完后返回的结果值
	 * @throws Exception
	 * @since V0.1
	 */
	public String execute(String cmd) throws Exception {
		String result = "";
		Session session = null;
		try {
			if (login()) {
				// 打开一个会话
				session = conn.openSession();
				// 执行命令
				session.execCommand(cmd);
				result = processStdout(session.getStdout(), DEFAULTCHART);
				// 如果为输出为空，说明脚本执行出错了
				if (StringUtils.isBlank(result)) {
					result = processStdout(session.getStderr(), DEFAULTCHART);
				}
				conn.close();
				session.close();
			}
		} catch (IOException e) {
			throw new Exception("命令执行失败", e);
		} finally {
			if (conn != null) {
				conn.close();
			}
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 解析脚本执行返回的结果集
	 *
	 * @param in 输入流对象
	 * @param charset 编码
	 * @since V0.1
	 * @return 以纯文本的格式返回
	 * @throws Exception
	 */
	private String processStdout(InputStream in, String charset) throws Exception {
		InputStream stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(stdout, charset);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的编码字符集异常", e);
		} catch (IOException e) {
			throw new Exception("读取指纹失败", e);
		} finally {
			br.close();
			isr.close();
			stdout.close();
		}
		return buffer.toString();
	}

	public static void runCommond(String linuxIP,String usrName,String passwd,String filePath) throws Exception {

		RemoteExecuteUtil rec = new RemoteExecuteUtil(linuxIP, usrName, passwd);
		// 执行命令
//		System.out.println(rec.execute("ifconfig"));
		// 执行脚本
		rec.execute("sh "+filePath);
	}
}

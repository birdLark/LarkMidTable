package com.lark.util;

import com.jcraft.jsch.*;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 *
 *
 * @Date: 2021/3/31 14:54
 * @Description:
 * @Author: LarkMidTable
 **/
public class SFTPUtil {

	private ChannelSftp sftp;

	private Session session;
	/** SFTP 登录用户名*/
	private String username;
	/** SFTP 登录密码*/
	private String password;
	/** 私钥 */
	private String privateKey;
	/** SFTP 服务器地址IP地址*/
	private String host;
	/** SFTP 端口*/
	private int port;


	/**
	 * 构造基于密码认证的sftp对象
	 */
	public SFTPUtil(String username, String password, String host, int port) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * 构造基于秘钥认证的sftp对象
	 */
	public SFTPUtil(String username, String host, int port, String privateKey) {
		this.username = username;
		this.host = host;
		this.port = port;
		this.privateKey = privateKey;
	}

	public SFTPUtil(){}


	/**
	 * 连接sftp服务器
	 */
	public void login() throws JSchException {
		try {
			JSch jsch = new JSch();
			if (privateKey != null) {
				jsch.addIdentity(privateKey);// 设置私钥
			}

			session = jsch.getSession(username, host, port);

			if (password != null) {
				session.setPassword(password);
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();

			sftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接 server
	 */
	public void logout(){
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
		}
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
			}
		}
	}


	/**
	 * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
	 * @param basePath  服务器的基础路径
	 * @param directory  上传到该目录
	 * @param sftpFileName  sftp端文件名
	 * @param input   输入流
	 */
	public void upload(String basePath,String directory, String sftpFileName, InputStream input) throws SftpException{
		try {
			sftp.cd(basePath);
			sftp.cd(directory);
		} catch (SftpException e) {
			//目录不存在，则创建文件夹
			String [] dirs=directory.split("/");
			String tempPath=basePath;
			for(String dir:dirs){
				if(null== dir || "".equals(dir)) continue;
				tempPath+="/"+dir;
				try{
					sftp.cd(tempPath);
				}catch(SftpException ex){
					sftp.mkdir(tempPath);
					sftp.cd(tempPath);
				}
			}
		}
		sftp.put(input, sftpFileName);  //上传文件
	}


	/**
	 * 下载文件。
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile 存在本地的路径
	 */
	public void download(String directory, String downloadFile, String saveFile)
			throws SftpException, FileNotFoundException, FileNotFoundException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		File file = new File(saveFile);
		sftp.get(downloadFile, new FileOutputStream(file));
	}

	/**
	 * 下载文件
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件名
	 * @return 字节数组
	 */
	public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		InputStream is = sftp.get(downloadFile);

		byte[] fileData = IOUtils.toByteArray(is);

		return fileData;
	}


	/**
	 * 删除文件
	 * @param directory 要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 */
	public void delete(String directory, String deleteFile) throws SftpException{
		sftp.cd(directory);
		sftp.rm(deleteFile);
	}


	/**
	 * 列出目录下的文件
	 * @param directory 要列出的目录
	 */
	public Vector<?> listFiles(String directory) throws SftpException {
		return sftp.ls(directory);
	}

	/**
	 * 判断是否含有某文件
	 * @param src 文件路径
	 */
	public boolean isEntity(String src){
		try{
			sftp.get(src);
			return true;
		}catch (Exception e){
			return false;
		}
	}

	//上传文件测试
	public static void UploadJAR(String host,Integer port,String username,String password,String filePath,String fileLinuxName,String upbasePath,String upDirectory) throws SftpException, IOException, JSchException {
		SFTPUtil sftp = new SFTPUtil(username,password,host,port);
		sftp.login();
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);

		sftp.upload(upbasePath,upDirectory, fileLinuxName, is);

		/* Vector<?> ss = sftp.sftp.ls("/usr/local/java");
        for (Object s:ss) {
            System.out.println(s.toString());
        }
        ChannelSftp sftp1 = sftp.sftp;
        try{
            sftp1.get("usr/local/java/article_run.sh");
            System.out.println("有文件");
        }catch (Exception e){
            System.out.println("没有文件");
        }*/

		sftp.logout();
	}
}

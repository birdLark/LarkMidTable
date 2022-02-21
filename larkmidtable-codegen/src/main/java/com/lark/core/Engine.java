package com.lark.core;

import com.lark.template.JavaFileTemplate;
import com.lark.util.FlinkUtil;
import com.lark.util.ParamUtil;
import org.apache.maven.shared.invoker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;

public class Engine {
	public static String className = "Hello";
	public static String javaFilePath = Engine.getPath().concat("\\src\\main\\java\\GenCode\\").concat(className)
			.concat(".java");
	public static String shellPath = Engine.getPath().concat("\\shell\\test.sh");
	public static String pomPath = Engine.getPath().concat("\\pom.xml");
	public static String mavenPath = "D:\\maven\\apache-maven-3.6.1";
	public static String flinkPath = Engine.getPath().concat("\\target\\Lark-1.0-SNAPSHOT-jar-with-dependencies.jar");
	public static String host = "192.168.1.204";
	public static Integer port = 22;
	public static String username = "";
	public static String password = "";
	public static String uploadJarName = "Lark-1.0-SNAPSHOT-jar-with-dependencies.jar";
	public static String uploadShellName = "test.sh";
	public static String uploadBasePath = "/home";
	public static String uploadSecondPath = "/java_upload_test";
	public static String linuxShellPath = uploadBasePath.concat(uploadSecondPath).concat("/").concat(uploadShellName);
	public static String linuxJARPath = uploadBasePath.concat(uploadSecondPath).concat("/").concat(uploadJarName);

	private static final Logger logger = LoggerFactory.getLogger(Engine.class);

	public static void main(String[] args) throws Exception {
		ParamUtil param = ParamUtil.getParam(args);
		Engine engine = new Engine();
		engine.start(param);
	}

	public  void start(ParamUtil param) {

		// write java File
		Engine.writeJAVAFile(param, javaFilePath);
		// 同时将文件写入到其他的目录
		Engine.writeJAVAFile(param, javaFilePath);
		logger.info("The task to generate Flink was successful!!!");
		//		Engine.packageRunJAR(pomPath, mavenPath);
		//		Engine.genShellFile(shellPath,param.getModel());
		//		logger.info("The task of packing Flink was successful!!!");
		//		logger.info("Upload Flink tasks to the server!!!");
		//		SFTPUtil.UploadJAR(host, port, username, password, flinkPath, uploadJarName, uploadBasePath, uploadSecondPath);
		//		SFTPUtil.UploadJAR(host, port, username, password, shellPath, uploadShellName, uploadBasePath, uploadSecondPath);
		//		logger.info("Upload Flink tasks to the server successfully!!!");
		//		logger.info("Start submitting Flink's tasks!!!");
		//		RemoteExecuteUtil.runCommond(host, username, password, linuxShellPath);
		//		logger.info("The task to submit Flink was successful!!!");
	}

	private static void genShellFile(String shellPath, String model) throws IOException {
		FileWriter writer = new FileWriter(shellPath);
		if ("standalone".equals(model)) {
			writer.write(FlinkUtil.standaloneCommit.concat(linuxJARPath));
		} else {
			writer.write(FlinkUtil.yarnCommit.concat(linuxJARPath));
		}

		writer.flush();
		writer.close();
	}

	private static void packageRunJAR(String pomPath, String mavenPath) {
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(new File(pomPath));
		request.setGoals(Collections.singletonList("package"));
		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(mavenPath));
		try {
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get local Path
	 * @return
	 */
	public static String getPath() {
		String filePath = System.getProperty("user.dir");
		return filePath;
	}

	/**
	 * write java File
	 */
	public static boolean writeJAVAFile(ParamUtil param, String filePath) {
		String fileContent = JavaFileTemplate.getJavaFileContent(param);
		try {
			FileOutputStream fos = new FileOutputStream(filePath, false);
			byte[] b = fileContent.getBytes();
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

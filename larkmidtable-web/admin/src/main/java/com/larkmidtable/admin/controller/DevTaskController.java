package com.larkmidtable.admin.controller;

import com.alibaba.fastjson.JSON;
import com.larkmidtable.admin.entity.*;
import com.larkmidtable.admin.mapper.DevJarMapper;
import com.larkmidtable.admin.mapper.DevTaskMapper;
import com.larkmidtable.admin.util.DruidDataSource;
import com.larkmidtable.core.biz.model.ReturnT;
import com.larkmidtable.core.util.ProcessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/devTask")
@Api(tags = "数据开发-开发任务列表")
public class DevTaskController extends BaseController {

	@Resource
	private DevTaskMapper devTaskMapper;

	@ApiOperation("文件上传")
	@PostMapping("/upload")
	public ReturnT<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath) {
		saveFile(file, filePath);
		return ReturnT.SUCCESS;
	}

	@PostMapping(value ="/execute")
	@ApiOperation("执行SQL或者JAR任务")
	public ResponseData executeSql(@RequestBody DevTask devTask) {
		try {
			int id = devTask.getId();
			String flinkHome = "";
			String runtype = devTask.getRuntype();
			String run_param = devTask.getRun_param();
			String jarpath = devTask.getJarpath();
			String sql_text = devTask.getSql_text();
			String sqlPath ="";
			//生成脚本文件
			String shellPath ="";
			String cmdstr ="";
			if(!jarpath.equals("")) {
				shellPath = buildFlinkTextFile(flinkHome,run_param,jarpath);
			} else {
				shellPath =  buildFlinkSQLFile(flinkHome,run_param,sqlPath);
			}
			// 调用执行的脚本文件
			String[] cmdStrArray = buildFlinkExecutorCmd(shellPath);
			for (int j = 0; j < cmdStrArray.length; j++) {
				cmdstr += cmdStrArray[j] + " ";
			}
			final Process process = Runtime.getRuntime().exec(cmdstr);
			return ResponseData.successWithData("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseData.fail(e.getMessage());
		}
	}

	public static String buildFlinkSQLFile(String flinkShPath, String runParam,String jarPath) {

		return null;
	}

	public static String buildFlinkTextFile(String flinkShPath, String tmpFilePath,String sqlPath) {

		return null;
	}

	public static String[] buildFlinkExecutorCmd(String shellPath) {
		List<String> cmdArr = new ArrayList<>();
		cmdArr.add("python");
		cmdArr.add(shellPath);
		return cmdArr.toArray(new String[cmdArr.size()]);
	}

	private void saveFile(MultipartFile file, String filePath) {
		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename(); //获取上传文件原来的名称
			File temp = new File(filePath);
			if (!temp.exists()) {
				temp.mkdirs();
			}

			File localFile = new File(filePath + filename);
			try {
				file.transferTo(localFile); //把上传的文件保存至本地
				System.out.println(file.getOriginalFilename() + " 上传成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@ApiOperation("获取所有数据")
	@GetMapping("/list")
	public ReturnT<List<DevTask>> selectList() {
		// page list
		List<DevTask> list = devTaskMapper.findAll();
		return new ReturnT<> (list);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ApiOperation("删除数据")
	public ReturnT<String> delete(int id) {
		int result = devTaskMapper.delete(id);
		return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
	}

}

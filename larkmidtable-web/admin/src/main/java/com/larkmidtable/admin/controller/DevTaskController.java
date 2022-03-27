package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.entity.DevEnvSetting;
import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.entity.ResponseData;
import com.larkmidtable.admin.mapper.DevTaskMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/devTask")
@Api(tags = "数据开发-开发任务列表")
public class DevTaskController extends BaseController {

	@Resource
	private DevTaskMapper devTaskMapper;

	@ApiOperation("文件上传")
	@PostMapping("/upload")
	public ReturnT<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("tasktype")String tasktype) {
		try {
			saveFile(file,tasktype);
			return new ReturnT(ReturnT.SUCCESS_CODE,"文件上传成功！");
		}catch (Exception e ){
			return new ReturnT(ReturnT.FAIL_CODE,"文件上传失败！");
		}
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

	private void saveFile(MultipartFile file,String tasktype) throws IOException {
		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String path = devTaskMapper.findPath(tasktype);
			File temp = new File(path);
			if (!temp.exists()) {
				temp.mkdirs();
			}
			File localFile = new File(path+"/"+filename);
			localFile.delete();
			file.transferTo(localFile); //把上传的文件保存至本地
		}
	}

	@ApiOperation("获取所有数据")
	@GetMapping("/list")
	public ReturnT<Map<String, Object>> selectList(@RequestParam(value = "current", required = false, defaultValue = "1") int current,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "type", required = false) String type) {
		// page list
		List<DevTask> list = devTaskMapper.findList((current - 1) * size,size,type);
		Map<String, Object> maps = new HashMap<>();
		maps.put("recordsTotal", list.size());    // 过滤后的总记录数
		maps.put("data", list);                    // 分页列表
		return new ReturnT<>(maps);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ApiOperation("删除数据")
	public ReturnT<String> delete(int id) {
		int result = devTaskMapper.delete(id);
		return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
	}

}

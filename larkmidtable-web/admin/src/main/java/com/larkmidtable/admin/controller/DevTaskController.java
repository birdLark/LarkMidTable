package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.mapper.DevJarMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/devTask")
@Api(tags = "数据开发任务")
public class DevTaskController extends BaseController {

	@Resource
	private DevJarMapper devJarMapper;

	@ApiOperation("新增数据")
	@PostMapping("/add")
	public ReturnT<String> insert(HttpServletRequest request, @RequestBody DevTask entity) {
		entity.setCreate_time(new Date().toString());
		this.devJarMapper.save(entity);
		return ReturnT.SUCCESS;
	}

	@ApiOperation("文件上传")
	@PostMapping("/upload")
	public ReturnT<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("filePath") String filePath) {
		saveFile(file, filePath);
		return ReturnT.SUCCESS;
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
}

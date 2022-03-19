package com.larkmidtable.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.larkmidtable.admin.entity.BaseForm;
import com.larkmidtable.admin.entity.JobProject;
import com.larkmidtable.admin.service.JobRegistryService;
import com.larkmidtable.admin.entity.JobRegistry;
import com.larkmidtable.admin.util.PageUtils;
import com.larkmidtable.core.util.OSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobRegistry")
@Api(tags = "数据集成-执行器资源监控")
public class JobRegistryController extends BaseController {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private JobRegistryService jobRegistryService;

	@GetMapping("/list")
	@ApiOperation("分页查询所有数据")
	public R<IPage<JobRegistry>> selectAll(@RequestParam("size") Integer size,
										   @RequestParam("current") Integer current) {
		Page<JobRegistry> page = new Page(current, size);
		List<JobRegistry> jobRegistryList = new ArrayList<>();
		JobRegistry jobRegistry = new JobRegistry();
		jobRegistry.setRegistryKey("LarkMidTable数据中台");
		jobRegistry.setRegistryValue("localhost");
		jobRegistry.setMemoryUsage(OSUtils.memoryUsage());
		jobRegistry.setLoadAverage(OSUtils.loadAverage());
		jobRegistry.setCpuUsage(OSUtils.cpuUsage());
		jobRegistry.setUpdateTime(new Date());
		jobRegistryList.add(jobRegistry);
		page.setRecords(jobRegistryList);
		return success(page);
	}

	protected QueryWrapper<JobRegistry> pageQueryWrapperCustom(Map<String, Object> map) {
		// mybatis plus 分页相关的参数
		Map<String, Object> pageHelperParams = PageUtils.filterPageParams(map);
		//过滤空值，分页查询相关的参数
		Map<String, Object> columnQueryMap = PageUtils.filterColumnQueryParams(map);

		QueryWrapper<JobRegistry> queryWrapper = new QueryWrapper<>();

		//排序 操作
		pageHelperParams.forEach((k, v) -> {
			switch (k) {
				case "ascs":
					queryWrapper.orderByAsc(StrUtil.toUnderlineCase(StrUtil.toString(v)));
					break;
				case "descs":
					queryWrapper.orderByDesc(StrUtil.toUnderlineCase(StrUtil.toString(v)));
					break;
			}
		});

		columnQueryMap.forEach((k, v) -> {
			switch (k) {
				case "datasourceName":
					queryWrapper.like(StrUtil.toUnderlineCase(k), v);
					break;
				default:
					queryWrapper.eq(StrUtil.toUnderlineCase(k), v);
			}
		});

		return queryWrapper;
	}
}

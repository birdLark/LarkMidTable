package com.larkmidtable.admin.controller;

import com.larkmidtable.admin.service.JobService;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
@Api(tags = "首页接口")
@RequestMapping("/api")
public class IndexController {

    @Resource
    private JobService jobService;


    @GetMapping("/index")
    @ApiOperation("数据统计功能")
    public ReturnT<Map<String, Integer>> index() {
        return new ReturnT<>(jobService.dashboardInfo());
    }

	@GetMapping("/overview")
	@ApiOperation("数据统计功能")
	public ReturnT<String> overview() {
		// 创建 HttpClient 客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建 HttpGet 请求
		HttpGet httpGet = new HttpGet("http://125.77.159.153:8081/jobs/overview");
		// 设置长连接
		httpGet.setHeader("Connection", "keep-alive");
		// 设置代理（模拟浏览器版本）
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		// 设置 Cookie
		httpGet.setHeader("Cookie", "UM_distinctid=16442706a09352-0376059833914f-3c604504-1fa400-16442706a0b345; CNZZDATA1262458286=1603637673-1530123020-%7C1530123020; JSESSIONID=805587506F1594AE02DC45845A7216A4");

		CloseableHttpResponse httpResponse = null;
		try {
			// 请求并获得响应结果
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			// 输出请求结果
			return new ReturnT<String>(EntityUtils.toString(httpEntity));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 无论如何必须关闭连接
		finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ReturnT<String>("{}");
	}


	@PostMapping("/chartInfo")
    @ApiOperation("图表信息")
    public ReturnT<Map<String, Object>> chartInfo() {
        return jobService.chartInfo();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

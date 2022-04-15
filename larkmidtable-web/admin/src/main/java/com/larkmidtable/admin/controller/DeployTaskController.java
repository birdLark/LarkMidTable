package com.larkmidtable.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.larkmidtable.admin.entity.DeployTask;
import com.larkmidtable.admin.entity.DevTask;
import com.larkmidtable.admin.entity.ResponseData;
import com.larkmidtable.admin.mapper.DevTaskMapper;
import com.larkmidtable.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.flink.sql.parser.impl.FlinkSqlParserImpl;
import org.apache.flink.sql.parser.validate.FlinkSqlConformance;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.apache.calcite.avatica.util.Quoting.BACK_TICK;


@RestController
@RequestMapping("/api/deployTask")
@Api(tags = "数据开发-开发任务列表")
public class DeployTaskController extends BaseController {

	@ApiOperation("获取所有数据")
	@GetMapping("/list")
	public ReturnT<Map<String, Object>> selectList(@RequestParam(value = "current", required = false, defaultValue = "1") int current,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "type", required = false) String type) {

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
		List<DeployTask> list = new ArrayList<DeployTask>();
		try {
			// 请求并获得响应结果
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String entityJSON = EntityUtils.toString(httpEntity);
			JSONObject jsonObject =  JSON.parseObject(entityJSON);
			JSONArray jobs = jsonObject.getJSONArray("jobs");
			for(int i=0;i<jobs.size();i++) {
				DeployTask deployTask = new DeployTask();
				JSONObject deployJsonObject = jobs.getJSONObject(i);
				String running = deployJsonObject.getJSONObject("tasks").getString("running");
				String canceling = deployJsonObject.getJSONObject("tasks").getString("canceling");
				String canceled = deployJsonObject.getJSONObject("tasks").getString("canceled");
				String jid = deployJsonObject.get("jid").toString();
				String jobName = deployJsonObject.get("name").toString();
				String state = deployJsonObject.get("state").toString();
				Date date = new Date();
				date.setTime(Long.parseLong(deployJsonObject.get("start-time").toString()));
				String starttime = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
				String endtime = deployJsonObject.get("end-time").toString();
				String duration = secondToTime(Long.parseLong(deployJsonObject.get("duration").toString()));
				deployTask.setJid(jid);
				deployTask.setName(jobName);
				deployTask.setStatus(state);
				deployTask.setBegintime(starttime);
				deployTask.setEndtime(endtime);
				deployTask.setDuration(duration);
				deployTask.setTasknumber(running+"|"+canceling+"|"+canceled);

				list.add(deployTask);
			}
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
		Map<String, Object> maps = new HashMap<>();
		maps.put("recordsTotal", list.size());    // 过滤后的总记录数
		maps.put("data", list);                    // 分页列表
		return new ReturnT<>(maps);
	}

	@ApiOperation("验证SQL")
	@GetMapping("/checkSQL")
	public ResponseData checkSQL(
			@RequestParam(value = "sqlText", required = false) String sqlText
	) {
		try {
			parseFlinkSql(sqlText);
			return ResponseData.successWithMsg("验证成功");
		} catch (Exception e) {
			return ResponseData.fail("验证失败:"+e.getMessage());
		}
	}

	private static String secondToTime(long milliseconds) {

		final long day = TimeUnit.MILLISECONDS.toDays(milliseconds);

		final long hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));

		final long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));

		final long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));

		final long ms = TimeUnit.MILLISECONDS.toMillis(milliseconds)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));
		return day+"天,"+hours+"小时,"+minutes+"分钟,"+seconds+"秒";
	}

	public static List<String> parseFlinkSql(String sql) {
		List<String> sqlList = new ArrayList<>();
		if (sql != null && !sql.isEmpty()) {
			try {
				SqlParser parser = SqlParser.create(sql, SqlParser.configBuilder()
						.setParserFactory(FlinkSqlParserImpl.FACTORY)
						.setQuoting(BACK_TICK)
						.setUnquotedCasing(Casing.TO_LOWER)   //字段名统一转化为小写
						.setQuotedCasing(Casing.UNCHANGED)
						.setConformance(FlinkSqlConformance.DEFAULT)
						.build()
				);
				List<SqlNode> sqlNodeList = parser.parseStmtList().getList();

				if (sqlNodeList != null && !sqlNodeList.isEmpty()) {
					for (SqlNode sqlNode : sqlNodeList) {
						sqlList.add(sqlNode.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sqlList;
	}

	public static void main(String[] args) {
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
		List<DeployTask> list = new ArrayList<DeployTask>();
		try {
			// 请求并获得响应结果
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String entityJSON = EntityUtils.toString(httpEntity);
			JSONObject jsonObject =  JSON.parseObject(entityJSON);
			JSONArray jobs = jsonObject.getJSONArray("jobs");
			for(int i=0;i<jobs.size();i++) {
				DeployTask deployTask = new DeployTask();
				JSONObject deployJsonObject = jobs.getJSONObject(i);
				String running = deployJsonObject.getJSONObject("tasks").getString("running");
				String canceling = deployJsonObject.getJSONObject("tasks").getString("canceling");
				String canceled = deployJsonObject.getJSONObject("tasks").getString("canceled");
				String jid = deployJsonObject.get("jid").toString();
				String jobName = deployJsonObject.get("name").toString();
				String state = deployJsonObject.get("state").toString();
				Date date = new Date();
				date.setTime(Long.parseLong(deployJsonObject.get("start-time").toString()));
				String starttime = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
				String endtime = deployJsonObject.get("end-time").toString();
				String duration = secondToTime(Long.parseLong(deployJsonObject.get("duration").toString()));
				deployTask.setJid(jid);
				deployTask.setName(jobName);
				deployTask.setStatus(state);
				deployTask.setBegintime(starttime);
				deployTask.setEndtime(endtime);
				deployTask.setDuration(duration);
				deployTask.setTasknumber(running+"|"+canceling+"|"+canceled);
				list.add(deployTask);
			}
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
	}
}

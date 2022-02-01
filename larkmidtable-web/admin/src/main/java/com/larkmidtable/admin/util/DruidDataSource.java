package com.larkmidtable.admin.util;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import com.larkmidtable.admin.entity.JobDatasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 *
 * @Date: 2022/2/1 13:01
 * @Description:
 **/
public class DruidDataSource {
	public static Object executeSql(JobDatasource datasource, String sql, Map<String, Object> map) {
		DruidPooledConnection connection = null;
		try {

			connection = DruidDataSource.getPooledConnection(datasource);
			PreparedStatement statement = connection.prepareStatement(sql);
			//参数注入

			Iterator<Object> iter = map.values().iterator();
			int j = 1;
			while (iter.hasNext()) {
				statement.setObject(j, iter.next());
				j = j + 1;
			}
			boolean hasResultSet = statement.execute();
			if (hasResultSet) {
				ResultSet rs = statement.getResultSet();
				int columnCount = rs.getMetaData().getColumnCount();

				List<String> columns = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rs.getMetaData().getColumnLabel(i);
					columns.add(columnName);
				}
				List<JSONObject> list = new ArrayList<>();
				while (rs.next()) {
					JSONObject jo = new JSONObject();
					columns.stream().forEach(t -> {
						try {
							Object value = rs.getObject(t);
							jo.put(t, value);
						} catch (SQLException throwables) {
							throwables.printStackTrace();
						}
					});
					list.add(jo);
				}
				return list;
			} else {
				int updateCount = statement.getUpdateCount();
				return updateCount + " rows affected";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	public static DruidPooledConnection getPooledConnection(JobDatasource jds) throws SQLException {
		com.alibaba.druid.pool.DruidDataSource pool = getJdbcConnectionPool(jds);
		DruidPooledConnection connection = pool.getConnection();
		return connection;
	}

	public static com.alibaba.druid.pool.DruidDataSource getJdbcConnectionPool(JobDatasource ds) {
		com.alibaba.druid.pool.DruidDataSource druidDataSource = new com.alibaba.druid.pool.DruidDataSource();
		druidDataSource.setName(ds.getDatasourceName());
		druidDataSource.setUrl(ds.getJdbcUrl());
		druidDataSource.setUsername(AESUtil.decrypt(ds.getJdbcUsername()));
		druidDataSource.setPassword(AESUtil.decrypt(ds.getJdbcPassword()));
		druidDataSource.setDriverClassName(ds.getJdbcDriverClass());
		druidDataSource.setConnectionErrorRetryAttempts(3);       //失败后重连次数
		druidDataSource.setBreakAfterAcquireFailure(true);
		return druidDataSource;
	}
}

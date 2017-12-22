package com.wangx.shardingjdbc.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceUtil {
	private static final String URL_PREFIX = "jdbc:mysql://localhost:3306/";

	private static final String USER_NAME = "root";

	private static final String PASSWORD = "wang766";

	public static DataSource createDataSource(final String dataSourceName) {
		BasicDataSource result = new BasicDataSource();
		result.setDriverClassName("com.mysql.jdbc.Driver");
		result.setUrl(URL_PREFIX + dataSourceName);
		result.setUsername(USER_NAME);
		result.setPassword(PASSWORD);
		return result;
	}
}

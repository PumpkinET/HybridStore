package com.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLUtil {
	/*
	 * this class is used to open and get SQL connection
	 */
	private static Connection connection = null;
	public static String URL;
	public static String username;
	public static String password;

	public static Connection getConnection(String dbName) throws ClassNotFoundException, SQLException {
		if (connection == null || connection.isClosed()) {
			Class.forName("com.mysql.jdbc.Driver");
			if (Config.parseConfig()) {
				String url = MySQLUtil.URL + dbName;
				connection = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);
				return connection;
			} else
				return null;
		} else
			return connection;
	}
}

package com.server.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.server.model.Login;
import com.server.util.MySQLUtil;

public class AuthDAO {
	private String dbName;
	private Connection connection;
	
	public String getDbName() {
		return dbName;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		setConnection(getDbName());
		return connection;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public void setConnection(String dbName) throws ClassNotFoundException, SQLException {
		connection = MySQLUtil.getConnection(dbName);
	}
	
	public AuthDAO() {
	}
	
	public boolean login(Login login) {
		int count = 0;
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME='"
					+ login.getUsername() + "' AND PASSWORD='" + login.getPassword() + "'");

			while (rs.next())
				count++;

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count == 1;
	}
}

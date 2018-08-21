package com.server.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Category;
import com.server.util.MySQLUtil;

public class CategoriesDAO {
	private String dbName;// database name
	private Connection connection;// sql connection
	
	public String getDbName() {
		return dbName;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		setConnection();
		return connection;
	}
	
	public void setConnection() throws ClassNotFoundException, SQLException {
		connection = MySQLUtil.getConnection();
	}
	
	public CategoriesDAO() {
		try {
			connection = MySQLUtil.getConnection();//initialize db connection
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return array list of all categories in database
	 */
	public List<Category> getAll() {
		List<Category> temp = new ArrayList<Category>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CATEGORIES");
			while (rs.next())
				temp.add(new Category(rs.getString(1), rs.getString(2)));
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
}

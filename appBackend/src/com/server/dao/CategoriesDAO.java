package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Category;
import com.server.util.MySQLUtil;

public class CategoriesDAO {
	public static List<Category> getAll() {
		List<Category> temp = new ArrayList<Category>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CATEGORIES");
			while (rs.next())
				temp.add(new Category(rs.getString(1), rs.getString(2)));
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}
}

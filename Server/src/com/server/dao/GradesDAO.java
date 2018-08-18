package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.server.model.Grades;
import com.server.util.Config;
import com.server.util.MySQLUtil;

public class GradesDAO {
	public static ArrayList<Grades> getAll(String dbName) {
		ArrayList<Grades> temp = new ArrayList<Grades>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM GRADES");
			while (rs.next())
				temp.add(new Grades(rs.getInt(1), rs.getString(2)));

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

}

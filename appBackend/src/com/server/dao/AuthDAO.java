package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.server.android.LoginAndroid;
import com.server.model.Login;
import com.server.util.MySQLUtil;

public class AuthDAO {
	public static Login login(LoginAndroid login) {
		Login loginObj = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE USERNAME='" + login.getUsername()
					+ "' AND PASSWORD='" + login.getPassword() + "'");

			while (rs.next()) {
				loginObj = new Login(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return loginObj;
	}
}

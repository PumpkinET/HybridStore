package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.server.model.Login;
import com.server.util.MySQLUtil;

public class AuthDAO {
	public static boolean login(Login login) {
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME='" + login.getUsername()
					+ "' AND PASSWORD='" + login.getPassword() + "'");

			while (rs.next())
				count++;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return count == 1;
	}
}

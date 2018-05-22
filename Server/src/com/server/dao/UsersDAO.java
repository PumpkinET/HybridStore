package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Users;
import com.server.util.MySQLUtil;

public class UsersDAO {
	public static Users get(String username) {
		Users temp = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT USERNAME, GRADE, NAME, AGE, ADDRESS  FROM USERS WHERE USERNAME='" + username + "'");
			while (rs.next())
				temp = new Users(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static List<Users> getAll() {
		List<Users> temp = new ArrayList<Users>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERNAME, GRADE, NAME, AGE, ADDRESS  FROM USERS");
			while (rs.next())
				temp.add(new Users(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static boolean post(Users user) {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);

			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO USERS(USERNAME, GRADE, NAME, AGE, ADDRESS) VALUES(?,?,?,?,?)");

			stmt.setString(1, user.getUsername());
			stmt.setInt(2, user.getGrade());
			stmt.setString(3, user.getName());
			stmt.setInt(4, user.getAge());
			stmt.setString(5, user.getAddress());

			result = stmt.executeUpdate() == 1;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public static boolean put(Users user) {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);

			PreparedStatement stmt = con
					.prepareStatement("UPDATE USERS SET GRADE=?, NAME=?, AGE=?, ADDRESS=? WHERE USERNAME=?");

			stmt.setInt(1, user.getGrade());
			stmt.setString(2, user.getName());
			stmt.setInt(3, user.getAge());
			stmt.setString(4, user.getAddress());
			stmt.setString(5, user.getUsername());
			result = stmt.executeUpdate() == 1;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public static boolean delete(String username) {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);

			PreparedStatement stmt = con.prepareStatement("DELETE FROM USERS WHERE USERNAME=?");
			stmt.setString(1, username);
			result = stmt.executeUpdate() == 1;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

}

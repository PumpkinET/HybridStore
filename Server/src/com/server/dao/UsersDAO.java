package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.CRUDMessages;
import com.server.model.ErrorMessage;
import com.server.model.UserGrades;
import com.server.model.Users;
import com.server.util.Config;
import com.server.util.MySQLUtil;

public class UsersDAO {
	public static Users get(String dbName, String username) {
		Users temp = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT USERNAME, PASSWORD, EMAIL, GRADE, NAME, AGE, ADDRESS, ID FROM USERS WHERE USERNAME='"
							+ username + "'");
			while (rs.next())
				temp = new Users(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8));

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static List<Users> getAll(String dbName) {
		List<Users> temp = new ArrayList<Users>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.GRADE, USERS.NAME, USERS.AGE, USERS.ADDRESS, USERS.ID, GRADES.VALUE FROM USERS INNER JOIN GRADES ON USERS.GRADE = GRADES.ID");
			while (rs.next())
				temp.add(new Users(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9)));
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static ErrorMessage post(String dbName, Users user) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO USERS(USERNAME, PASSWORD, EMAIL, GRADE, NAME, AGE, ADDRESS, ID) VALUES(?,?,?,?,?,?,?,?)");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setInt(4, user.getGrade());
			stmt.setString(5, user.getName());
			stmt.setInt(6, user.getAge());
			stmt.setString(7, user.getAddress());
			stmt.setString(8, user.getId());

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.add);

			stmt.close();
			con.close();
		} catch (Exception e) {
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	public static ErrorMessage put(String dbName, Users user) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement(
					"UPDATE USERS SET PASSWORD=?, EMAIL=?, GRADE=?, NAME=?, AGE=?, ADDRESS=?, ID=? WHERE USERNAME=?");

			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getEmail());
			stmt.setInt(3, user.getGrade());
			stmt.setString(4, user.getName());
			stmt.setInt(5, user.getAge());
			stmt.setString(6, user.getAddress());
			stmt.setString(7, user.getId());
			stmt.setString(8, user.getUsername());

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.update);

			stmt.close();
			con.close();
		} catch (Exception e) {
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	public static ErrorMessage delete(String dbName, String username) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement("DELETE FROM USERS WHERE USERNAME=?");
			stmt.setString(1, username);
			
			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.remove);

			stmt.close();
			con.close();
		} catch (Exception e) {
			result.setErrorMessage(e.toString());
		}
		return result;
	}
	
	public static ArrayList<UserGrades> getUsersList(String dbName) {
		ArrayList<UserGrades> users = new ArrayList<UserGrades>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERNAME, GRADE FROM USERS");
			while (rs.next())
				users.add(new UserGrades(rs.getString(1), rs.getInt(2)));

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return users;
	}

}

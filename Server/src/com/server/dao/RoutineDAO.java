package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Routine;
import com.server.util.Config;
import com.server.util.MySQLUtil;

public class RoutineDAO {

	public static Routine get(int id) {
		Routine temp = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ROUTINE WHERE id=" + id);
			while (rs.next())
				temp = new Routine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static List<Routine> getAll() {
		List<Routine> temp = new ArrayList<Routine>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ROUTINE");
			while (rs.next())
				temp.add(new Routine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6)));
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static boolean post(Routine routine) {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO ROUTINE (ADMINUSER,TARGETUSER,TITLE, STARTDATE,ENDDATE) VALUES (?,?,?,?,?)");

			stmt.setString(1, routine.getAdminuser());
			stmt.setString(2, routine.getTargetuser());
			stmt.setString(3, routine.getTitle());
			stmt.setString(4, routine.getStartDate());
			stmt.setString(5, routine.getEndDate());

			result = stmt.executeUpdate() == 1;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public static boolean put(Routine routine) {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement(
					"UPDATE ROUTINE SET ADMINUSER=?, TARGETUSER=?, TITLE=?, START=?, END=? WHERE ROUTINE=?");

			stmt.setString(1, routine.getAdminuser());
			stmt.setString(2, routine.getTargetuser());
			stmt.setString(3, routine.getTitle());
			stmt.setString(4, routine.getStartDate());
			stmt.setString(5, routine.getEndDate());
			result = stmt.executeUpdate() == 1;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public static boolean delete(int id) {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement("DELETE FROM ROUTINE WHERE ID=?");
			stmt.setInt(1, id);
			result = stmt.executeUpdate() == 1;

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

}

package com.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.CRUDMessages;
import com.server.model.ErrorMessage;
import com.server.model.UserGrades;
import com.server.model.Users;
import com.server.util.MySQLUtil;

public class UsersDAO {
	private String dbName;// database name
	private Connection connection;// sql connection

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

	public UsersDAO() {
	}

	/**
	 * @param username
	 *            to specify which user to select in the database
	 * @return object of user details
	 */
	public Users get(String username) {
		Users temp = null;
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT USERNAME, PASSWORD, EMAIL, GRADE, NAME, AGE, ADDRESS, ID FROM USERS WHERE USERNAME='"
							+ username + "'");
			while (rs.next())
				temp = new Users(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8));

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * @return list of all the users in the database
	 */
	public List<Users> getAll() {
		List<Users> temp = new ArrayList<Users>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.GRADE, USERS.NAME, USERS.AGE, USERS.ADDRESS, USERS.ID, GRADES.VALUE FROM USERS INNER JOIN GRADES ON USERS.GRADE = GRADES.ID");
			while (rs.next())
				temp.add(new Users(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9)));
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * @param user
	 *            to specify what user to insert into the database
	 * @return error message with results
	 */
	public ErrorMessage post(Users user) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
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
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	/**
	 * @param user
	 *            to specify which user to update in the database
	 * @return error message with results
	 */
	public ErrorMessage put(Users user) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
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
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	/**
	 * @param username
	 *            to specify which user to delete from the database
	 * @return error message with results
	 */
	public ErrorMessage delete(String username) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM USERS WHERE USERNAME=?");
			stmt.setString(1, username);

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.remove);

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	/**
	 * 
	 * @return array list of all the users select only username and grade
	 */
	public ArrayList<UserGrades> getUsersList() {
		ArrayList<UserGrades> users = new ArrayList<UserGrades>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERNAME, GRADE FROM USERS");
			while (rs.next())
				users.add(new UserGrades(rs.getString(1), rs.getInt(2)));

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
}

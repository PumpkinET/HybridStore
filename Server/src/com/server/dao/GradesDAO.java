package com.server.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.server.model.Grades;
import com.server.util.MySQLUtil;

public class GradesDAO {
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

	public GradesDAO() {
	}

	/**
	 * @return array list of all grades
	 */
	public ArrayList<Grades> getAll() {
		ArrayList<Grades> temp = new ArrayList<Grades>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM GRADES");
			while (rs.next())
				temp.add(new Grades(rs.getInt(1), rs.getString(2)));

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
}

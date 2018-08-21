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
import com.server.model.Routine;
import com.server.util.MySQLUtil;

public class RoutineDAO {
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

	public RoutineDAO() {
	}

	/**
	 * @return array list of all routine dates in database
	 */
	public List<Routine> getAll() {
		List<Routine> temp = new ArrayList<Routine>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ROUTINE");
			while (rs.next())
				temp.add(new Routine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6)));
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * @param routine
	 *            to specify what routine to insert into the database
	 * @return error message with results
	 */
	public ErrorMessage post(Routine routine) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
					"INSERT INTO ROUTINE (ADMINUSER,TARGETUSER,TITLE, STARTDATE,ENDDATE) VALUES (?,?,?,?,?)");

			stmt.setString(1, routine.getAdminuser());
			stmt.setString(2, routine.getTargetuser());
			stmt.setString(3, routine.getTitle());
			stmt.setString(4, routine.getStartDate());
			stmt.setString(5, routine.getEndDate());

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.add);

			stmt.close();
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	/**
	 * @param routine
	 *            to specify which routine to update from the database
	 * @return error message with results
	 */
	public ErrorMessage put(Routine routine) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
					"UPDATE ROUTINE SET ADMINUSER=?, TARGETUSER=?, TITLE=?, START=?, END=? WHERE ROUTINE=?");

			stmt.setString(1, routine.getAdminuser());
			stmt.setString(2, routine.getTargetuser());
			stmt.setString(3, routine.getTitle());
			stmt.setString(4, routine.getStartDate());
			stmt.setString(5, routine.getEndDate());

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
}

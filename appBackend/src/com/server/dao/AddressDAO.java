package com.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import com.server.android.AddressAndroid;
import com.server.android.OrderAndroid;
import com.server.model.CRUDMessages;
import com.server.model.ErrorMessage;
import com.server.model.Order;
import com.server.util.MySQLUtil;

public class AddressDAO {
	private String dbName;// database name
	private Connection connection;// sql connection
	
	public String getDbName() {
		return dbName;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		setConnection();
		return connection;
	}
	
	public void setConnection() throws ClassNotFoundException, SQLException {
		connection = MySQLUtil.getConnection();
	}
	
	public AddressDAO() {
		try {
			connection = MySQLUtil.getConnection();//initialize db connection
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * register new order
	 * @param address
	 * @return error message with results
	 */
	public ErrorMessage register_address(AddressAndroid address) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
					"INSERT INTO ADDRESS(EMAIL, FULLNAME, STREETADD, COUNTRY, CITY, POSTALCODE, PHONENUMBER) VALUES(?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE FULLNAME=VALUES(FULLNAME), STREETADD=VALUES(STREETADD), COUNTRY=VALUES(COUNTRY), CITY=VALUES(CITY), POSTALCODE=VALUES(POSTALCODE), PHONENUMBER=VALUES(PHONENUMBER)");
			stmt.setString(1, address.getEmail());
			stmt.setString(2, address.getFullname());
			stmt.setString(3, address.getStreetAdd());
			stmt.setString(4, address.getCountry());
			stmt.setString(5, address.getCity());
			stmt.setString(6, address.getPostalCode());
			stmt.setString(7, address.getPhonenumber());
			
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

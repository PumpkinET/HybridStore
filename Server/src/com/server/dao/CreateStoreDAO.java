package com.server.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.server.model.CreateStore;
import com.server.util.MySQLUtil;

public class CreateStoreDAO {
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

	public CreateStoreDAO() {
	}

	/**
	 * this function is used to generate field type in SQL syntax
	 * 
	 * @param target
	 * @return field string based on target type
	 */
	public static String fieldType(int target) {
		String str = null;
		switch (target) {
		case 0:
			str = " VARCHAR(50)";
			break;
		case 1:
			str = " INT(11) DEFAULT '0'";
			break;
		case 2:
			str = " VARCHAR(999)";
			break;
		case 3:
			str = " DATE";
			break;
		case 4: 
			str = " FLOAT";
			break;
		}
		return str;
	}

	/**
	 * this function is used to generate create schema in SQL syntax
	 * 
	 * @param dbName
	 * @return create schema string
	 */
	public String createSchema(String dbName) {
		try {
			Statement stmt = getConnection().createStatement();
			stmt.executeUpdate("CREATE DATABASE " + dbName);
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbName;
	}

	/**
	 * this function is used to execute basic table queries 
	 * routine(id, adminuser, targetuser, title, startDate, endDate) 
	 * users (username, password, email,
	 * grade, name, age, address, id) grades (id, value)
	 */
	public void createDefaultTables() {
		String routine = "CREATE TABLE routine (" + "id INT(11) NOT NULL AUTO_INCREMENT," + "adminuser VARCHAR(45),"
				+ "targetuser VARCHAR(45)," + "title VARCHAR(45)," + "startDate VARCHAR(45)," + "endDate VARCHAR(45),"
				+ "PRIMARY KEY(id))";

		String users = "CREATE TABLE users (" + "username varchar(45) NOT NULL, "
				+ "password varchar(45) DEFAULT NULL, " + "email varchar(45) DEFAULT NULL, "
				+ "grade int(11) DEFAULT NULL, " + "name varchar(45) DEFAULT NULL, " + "age int(11) DEFAULT NULL, "
				+ "address varchar(500) DEFAULT NULL, " + "id varchar(45) DEFAULT NULL, " + "PRIMARY KEY (username))";

		String grades = "CREATE TABLE GRADES (id int(11) NOT NULL, value varchar(45) DEFAULT NULL, PRIMARY KEY (id))";
		String grades_values = "INSERT INTO GRADES(ID, VALUE) VALUES(0, 'employee'),(1, 'adminstrator'),(2, 'owner')";
		String admin = "INSERT INTO USERS(USERNAME, PASSWORD, GRADE) VALUES('Admin', 'Admin', 2)";

		try {
			Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(routine);
			stmt.executeUpdate(users);
			stmt.executeUpdate(grades);
			stmt.executeUpdate(grades_values);
			stmt.executeUpdate(admin);
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function is used to execute different SQL queries to generate store
	 * 
	 * @param store
	 */
	public void createItemsTable(CreateStore store) {
		this.setDbName("");
		createSchema(store.getStoreName());

		this.setDbName(store.getStoreName());
		createDefaultTables();
		String item = "CREATE TABLE ITEM (";

		for (int i = 0; i < store.getFields().length; i++)
			item += store.getFields()[i].getName() + fieldType(store.getFields()[i].getType()) + " NOT NULL" + ",";

		item += "PRIMARY KEY (id))";

		try {
			Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(item);

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

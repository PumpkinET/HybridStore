package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.server.model.CreateStore;

public class CreateStoreDAO {
	public static String createSchema(String dbName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String userName = "root";
			String password = "4a5awhat";
			String url = "jdbc:mysql://localhost:3306/";
			Connection connection = DriverManager.getConnection(url, userName, password);
			String sql = "CREATE DATABASE " + dbName;
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbName;
	}

	public static void createDefaultTables(String dbName) {
		String routine = "CREATE TABLE routine (" + "id INT(11) NOT NULL AUTO_INCREMENT," + "adminuser VARCHAR(45),"
				+ "targetuser VARCHAR(45)," + "title VARCHAR(45)," + "startDate VARCHAR(45)," + "endDate VARCHAR(45),"
				+ "PRIMARY KEY(id))";

		String users = "CREATE TABLE users (" + "username varchar(45) NOT NULL, "
				+ "password varchar(45) DEFAULT NULL, " + "email varchar(45) DEFAULT NULL, "
				+ "grade int(11) DEFAULT NULL, " + "name varchar(45) DEFAULT NULL, " + "age int(11) DEFAULT NULL, "
				+ "address varchar(500) DEFAULT NULL, " + "id varchar(45) DEFAULT NULL, " + "PRIMARY KEY (username))";

		String admin = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES('Admin', 'Admin')";

		try {
			String userName = "root";
			String password = "4a5awhat";
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection connection = DriverManager.getConnection(url, userName, password);

			Statement statement = connection.createStatement();
			statement.executeUpdate(routine);
			statement.close();

			statement = connection.createStatement();
			statement.executeUpdate(users);
			statement.close();

			statement = connection.createStatement();
			statement.executeUpdate(admin);
			statement.close();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String fieldType(int target) {
		String str = null;
		switch (target) {
		case 0:
			str = " VARCHAR(50)";
			break;
		case 1:
			str = " INT(11)";
			break;
		case 2:
			str = " VARCHAR(999)";
			break;
		case 3:
			str = " DATE";
			break;
		}
		return str;
	}

	public static void createItemsTable(CreateStore store) {

		createSchema(store.getStoreName());
		createDefaultTables(store.getStoreName());

		String item = "CREATE TABLE ITEM (";
		for (int i = 0; i < store.getFields().length; i++) {
			item += store.getFields()[i].getName() + fieldType(store.getFields()[i].getType()) + " NOT NULL" + ",";
		}

		item += "PRIMARY KEY (id))";

		System.out.println(item);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String userName = "root";
			String password = "4a5awhat";
			String url = "jdbc:mysql://localhost:3306/" + store.getStoreName();
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement();
			statement.executeUpdate(item);
			statement.close();

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

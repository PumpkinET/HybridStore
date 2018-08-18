package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.server.model.AddItem;
import com.server.model.CRUDMessages;
import com.server.model.ErrorMessage;
import com.server.model.Item;
import com.server.model.Items;
import com.server.util.Config;
import com.server.util.MySQLUtil;

public class ItemsDAO {

	public static Items getAll(String dbName) {
		Items item = new Items();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);
			Statement stmt = con.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery("SELECT * FROM ITEM");

			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnName(i);
				item.addCol(name);
			}
			stmt.close();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM ITEM");
			while (rs.next()) {
				ArrayList<Object> obj = new ArrayList<Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					obj.add(rs.getObject(i));
				}
				item.addItem(obj);
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return item;
	}

	public static ArrayList<Item> getCartHistory(String dbName, String itemsString) {
		ArrayList<Item> item = new ArrayList<Item>();
		String itemsArray[] = itemsString.split(",");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			Statement stmt = con.createStatement();
			String patent = "";

			for (int i = 1; i < itemsArray.length; i++) {
				System.out.println(itemsArray[i]);
				patent += " OR id=" + itemsArray[i];
			}

			ResultSet rs = stmt.executeQuery(
					"SELECT id, title, image, description, price FROM ITEM WHERE id=" + itemsArray[0] + patent);

			while (rs.next()) {
				item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return item;
	}

	public static ArrayList<Item> getStoreItems(String dbName) {
		ArrayList<Item> item = new ArrayList<Item>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, title, image, description, price FROM ITEM");

			while (rs.next()) {
				item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return item;
	}

	public static ErrorMessage delete(String dbName, int id) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			PreparedStatement stmt = con.prepareStatement("DELETE FROM ITEM WHERE ID=?");
			stmt.setInt(1, id);
			
			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.remove);
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	public static ErrorMessage post(String dbName, AddItem[] item) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			String str = "";
			String hmm = "";
			for (int i = 1; i < item.length; i++) {
				str += "," + item[i].column;
				hmm += ",?";
			}

			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO ITEM(" + item[0].column + str + ") VALUES(?" + hmm + ")");

			for (int i = 0; i < item.length; i++) {
				stmt.setObject(i + 1, item[i].value);
			}
			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.add);
			stmt.close();
			con.close();
		} catch (Exception e) {
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	public static ErrorMessage put(String dbName, AddItem[] item) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Config.parseConfig();
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			Connection con = DriverManager.getConnection(url, MySQLUtil.username, MySQLUtil.password);

			String str = "";
			for (int i = 1; i < item.length; i++) {
				if (i == item.length - 1)
					str += item[i].column + "=?";
				else
					str += item[i].column + "=? ,";
			}

			PreparedStatement stmt = con.prepareStatement("UPDATE ITEM SET " + str + " WHERE " + item[0].column + "=?");

			for (int i = 1; i < item.length; i++) {
				stmt.setObject(i, item[i].value);
			}
			stmt.setObject(item.length, item[0].value);

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.update);

			stmt.close();
			con.close();
		} catch (Exception e) {
			result.setErrorMessage(e.toString());
		}
		return result;
	}
}

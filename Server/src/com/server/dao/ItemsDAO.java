package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

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
			
			for(int i = 1; i<itemsArray.length; i++) {
				System.out.println(itemsArray[i]);
				patent += " OR id="+itemsArray[i];
			}
			
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, image, description, price FROM ITEM WHERE id="+itemsArray[0] + patent);
			
			
			
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
}

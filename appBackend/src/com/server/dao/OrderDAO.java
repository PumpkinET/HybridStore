package com.server.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.server.android.OrderAndroid;
import com.server.model.Order;
import com.server.util.MySQLUtil;

public class OrderDAO {
	public static ArrayList<OrderAndroid> getOrderByShop(String shopName) {
		ArrayList<OrderAndroid> orders = new ArrayList<OrderAndroid>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE SHOPNAME='" + shopName + "'");

			while (rs.next()) {
				orders.add(new OrderAndroid(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11)));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return orders;
	}

	public static ArrayList<Order> getOrder(String email) {
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT SHOP.SHOPNAME, SHOP.SHOPTHUMBNAIL, SHOP.SHOPIP, ORDERS.TOTALPRICE, ORDERS.STREET_ADR, ORDERS.COUNTRY, ORDERS.CITY, ORDERS.POSTALCODE, ORDERS.ITEMS  FROM ORDERS INNER JOIN SHOP ON  ORDERS.SHOPNAME = SHOP.SHOPNAME WHERE ORDERS.EMAIL='"
							+ email + "'");

			while (rs.next()) {
				orders.add(new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return orders;
	}

	public static void register_order(OrderAndroid order) throws IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO ORDERS(EMAIL, SHOPNAME, FIRSTNAME, LASTNAME, STREET_ADR, COUNTRY, CITY, POSTALCODE, TOTALPRICE, ITEMS) VALUES(?,?,?,?,?,?,?,?,?,?)");

			stmt.setString(1, order.getEmail());
			stmt.setString(2, order.getShopName());
			stmt.setString(3, order.getFirstName());
			stmt.setString(4, order.getLastName());
			stmt.setString(5, order.getStreetAdd());
			stmt.setString(6, order.getCountry());
			stmt.setString(7, order.getCity());
			stmt.setString(8, order.getPostalCode());
			stmt.setString(9, order.getTotalPrice());
			stmt.setString(10, order.getItems());
			stmt.executeUpdate();

			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

package com.server.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import com.server.android.OrderAndroid;
import com.server.model.Order;
import com.server.util.MySQLUtil;

public class OrderDAO {
	private String dbName;
	private Connection connection;
	
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
	
	public OrderDAO() {
		try {
			connection = MySQLUtil.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<OrderAndroid> getOrderByShop(String shopName, String startYear, String endYear) {
		ArrayList<OrderAndroid> orders = new ArrayList<OrderAndroid>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = null;
			
			if(startYear != null && endYear != null)
			{
				rs = stmt.executeQuery(
						"SELECT ORDERS.ID, ORDERS.EMAIL, ORDERS.SHOPNAME, ORDERS.FIRSTNAME, ORDERS.LASTNAME, ORDERS.STREET_ADR, ORDERS.COUNTRY, ORDERS.CITY, ORDERS.POSTALCODE, ORDERS.TOTALPRICE, ORDERS.ITEMS, ORDERS.STATUS, STATUS.VALUE, ORDERS.DATE FROM ORDERS INNER JOIN STATUS ON ORDERS.STATUS = STATUS.ID  WHERE SHOPNAME='"
								+ shopName + "' AND ORDERS.DATE BETWEEN '" + startYear + "' AND '" + endYear +"'");
			}
			else if(startYear != null && endYear == null) {
				rs = stmt.executeQuery(
						"SELECT ORDERS.ID, ORDERS.EMAIL, ORDERS.SHOPNAME, ORDERS.FIRSTNAME, ORDERS.LASTNAME, ORDERS.STREET_ADR, ORDERS.COUNTRY, ORDERS.CITY, ORDERS.POSTALCODE, ORDERS.TOTALPRICE, ORDERS.ITEMS, ORDERS.STATUS, STATUS.VALUE, ORDERS.DATE FROM ORDERS INNER JOIN STATUS ON ORDERS.STATUS = STATUS.ID  WHERE SHOPNAME='"
								+ shopName + "' AND ORDERS.DATE BETWEEN '" + startYear + "-1-1' AND '" + startYear +"-12-31'");
			}
			else
				rs = stmt.executeQuery(
						"SELECT ORDERS.ID, ORDERS.EMAIL, ORDERS.SHOPNAME, ORDERS.FIRSTNAME, ORDERS.LASTNAME, ORDERS.STREET_ADR, ORDERS.COUNTRY, ORDERS.CITY, ORDERS.POSTALCODE, ORDERS.TOTALPRICE, ORDERS.ITEMS, ORDERS.STATUS, STATUS.VALUE, ORDERS.DATE FROM ORDERS INNER JOIN STATUS ON ORDERS.STATUS = STATUS.ID  WHERE SHOPNAME='"
								+ shopName + "'");
				
				
			while (rs.next()) {
				orders.add(new OrderAndroid(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getInt(12), rs.getString(13), rs.getDate(14)));
			}
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	public ArrayList<Order> getOrder(String email) {
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT SHOP.SHOPNAME, SHOP.SHOPTHUMBNAIL, SHOP.SHOPIP, ORDERS.TOTALPRICE, ORDERS.STREET_ADR, ORDERS.COUNTRY, ORDERS.CITY, ORDERS.POSTALCODE, ORDERS.ITEMS, ORDERS.STATUS  FROM ORDERS INNER JOIN SHOP ON  ORDERS.SHOPNAME = SHOP.SHOPNAME WHERE ORDERS.EMAIL='"
							+ email + "'");

			while (rs.next()) {
				orders.add(
						new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10)));
			}
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	public boolean put(String dbName, OrderAndroid order) {
		boolean result = false;
		try {
			PreparedStatement stmt = getConnection().prepareStatement("UPDATE ORDERS SET STATUS=? WHERE ID=?");

			stmt.setInt(1, order.getStatus());
			stmt.setInt(2, Integer.parseInt(order.getId()));
			
			result = stmt.executeUpdate() == 1;

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void register_order(OrderAndroid order) throws IOException {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
					"INSERT INTO ORDERS(EMAIL, SHOPNAME, FIRSTNAME, LASTNAME, STREET_ADR, COUNTRY, CITY, POSTALCODE, TOTALPRICE, ITEMS, DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?)");

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
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			
			stmt.setDate(11, date);
			stmt.executeUpdate();

			stmt.close();
			getConnection().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

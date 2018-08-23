package com.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Shop;
import com.server.util.MySQLUtil;

public class ShopDAO {
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
	
	public ShopDAO() {
		try {
			connection = MySQLUtil.getConnection();//initialize db connection
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param category
	 * @return array list of filtered items by category
	 */
	public List<Shop> getAll(String category) {
		List<Shop> temp = new ArrayList<Shop>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs;
			if (category.equals("All"))
				rs = stmt.executeQuery("SELECT * FROM SHOP");
			else
				rs = stmt.executeQuery("SELECT * FROM SHOP WHERE CATEGORY='" + category + "'");
			while (rs.next())
				temp.add(new Shop(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8)));
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public void createStore(Shop shop) {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(
					"INSERT INTO SHOP(SHOPNAME, SHOPOWNER, SHOPTHUMBNAIL, SHOPDESCRIPTION, SHOPIP, CATEGORY) VALUES(?,?,?,?,?,?)");

			stmt.setString(1, shop.getShopName());
			stmt.setString(2, shop.getShopOwner());
			stmt.setString(3, shop.getShopThumbnail());
			stmt.setString(4, shop.getShopDescription());
			stmt.setString(5, shop.getShopIp());
			stmt.setString(6, shop.getShopCategory());

			stmt.executeUpdate();
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

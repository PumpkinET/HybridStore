package com.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Shop;
import com.server.util.MySQLUtil;

public class ShopDAO {
	public static List<Shop> getAll(String category) {
		List<Shop> temp = new ArrayList<Shop>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);
			Statement stmt = con.createStatement();
			ResultSet rs;
			if (category.equals("All"))
				rs = stmt.executeQuery("SELECT * FROM SHOP");
			else
				rs = stmt.executeQuery("SELECT * FROM SHOP WHERE CATEGORY='" + category + "'");
			while (rs.next())
				temp.add(new Shop(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6)));
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	public static void createStore(Shop shop) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(MySQLUtil.URL, MySQLUtil.Username, MySQLUtil.Password);

			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO SHOP(SHOPNAME, SHOPOWNER, SHOPTHUMBNAIL, SHOPDESCRIPTION, SHOPIP, CATEGORY) VALUES(?,?,?,?,?,?)");

			stmt.setString(1, shop.getShopName());
			stmt.setString(2, shop.getShopOwner());
			stmt.setString(3, shop.getShopThumbnail());
			stmt.setString(4, shop.getShopDescription());
			stmt.setString(5, shop.getShopIp());
			stmt.setString(6, shop.getShopCategory());

			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

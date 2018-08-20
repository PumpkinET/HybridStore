package com.server.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import com.server.android.LoginAndroid;
import com.server.android.RegisterAndroid;
import com.server.model.Login;
import com.server.util.MySQLUtil;

public class AuthDAO {
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
	
	public AuthDAO() {
		try {
			connection = MySQLUtil.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Login login(LoginAndroid login) {
		Login loginObj = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE EMAIL='" + login.getEmail() + "' AND PASSWORD='"
					+ login.getPassword() + "'");

			while (rs.next()) {
				loginObj = new Login(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginObj;
	}

	public void register(RegisterAndroid register) throws IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			PreparedStatement stmt = getConnection()
					.prepareStatement("INSERT INTO USERS(EMAIL, PASSWORD, AVATAR, NAME) VALUES(?,?,?,?)");

			stmt.setString(1, register.getEmail());
			stmt.setString(2, register.getPassword());
			stmt.setString(3, "http://10.0.0.21/android/profile/" + register.getEmail());
			stmt.setString(4, register.getName());

			stmt.executeUpdate();

			stmt.close();
			getConnection().close();

			String dataString = register.getImageString();
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(dataString);
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			File outputfile = new File("C:\\AppServ\\www\\android\\profile\\" + register.getEmail());
			ImageIO.write(image, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(RegisterAndroid register) throws IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			PreparedStatement stmt = getConnection().prepareStatement("UPDATE USERS SET PASSWORD=?, NAME=? WHERE EMAIL=?");

			stmt.setString(1, register.getPassword());
			stmt.setString(2, register.getName());
			stmt.setString(3, register.getEmail());
			stmt.executeUpdate();

			stmt.close();
			getConnection().close();

			if (register.getImageString() != null) {
				String dataString = register.getImageString();
				byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(dataString);
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
				File outputfile = new File("C:\\AppServ\\www\\android\\profile\\" + register.getEmail());
				ImageIO.write(image, "png", outputfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.server.model.AddItem;
import com.server.model.CRUDMessages;
import com.server.model.ErrorMessage;
import com.server.model.Item;
import com.server.model.Items;
import com.server.util.MySQLUtil;

public class ItemsDAO {
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

	public ItemsDAO() {
	}

	/**
	 * @return all items in database including their columns
	 */
	public Items getAll() {
		Items item = new Items();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs;

			rs = stmt.executeQuery("SELECT * FROM ITEM");

			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnName(i);
				item.addCol(name);
			}

			rs = stmt.executeQuery("SELECT * FROM ITEM");
			while (rs.next()) {
				ArrayList<Object> obj = new ArrayList<Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					obj.add(rs.getObject(i));
				}
				item.addItem(obj);
			}
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * @param itemsString
	 *            to specify which items
	 * @return array list of items based on the filter
	 */
	public ArrayList<Item> getCartHistory(String itemsString) {
		ArrayList<Item> item = new ArrayList<Item>();
		String itemsArray[] = itemsString.split(",");// convert string to array
		try {
			Statement stmt = getConnection().createStatement();
			String patent = "";

			for (int i = 1; i < itemsArray.length; i++)
				patent += " OR id=" + itemsArray[i];

			ResultSet rs = stmt.executeQuery(
					"SELECT id, title, image, description, price FROM ITEM WHERE id=" + itemsArray[0] + patent);

			while (rs.next())
				item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * this function is used in application
	 * 
	 * @return array list of store items select only id, title, image, description
	 */
	public ArrayList<Item> getStoreItems() {
		ArrayList<Item> item = new ArrayList<Item>();
		try {
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, title, image, description, price FROM ITEM");

			while (rs.next())
				item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * @param id
	 *            to specify which item to delete from the database
	 * @return error message with results
	 */
	public ErrorMessage delete(int id) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM ITEM WHERE ID=?");
			stmt.setInt(1, id);

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.remove);

			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	/**
	 * @param item
	 *            to specify what item to insert into the database
	 * @return error message with results
	 */
	public ErrorMessage post(AddItem[] item) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			String str = "";
			String hmm = "";
			for (int i = 1; i < item.length; i++) {
				str += "," + item[i].column;
				hmm += ",?";
			}

			PreparedStatement stmt = getConnection()
					.prepareStatement("INSERT INTO ITEM(" + item[0].column + str + ") VALUES(?" + hmm + ")");

			for (int i = 0; i < item.length; i++)
				stmt.setObject(i + 1, item[i].value);

			result.setResult(stmt.executeUpdate() == 1);
			result.setErrorMessage(CRUDMessages.add);
			stmt.close();
			getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	/**
	 * @param item
	 *            to specify which item to update from the database
	 * @return error message with results
	 */
	public ErrorMessage put(AddItem[] item) {
		ErrorMessage result = new ErrorMessage(false, "");
		try {
			String str = "";
			for (int i = 1; i < item.length; i++) {
				if (i == item.length - 1)
					str += item[i].column + "=?";
				else
					str += item[i].column + "=? ,";
			}

			PreparedStatement stmt = getConnection()
					.prepareStatement("UPDATE ITEM SET " + str + " WHERE " + item[0].column + "=?");

			for (int i = 1; i < item.length; i++) {
				stmt.setObject(i, item[i].value);
			}
			stmt.setObject(item.length, item[0].value);

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

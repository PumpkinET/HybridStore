package com.server.service;

import java.util.List;

import com.server.dao.ItemsDAO;
import com.server.model.Items;


public class ItemsService {
	public static Items getAll(String dbName) {
		return ItemsDAO.getAll(dbName);
	}
}

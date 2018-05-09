package com.server.service;

import java.util.List;

import com.server.dao.UsersDAO;
import com.server.model.Users;

public class UsersService {
	
	public static List<Users> getAll() {
		return UsersDAO.getAll();
	}

	public static boolean put(Users user) {
		return UsersDAO.put(user);
	}

	public static boolean post(Users user) {
		return UsersDAO.post(user);
	}

	public static boolean delete(String username) {
		return UsersDAO.delete(username);
	}
}

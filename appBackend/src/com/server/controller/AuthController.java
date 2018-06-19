package com.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.android.LoginAndroid;
import com.server.dao.AuthDAO;
import com.server.model.Login;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("HEY");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (reader != null)
			json = reader.readLine();
		Gson gson = new GsonBuilder().create();
		LoginAndroid login = gson.fromJson(json, LoginAndroid.class);
		Login loginObj = AuthDAO.login(login);
		if (loginObj == null) {
			response.setStatus(403);
		} else {
			response.getWriter().write(new Gson().toJson(AuthDAO.login(login)));
			response.getWriter().close();
		}
	}
}

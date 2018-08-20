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
import com.server.util.SessionUtil;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthDAO authDAO;
	public AuthController() {
		super();
		authDAO = new AuthDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (reader != null)
			json = reader.readLine();
		Gson gson = new GsonBuilder().create();
		LoginAndroid login = gson.fromJson(json, LoginAndroid.class);
		Login loginObj = authDAO.login(login);
		if (loginObj == null) {
			response.setStatus(403);
		} else {
			String session = request.getSession().getId();
			Login secured = new Login(loginObj.getEmail(), loginObj.getPassword(), loginObj.getAvatar(), loginObj.getName(), session);
			SessionUtil.sessions.put(session, secured);
			response.getWriter().write(new Gson().toJson(secured));
			response.getWriter().close();
		}
	}
}

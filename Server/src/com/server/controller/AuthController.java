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
import com.server.dao.AuthDAO;
import com.server.dao.UsersDAO;
import com.server.model.Login;
import com.server.util.SessionUtil;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthController() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().write("ٍsfayen");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		
		Gson gson = new GsonBuilder().create();
		Login login = gson.fromJson(json, Login.class);
		boolean result = AuthDAO.login(login);

		if (result == false)
			response.setStatus(403);
		else {
			String session = request.getSession().getId();
			SessionUtil.sessions.put(session, UsersDAO.get(login.getUsername()));
			System.out.println(SessionUtil.sessions.toString());
			response.getWriter().write(session);
			response.getWriter().close();
		}
	}
}

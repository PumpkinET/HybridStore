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
import com.server.model.Users;
import com.server.util.SessionUtil;

@WebServlet("/AuthController")

public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AuthDAO authDAO;
	private UsersDAO usersDAO;

	public AuthController() {
		super();
		// initialize daos
		authDAO = new AuthDAO();
		usersDAO = new UsersDAO();
	}

	/**
	 * post username and password information to login 
	 * parameter dbName : specify database name
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content
		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			authDAO.setDbName(dbName);// initialize db connection
			usersDAO.setDbName(dbName);// initialize db connection

			// read buffer and convert it to the correct model (class)
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null)
				json = br.readLine();
			Gson gson = new GsonBuilder().create();
			Login login = gson.fromJson(json, Login.class);

			boolean result = authDAO.login(login);
			if (result == false)
				response.setStatus(403);// HTTP.FORBDDEN status code
			else {

				String session = request.getSession().getId();
				Users user = usersDAO.get(login.getUsername());

				if (user.getGrade() != 0)// save admin/owner session
					SessionUtil.adminSessions.put(session, user);

				SessionUtil.sessions.put(session, user);// save user session
				user.setSession(session);
				user.setDbName(dbName);//store database name for future requests
				response.getWriter().write(new Gson().toJson(user));// return profile object with session details
				response.getWriter().close();
			}
		}
	}

	/**
	 * set HTTP options
	 */
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);// HTTP.OK status
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*:*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

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
import com.server.android.RegisterAndroid;
import com.server.dao.AuthDAO;
import com.server.model.ErrorMessage;
import com.server.util.SessionUtil;

@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthDAO authDAO;

	public SignupController() {
		super();
		// initialize daos
		authDAO = new AuthDAO();
	}

	/**
	 * post new user to database
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		//read buffer and convert it to the correct model (class)
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (reader != null)
			json = reader.readLine();
		Gson gson = new GsonBuilder().create();
		ErrorMessage result = authDAO.register(gson.fromJson(json, RegisterAndroid.class));
		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}

	/**
	 * update user profile
	 * parameter session : specify session to identify current user
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content

		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null) {
			//read buffer and convert it to the correct model (class)
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (reader != null)
				json = reader.readLine();
			
			Gson gson = new GsonBuilder().create();
			ErrorMessage result = authDAO.update(gson.fromJson(json, RegisterAndroid.class));
			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		} else
			response.setStatus(401);// HTTP.UNAUTHORIZED status
	}
}

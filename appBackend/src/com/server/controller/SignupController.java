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

@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthDAO authDAO;
	public SignupController() {
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
		authDAO.register(gson.fromJson(json, RegisterAndroid.class));
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (reader != null)
			json = reader.readLine();
		Gson gson = new GsonBuilder().create();
		authDAO.update(gson.fromJson(json, RegisterAndroid.class));
	}

}

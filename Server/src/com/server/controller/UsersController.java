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
import com.server.dao.UsersDAO;
import com.server.model.Users;

@WebServlet("/UsersController/*")
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UsersController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getParameter("dbName") != null) {
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(UsersDAO.getAll(request.getParameter("dbName"))));
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("dbName") != null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null)
				json = br.readLine();
			Gson gson = new GsonBuilder().create();

			boolean result = UsersDAO.post(request.getParameter("dbName"), gson.fromJson(json, Users.class));

			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getParameter("dbName") != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null)
				json = br.readLine();
			Gson gson = new GsonBuilder().create();

			boolean result = UsersDAO.put(request.getParameter("dbName"), gson.fromJson(json, Users.class));

			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getParameter("dbName") != null) {
			String[] urldata = request.getPathInfo().split("/");

			boolean result = UsersDAO.delete(request.getParameter("dbName"), urldata[1]);

			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}

}

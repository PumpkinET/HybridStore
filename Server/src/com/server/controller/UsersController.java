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
import com.server.model.ErrorMessage;
import com.server.model.Users;
import com.server.util.SessionUtil;

@WebServlet("/UsersController/*")
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAO usersDAO;

	public UsersController() {
		super();
		usersDAO = new UsersDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			usersDAO.setDbName(dbName);
			response.getWriter().write(new Gson().toJson(usersDAO.getAll()));
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if (br != null)
					json = br.readLine();
				Gson gson = new GsonBuilder().create();

				ErrorMessage result = usersDAO.post(gson.fromJson(json, Users.class));

				response.getWriter().write(new Gson().toJson(result));
				response.getWriter().close();
			}
		}	else response.setStatus(401);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if (br != null)
					json = br.readLine();
				Gson gson = new GsonBuilder().create();

				ErrorMessage result = usersDAO.put(gson.fromJson(json, Users.class));

				response.getWriter().write(new Gson().toJson(result));
				response.getWriter().close();
			}
		} else response.setStatus(401);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				String user = request.getParameter("user");
				if (user != null) {
					ErrorMessage result = usersDAO.delete(user);

					response.getWriter().write(new Gson().toJson(result));
					response.getWriter().close();
				}
			}
		}else response.setStatus(401);
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*:*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

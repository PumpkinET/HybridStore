package com.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.server.dao.UsersDAO;

@WebServlet("/UserGradesController")
public class UserGradesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAO usersDAO;
	public UserGradesController() {
		super();
		//initialize daos
		usersDAO = new UsersDAO();
	}
	/**
	 * get all usersname with different grades
	 * parameter dbName : specify database name
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			usersDAO.setDbName(dbName);//initialize db connection
			
			response.getWriter().write(new Gson().toJson(usersDAO.getUsersList()));
			response.getWriter().close();
		}
	}
	
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);//HTTP.OK status
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*:*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

package com.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.server.dao.GradesDAO;

@WebServlet("/GradesController")
public class GradesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GradesController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getParameter("dbName") != null) {
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(GradesDAO.getAll(request.getParameter("dbName"))));
			response.getWriter().close();
		}
	}

}

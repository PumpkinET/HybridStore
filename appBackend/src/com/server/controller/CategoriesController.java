package com.server.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.server.dao.CategoriesDAO;

@WebServlet("/CategoriesController")
public class CategoriesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CategoriesController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(CategoriesDAO.getAll()));
		response.getWriter().close();
	}

}

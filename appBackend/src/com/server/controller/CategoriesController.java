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
	private CategoriesDAO categoriesDAO;

	public CategoriesController() {
		super();
		// initialize daos
		categoriesDAO = new CategoriesDAO();
	}

	/**
	 * get categories
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		response.getWriter().write(new Gson().toJson(categoriesDAO.getAll()));
		response.getWriter().close();
	}

}

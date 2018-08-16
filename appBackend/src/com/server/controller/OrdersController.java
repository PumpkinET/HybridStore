package com.server.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.server.dao.OrderDAO;

@WebServlet("/OrdersController")
public class OrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrdersController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		if (request.getParameter("shopName") != null) {
			response.getWriter().write(new Gson().toJson(OrderDAO.getOrderByShop(request.getParameter("shopName"))));
			response.getWriter().close();
		}
	}
}

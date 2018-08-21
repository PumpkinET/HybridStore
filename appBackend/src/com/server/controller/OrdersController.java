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
import com.server.android.OrderAndroid;
import com.server.dao.OrderDAO;

@WebServlet("/OrdersController")
public class OrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;

	public OrdersController() {
		super();
		// initialize daos
		orderDAO = new OrderDAO();
	}

	/**
	 * gets orders
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		if (request.getParameter("shopName") != null) {
			if (request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
				response.getWriter().write(new Gson().toJson(orderDAO.getOrderByShop(request.getParameter("shopName"),
						request.getParameter("startDate"), request.getParameter("endDate"))));
			} else if (request.getParameter("startDate") != null) {
				response.getWriter().write(new Gson().toJson(orderDAO.getOrderByShop(request.getParameter("shopName"),
						request.getParameter("startDate"), null)));
			} else {
				response.getWriter().write(
						new Gson().toJson(orderDAO.getOrderByShop(request.getParameter("shopName"), null, null)));
			}
			response.getWriter().close();
		}
	}

	/**
	 * update order status
	 * parameter dbName : specify database name
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		if (request.getParameter("shopName") != null) {
			boolean result = orderDAO.put(request.getParameter("dbName"), gson.fromJson(json, OrderAndroid.class));
			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}
}

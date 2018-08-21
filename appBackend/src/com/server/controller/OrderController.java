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
import com.server.util.SessionUtil;

@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;

	public OrderController() {
		super();
		// initialize daos
		orderDAO = new OrderDAO();
	}

	/**
	 * get all orders by session
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content

		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null) {
			response.getWriter()
					.write(new Gson().toJson(orderDAO.getOrder(SessionUtil.sessions.get(session).getEmail())));
			response.getWriter().close();
		} else
			response.setStatus(401);// HTTP.UNAUTHORIZED status
	}

	/**
	 * post new order
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content

		String session = request.getParameter("session");
		System.out.println(session);
		if (session != null && SessionUtil.sessions.get(session) != null) {
			
			// read buffer and convert it to the correct model (class)
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null)
				json = br.readLine(); 
			Gson gson = new GsonBuilder().create();  
			OrderAndroid order = gson.fromJson(json, OrderAndroid.class);
			System.out.println(order.toString());
			orderDAO.register_order(gson.fromJson(json, OrderAndroid.class));
		} else
			response.setStatus(401);// HTTP.UNAUTHORIZED status  
	}

}

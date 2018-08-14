package com.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.android.OrderAndroid;
import com.server.dao.OrderDAO;
import com.server.dao.ShopDAO;
import com.server.model.Shop;
import com.server.util.SessionUtil;


@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrderController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String session = request.getParameter("session");
		
		response.getWriter().write(new Gson().toJson(OrderDAO.getOrder(SessionUtil.sessions.get(session).getEmail())));
		//response.getWriter().write(new Gson().toJson(OrderDAO.getOrder("sheli@gmail.com1")));
		response.getWriter().close();
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		OrderDAO.register_order(gson.fromJson(json, OrderAndroid.class));
	}

}

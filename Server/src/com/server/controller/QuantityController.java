package com.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.dao.ItemsDAO;
import com.server.model.AddItem;
import com.server.model.ErrorMessage;
import com.server.model.OrderItems;
import com.server.util.SessionUtil;

@WebServlet("/QuantityController/*")
public class QuantityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemsDAO itemsDAO;

	public QuantityController() {
		super();
		// initialize daos
		itemsDAO = new ItemsDAO();
	}

	/**
	 * post new items to database
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content

		String dbName = request.getParameter("dbName");
		itemsDAO.setDbName(dbName);// initialize db connection

		// read buffer and convert it to the correct model (class)
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();

		Gson gson = new GsonBuilder().create();

		ArrayList<ErrorMessage> result = itemsDAO.updateQuantity(gson.fromJson(json, OrderItems.class));
		System.out.println(Arrays.asList(result.toString()));
		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}
}

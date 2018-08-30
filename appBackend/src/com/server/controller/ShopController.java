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
import com.server.dao.ShopDAO;
import com.server.model.ErrorMessage;
import com.server.model.Shop;

@WebServlet("/ShopController")
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopDAO shopDAO;

	public ShopController() {
		super();
		// initialize daos
		shopDAO = new ShopDAO();
	}

	/**
	 * get categories
	 * parameter category : specify category
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		String cat = request.getParameter("category");
		String target = request.getParameter("target");
		if(target != null) 
			response.getWriter().write(new Gson().toJson(shopDAO.get(target)));
		else if (cat == null)
			response.getWriter().write(new Gson().toJson(shopDAO.getAll("All")));
		else
			response.getWriter().write(new Gson().toJson(shopDAO.getAll(cat)));
		response.getWriter().close();
	}

	/**
	 * create new store
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		Shop shop = gson.fromJson(json, Shop.class);
		shop.setShopIp(shop.getShopIp() + "/ItemsController?dbName=" + shop.getShopName() + "&filter=true");
		
		ErrorMessage result = shopDAO.createStore(shop);
		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}
	
	/**
	 * update store
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		Shop shop = gson.fromJson(json, Shop.class);

		System.out.println(shop.toString());
		ErrorMessage result = shopDAO.update(shop);
		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}

}

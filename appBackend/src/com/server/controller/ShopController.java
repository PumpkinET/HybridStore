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
import com.server.model.Shop;

@WebServlet("/ShopController")
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShopController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String cat = request.getParameter("category");
		if (cat == null)
			response.getWriter().write(new Gson().toJson(ShopDAO.getAll("All")));
		else
			response.getWriter().write(new Gson().toJson(ShopDAO.getAll(cat)));
		response.getWriter().close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		Shop shop = gson.fromJson(json, Shop.class);

		shop.setShopName(shop.getShopName());
		shop.setShopOwner(shop.getShopOwner());
		shop.setShopThumbnail(shop.getShopThumbnail());
		shop.setShopDescription(shop.getShopDescription());
		shop.setShopIp(shop.getShopIp() + "/ItemsController?dbName=" + shop.getShopName() + "&filter=true");
		shop.setShopCategory(shop.getShopCategory());

		ShopDAO.createStore(shop);
	}

}

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

/**
 * Servlet implementation class ShopController
 */
@WebServlet("/ShopController")
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		String cat = request.getParameter("category");
		if(cat == null)
			response.getWriter().write(new Gson().toJson(ShopDAO.getAll("All")));
		else 
			response.getWriter().write(new Gson().toJson(ShopDAO.getAll(cat)));
		response.getWriter().close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		Shop shop = gson.fromJson(json, Shop.class);
		shop.setShopIp("http://10.0.0.21:8080/Server/ItemsController?dbName="+shop.getShopName());
		shop.setShopDescription("Long description example");
		shop.setShopThumbnail("http://images.globes.co.il/images/NewGlobes/big_image_800/2015/aroma-575_2014731T130210.jpg");
		ShopDAO.createStore(shop);
	}

}

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
import com.server.dao.ItemsDAO;
import com.server.model.AddItem;
import com.server.model.ErrorMessage;

@WebServlet("/ItemsController/*")
public class ItemsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemsController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("dbName") != null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/json");

			if (request.getParameter("filter") != null) {
				if (request.getParameter("items") != null)
					response.getWriter().write(new Gson().toJson(
							ItemsDAO.getCartHistory(request.getParameter("dbName"), request.getParameter("items"))));
				else
					response.getWriter()
							.write(new Gson().toJson(ItemsDAO.getStoreItems(request.getParameter("dbName"))));
			} else {
				if (request.getParameter("items") != null)
					response.getWriter().write(new Gson().toJson(
							ItemsDAO.getCartHistory(request.getParameter("dbName"), request.getParameter("items"))));
				else
					response.getWriter().write(new Gson().toJson(ItemsDAO.getAll(request.getParameter("dbName"))));
			}
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		if (request.getParameter("dbName") != null) {
			ErrorMessage result = ItemsDAO.post(request.getParameter("dbName"), gson.fromJson(json, AddItem[].class));
			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		if (request.getParameter("dbName") != null) {
			ErrorMessage result = ItemsDAO.put(request.getParameter("dbName"), gson.fromJson(json, AddItem[].class));
			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getParameter("dbName") != null) {
			ErrorMessage result = ItemsDAO.delete(request.getParameter("dbName"),
					Integer.parseInt(request.getParameter("item")));

			response.getWriter().write(new Gson().toJson(result));
			response.getWriter().close();
		}
	}

}

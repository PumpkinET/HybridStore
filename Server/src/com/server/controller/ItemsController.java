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
import com.server.util.SessionUtil;

@WebServlet("/ItemsController/*")
public class ItemsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemsDAO itemsDAO;

	public ItemsController() {
		super();
		itemsDAO = new ItemsDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");

		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			itemsDAO.setDbName(dbName);
			String filter = request.getParameter("filter");
			String items = request.getParameter("items");
			if (filter != null) {
				if (items != null)
					response.getWriter().write(new Gson().toJson(itemsDAO.getCartHistory(items)));
				else
					response.getWriter().write(new Gson().toJson(itemsDAO.getStoreItems()));
			} else {
				if (items != null)
					response.getWriter().write(new Gson().toJson(itemsDAO.getCartHistory(items)));
				else
					response.getWriter().write(new Gson().toJson(itemsDAO.getAll()));
			}
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				itemsDAO.setDbName(dbName);
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if (br != null)
					json = br.readLine();
				Gson gson = new GsonBuilder().create();
				ErrorMessage result = itemsDAO.post(gson.fromJson(json, AddItem[].class));
				response.getWriter().write(new Gson().toJson(result));
				response.getWriter().close();
			}
		} else
			response.setStatus(401);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				itemsDAO.setDbName(dbName);
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if (br != null)
					json = br.readLine();
				Gson gson = new GsonBuilder().create();
				ErrorMessage result = itemsDAO.put(gson.fromJson(json, AddItem[].class));
				response.getWriter().write(new Gson().toJson(result));
				response.getWriter().close();
			}
		} else
			response.setStatus(401);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				itemsDAO.setDbName(dbName);
				String itemId = request.getParameter("item");
				if (itemId != null) {
					ErrorMessage result = itemsDAO.delete(Integer.parseInt(itemId));
					response.getWriter().write(new Gson().toJson(result));
					response.getWriter().close();
				}
			}
		} else
			response.setStatus(401);
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "localhost");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

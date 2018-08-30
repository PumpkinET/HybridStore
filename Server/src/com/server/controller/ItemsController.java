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
		//initialize daos
		itemsDAO = new ItemsDAO();
	}

	/**
	 * case 1 : get items details by string array, example : filter=true&items=1,2,3
	 * case 2 : get all store items for application (id, title, image, description) fields only
	 * case 3 : get all store items for website, all fields are retrieved
	 * case 4 : get all missing items, example : missing=true
	 * parameter dbName : specify database name
	 * parameter filter : whether select all items or selection
	 * parameter items : specify items selection
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content

		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			itemsDAO.setDbName(dbName);//initialize db connection
			String filter = request.getParameter("filter");
			String items = request.getParameter("items");
			String missing = request.getParameter("missing");
			if (filter != null) {
				if (items != null)
					response.getWriter().write(new Gson().toJson(itemsDAO.getCartHistory(items)));//case 1
				else
					response.getWriter().write(new Gson().toJson(itemsDAO.getStoreItems()));//case 2
			} else {
				if (items != null)
					response.getWriter().write(new Gson().toJson(itemsDAO.getCartHistory(items)));//case 1
				else if(missing != null)
					response.getWriter().write(new Gson().toJson(itemsDAO.getMissingItems()));//case 4
				else
					response.getWriter().write(new Gson().toJson(itemsDAO.getAll()));//case 3
			}
			response.getWriter().close();
		}
	}

	/**
	 * post new items to database
	 * parameter session : specify session to identify current user
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null && SessionUtil.sessions.get(session).getGrade() != 0) {
			String dbName = SessionUtil.sessions.get(session).getDbName();
			if (dbName != null) {
				itemsDAO.setDbName(dbName);//initialize db connection
				
				//read buffer and convert it to the correct model (class)
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
			response.setStatus(401);//HTTP.UNAUTHORIZED status
	}

	/**
	 * update existing item
	 * parameter session : specify session to identify current user
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null && SessionUtil.sessions.get(session).getGrade() != 0) {
			String dbName = SessionUtil.sessions.get(session).getDbName();
			if (dbName != null) {
				itemsDAO.setDbName(dbName);//initialize db connection
				
				//read buffer and convert it to the correct model (class)
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
			response.setStatus(401);//HTTP.UNAUTHORIZED status
	}
	/**
	 * delete existing item
	 * parameter session : specify session to identify current user
	 * parameter item : specify which item to delete
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null && SessionUtil.sessions.get(session).getGrade() != 0) {
			String dbName = SessionUtil.sessions.get(session).getDbName();
			if (dbName != null) {
				itemsDAO.setDbName(dbName);//initialize db connection
				
				String itemId = request.getParameter("item");
				if (itemId != null) {
					ErrorMessage result = itemsDAO.delete(Integer.parseInt(itemId));
					response.getWriter().write(new Gson().toJson(result));
					response.getWriter().close();
				}
			}
		} else
			response.setStatus(401);//HTTP.UNAUTHORIZED status
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);//HTTP.OK status
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

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
import com.server.dao.RoutineDAO;
import com.server.model.ErrorMessage;
import com.server.model.Routine;
import com.server.util.SessionUtil;

@WebServlet("/RoutineController")
public class RoutineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoutineDAO routineDAO;

	public RoutineController() {
		super();
		//initialize daos
		routineDAO = new RoutineDAO();
	}
	/**
	 * get routine 
	 * parameter dbName : specify database name
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");//specify return content
		
		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			routineDAO.setDbName(dbName);//initialize db connection
			
			response.getWriter().write(new Gson().toJson(routineDAO.getAll()));
			response.getWriter().close();
		}
		else response.setStatus(401);//HTTP.UNAUTHORIZED status
	}
	/**
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
				routineDAO.setDbName(dbName);//initialize db connection
				
				//read buffer and convert it to the correct model (class)
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if (br != null)
					json = br.readLine();
				Gson gson = new GsonBuilder().create();

				ErrorMessage result = routineDAO.post(gson.fromJson(json, Routine.class));

				response.getWriter().write(new Gson().toJson(result));
				response.getWriter().close();
			}
		}
		else response.setStatus(401);//HTTP.UNAUTHORIZED status
	}
	/**
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
				routineDAO.setDbName(dbName);//initialize db connection
				
				//read buffer and convert it to the correct model (class)
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String json = "";
				if (br != null)
					json = br.readLine();
				Gson gson = new GsonBuilder().create();

				ErrorMessage result = routineDAO.put(gson.fromJson(json, Routine.class));

				response.getWriter().write(new Gson().toJson(result));
				response.getWriter().close();
			}
		}
		else response.setStatus(401);//HTTP.UNAUTHORIZED status
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);//HTTP.OK status
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*:*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

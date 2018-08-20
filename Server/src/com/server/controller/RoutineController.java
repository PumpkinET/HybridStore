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
		routineDAO = new RoutineDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		if (dbName != null) {
			routineDAO.setDbName(dbName);
			response.getWriter().write(new Gson().toJson(routineDAO.getAll()));
			response.getWriter().close();
		}
		else response.setStatus(401);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				routineDAO.setDbName(dbName);
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
		else response.setStatus(401);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		String dbName = request.getParameter("dbName");
		String session = request.getParameter("session");
		if (session != null && SessionUtil.adminSessions.get(session) != null) {
			if (dbName != null) {
				routineDAO.setDbName(dbName);
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
		else response.setStatus(401);
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*:*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

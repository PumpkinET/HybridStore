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
import com.server.model.Routine;
import com.server.model.Users;
import com.server.service.UsersService;

@WebServlet("/RoutineController")
public class RoutineController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RoutineController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(RoutineDAO.getAll()));
		response.getWriter().close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();

		boolean result = RoutineDAO.post(gson.fromJson(json, Routine.class));

		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();

		boolean result = RoutineDAO.put(gson.fromJson(json, Routine.class));

		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String[] urldata = request.getPathInfo().split("/");

		boolean result = UsersService.delete(urldata[1]);

		response.getWriter().write(new Gson().toJson(result));
		response.getWriter().close();
	}

}

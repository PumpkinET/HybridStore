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
import com.server.android.AddressAndroid;
import com.server.android.OrderAndroid;
import com.server.dao.AddressDAO;
import com.server.dao.OrderDAO;
import com.server.util.SessionUtil;

@WebServlet("/AddressController")
public class AddressController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AddressDAO AddressDAO;

	public AddressController() {
		super();
		// initialize daos
		AddressDAO = new AddressDAO();
	}

	/**
	 * get all address by session
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("application/json");// specify return content
//
//		String session = request.getParameter("session");
//		if (session != null && SessionUtil.sessions.get(session) != null) {
//			response.getWriter()
//					.write(new Gson().toJson(AddressDAO.getOrder(SessionUtil.sessions.get(session).getEmail())));
//			response.getWriter().close();
//		} else
//			response.setStatus(401);// HTTP.UNAUTHORIZED status
//	}

	/**
	 * post new address
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content

		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null) {
			System.out.println("HERE");
			// read buffer and convert it to the correct model (class)
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null)
				json = br.readLine(); 
			Gson gson = new GsonBuilder().create();  
			AddressAndroid address = gson.fromJson(json, AddressAndroid.class);
			
			AddressDAO.register_address((gson.fromJson(json, AddressAndroid.class)));
		} else
			response.setStatus(401);// HTTP.UNAUTHORIZED status  
	}

}

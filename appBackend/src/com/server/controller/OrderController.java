package com.server.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.android.OrderAndroid;
import com.server.dao.OrderDAO;
import com.server.model.ErrorMessage;
import com.server.util.SessionUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;

	public OrderController() {
		super();
		// initialize daos
		orderDAO = new OrderDAO();
	}

	/**
	 * get all orders by session
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content

		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null) {
			response.getWriter()
					.write(new Gson().toJson(orderDAO.getOrder(SessionUtil.sessions.get(session).getEmail())));
			response.getWriter().close();
		} else
			response.setStatus(401);// HTTP.UNAUTHORIZED status
	}

	/**
	 * post new order
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");// specify return content

		String session = request.getParameter("session");
		if (session != null && SessionUtil.sessions.get(session) != null) {
			// read buffer and convert it to the correct model (class)
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null)
				json = br.readLine(); 
			Gson gson = new GsonBuilder().create();  
			OrderAndroid order = gson.fromJson(json, OrderAndroid.class);
			
			// if(result.isResult() == true) {
			// update quantity in server store
			JSONObject jObj = new JSONObject();
			URL url = new URL("http://10.100.102.40:8080/Server/QuantityController?dbName=" + order.getShopName());

			try {
				jObj.put("items", order.getItems());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setFixedLengthStreamingMode(jObj.toString().getBytes().length);
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			OutputStream os = new BufferedOutputStream(conn.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			System.out.println(jObj.toString());
			writer.write(jObj.toString());
			writer.flush();
			writer.close();
			os.close();
			conn.connect();

			int statusCode = conn.getResponseCode();
			if (statusCode == 200) {
//				BufferedReader br_res = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				String json_res = "";
//				if (br_res != null)
//					json_res = br_res.readLine();
//				
//				Gson gson_res = new GsonBuilder().create();  
//				ErrorMessage[] errors = gson_res.fromJson(json_res, ErrorMessage[].class);
//				
//				boolean bRegisterOrder = true;
//				String missingItems = "";
//				for(int i = 0; i<errors.length; i++) {
//					if(errors[i].isResult() == false ) {
//						missingItems += "Item id :" + errors[i].getErrorMessage() + " is missing\n";
//						bRegisterOrder = false;
//					}
//				}
//				if(bRegisterOrder) {
					ErrorMessage result = orderDAO.register_order(gson.fromJson(json, OrderAndroid.class));
					response.getWriter().write(new Gson().toJson(result));
//				} else {
//					response.getWriter().write(new Gson().toJson(new ErrorMessage(false, missingItems)));
//				}
				response.getWriter().close();
			}
		} else
			response.setStatus(401);// HTTP.UNAUTHORIZED status
	}

}

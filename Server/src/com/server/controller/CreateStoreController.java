package com.server.controller;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.dao.CreateStoreDAO;
import com.server.model.CreateStore;

import net.sf.json.JSONObject;

@WebServlet("/CreateStoreController")
public class CreateStoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CreateStoreDAO createStoreDAO;

	public CreateStoreController() {
		super();
		// initialize daos
		createStoreDAO = new CreateStoreDAO();
	}

	/**
	 * post new store
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// read buffer and convert it to the correct model (class)
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		CreateStore store = gson.fromJson(json, CreateStore.class);

		// register store in application server
		URL url = new URL("http://10.0.0.21:8080/appBackend/ShopController");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		JSONObject jObj = new JSONObject();

		jObj.put("shopName", store.getStoreName());
		jObj.put("shopOwner", store.getOwnerName());
		jObj.put("shopThumbnail", store.getThumbnail());
		jObj.put("shopDescription", store.getDescription());
		jObj.put("shopIp", store.getIp());
		jObj.put("shopCategory", store.getCategory());

		OutputStream os = conn.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(jObj.toString());
		writer.flush();
		writer.close();

		conn.connect();
		int statusCode = conn.getResponseCode();
		if (statusCode == 200) {// HTTP.OK
			createStoreDAO.createItemsTable(store);
		}
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);// HTTP.OK status
	}

	private void setAccessControlHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "http://*:*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Methods", "PUT");
		response.setHeader("Access-Control-Allow-Methods", "DELETE");
	}
}

package com.server.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

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

	public CreateStoreController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null)
			json = br.readLine();
		Gson gson = new GsonBuilder().create();
		CreateStore[] store = gson.fromJson(json, CreateStore[].class);
		System.out.println(Arrays.asList(store));
		URL url = new URL("http://10.0.0.21:8080/appBackend/ShopController");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		JSONObject jObj = new JSONObject();
        jObj.put("shopName", store[3].getStoreName());
        jObj.put("shopOwner", store[3].getOwnerName());

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jObj.toString());
        writer.flush();
        writer.close();
        
		conn.connect();
		int statusCode = conn.getResponseCode();
		
		CreateStoreDAO.createItemsTable(gson.fromJson(json, CreateStore[].class));
	}

}

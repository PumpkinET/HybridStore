package com.server.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.android.LoginAndroid;
import com.server.model.Image;

/**
 * Servlet implementation class UploadController
 */
@WebServlet("/UploadController")
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void saveImage(InputStream imageStream) {
		InputStream inStream = imageStream;

		try {

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("HEY");
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (reader != null)
			json = reader.readLine();
		Gson gson = new GsonBuilder().create();
		Image imageObj = gson.fromJson(json, Image.class);

		String dataString = imageObj.getImageString();
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(dataString);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		// write the image to a file
		File outputfile = new File("C:\\Users\\Dell Latitude\\Desktop\\"+imageObj.getImageName());
		ImageIO.write(image, "png", outputfile);
	}

}

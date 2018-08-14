package com.server.model;

public class Item {
	private String id;
	private String title;
	private String image;
	private String description;
	private String price;

	public Item(String id, String title, String image, String description, String price) {
		this.id = id;
		this.title = title;
		this.image = image;
		this.description = description;
		this.price = price;
	}

	public String getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}

	public String getImageResource() {
		return this.image;
	}

	public String getDescription() {
		return this.description;
	}

	public String getPrice() {
		return this.price;
	}
}

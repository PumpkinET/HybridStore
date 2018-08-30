package com.server.model;

public class Item {
	private String id;
	private String title;
	private String image;
	private String description;
	private float price;
	private int quantity;
	
	public Item(String id, String title, String image, String description, float price) {
		this.id = id;
		this.title = title;
		this.image = image;
		this.description = description;
		this.price = price;
	}
	
	public Item(String id, String title, String image, String description, float price, int quantity) {
		this.id = id;
		this.title = title;
		this.image = image;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
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

	public float getPrice() {
		return this.price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
}

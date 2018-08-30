package com.server.model;

public class OrderItems {
	private String items;

	public OrderItems(String items) {
		super();
		this.items = items;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "OrderItems [items=" + items + "]";
	}
	
}

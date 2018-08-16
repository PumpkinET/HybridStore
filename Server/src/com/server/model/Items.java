package com.server.model;

import java.util.ArrayList;

public class Items {
	private ArrayList<String> columns;
	private ArrayList<Object> items;

	public Items() {
		super();
		this.columns = new ArrayList<String>();
		this.items = new ArrayList<Object>();
	}

	public void addCol(String col) {
		this.columns.add(col);
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void addItem(Object obj) {
		this.items.add(obj);
	}

	public ArrayList<Object> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "Items [columns=" + columns + ", items=" + items + "]";
	}

}

package com.server.model;

public class Category {
	private String categoryName;
	private String categoryThumbnail;

	public Category(String categoryName, String categoryThumbnail) {
		this.categoryName = categoryName;
		this.categoryThumbnail = categoryThumbnail;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getCategoryThumbnail() {
		return categoryThumbnail;
	}
}

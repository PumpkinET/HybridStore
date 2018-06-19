package com.demo.hybridstore.com.hybridstore.model;


public class Categories {
    private String categoryName;
    private String categoryThumbnail;

    public Categories(String categoryName, String categoryThumbnail) {
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


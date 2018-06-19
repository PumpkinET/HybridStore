package com.demo.hybridstore.com.hybridstore.model;

public class Item {
    private String title;
    private String image;
    private String description;

    public Item(String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
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
}

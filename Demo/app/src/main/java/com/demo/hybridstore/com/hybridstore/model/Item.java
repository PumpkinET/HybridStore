package com.demo.hybridstore.com.hybridstore.model;

public class Item {
    private String title;
    private String image;
    private String description;
    private String price;

    public Item(String title, String image, String description, String price) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.price = price;
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

    public String getPrice() { return this.price; }
}

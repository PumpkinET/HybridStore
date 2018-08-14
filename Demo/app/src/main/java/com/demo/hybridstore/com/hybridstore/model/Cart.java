package com.demo.hybridstore.com.hybridstore.model;

import com.demo.hybridstore.CartActivity;

public class Cart {
    private String id;
    private String title;
    private String image;
    private String description;
    private String price;
    private String color = "#FFFFFF";

    public Cart(String id, String title, String image, String price) {
        this.id = id;
        this.title = title;
        this.image = image;
        //this.description = description;
        this.price = price;
    }

    public String getColor() {
        return this.color;
    }

    public void switchColor() {
        if (this.color.equals("#FFFFFF")) {
            this.color = "#bdc3c7";
        }
        else {
            this.color = "#FFFFFF";
        }
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

    public String getId() { return this.id; }

}

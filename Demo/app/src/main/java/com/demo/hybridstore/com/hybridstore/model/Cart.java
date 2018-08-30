package com.demo.hybridstore.com.hybridstore.model;


import com.demo.hybridstore.CartActivity;

public class Cart {
    private String id;
    private String title;
    private String image;
    private float price;
    private String color = "#FFFFFF";
    private int quantity;

    public Cart(String id, String title, String image, float price, int quantity) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.quantity = 1;
    }

    public String getColor() {
        return this.color;
    }

    public void switchColor() {
        if (this.color.equals("#FFFFFF")) {
            this.color = "#bdc3c7";
        } else {
            this.color = "#FFFFFF";
        }
    }

    public String getTitle() {
        return this.title;
    }

    public String getImageResource() {
        return this.image;
    }

    public float getPrice() {
        return this.price;
    }

    public String getId() {
        return this.id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

}

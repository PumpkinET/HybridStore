package com.demo.hybridstore.com.hybridstore.model;

import java.util.ArrayList;


public class Items {
    private ArrayList<String> columns;
    private ArrayList<Object[]> items;

    public Items() {
        super();
        this.columns = new ArrayList<String>();
        this.items = new ArrayList<Object[]>();
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public ArrayList<Object[]> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Items [columns=" + columns + ", items=" + items + "]";
    }

}

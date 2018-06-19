package com.demo.hybridstore.com.hybridstore.model;

public class Shop {
    private String shopName;
    private String shopOwner;
    private String shopThumbnail;
    private String shopDescription;
    private String shopIp;

    public Shop(String shopName, String shopOwner, String shopThumbnail, String shopDescription, String shopIp) {
        super();
        this.shopName = shopName;
        this.shopOwner = shopOwner;
        this.shopThumbnail = shopThumbnail;
        this.shopDescription = shopDescription;
        this.shopIp = shopIp;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public String getShopThumbnail() {
        return shopThumbnail;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public String getShopIp() {
        return shopIp;
    }
}

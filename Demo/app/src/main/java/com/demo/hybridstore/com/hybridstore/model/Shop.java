package com.demo.hybridstore.com.hybridstore.model;

public class Shop {
    private String shopName;
    private String shopOwner;
    private String shopThumbnail;
    private String shopDescription;
    private String shopIp;
    private String shopCategory;
    private String shopPhone;
    private String shopAddress;

    public Shop(String shopName, String shopOwner, String shopThumbnail, String shopDescription, String shopIp,
                String shopCategory, String shopPhone, String shopAddress) {
        super();
        this.shopName = shopName;
        this.shopOwner = shopOwner;
        this.shopThumbnail = shopThumbnail;
        this.shopDescription = shopDescription;
        this.shopIp = shopIp;
        this.shopCategory = shopCategory;
        this.shopPhone = shopPhone;
        this.shopAddress = shopAddress;
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

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopCategory(String category) {
        this.shopCategory = category;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public void setShopThumbnail(String shopThumbnail) {
        this.shopThumbnail = shopThumbnail;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public void setShopIp(String shopIp) {
        this.shopIp = shopIp;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    @Override
    public String toString() {
        return "Shop [shopName=" + shopName + ", shopOwner=" + shopOwner + ", shopThumbnail=" + shopThumbnail
                + ", shopDescription=" + shopDescription + ", shopIp=" + shopIp + ", shopCategory=" + shopCategory
                + ", shopPhone=" + shopPhone + ", shopAddress=" + shopAddress + "]";
    }
}

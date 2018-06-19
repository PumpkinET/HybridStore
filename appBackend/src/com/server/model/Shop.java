package com.server.model;

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

	
	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	@Override
	public String toString() {
		return "Shop [shopName=" + shopName + ", shopOwner=" + shopOwner + ", shopThumbnail=" + shopThumbnail
				+ ", shopDescription=" + shopDescription + ", shopIp=" + shopIp + "]";
	}

}

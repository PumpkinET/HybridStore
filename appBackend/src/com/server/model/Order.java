package com.server.model;

import java.sql.Date;

public class Order {
	private String shopName;
	private String shopThumbnail;
	private String shopIp;
	private float finalPrice;
	private String streetAdd;
	private String country;
	private String city;
	private String postalcode;
	private String items;
	private int	status;
	private java.sql.Date date;
	private String phoneNumber;
	
	
	public Order(String shopName, String shopThumbnail, String shopIp, float finalPrice, String streetAdd,
			String country, String city, String postalcode, String items, int status, String phoneNumber) {
		super();
		this.shopName = shopName;
		this.shopThumbnail = shopThumbnail;
		this.shopIp = shopIp;
		this.finalPrice = finalPrice;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalcode = postalcode;
		this.items = items;
		this.status = status;
		this.phoneNumber = phoneNumber;
	}
	
	
	public Order(String shopName, String shopThumbnail, String shopIp, float finalPrice, String streetAdd,
			String country, String city, String postalcode, String items, int status, Date date, String phoneNumber) {
		super();
		this.shopName = shopName;
		this.shopThumbnail = shopThumbnail;
		this.shopIp = shopIp;
		this.finalPrice = finalPrice;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalcode = postalcode;
		this.items = items;
		this.status = status;
		this.date = date;
		this.phoneNumber = phoneNumber;
	}


	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopThumbnail() {
		return shopThumbnail;
	}

	public void setShopThumbnail(String shopThumbnail) {
		this.shopThumbnail = shopThumbnail;
	}

	public String getShopIp() {
		return shopIp;
	}

	public void setShopIp(String shopIp) {
		this.shopIp = shopIp;
	}

	public float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getStreetAdd() {
		return streetAdd;
	}

	public void setStreetAdd(String streetAdd) {
		this.streetAdd = streetAdd;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString() {
		return "Order [shopName=" + shopName + ", shopThumbnail=" + shopThumbnail + ", shopIp=" + shopIp
				+ ", finalPrice=" + finalPrice + ", streetAdd=" + streetAdd + ", country=" + country + ", city=" + city
				+ ", postalcode=" + postalcode + ", items=" + items + ", status=" + status + ", date=" + date
				+ ", phoneNumber=" + phoneNumber + "]";
	}
}

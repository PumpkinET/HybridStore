package com.server.android;

import java.sql.Date;

public class OrderAndroid {
	String id;
	String email;
	String shopName;
	String fullName;
	String streetAdd;
	String country;
	String city;
	String postalCode;
	float totalPrice;
	String items;
	int status;
	String statusValue;
	java.sql.Date date;
	String phoneNumber;
	
	public OrderAndroid(String id, int status) {
		this.id = id;
		this.status = status;
	}
	
	public OrderAndroid(String id, String email, String shopName, String fullName, String streetAdd,
			String country, String city, String postalCode, float totalPrice, String items, String phoneNumber) {
		super();
		this.id = id;
		this.email = email;
		this.shopName = shopName;
		this.fullName = fullName;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.totalPrice = totalPrice;
		this.items = items;
		this.phoneNumber = phoneNumber;
	}

	public OrderAndroid(String id, String email, String shopName, String fullName, String streetAdd,
			String country, String city, String postalCode, float totalPrice, String items, int status,
			String statusValue, Date date, String phoneNumber) {
		super();
		this.id = id;
		this.email = email;
		this.shopName = shopName;
		this.fullName = fullName;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.totalPrice = totalPrice;
		this.items = items;
		this.status = status;
		this.statusValue = statusValue;
		this.date = date;
		this.phoneNumber = phoneNumber;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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
		return "OrderAndroid [id=" + id + ", email=" + email + ", shopName=" + shopName + ", fullName=" + fullName
				+ ", streetAdd=" + streetAdd + ", country=" + country + ", city=" + city + ", postalCode=" + postalCode
				+ ", totalPrice=" + totalPrice + ", items=" + items + ", status=" + status + ", statusValue="
				+ statusValue + ", date=" + date + ", phoneNumber=" + phoneNumber + "]";
	}
}

package com.server.android;

public class OrderAndroid {
	String id;
	String email;
	String shopName;
	String firstName;
	String lastName;
	String streetAdd;
	String country;
	String city;
	String postalCode;
	String totalPrice;
	String items;
	int status;
	String statusValue;
	
	public OrderAndroid(String id, int status) {
		this.id = id;
		this.status = status;
	}
	
	public OrderAndroid(String email, String shopName, String firstName, String lastName, String streetAdd,
			String country, String city, String postalCode, String totalPrice, String items) {
		super();
		this.email = email;
		this.shopName = shopName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.totalPrice = totalPrice;
		this.items = items;
	}

	public OrderAndroid(String id, String email, String shopName, String firstName, String lastName, String streetAdd,
			String country, String city, String postalCode, String totalPrice, String items, int status, String statusValue) {
		super();
		this.id = id;
		this.email = email;
		this.shopName = shopName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.totalPrice = totalPrice;
		this.items = items;
		this.status = status;
		this.statusValue = statusValue;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
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

	@Override
	public String toString() {
		return "OrderAndroid [id=" + id + ", email=" + email + ", shopName=" + shopName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", streetAdd=" + streetAdd + ", country=" + country + ", city=" + city
				+ ", postalCode=" + postalCode + ", totalPrice=" + totalPrice + ", items=" + items + ", status="
				+ status + ", statusValue=" + statusValue + "]";
	}
}

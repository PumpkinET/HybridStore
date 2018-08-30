package com.server.android;

public class AddressAndroid {
	String email;
	String fullname;
	String streetAdd;
	String country;
	String city;
	String postalCode;
	String phonenumber;
	
	public AddressAndroid(String email, String fullname, String streetAdd, String country, String city,
			String postalCode, String phonenumber) {
		super();
		this.email = email;
		this.fullname = fullname;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		return "AddressAndroid [email=" + email + ", fullname=" + fullname + ", streetAdd=" + streetAdd + ", country="
				+ country + ", city=" + city + ", postalCode=" + postalCode + ", phonenumber=" + phonenumber + "]";
	}
}

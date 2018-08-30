package com.server.model;

public class Login {
	String email;
	String password;
	String avatar;
	String name;
	String fullname;
	String streetAdd;
	String country;
	String city;
	String postalCode;
	String phonenumber;
	String session;

	public Login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	
	public Login(String email, String password, String avatar, String name, String fullname, String streetAdd,
			String country, String city, String postalCode, String phonenumber) {
		super();
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.name = name;
		this.fullname = fullname;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.phonenumber = phonenumber;
	}




	public Login(String email, String password, String avatar, String name, String fullname, String streetAdd,
			String country, String city, String postalCode, String phonenumber, String session) {
		super();
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.name = name;
		this.fullname = fullname;
		this.streetAdd = streetAdd;
		this.country = country;
		this.city = city;
		this.postalCode = postalCode;
		this.phonenumber = phonenumber;
		this.session = session;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getName() {
		return name;
	}

	public String getSession() {
		return session;
	}
	
	

	public String getFullname() {
		return fullname;
	}

	public String getStreetAdd() {
		return streetAdd;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	@Override
	public String toString() {
		return "Login [email=" + email + ", password=" + password + ", avatar=" + avatar + ", name=" + name
				+ ", fullname=" + fullname + ", streetAdd=" + streetAdd + ", country=" + country + ", city=" + city
				+ ", postalCode=" + postalCode + ", phonenumber=" + phonenumber + ", session=" + session + "]";
	}
	
}

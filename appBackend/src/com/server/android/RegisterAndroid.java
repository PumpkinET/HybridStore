package com.server.android;

public class RegisterAndroid {
	String email;
	String name;
	String password;
	private String imageString;
	public RegisterAndroid(String email, String name, String password, String imageString) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.imageString = imageString;
	}
	public String getImageString() {
		return imageString;
	}
	public void setImageString(String imageString) {
		this.imageString = imageString;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	@Override
	public String toString() {
		return "RegisterAndroid [email=" + email + ", name=" + name + ", password=" + password + "]";
	}
	
}

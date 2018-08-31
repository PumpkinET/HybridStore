package com.server.android;

public class RegisterAndroid {
	String email;
	String password;
	private String imageString;

	public RegisterAndroid(String email, String password, String imageString) {
		super();
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "RegisterAndroid [email=" + email +  ", password=" + password + "]";
	}

}

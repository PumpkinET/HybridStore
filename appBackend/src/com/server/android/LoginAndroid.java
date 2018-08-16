package com.server.android;

public class LoginAndroid {
	String email;
	String password;

	public LoginAndroid(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}

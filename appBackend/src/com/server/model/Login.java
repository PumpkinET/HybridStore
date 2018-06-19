package com.server.model;

public class Login {
	String username;
	String password;
	String email;
	String avatar;
	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Login(String username, String password, String email, String avatar) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
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
	
}

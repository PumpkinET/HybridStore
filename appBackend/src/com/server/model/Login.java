package com.server.model;

public class Login {
	String email;
	String password;
	String avatar;
	String name;
	String session;

	public Login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public Login(String email, String password, String avatar, String name) {
		super();
		this.password = password;
		this.email = email;
		this.avatar = avatar;
		this.name = name;
	}

	public Login(String email, String password, String avatar, String name, String session) {
		super();
		this.password = password;
		this.email = email;
		this.avatar = avatar;
		this.name = name;
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

	@Override
	public String toString() {
		return "Login [email=" + email + ", password=" + password + ", avatar=" + avatar + ", name=" + name
				+ ", session=" + session + "]";
	}

}

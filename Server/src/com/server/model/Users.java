package com.server.model;

public class Users {
	String username;
	String password;
	String email;
	int grade;
	String name;
	int age;
	String address;
	String id;
	String value;
	String session;
	String dbName;
	
	public Users(String username, String password, String email, int grade, String name, int age, String address,
			String id) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.grade = grade;
		this.name = name;
		this.age = age;
		this.address = address;
		this.id = id;
	}
	public Users(String username, String password, String email, int grade, String name, int age, String address,
			String id, String value) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.grade = grade;
		this.name = name;
		this.age = age;
		this.address = address;
		this.id = id;
		this.value = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password + ", email=" + email + ", grade=" + grade
				+ ", name=" + name + ", age=" + age + ", address=" + address + ", id=" + id + ", value=" + value + "]";
	}

}

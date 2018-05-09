package com.server.model;

public class Users {
	String username;
	int grade;
	String name;
	int age;
	String address;
	
	public Users(String username, int grade, String name, int age, String address) {
		super();
		this.username = username;
		this.grade = grade;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public int getGrade() {
		return grade;
	}
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public String getAddress() {
		return address;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Users [username=" + username +  ", grade=" + grade + ", name=" + name
				+ ", age=" + age + ", address=" + address + "]";
	}
	
}

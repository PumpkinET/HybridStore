package com.server.model;

public class UserGrades {
	private String username;
	private int grade;
	public UserGrades(String username, int grade) {
		super();
		this.username = username;
		this.grade = grade;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "UserGrades [username=" + username + ", grade=" + grade + "]";
	}
	
}

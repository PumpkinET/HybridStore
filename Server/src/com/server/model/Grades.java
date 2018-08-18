package com.server.model;

public class Grades {
	private int id;
	private String value;
	public Grades(int id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Grades [id=" + id + ", value=" + value + "]";
	}
	
}

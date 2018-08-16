package com.server.model;

public class Fields {

	private String name;
	private int type;

	public Fields(String name, int type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Fields [name=" + name + ", type=" + type + "]";
	}

}

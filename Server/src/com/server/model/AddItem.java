package com.server.model;

public class AddItem {
	public String column;
	public String value;

	public AddItem(String column, String value) {
		super();
		this.column = column;
		this.value = value;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AddItem [column=" + column + ", value=" + value + "]";
	}

}

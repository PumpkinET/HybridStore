package com.server.model;

public class CreateStore {
	private String storeName;
	private String ownerName;
	private String name;
	private int type;
	private boolean primary;
	public CreateStore(String storeName, String ownerName, String name, int type, boolean primary) {
		super();
		this.storeName = storeName;
		this.ownerName = ownerName;
		this.name = name;
		this.type = type;
		this.primary = primary;
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
	public boolean getPrimary() {
		return primary;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	@Override
	public String toString() {
		return "CreateStore [storeName=" + storeName + ", ownerName=" + ownerName + ", name=" + name + ", type=" + type
				+ ", primary=" + primary + "]";
	}
	
	
}

package com.server.model;

import java.util.Arrays;

public class CreateStore {
	private String storeName;
	private String ownerName;
	private String thumbnail;
	private String description;
	private String ip;
	private String category;
	private Fields[] fields;
	private String phone;
	private String address;
	
	public CreateStore(String storeName, String ownerName, String thumbnail, String description, String ip,
			String category, Fields[] fields, String phone, String address) {
		super();
		this.storeName = storeName;
		this.ownerName = ownerName;
		this.thumbnail = thumbnail;
		this.description = description;
		this.ip = ip;
		this.category = category;
		this.fields = fields;
		this.phone = phone;
		this.address = address;
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Fields[] getFields() {
		return fields;
	}

	public void setFields(Fields[] fields) {
		this.fields = fields;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CreateStore [storeName=" + storeName + ", ownerName=" + ownerName + ", thumbnail=" + thumbnail
				+ ", description=" + description + ", ip=" + ip + ", category=" + category + ", fields="
				+ Arrays.toString(fields) + ", phone=" + phone + ", address=" + address + "]";
	}

	
}

package com.server.model;

public class Image {
	private String imageString;
	private String imageName;
	public Image(String imageString, String imageName) {
		super();
		this.imageString = imageString;
		this.imageName = imageName;
	}
	public String getImageString() {
		return imageString;
	}
	public void setImageString(String imageString) {
		this.imageString = imageString;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	@Override
	public String toString() {
		return "Image [imageString=" + imageString + ", imageName=" + imageName + "]";
	}
	
}

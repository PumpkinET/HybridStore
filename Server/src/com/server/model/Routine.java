package com.server.model;

public class Routine {
	int id;
	String adminuser;
	String targetuser;
	String title;
	String startDate;
	String endDate;
	
	public Routine(int id, String adminuser, String targetuser, String title, String startDate, String endDate) {
		super();
		this.id = id;
		this.adminuser = adminuser;
		this.targetuser = targetuser;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdminuser() {
		return adminuser;
	}

	public void setAdminuser(String adminuser) {
		this.adminuser = adminuser;
	}

	public String getTargetuser() {
		return targetuser;
	}

	public void setTargetuser(String targetuser) {
		this.targetuser = targetuser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Routine [id=" + id + ", adminuser=" + adminuser + ", targetuser=" + targetuser + ", title=" + title
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}

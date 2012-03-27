package com.logictree.supply.models;

public class LoginInfo {

	
	private String status;
	
	private String username;
	private String password;

	private String userId;
	private String name;
	private String address;
	private String city;
	private String state;
	private String phone;
	private String licence;
	private String firstLoginTime;
	private String lastLoginTime;
	private String error;

	/*public LoginInfo(String username, String password, String userId,
			String firstLoginTime, String lastLoginTime) {
		super();
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.firstLoginTime = firstLoginTime;
		this.lastLoginTime = lastLoginTime;
	}*/

	public LoginInfo(String username, String password, String userId,
			String name , String address, String city, String state, String phone,
			String licence, String firstLoginTime, String lastLoginTime) {
		super();
		this.username = username;
		this.password = password;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.licence = licence;
		this.firstLoginTime = firstLoginTime;
		this.lastLoginTime = lastLoginTime;
	}

	public LoginInfo() {
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getUserId() {
		return userId;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPhone() {
		return phone;
	}

	public String getLicence() {
		return licence;
	}

	public String getFirstLoginTime() {
		return firstLoginTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setFirstLoginTime(String firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	

}

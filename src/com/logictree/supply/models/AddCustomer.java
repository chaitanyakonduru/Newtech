package com.logictree.supply.models;

public class AddCustomer extends Priority {
	private String success;
	private String customerId;
	
	public AddCustomer() {
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getSuccess() {
		return success;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

}

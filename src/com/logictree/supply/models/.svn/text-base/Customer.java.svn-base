package com.logictree.supply.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer extends Priority implements Parcelable {

	// <customer>
	// <id>11</id>
	// <customers_name>Geeth Paruchuri</customers_name>
	// <phone>912-225-2482</phone>
	// <licence>567854</licence>
	// <address>Street 123</address>
	// <city>Los Angeles</city>
	// <state>California</state>
	// <zipcode>90001</zipcode>
	// <msa>Yes</msa>
	// <price_level>3</price_level>
	// <status>Inactive</status>
	// </customer>

	private String customerId;
	private String customername;
	private String phoneNo;
	private String licence;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private boolean isMsaFlagOn;
	private String price_level;
	private String status;
	private boolean isSaved;
	
	private String createTime;
	private String updateTime;
	private String businessName;
	
	public Customer(String customerId,String customerName, String businessname, String phoneNo,
			String licence, String address, String city, String state,
			String zipcode, boolean isMsaFlagOn, String price_level,
			String status, String createTime, String updateTime, boolean isSaved) {
		super();
		this.customerId = customerId;
		this.customername = customerName;
		this.licence = licence;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phoneNo = phoneNo;
		this.isMsaFlagOn = isMsaFlagOn;
		this.status = status;
		this.price_level = price_level;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.businessName = businessname;
	}

	public Customer() {
	}

	public String getPrice_level() {
		return price_level;
	}

	public void setPrice_level(String price_level) {
		this.price_level = price_level;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomername() {
		return customername;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public boolean isMsaFlagOn() {
		return isMsaFlagOn;
	}

	public void setMsaFlagOn(boolean isMsaFlagOn) {
		this.isMsaFlagOn = isMsaFlagOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreatedDate(String createdDate) {
		this.createTime = createdDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		List<String> list = new ArrayList<String>();
		list.add(customerId);
		list.add(customername);
		list.add(businessName);
		list.add(licence);
		list.add(address);
		list.add(city);
		list.add(state);
		list.add(zipcode);
		list.add(price_level);
		list.add(status);
		list.add(createTime);
		list.add(updateTime);
		dest.writeStringList(list);
		
		dest.writeInt(isMsaFlagOn ? 1 : 0);
	}

	public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {

		public Customer createFromParcel(Parcel in) {
			return new Customer(in);
		}

		public Customer[] newArray(int size) {
			return new Customer[size];
		}
	};

	public static Parcelable.Creator<Customer> getCreator() {
		return CREATOR;
	}

	private Customer(Parcel in) {
		List<String> list = new ArrayList<String>();
		in.readStringList(list);
		this.customerId = list.get(0);
		this.customername = list.get(1);
		this.businessName = list.get(2);
		this.licence = list.get(3);
		this.address = list.get(4);
		this.city = list.get(5);
		this.state = list.get(6);
		this.zipcode = list.get(7);
		this.price_level = list.get(8);
		this.status = list.get(9);
		this.createTime = list.get(10);
		this.updateTime = list.get(11);
		
		this.isMsaFlagOn = in.readInt() == 0 ? false : true;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}
}
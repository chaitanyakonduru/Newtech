package com.logictree.supply.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderInfo implements Parcelable {
	
	private String orderDate;
	private String amount;
	private String custId;
	private String customerName;
	private String orderBy;

	
	private List<Product> products;
	
	public OrderInfo(){
		products = new ArrayList<Product>();
	}
	
//	public OrderInfo(String orderDate, String amount, String custId,
//			String custname, String orderBy, String productId, String upcCode,
//			String productDescription, String quantity, String totalUnit,
//			String srp, String subretail, String price, String totalCost) {
//		super();
//		this.orderDate = orderDate;
//		this.amount = amount;
//		this.custId = custId;
//		this.custname = custname;
//		this.orderBy = orderBy;
//		this.productId = productId;
//		this.upcCode = upcCode;
//		this.productDescription = productDescription;
//		this.quantity = quantity;
//		this.totalUnit = totalUnit;
//		this.srp = srp;
//		this.subretail = subretail;
//		this.price = price;
//		this.totalCost = totalCost;
//	}

	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String custname) {
		this.customerName = custname;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	public int describeContents() {
		return 0;
	}
	
//	<orders><order>
//	<order_date>11/18/2011</order_date>
//	<amount>20</amount>
//	<customer_id>7</customer_id>
//	<customer_name>Neelima Paruchuri</customer_name>
//	<orderby>John</orderby></order></orders>
//	<products>
//	<product><id>131</id>
//	<upc_code>12756458</upc_code>
//	<product_description>DOPEN Winter Pouches</product_description>
//	<quantity>1</quantity>
//	<total_unit>1</total_unit>
//	<srp>10</srp>
//	<sub_retail>10</sub_retail>
//	<price>20</price>
//	<total_cost>20</total_cost></product></products>
	
	public void writeToParcel(Parcel dest, int flags) {
		List<String> list = new ArrayList<String>();
		list.add(orderDate);
		list.add(amount);
		list.add(custId);
		list.add(customerName);
		list.add(orderBy);
		dest.writeStringList(list);
	}
	
	public OrderInfo(Parcel in) {
		List<String> list = new ArrayList<String>();
		in.readStringList(list);
		this.orderDate = list.get(0);
		this.amount = list.get(1);
		this.custId = list.get(2);
		this.customerName = list.get(3);
		this.orderBy = list.get(4);
	}
	
	public static final Parcelable.Creator<OrderInfo> CREATOR = new Parcelable.Creator<OrderInfo>() {

		public OrderInfo createFromParcel(Parcel source) {
			return new OrderInfo(source);
		}

		public OrderInfo[] newArray(int size) {
			return new OrderInfo[size];
		}
	};
	
	public static Parcelable.Creator<OrderInfo> getCreator() {
		return CREATOR;
	}
}

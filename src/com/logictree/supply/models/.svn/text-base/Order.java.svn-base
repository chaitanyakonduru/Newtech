package com.logictree.supply.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Order extends Priority implements Parcelable {

	private String orderId;
	private String businessname;
	private String orderDate;
	private String amount;
	private String orderBY;
	private String status;
	private String custId;
	private boolean warehouse;
	private boolean isSaved;
	private boolean isSend;
	private boolean isNewOrder;
	private String orderNotes;
	
	private List<Product> products;

	public Order(String orderId, String businessname, String orderDate,
			String amount, String orderBY, String status,
			String custId, boolean warehouse , boolean isSaved, boolean isNewOrder) {
		super();
		this.orderId = orderId;
		this.businessname = businessname;
		this.orderDate = orderDate;
		this.amount = amount;
		this.orderBY = orderBY;
		this.status = status;
		this.custId = custId;
		this.warehouse = warehouse;
		products = new ArrayList<Product>();
	}

	public Order(){
		
	}
	
	public Order(Parcel in) {
		
//		list.add(orderId);
//		list.add(orderDate);
//		list.add(amount);
//		list.add(custId);
//		list.add(customername);
//		list.add(orderBY);
//		list.add(status);
		
		List<String> list = new ArrayList<String>();
		in.readStringList(list);
		this.orderId=list.get(0);
		orderDate=list.get(1);
		amount=list.get(2);
		custId=list.get(3);
		businessname=list.get(4);
		orderBY=list.get(5);
		status=list.get(6);
		warehouse = in.readInt() == 1 ? true : false;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public String getOrderDate() {
		return orderDate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getOrderBY() {
		return orderBY;
	}

	public void setOrderBY(String orderBY) {
		this.orderBY = orderBY;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}
	
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	
	public String getBusinessname() {
		return businessname;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static List<Order> getDefault() {
		List<Order> orders = new ArrayList<Order>();
//		orders.add(new Order("1","Jimmy Jones","09/28/2011","450","Smith","Sent","yes",
//				"11", "123", "1234", "150", "2", "20", "4", "30",
//				"Smith","10/10/11","Smith","11/10/11", "1234"));
//		orders.add(new Order("2","SMITH","09/28/2011","350","Smith","Pending","yes",
//				"11", "123", "1234", "70", "2", "20", "5", "30",
//				"Admin","10/10/11","Smith","21/10/11", "9234"));
//		orders.add(new Order("3","Josh Terry","09/28/2011","500","Smith","Sent","yes",
//				"11", "123", "1234", "100", "2", "20", "5", "30",
//				"Smith","10/10/11","Smith","11/10/11","4567"));
//		orders.add(new Order("4","SMITH","09/28/2011","500","Smith","Pending","yes",
//				"11", "123", "1234", "100", "2", "20", "5", "30",
//				"Smith","10/10/11","Smith","21/10/11","2345"));
//		orders.add(new Order("5","Jimmy Jones","09/28/2011","400","Smith","Sent","yes",
//				"11", "123", "1234", "100", "2", "20", "4", "30",
//				"Smith","10/10/11","Smith","11/10/11","1234"));
//		orders.add(new Order("6","Geeth Paruchuri","09/28/2011","300","Smith","Pending","yes",
//				"11", "123", "1234", "100", "2", "20", "3", "30",
//				"Smith","10/10/11","Smith","21/10/11","9456"));
//		orders.add(new Order("7","Josh Terry","09/28/2011","100","Smith","Sent","yes",
//				"11", "123", "1234", "50", "2", "20", "2", "30",
//				"Smith","10/10/11","Smith","21/10/11","4563"));
//		orders.add(new Order("8","Jimmy Jones","09/28/2011","450","Smith","Pending","yes",
//				"11", "123", "1234", "90", "2", "10", "5", "30",
//				"Smith","10/10/11","Smith","11/10/11","3765"));
//		orders.add(new Order("9","SMITH","09/28/2011","150","Smith","Pending","yes",
//				"11", "123", "1234", "30", "2", "20", "5", "30",
//				"Smith","10/10/11","Smith","11/10/11","8563"));
//		orders.add(new Order("1","Josh Terry","09/28/2011","450","Smith","Sent","yes",
//				"11", "123", "1234", "90", "2", "20", "5", "30",
//				"Smith","10/10/11","Smith","21/10/11","8888"));
//		orders.add(new Order("2","Geeth Paruchuri","09/28/2011","450","Smith","Sent","yes",
//				"11", "123", "1234", "90", "2", "20", "5", "30",
//				"Smith","10/10/11","Smith","11/10/11","1289"));
		return orders;
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof Order){
			if(object!=null){
				Order order = (Order) object;
				if(orderId.equals(order.getOrderId())){
					return true;
				}
			}
		}
		return false;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		List<String> list = new ArrayList<String>();
		list.add(orderId);
		list.add(orderDate);
		list.add(amount);
		list.add(custId);
		list.add(businessname);
		list.add(orderBY);
		list.add(status);
//		list.add(warehouse);
		dest.writeStringList(list);
		dest.writeInt(warehouse ? 1 : 0);
	}
	
	public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {

		public Order createFromParcel(Parcel in) {
			return new Order(in);
		}

		public Order[] newArray(int size) {
			return new Order[size];
		}
	};
	
	public static Parcelable.Creator<Order> getCreator() {
		return CREATOR;
	}

	public void setWarehouse(boolean warehouse) {
		this.warehouse = warehouse;
	}

	public boolean isWarehouse() {
		return warehouse;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public boolean isSaved() {
		return isSaved;
	}
	
	public void setNewOrder(boolean isNewOrder) {
		this.isNewOrder = isNewOrder;
	}

	public boolean isNewOrder() {
		return isNewOrder;
	}

	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	public String getOrderNotes() {
		return orderNotes;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public boolean isSend() {
		return isSend;
	}
}
package com.logictree.supply.models;

import java.util.ArrayList;
import java.util.List;

public class Product extends Priority {
	
	 /*<product>
	  <id>4</id> 
	  <department_name>Mobile</department_name> 
	  <product_name>A6 Samsung</product_name> 
	  <upc_code>12345</upc_code> 
	  <unit>5</unit> 
	  <suggested_retail_price>400</suggested_retail_price> 
	  <price>400</price> 
	  <cost>420</cost> 
	  <status>Inactive</status> 
	  </product>*/

	private String productId;
	private String departmentName;
	private String productName;
	private String upcCode;
	private String unit;
	private String suggestedPrice;
	private String price;
	private String cost;
	private String status;
	private String quantity ="1";
	private String subRetail = "1";
	private String totalCost = "1";
	private String orderId;
	private String userId;
	private boolean saved;
	private boolean isSend;
	private boolean newOrder;
	
	public Product(String productId, String departmentName, String productName,
			String upcCode, String unit, String suggestedPrice, String price, String cost,
			String status) {
		super();
		this.productId = productId;
		this.departmentName = departmentName;
		this.productName = productName;
		this.upcCode = upcCode;
		this.unit = unit;
		this.suggestedPrice = suggestedPrice;
		this.price = price;
		this.cost = cost;
		this.status = status;
	}
	
	public Product() {
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUpcCode() {
		return upcCode;
	}

	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSuggestedPrice() {
		return suggestedPrice;
	}

	public void setSuggestedPrice(String suggestedPrice) {
		this.suggestedPrice = suggestedPrice;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public static List<Product> getDefault() {
		List<Product> products = new ArrayList<Product>();
//		products.add(new Product("1", "Beauty","A0 COPEN Winter","76756458","3","3956","45","Active","1",
//				"123", "500", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("2", "Health","A1 COPE WHISKY","76756458","3","3956","45","Inactive","1",
//				"123", "400", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("3", "Batteries","A0 COPEN Winter","76756458","3","3956","45","Active","1",
//				"123", "300", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("4", "Chew","A1 COPE WHISKY","76756458","3","3956","45","Active","1",
//				"123", "100", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("5", "Beauty","A0 COPEN Winter","76756458","3","3956","45","Active","1",
//				"123", "300", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("6", "Health","A1 COPE WHISKY","76756458","3","3956","45","Active","1",
//				"123", "500", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("7", "Chew","A0 COPEN Winter","76756458","3","3956","45","Active","1",
//				"123", "450", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("8", "Health","A1 COPE WHISKY","76756458","3","3956","45","Inactive","1",
//				"123", "350", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("9", "Chew","A0 COPEN Winter","76756458","3","3956","45","Active","1",
//				"123", "250", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
//		products.add(new Product("10", "Smoke","A1 COPE WHISKY","76756458","3","3956","45","Active","1",
//				"123", "150", "10", "15", "20", "30", "50", "Smith","10/10/11","Smith","11/10/11"));
		return products;
	}

	public void addProduct(Product mProduct) {
		List<Product> products = new ArrayList<Product>();
		products.add(mProduct);
	}

	public void setSubRetail(String subRetail) {
		this.subRetail = subRetail;
	}

	public String getSubRetail() {
		return subRetail;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object o) {

		if(o == this){
			return true;
		}
		
		if( o instanceof Product ){
			if(this.productId.equals(((Product) o).getProductId())){
				return true;
			}
			return false;
		}
		
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean isSaved() {
		return saved;
	}
	
	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}
	
	public boolean isSend() {
		return isSend;
	}

	public void setNewOrder(boolean newOrder) {
		this.newOrder = newOrder;
	}

	public boolean isNewOrder() {
		return newOrder;
	}
}
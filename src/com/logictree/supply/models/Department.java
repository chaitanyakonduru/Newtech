package com.logictree.supply.models;

public class Department extends Priority {
	
	private String deptId;
	private String deptName;
	private String status;
	
//	private String display_order;
//	private String createdBy;
//	private String createdDate;
//	private String modifiedBy;
//	private String modifiedDate;
	
	/*<departments>
	  <id>5</id> 
	  <departments_name>Testing</departments_name> 
	  <status>Active</status> 
	  </departments>*/
	
	public Department(String deptId, String deptName, String status) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.status = status;
//		this.display_order = display_order;
//		this.createdBy = createdBy;
//		this.createdDate = createdDate;
//		this.modifiedBy = modifiedBy;
//		this.modifiedDate = modifiedDate;
	}

	public Department() {
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return " "+deptName;
	}
}

package com.logictree.supply.models;

public class RoleScreen {
	
	private String screen_roleId;
	private String screenId;
	private String roleId;
	private String createAcc;
	private String updateAcc;
	private String viewAcc;
	private String deleteAcc;
	private String printAcc;
	private String createdBy;
	private String createdOn;
	private String modifiedBy;
	private String modifiedOn;
	
	public RoleScreen(String screen_roleId, String screenId, String roleId,
			String createAcc, String updateAcc, String viewAcc,
			String deleteAcc, String printAcc, String createdBy,
			String createdOn, String modifiedBy, String modifiedOn) {
		super();
		this.screen_roleId = screen_roleId;
		this.screenId = screenId;
		this.roleId = roleId;
		this.createAcc = createAcc;
		this.updateAcc = updateAcc;
		this.viewAcc = viewAcc;
		this.deleteAcc = deleteAcc;
		this.printAcc = printAcc;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
	}
	public String getScreen_roleId() {
		return screen_roleId;
	}
	public void setScreen_roleId(String screen_roleId) {
		this.screen_roleId = screen_roleId;
	}
	public String getScreenId() {
		return screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getCreateAcc() {
		return createAcc;
	}
	public void setCreateAcc(String createAcc) {
		this.createAcc = createAcc;
	}
	public String getUpdateAcc() {
		return updateAcc;
	}
	public void setUpdateAcc(String updateAcc) {
		this.updateAcc = updateAcc;
	}
	public String getViewAcc() {
		return viewAcc;
	}
	public void setViewAcc(String viewAcc) {
		this.viewAcc = viewAcc;
	}
	public String getDeleteAcc() {
		return deleteAcc;
	}
	public void setDeleteAcc(String deleteAcc) {
		this.deleteAcc = deleteAcc;
	}
	public String getPrintAcc() {
		return printAcc;
	}
	public void setPrintAcc(String printAcc) {
		this.printAcc = printAcc;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}

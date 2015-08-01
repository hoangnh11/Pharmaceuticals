package com.viviproject.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class EnPlanSaleRowItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7889614512410973927L;
	private String name;
	private String plan;
	@SerializedName("do")
	private String doItem;
	private String left;
	private String complete;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getDoItem() {
		return doItem;
	}
	public void setDoItem(String doItem) {
		this.doItem = doItem;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

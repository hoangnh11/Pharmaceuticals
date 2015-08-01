package com.viviproject.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class EnPlanSaleRowItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7889614512410973927L;
	private String name;
	private int plan;
	@SerializedName("do")
	private int doItem;
	private int left;
	private String complete;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlan() {
		return plan;
	}
	public void setPlan(int plan) {
		this.plan = plan;
	}
	public int getDoItem() {
		return doItem;
	}
	public void setDoItem(int doItem) {
		this.doItem = doItem;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
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

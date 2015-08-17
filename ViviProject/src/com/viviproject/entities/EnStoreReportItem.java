package com.viviproject.entities;

import java.io.Serializable;

public class EnStoreReportItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2562936934956530790L;
	private String employee_id;
	private String store_id;
	private String name;
	private String address;
	private String sumary;
	
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSumary() {
		return sumary;
	}
	public void setSumary(String sumary) {
		this.sumary = sumary;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

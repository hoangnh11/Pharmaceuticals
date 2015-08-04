package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnStoreItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 847245447234456025L;
	private String id;
	private String code;
	private String name;
	private String address;
	private ArrayList<EnGimicStoreItem> gimics;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public ArrayList<EnGimicStoreItem> getGimics() {
		return gimics;
	}
	public void setGimics(ArrayList<EnGimicStoreItem> gimics) {
		this.gimics = gimics;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

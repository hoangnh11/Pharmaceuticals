package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnGimicManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8474103259430102184L;
	private ArrayList<EnGimicItem> gimics;
	private ArrayList<EnStoreItem> stores;
	private String status;
	public ArrayList<EnGimicItem> getGimics() {
		return gimics;
	}
	public void setGimics(ArrayList<EnGimicItem> gimics) {
		this.gimics = gimics;
	}
	public ArrayList<EnStoreItem> getStores() {
		return stores;
	}
	public void setStores(ArrayList<EnStoreItem> stores) {
		this.stores = stores;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

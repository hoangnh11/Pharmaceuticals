package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnStoreReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8550174807821440307L;
	private ArrayList<EnStoreReportItem> stores;
	
	public ArrayList<EnStoreReportItem> getStores() {
		return stores;
	}
	public void setStores(ArrayList<EnStoreReportItem> stores) {
		this.stores = stores;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

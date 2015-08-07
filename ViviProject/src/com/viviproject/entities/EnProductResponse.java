package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnProductResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6092594970855315392L;
	private ArrayList<EnProduct> categories;
	private String status;
	public ArrayList<EnProduct> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<EnProduct> categories) {
		this.categories = categories;
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

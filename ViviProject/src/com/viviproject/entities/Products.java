package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Products implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -906355085374706792L;
	private ArrayList<EnProducts> products;
	private String status;
	
	/**
	 * @return the products
	 */
	public ArrayList<EnProducts> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<EnProducts> products) {
		this.products = products;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}	
	
}

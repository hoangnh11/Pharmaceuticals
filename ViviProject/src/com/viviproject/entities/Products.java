package com.viviproject.entities;

import java.io.Serializable;

public class Products implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -906355085374706792L;
	private EnProducts products;
	private String status;
	/**
	 * @return the products
	 */
	public EnProducts getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(EnProducts products) {
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

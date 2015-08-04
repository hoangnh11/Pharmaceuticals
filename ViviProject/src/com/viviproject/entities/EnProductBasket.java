package com.viviproject.entities;

import java.io.Serializable;

public class EnProductBasket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -100137740213630436L;
	private int product_id;
	private int quantity;
	/**
	 * @return the product_id
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
	
}

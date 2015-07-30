package com.viviproject.entities;

import java.io.Serializable;

public class EnGimicBasket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912643394544631744L;
	private int gimic_id;
	private int quantity;
	
	/**
	 * @return the gimic_id
	 */
	public int getGimic_id() {
		return gimic_id;
	}
	/**
	 * @param gimic_id the gimic_id to set
	 */
	public void setGimic_id(int gimic_id) {
		this.gimic_id = gimic_id;
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

package com.viviproject.entities;

import java.io.Serializable;

public class EnPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2598012631872825876L;
	private String discount_id;
	private String quantity;
	private String point;
	/**
	 * @return the discount_id
	 */
	public String getDiscount_id() {
		return discount_id;
	}
	/**
	 * @param discount_id the discount_id to set
	 */
	public void setDiscount_id(String discount_id) {
		this.discount_id = discount_id;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the point
	 */
	public String getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(String point) {
		this.point = point;
	}
			
}

package com.viviproject.entities;

import java.io.Serializable;

public class EnOther implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1611807936412115184L;
	private String subtype;
	private String discount_id;
	private String quantity;
	private String max;
	private String with_point;	
	
	
	/**
	 * @return the subtype
	 */
	public String getSubtype() {
		return subtype;
	}
	/**
	 * @param subtype the subtype to set
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
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
	 * @return the max
	 */
	public String getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(String max) {
		this.max = max;
	}
	/**
	 * @return the with_point
	 */
	public String getWith_point() {
		return with_point;
	}
	/**
	 * @param with_point the with_point to set
	 */
	public void setWith_point(String with_point) {
		this.with_point = with_point;
	}
}

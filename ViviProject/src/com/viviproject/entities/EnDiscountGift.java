package com.viviproject.entities;

import java.io.Serializable;

public class EnDiscountGift implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5375189725668497104L;
	private String id;
	private String other_id;
	private String gift_code;
	private String gift_name;
	private String quantity;
	private int quantity_receive;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the other_id
	 */
	public String getOther_id() {
		return other_id;
	}
	/**
	 * @param other_id the other_id to set
	 */
	public void setOther_id(String other_id) {
		this.other_id = other_id;
	}
	/**
	 * @return the gift_code
	 */
	public String getGift_code() {
		return gift_code;
	}
	/**
	 * @param gift_code the gift_code to set
	 */
	public void setGift_code(String gift_code) {
		this.gift_code = gift_code;
	}
	/**
	 * @return the gift_name
	 */
	public String getGift_name() {
		return gift_name;
	}
	/**
	 * @param gift_name the gift_name to set
	 */
	public void setGift_name(String gift_name) {
		this.gift_name = gift_name;
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
	 * @return the quantity_receive
	 */
	public int getQuantity_receive() {
		return quantity_receive;
	}
	/**
	 * @param quantity_receive the quantity_receive to set
	 */
	public void setQuantity_receive(int quantity_receive) {
		this.quantity_receive = quantity_receive;
	}
	
	
}

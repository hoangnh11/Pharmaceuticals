package com.viviproject.entities;

import java.io.Serializable;

public class EnGift implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7393138535454994694L;
	private String id;
	private String gift_id;
	private String gift_quantity;
	private String gift_code;
	private String gift_name;
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
	 * @return the gift_id
	 */
	public String getGift_id() {
		return gift_id;
	}
	/**
	 * @param gift_id the gift_id to set
	 */
	public void setGift_id(String gift_id) {
		this.gift_id = gift_id;
	}
	/**
	 * @return the gift_quantity
	 */
	public String getGift_quantity() {
		return gift_quantity;
	}
	/**
	 * @param gift_quantity the gift_quantity to set
	 */
	public void setGift_quantity(String gift_quantity) {
		this.gift_quantity = gift_quantity;
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
		
}

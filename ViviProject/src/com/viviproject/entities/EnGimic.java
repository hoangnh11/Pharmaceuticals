package com.viviproject.entities;

import java.io.Serializable;

public class EnGimic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3066019496455746325L;
	private String id;
	private String code;
	private String name;
	private String unit;
	private String quantity_max;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the quantity_max
	 */
	public String getQuantity_max() {
		return quantity_max;
	}
	/**
	 * @param quantity_max the quantity_max to set
	 */
	public void setQuantity_max(String quantity_max) {
		this.quantity_max = quantity_max;
	}	
	
}

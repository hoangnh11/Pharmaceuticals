package com.viviproject.entities;

import java.io.Serializable;

public class EnProducts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7903624302907949396L;
	private String id;
	private String code;
	private String name;
	private String preview_text;
	private String unit;
	private String price;
	private String color;
	private EnDiscount discount;
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
	 * @return the preview_text
	 */
	public String getPreview_text() {
		return preview_text;
	}
	/**
	 * @param preview_text the preview_text to set
	 */
	public void setPreview_text(String preview_text) {
		this.preview_text = preview_text;
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the discount
	 */
	public EnDiscount getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(EnDiscount discount) {
		this.discount = discount;
	}
		
}

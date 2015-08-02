package com.viviproject.entities;

import java.io.Serializable;

public class EnProductSales implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7429843011886558336L;
	private String id;
	private String product_id;
	private String product_qty;
	private String name;
	private String code;
	private String tempProductQty;
		
	/**
	 * @return the tempProductQty
	 */
	public String getTempProductQty() {
		return tempProductQty;
	}
	/**
	 * @param tempProductQty the tempProductQty to set
	 */
	public void setTempProductQty(String tempProductQty) {
		this.tempProductQty = tempProductQty;
	}
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
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the product_qty
	 */
	public String getProduct_qty() {
		return product_qty;
	}
	/**
	 * @param product_qty the product_qty to set
	 */
	public void setProduct_qty(String product_qty) {
		this.product_qty = product_qty;
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
}

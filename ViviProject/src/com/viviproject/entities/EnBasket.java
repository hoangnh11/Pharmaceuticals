package com.viviproject.entities;

import java.io.Serializable;

public class EnBasket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4710719235869967604L;
	private int product_id;
	private int quantity;
	private int point;
	private int sale;
	private int other;
		
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
	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}
	/**
	 * @return the sale
	 */
	public int getSale() {
		return sale;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale(int sale) {
		this.sale = sale;
	}
	/**
	 * @return the other
	 */
	public int getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(int other) {
		this.other = other;
	}
	
	
}

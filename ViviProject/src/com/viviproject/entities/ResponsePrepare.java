package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponsePrepare implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6685024336724067385L;
	private ArrayList<EnSalePrepare> basket;
	private int subtotal;
	private int total_discount;
	private int total_point;
	private int total;
	private String status;
	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the basket
	 */
	public ArrayList<EnSalePrepare> getBasket() {
		return basket;
	}
	/**
	 * @param basket the basket to set
	 */
	public void setBasket(ArrayList<EnSalePrepare> basket) {
		this.basket = basket;
	}
	/**
	 * @return the subtotal
	 */
	public int getSubtotal() {
		return subtotal;
	}
	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	/**
	 * @return the total_discount
	 */
	public int getTotal_discount() {
		return total_discount;
	}
	/**
	 * @param total_discount the total_discount to set
	 */
	public void setTotal_discount(int total_discount) {
		this.total_discount = total_discount;
	}
	/**
	 * @return the total_point
	 */
	public int getTotal_point() {
		return total_point;
	}
	/**
	 * @param total_point the total_point to set
	 */
	public void setTotal_point(int total_point) {
		this.total_point = total_point;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
		
}

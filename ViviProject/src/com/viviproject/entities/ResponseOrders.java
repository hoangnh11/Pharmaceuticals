package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseOrders implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -60702172261621941L;
	private ArrayList<EnOrder> orders;
	private String message;
	private String status;
	/**
	 * @return the orders
	 */
	public ArrayList<EnOrder> getOrders() {
		return orders;
	}
	/**
	 * @param orders the orders to set
	 */
	public void setOrders(ArrayList<EnOrder> orders) {
		this.orders = orders;
	}
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

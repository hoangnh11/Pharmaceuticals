package com.viviproject.entities;

import java.io.Serializable;

public class EnLatestFiveOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5613816234729715525L;
	private String date;
	private String total;
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
		
}

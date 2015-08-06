package com.viviproject.entities;

import java.io.Serializable;

public class EnLatestThreeOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5646059763942982563L;
	private String month;
	private String total;
	
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
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

package com.viviproject.entities;

import java.io.Serializable;

public class EnDiscount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472687065777772906L;
	private EnPoint point;
	private EnOther other;
	private EnSale sale;
		
	/**
	 * @return the other
	 */
	public EnOther getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(EnOther other) {
		this.other = other;
	}
	/**
	 * @return the sale
	 */
	public EnSale getSale() {
		return sale;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale(EnSale sale) {
		this.sale = sale;
	}
	/**
	 * @return the point
	 */
	public EnPoint getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(EnPoint point) {
		this.point = point;
	}

}

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnDiscount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472687065777772906L;
	private ArrayList<EnPoint> point;
	private ArrayList<EnOther> other;
	private ArrayList<EnSale> sale;

	/**
	 * @return the point
	 */
	public ArrayList<EnPoint> getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(ArrayList<EnPoint> point) {
		this.point = point;
	}

	/**
	 * @return the other
	 */
	public ArrayList<EnOther> getOther() {
		return other;
	}

	/**
	 * @param other
	 *            the other to set
	 */
	public void setOther(ArrayList<EnOther> other) {
		this.other = other;
	}

	/**
	 * @return the sale
	 */
	public ArrayList<EnSale> getSale() {
		return sale;
	}

	/**
	 * @param sale
	 *            the sale to set
	 */
	public void setSale(ArrayList<EnSale> sale) {
		this.sale = sale;
	}
}

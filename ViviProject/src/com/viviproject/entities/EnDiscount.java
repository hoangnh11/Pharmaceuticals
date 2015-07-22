package com.viviproject.entities;

import java.io.Serializable;

public class EnDiscount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472687065777772906L;
	private EnPoint point;
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

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnDiscountProgram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8257228659813280104L;
	private ArrayList<EnDiscountProgramItem> discounts;
	private String status;
	
	public ArrayList<EnDiscountProgramItem> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(ArrayList<EnDiscountProgramItem> discounts) {
		this.discounts = discounts;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnCoverReportByType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5808513601649271034L;
	private String date;
	private ArrayList<EnPlanSaleRowItem> row_items;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<EnPlanSaleRowItem> getRow_items() {
		return row_items;
	}
	public void setRow_items(ArrayList<EnPlanSaleRowItem> row_items) {
		this.row_items = row_items;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnPlanSaleSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2182844890820162508L;
	private String date;
	private EnPlanSaleRowItem row_sumary;
	private ArrayList<EnPlanSaleRowItem> row_items;
	private EnPlanSaleRowItem row_customer;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public EnPlanSaleRowItem getRow_sumary() {
		return row_sumary;
	}
	public void setRow_sumary(EnPlanSaleRowItem row_sumary) {
		this.row_sumary = row_sumary;
	}
	public ArrayList<EnPlanSaleRowItem> getRow_items() {
		return row_items;
	}
	public void setRow_items(ArrayList<EnPlanSaleRowItem> row_items) {
		this.row_items = row_items;
	}
	public EnPlanSaleRowItem getRow_customer() {
		return row_customer;
	}
	public void setRow_customer(EnPlanSaleRowItem row_customer) {
		this.row_customer = row_customer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

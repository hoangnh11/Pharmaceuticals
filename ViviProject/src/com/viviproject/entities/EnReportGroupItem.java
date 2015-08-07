package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnReportGroupItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4457590513271251774L;
	private String catalog_group_id;
	private String name;
	private int quantity;
	private String sale;
	private ArrayList<EnReportProductItem> products;
	
	public String getCatalog_group_id() {
		return catalog_group_id;
	}
	public void setCatalog_group_id(String catalog_group_id) {
		this.catalog_group_id = catalog_group_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public ArrayList<EnReportProductItem> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<EnReportProductItem> products) {
		this.products = products;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

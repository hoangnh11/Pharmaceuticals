package com.viviproject.entities;

import java.io.Serializable;

public class EnReportProductItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 282097836249905153L;
	private String id;
	private String code;
	private String name;
	private String quantity;
	private String sale;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

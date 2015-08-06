package com.viviproject.entities;

import java.io.Serializable;

public class EnCompanyNewsDetailsRespone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333755669026542961L;
	private EnCompanyNewsDetails detail;
	private String status;
	public EnCompanyNewsDetails getDetail() {
		return detail;
	}
	public void setDetail(EnCompanyNewsDetails detail) {
		this.detail = detail;
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

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnReportProfitResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2952060156026387671L;
	private ArrayList<EnReportGroupItem> groups;
	private String total_sale;
	private String message;
	private String status;
	public ArrayList<EnReportGroupItem> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<EnReportGroupItem> groups) {
		this.groups = groups;
	}
	public String getTotal_sale() {
		return total_sale;
	}
	public void setTotal_sale(String total_sale) {
		this.total_sale = total_sale;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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

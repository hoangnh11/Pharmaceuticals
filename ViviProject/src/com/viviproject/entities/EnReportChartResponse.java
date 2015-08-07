package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnReportChartResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5708930266319507838L;
	private EnReportDay day;
	private EnReportMonth month;
	private String message;
	private String status;
	
	public EnReportDay getDay() {
		return day;
	}
	public void setDay(EnReportDay day) {
		this.day = day;
	}
	public EnReportMonth getMonth() {
		return month;
	}
	public void setMonth(EnReportMonth month) {
		this.month = month;
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

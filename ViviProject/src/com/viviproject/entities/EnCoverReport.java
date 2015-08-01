package com.viviproject.entities;

import java.io.Serializable;

public class EnCoverReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5732791355725023830L;
	private String month_days;
	private String working_days;
	private EnCoverReportByType today;
	private EnCoverReportByType sumary;
	private String message;
	private String status;
	
	public String getMonth_days() {
		return month_days;
	}
	public void setMonth_days(String month_days) {
		this.month_days = month_days;
	}
	public String getWorking_days() {
		return working_days;
	}
	public void setWorking_days(String working_days) {
		this.working_days = working_days;
	}
	public EnCoverReportByType getToday() {
		return today;
	}
	public void setToday(EnCoverReportByType today) {
		this.today = today;
	}
	public EnCoverReportByType getSumary() {
		return sumary;
	}
	public void setSumary(EnCoverReportByType sumary) {
		this.sumary = sumary;
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

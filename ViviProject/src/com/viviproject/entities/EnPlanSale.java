package com.viviproject.entities;

import java.io.Serializable;

public class EnPlanSale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200387204645932904L;
	private String month_days;
	private String working_days;
	private EnPlanSaleSummary today; 
	private EnPlanSaleSummary sumary;
	private String message;
	private String status;
	
	public EnPlanSale() {
		super();
	}
	
	public EnPlanSale(String month_days, String working_days,
			EnPlanSaleSummary today, EnPlanSaleSummary sumary, String message,
			String status) {
		super();
		this.month_days = month_days;
		this.working_days = working_days;
		this.today = today;
		this.sumary = sumary;
		this.message = message;
		this.status = status;
	}
	
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
	public EnPlanSaleSummary getToday() {
		return today;
	}
	public void setToday(EnPlanSaleSummary today) {
		this.today = today;
	}
	public EnPlanSaleSummary getSumary() {
		return sumary;
	}
	public void setSumary(EnPlanSaleSummary sumary) {
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

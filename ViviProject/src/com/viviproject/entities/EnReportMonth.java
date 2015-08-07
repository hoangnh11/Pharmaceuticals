package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnReportMonth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090486921760305575L;
	private String month;
	private ArrayList<EnItemDataChart> chart_data;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public ArrayList<EnItemDataChart> getChart_data() {
		return chart_data;
	}
	public void setChart_data(ArrayList<EnItemDataChart> chart_data) {
		this.chart_data = chart_data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

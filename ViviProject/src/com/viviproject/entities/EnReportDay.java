package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnReportDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298804014185283241L;
	private String date;
	private ArrayList<EnItemDataChart> chart_data;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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

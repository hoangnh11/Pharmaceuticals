package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnNewsList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7548252831549360749L;
	private ArrayList<EnNews> news;
	private String status;
	
	public ArrayList<EnNews> getNews() {
		return news;
	}
	public void setNews(ArrayList<EnNews> news) {
		this.news = news;
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

package com.viviproject.entities;

import java.io.Serializable;

public class EnReportImageResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int image_id;
	private String status;
	
	public EnReportImageResponse(int image_id, String status) {
		super();
		this.image_id = image_id;
		this.status = status;
	}
	
	public EnReportImageResponse() {
		super();
	}
	
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
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

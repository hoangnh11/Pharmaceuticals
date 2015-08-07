package com.viviproject.entities;

import java.io.Serializable;

public class EnImageUrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3827669054059130080L;
	private String image_url;
	private String title;
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

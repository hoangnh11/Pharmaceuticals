package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnImageUrlResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8445938296855550654L;
	private ArrayList<EnImageUrl> images;
	private String status;
	public ArrayList<EnImageUrl> getImages() {
		return images;
	}
	public void setImages(ArrayList<EnImageUrl> images) {
		this.images = images;
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

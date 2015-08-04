package com.viviproject.entities;

import java.io.Serializable;

public class EnNews implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7536299394396418839L;
	private String date_time;
	private String title;
	private String preview_image;
	private String preview_text;
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPreview_image() {
		return preview_image;
	}
	public void setPreview_image(String preview_image) {
		this.preview_image = preview_image;
	}
	public String getPreview_text() {
		return preview_text;
	}
	public void setPreview_text(String preview_text) {
		this.preview_text = preview_text;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

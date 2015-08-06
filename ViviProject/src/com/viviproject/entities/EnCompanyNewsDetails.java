package com.viviproject.entities;

import java.io.Serializable;

public class EnCompanyNewsDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1559831228702545529L;
	private String id;
	private String timestamp_x;
	private String create_by;
	private String active;
	private String title;
	private String preview_image;
	private String preview_text;
	private String detail_text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimestamp_x() {
		return timestamp_x;
	}
	public void setTimestamp_x(String timestamp_x) {
		this.timestamp_x = timestamp_x;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
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
	public String getDetail_text() {
		return detail_text;
	}
	public void setDetail_text(String detail_text) {
		this.detail_text = detail_text;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}

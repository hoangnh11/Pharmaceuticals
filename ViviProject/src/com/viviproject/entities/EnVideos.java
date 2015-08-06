package com.viviproject.entities;

import java.io.Serializable;

public class EnVideos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5120874979123185524L;
	private String id;
	private String video_url;
	private String video_preview_image;
	private String preview_text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getVideo_preview_image() {
		return video_preview_image;
	}
	public void setVideo_preview_image(String video_preview_image) {
		this.video_preview_image = video_preview_image;
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

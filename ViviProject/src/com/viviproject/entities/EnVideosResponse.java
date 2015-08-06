package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnVideosResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6633943415641552300L;
	private ArrayList<EnVideos> videos;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ArrayList<EnVideos> getVideos() {
		return videos;
	}
	public void setVideos(ArrayList<EnVideos> videos) {
		this.videos = videos;
	}
}

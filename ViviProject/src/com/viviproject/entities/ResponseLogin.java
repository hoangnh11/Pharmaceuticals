package com.viviproject.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2689078963565282148L;
	private String code;
	private String message;
	private String status;
	private int gps_interval;
	
		
	/**
	 * @return the gps_interval
	 */
	public int getGps_interval() {
		return gps_interval;
	}
	/**
	 * @param gps_interval the gps_interval to set
	 */
	public void setGps_interval(int gps_interval) {
		this.gps_interval = gps_interval;
	}
	@SerializedName("access-token")
	private String accessToken;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}	
	
}

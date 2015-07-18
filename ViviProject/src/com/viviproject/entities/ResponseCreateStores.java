package com.viviproject.entities;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ResponseCreateStores implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2494209405692259512L;
	private String status;
	@SerializedName("access-token")
	private String accessToken;
	private int store_id;
	private String message; 
	
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
	/**
	 * @return the store_id
	 */
	public int getStore_id() {
		return store_id;
	}
	/**
	 * @param store_id the store_id to set
	 */
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	
	
}

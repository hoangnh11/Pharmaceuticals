package com.viviproject.entities;

import java.io.Serializable;

public class EnFeedback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1775148206815209096L;
	private int feedback_id;
	private String status;
	private String message;
	/**
	 * @return the feedback_id
	 */
	public int getFeedback_id() {
		return feedback_id;
	}
	/**
	 * @param feedback_id the feedback_id to set
	 */
	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
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
	
	
}

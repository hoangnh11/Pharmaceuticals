package com.viviproject.entities;

import java.io.Serializable;

public class ResponseReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4952628021791064717L;
	private int report_id;
	private String status;
	private String message;
	/**
	 * @return the report_id
	 */
	public int getReport_id() {
		return report_id;
	}
	/**
	 * @param report_id the report_id to set
	 */
	public void setReport_id(int report_id) {
		this.report_id = report_id;
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

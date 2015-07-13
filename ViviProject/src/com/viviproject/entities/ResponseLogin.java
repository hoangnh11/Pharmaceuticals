package com.viviproject.entities;

import java.io.Serializable;

public class ResponseLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2689078963565282148L;
	private String field;
	private String message;
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
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

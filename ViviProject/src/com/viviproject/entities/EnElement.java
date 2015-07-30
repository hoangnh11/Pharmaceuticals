package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnElement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6339118414454854663L;
	private ArrayList<EnGimic> elements;
	private String status;
	/**
	 * @return the elements
	 */
	public ArrayList<EnGimic> getElements() {
		return elements;
	}
	/**
	 * @param elements the elements to set
	 */
	public void setElements(ArrayList<EnGimic> elements) {
		this.elements = elements;
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
	
}

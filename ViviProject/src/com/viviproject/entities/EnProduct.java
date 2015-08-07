package com.viviproject.entities;

import java.io.Serializable;

public class EnProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5171825984191390331L;
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

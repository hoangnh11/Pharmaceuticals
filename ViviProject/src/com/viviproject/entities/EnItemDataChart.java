package com.viviproject.entities;

import java.io.Serializable;

public class EnItemDataChart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3163607288396337537L;
	private double plan;
	private String name;
	private int value;
	private String color;
	private int value_percent;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getValue_percent() {
		return value_percent;
	}
	public void setValue_percent(int value_percent) {
		this.value_percent = value_percent;
	}
	public double getPlan() {
		return plan;
	}
	public void setPlan(double plan) {
		this.plan = plan;
	}
}

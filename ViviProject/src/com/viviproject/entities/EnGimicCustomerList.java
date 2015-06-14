package com.viviproject.entities;

import java.io.Serializable;

public class EnGimicCustomerList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String drugStoreName;
	private String drugStoreAddress;
	private int numPen;
	private int numMagazin;
	private int numClock;
	private int numWaterBottle;
	public EnGimicCustomerList(String drugStoreName, String drugStoreAddress,
			int numPen, int numMagazin, int numClock, int numWaterBottle) {
		super();
		this.drugStoreName = drugStoreName;
		this.drugStoreAddress = drugStoreAddress;
		this.numPen = numPen;
		this.numMagazin = numMagazin;
		this.numClock = numClock;
		this.numWaterBottle = numWaterBottle;
	}
	public String getDrugStoreName() {
		return drugStoreName;
	}
	public void setDrugStoreName(String drugStoreName) {
		this.drugStoreName = drugStoreName;
	}
	public String getDrugStoreAddress() {
		return drugStoreAddress;
	}
	public void setDrugStoreAddress(String drugStoreAddress) {
		this.drugStoreAddress = drugStoreAddress;
	}
	public int getNumPen() {
		return numPen;
	}
	public void setNumPen(int numPen) {
		this.numPen = numPen;
	}
	public int getNumMagazin() {
		return numMagazin;
	}
	public void setNumMagazin(int numMagazin) {
		this.numMagazin = numMagazin;
	}
	public int getNumClock() {
		return numClock;
	}
	public void setNumClock(int numClock) {
		this.numClock = numClock;
	}
	public int getNumWaterBottle() {
		return numWaterBottle;
	}
	public void setNumWaterBottle(int numWaterBottle) {
		this.numWaterBottle = numWaterBottle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

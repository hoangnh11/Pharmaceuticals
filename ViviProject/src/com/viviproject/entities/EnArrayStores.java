package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnArrayStores implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3584795481650777319L;
	private ArrayList<EnStores> stores;
	
	/**
	 * @return the stores
	 */
	public ArrayList<EnStores> getStores() {
		return stores;
	}
	/**
	 * @param stores the stores to set
	 */
	public void setStores(ArrayList<EnStores> stores) {
		this.stores = stores;
	}	
}

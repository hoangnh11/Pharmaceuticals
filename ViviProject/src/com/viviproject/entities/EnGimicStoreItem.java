package com.viviproject.entities;

import java.io.Serializable;

public class EnGimicStoreItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916202674829894836L;
	private String gimic_id;
	private String code;
	private String name;
	private String total;
	
	public String getGimic_id() {
		return gimic_id;
	}
	public void setGimic_id(String gimic_id) {
		this.gimic_id = gimic_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

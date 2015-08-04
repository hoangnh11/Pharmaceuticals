package com.viviproject.entities;

import java.io.Serializable;

public class EnGimicItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1613556846184046063L;
	private String id;
	private String code;
	private String name;
	private String unit;
	private String quantity;
	private String import_quantity;
	private String export_quantity;
	private int left_quantity;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getImport_quantity() {
		return import_quantity;
	}
	public void setImport_quantity(String import_quantity) {
		this.import_quantity = import_quantity;
	}
	public String getExport_quantity() {
		return export_quantity;
	}
	public void setExport_quantity(String export_quantity) {
		this.export_quantity = export_quantity;
	}
	public int getLeft_quantity() {
		return left_quantity;
	}
	public void setLeft_quantity(int left_quantity) {
		this.left_quantity = left_quantity;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnStores implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9008580911734670301L;
	private String store_id;
	private String line;
	private String repeat;
	private String id;
	private String timestamp_x;
	private String uid;
	private String active;
	private String code;
	private String name;
	private String address;
	private String phone;
	private String lng;
	private String lat;
	private String region_id;
	private String district;
	private String vip;
	private int total;
	private String total_revenue;
	private String approve;
	private String latest_order;
	private String last_month_revenue;
	private ArrayList<EnOwner> owners;
	private ArrayList<EnEmployee> employees;
	private ArrayList<String> lines;
	private ArrayList<EnLatestFiveOrder> latest_5_orders;
	private ArrayList<EnLatestThreeOrder> latest_3_months;
	
	/**
	 * @return the latest_3_months
	 */
	public ArrayList<EnLatestThreeOrder> getLatest_3_months() {
		return latest_3_months;
	}
	/**
	 * @param latest_3_months the latest_3_months to set
	 */
	public void setLatest_3_months(ArrayList<EnLatestThreeOrder> latest_3_months) {
		this.latest_3_months = latest_3_months;
	}
	/**
	 * @return the latest_5_orders
	 */
	public ArrayList<EnLatestFiveOrder> getLatest_5_orders() {
		return latest_5_orders;
	}
	/**
	 * @param latest_5_orders the latest_5_orders to set
	 */
	public void setLatest_5_orders(ArrayList<EnLatestFiveOrder> latest_5_orders) {
		this.latest_5_orders = latest_5_orders;
	}
	/**
	 * @return the lines
	 */
	public ArrayList<String> getLines() {
		return lines;
	}
	/**
	 * @param lines the lines to set
	 */
	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	/**
	 * @return the employees
	 */
	public ArrayList<EnEmployee> getEmployees() {
		return employees;
	}
	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(ArrayList<EnEmployee> employees) {
		this.employees = employees;
	}
	/**
	 * @return the owners
	 */
	public ArrayList<EnOwner> getOwners() {
		return owners;
	}
	/**
	 * @param owners the owners to set
	 */
	public void setOwners(ArrayList<EnOwner> owners) {
		this.owners = owners;
	}
	/**
	 * @return the approve
	 */
	public String getApprove() {
		return approve;
	}
	/**
	 * @param approve the approve to set
	 */
	public void setApprove(String approve) {
		this.approve = approve;
	}
	/**
	 * @return the latest_order
	 */
	public String getLatest_order() {
		return latest_order;
	}
	/**
	 * @param latest_order the latest_order to set
	 */
	public void setLatest_order(String latest_order) {
		this.latest_order = latest_order;
	}
	/**
	 * @return the last_month_revenue
	 */
	public String getLast_month_revenue() {
		return last_month_revenue;
	}
	/**
	 * @param last_month_revenue the last_month_revenue to set
	 */
	public void setLast_month_revenue(String last_month_revenue) {
		this.last_month_revenue = last_month_revenue;
	}
	/**
	 * @return the total_revenue
	 */
	public String getTotal_revenue() {
		return total_revenue;
	}
	/**
	 * @param total_revenue the total_revenue to set
	 */
	public void setTotal_revenue(String total_revenue) {
		this.total_revenue = total_revenue;
	}
	/**
	 * @return the store_id
	 */
	public String getStore_id() {
		return store_id;
	}
	/**
	 * @param store_id the store_id to set
	 */
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the timestamp_x
	 */
	public String getTimestamp_x() {
		return timestamp_x;
	}
	/**
	 * @param timestamp_x the timestamp_x to set
	 */
	public void setTimestamp_x(String timestamp_x) {
		this.timestamp_x = timestamp_x;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * @return the region_id
	 */
	public String getRegion_id() {
		return region_id;
	}
	/**
	 * @param region_id the region_id to set
	 */
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * @return the vip
	 */
	public String getVip() {
		return vip;
	}
	/**
	 * @param vip the vip to set
	 */
	public void setVip(String vip) {
		this.vip = vip;
	}
	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}
	/**
	 * @param line the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}
	/**
	 * @return the repeat
	 */
	public String getRepeat() {
		return repeat;
	}
	/**
	 * @param repeat the repeat to set
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}

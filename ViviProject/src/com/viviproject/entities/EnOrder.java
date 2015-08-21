package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8138464037537264540L;
	private String id;
	private String uid;
	private String code;
	private String store_id;
	private String date_book;
	private String delivery;
	private String total;
	private String name;
	private String address;
	private ArrayList<EnProductSales> products;
	private ArrayList<EnGift> gifts;
		
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
	 * @return the products
	 */
	public ArrayList<EnProductSales> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<EnProductSales> products) {
		this.products = products;
	}
	/**
	 * @return the gifts
	 */
	public ArrayList<EnGift> getGifts() {
		return gifts;
	}
	/**
	 * @param gifts the gifts to set
	 */
	public void setGifts(ArrayList<EnGift> gifts) {
		this.gifts = gifts;
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
	 * @return the date_book
	 */
	public String getDate_book() {
		return date_book;
	}
	/**
	 * @param date_book the date_book to set
	 */
	public void setDate_book(String date_book) {
		this.date_book = date_book;
	}
	/**
	 * @return the delivery
	 */
	public String getDelivery() {
		return delivery;
	}
	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
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
	
}

package com.viviproject.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EnBasket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4710719235869967604L;
	private int product_id;
	private int quantity;
	private int point;
	private int sale;
	private int other;
	
	private String name;
	private String price;
	private float discount_point;
	private String note;
	private int discount_sale;	
	private ArrayList<EnDiscountGift> discount_gift;
	private int total;
			
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
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the discount_point
	 */
	public float getDiscount_point() {
		return discount_point;
	}
	/**
	 * @param discount_point the discount_point to set
	 */
	public void setDiscount_point(float discount_point) {
		this.discount_point = discount_point;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the discount_sale
	 */
	public int getDiscount_sale() {
		return discount_sale;
	}
	/**
	 * @param discount_sale the discount_sale to set
	 */
	public void setDiscount_sale(int discount_sale) {
		this.discount_sale = discount_sale;
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
	/**
	 * @return the discountGift
	 */
	public ArrayList<EnDiscountGift> getDiscountGift() {
		return discount_gift;
	}
	/**
	 * @param discountGift the discountGift to set
	 */
	public void setDiscountGift(ArrayList<EnDiscountGift> discountGift) {
		this.discount_gift = discountGift;
	}
	/**
	 * @return the product_id
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}
	/**
	 * @return the sale
	 */
	public int getSale() {
		return sale;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale(int sale) {
		this.sale = sale;
	}
	/**
	 * @return the other
	 */
	public int getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(int other) {
		this.other = other;
	}
	
	
}

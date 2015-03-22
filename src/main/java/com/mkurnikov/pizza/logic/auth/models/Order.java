package com.mkurnikov.pizza.logic.auth.models;

import com.mkurnikov.pizza.logic.paths.models.District;

import java.io.Serializable;

/**
 * Created by mkurnikov on 21.03.15.
 */
public class Order implements Serializable{
	private int order_id;
	private User creator;
	private District destination;
	private String pizza_title;

	public Order(int id, User creator, District destination, String pizza_title) {
		this.order_id = id;
		this.creator = creator;
		this.destination = destination;
		this.pizza_title = pizza_title;
	}

	public int getOrderId() {
		return order_id;
	}

	public User getCreator() {
		return creator;
	}

	public District getDestination() {
		return destination;
	}

	public String getPizza_title() {
		return pizza_title;
	}

	public Order clone() {
		return new Order(this.order_id, this.creator, this.destination, this.pizza_title);
	}

	public int hashCode() {
		return generateUniqueID().hashCode();
	}

	public boolean equals(Object obj) {
		if ( this == obj ) return true;
        if ( !(obj instanceof Order) ) return false;
		return this.generateUniqueID().equals(((Order) obj).generateUniqueID());
	}

	private String generateUniqueID() {
		return Integer.toString(order_id) + "-" + creator.getLogin() + "-" + destination.name;
	}
}

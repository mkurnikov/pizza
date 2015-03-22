package com.mkurnikov.pizza.web.utils;

import com.mkurnikov.pizza.logic.auth.models.Order;
import com.mkurnikov.pizza.logic.paths.models.District;

import java.io.Serializable;

public class OrderSorted implements Serializable {
	private Order order;
	private District source;
	private double overallTime;

	public OrderSorted(Order order, District source, double overallTime) {
		this.order = order;
		this.source = source;
		this.overallTime = overallTime;
	}

	public Order getOrder() {
		return order;
	}

	public District getSource() {
		return source;
	}

	public double getOverallTime() {
		return overallTime;
	}
}

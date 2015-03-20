package com.mkurnikov.pizza.db.gateway;

/**
 * Created by mkurnikov on 15.03.15.
 */
public class OrderTableGateway {

	private static OrderTableGateway instance = null;
	private OrderTableGateway() {}

	public static OrderTableGateway getInstance() {
		if (instance == null) {
			instance = new OrderTableGateway();
		}
		return instance;
	}
}

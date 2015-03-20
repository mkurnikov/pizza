package com.mkurnikov.pizza.logic;

import com.mkurnikov.pizza.logic.auth.models.User;
import com.mkurnikov.pizza.logic.paths.CityMap;
import com.mkurnikov.pizza.logic.paths.models.District;

import java.util.List;

public class PizzaSystem {
	private List<User> users;
	private CityMap cityMap;
	private User currentUser; 
	private District currentLocation;
	
	public User findUser(String login, String password) {
		return null;
	}
	
	public void save() {
	}
	
	public void load() {

	}

	public void placeOrder() {

	}
}

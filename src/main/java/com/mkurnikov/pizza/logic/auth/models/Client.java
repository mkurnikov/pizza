package com.mkurnikov.pizza.logic.auth.models;

/**
 * Created by mkurnikov on 23.02.15.
 */
public class Client extends User {
    private String place;
	
	public Client(String name, String login, String password) {
        super(name, login, password);
    }

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}

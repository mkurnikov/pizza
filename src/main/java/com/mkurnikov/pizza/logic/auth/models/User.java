package com.mkurnikov.pizza.logic.auth.models;

import com.mkurnikov.pizza.db.gateway.UserTableGateway;

import java.io.Serializable;

public class User implements Serializable{
	private String username;
	private String login;
	private String password;

	public User(String username, String login, String password) {
		this.username = username;
		this.login = login;
		this.password = password;
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	public void save() {
		UserTableGateway.getInstance().addClient(username, login, password);
	}

	public String getUsername() {
		return username;
	}

	public String getLogin() {
		return login;
	}
}

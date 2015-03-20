package com.mkurnikov.pizza.logic.auth.models;

public class User {
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
}

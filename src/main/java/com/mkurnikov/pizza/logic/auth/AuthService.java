package com.mkurnikov.pizza.logic.auth;

import com.mkurnikov.pizza.db.gateway.UserTableGateway;
import com.mkurnikov.pizza.logic.auth.models.User;

public class AuthService {
	private static AuthService instance = null;
	private UserTableGateway userTableGateway;
	private AuthService() {
		this.userTableGateway = UserTableGateway.getInstance();
	}

	public static AuthService getInstance() {
		if (instance == null) {
			instance = new AuthService();
		}
		return instance;
	}

	public boolean checkUser(String login, String password) {
//		UserTableGateway userTableGateway = new UserTableGateway();
		User user = this.userTableGateway.findUserByLogin(login);
		System.out.println(user.toString());
		if (user != null) {
			return user.checkPassword(password);
		}
		return false;
	}

	public boolean isLoginExists(String login) {
//		UserTableGateway userTableGateway = new UserTableGateway();
		User user = this.userTableGateway.findUserByLogin(login);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public void registerUser(String username, String login, String password) {
//		UserTableGateway userTableGateway = new UserTableGateway();
		this.userTableGateway.addClient(username, login, password);
	}

}

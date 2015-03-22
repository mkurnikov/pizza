package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.auth.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		if (username == null || username.isEmpty()) {
			req.setAttribute("error_message", "Введите имя пользователя");
			doGet(req, resp);
			return;
		}
		String login = req.getParameter("login");
		if (login == null || login.isEmpty()) {
			req.setAttribute("error_message", "Введите логин пользователя");
			doGet(req, resp);
			return;
		}
		String password = req.getParameter("password");
		if (password == null || password.isEmpty()) {
			req.setAttribute("error_message", "Введите пароль");
			doGet(req, resp);
			return;
		}

//		register user
		AuthService authService = AuthService.getInstance();
		if (!authService.isLoginExists(login)) {
			authService.registerUser(username, login, password);
			req.getSession().setAttribute("login", login);
			req.getSession().setAttribute("username", username);
			resp.sendRedirect("/home");
		} else {
			req.setAttribute("error_message", "Такой логин уже существует");
			doGet(req, resp);
			return;
		}

	}
}

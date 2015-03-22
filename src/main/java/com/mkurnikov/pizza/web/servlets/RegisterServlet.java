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
		String login = req.getParameter("login");
		String password = req.getParameter("password");

//		register user
		AuthService authService = AuthService.getInstance();
		if (!authService.isLoginExists(login)) {
			authService.registerUser(username, login, password);
			req.getSession().setAttribute("login", username);
			resp.sendRedirect("/home");
		} else {
			req.setAttribute("error_message", "This login already exists.");
			doGet(req, resp);
		}

	}
}

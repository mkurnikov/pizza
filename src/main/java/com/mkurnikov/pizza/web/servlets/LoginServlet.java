package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.auth.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher(req.getContextPath() + "/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");

		if (AuthService.getInstance().checkUser(login, password)) {
			req.getSession().setAttribute("login", login);
			resp.sendRedirect("/home");
		} else {
			req.setAttribute("error_message", "invalid credentials");
			doGet(req, resp);
		}
	}
}
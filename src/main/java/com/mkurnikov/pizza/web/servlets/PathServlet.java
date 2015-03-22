package com.mkurnikov.pizza.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PathServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getRequestURL().toString();
		if (url.endsWith("add")) {
			System.out.println("Add path functionality called.");
		}

		if (url.endsWith("delete")) {
			System.out.println("Remove path functionality called.");
		}

		if (url.endsWith("update")) {
			System.out.println("Update path functionality called.");
		}
		resp.sendRedirect("/admin");
	}
}

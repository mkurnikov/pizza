package com.mkurnikov.pizza.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorldServlet extends HttpServlet {
	private String message;

	public void init() {
		this.message = "hello, world";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
		out.println("params: " + request.getParameterMap().toString());
	}

	public void destroy() {
		//do nothing
	}
}

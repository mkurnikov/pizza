package com.mkurnikov.pizza.web;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UnauthorizedUserFilter implements Filter {
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
//		String[] urls = filterConfig.getInitParameter(",").split(" ");
//		System.out.println(this.urls.toString());
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String[] urls = filterConfig.getInitParameter("urlsToPass").split(" ");
//		System.out.println(Arrays.toString(urls));
		System.out.println();

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String url = request.getRequestURL().toString();
		System.out.println("current url: " + url);

		boolean passed = false;
//		if /login or /register, then pass
		for (int i = 0; i < urls.length; i++) {
			if (url.endsWith(urls[i])) {
				passed = true;
//				response.sendRedirect(request.getContextPath() + "/home");
//				return;
//				filterChain.doFilter(servletRequest, servletResponse);
//				System.out.println("lalala");
//				return;
			}
		}
//		System.out.println("program with passed");
		System.out.println("passed: " + passed);
		if (passed) {
			System.out.println("go down through filter chain");
		} else {
			String login = (String) request.getSession().getAttribute("login");
			if (login == null) {
				System.out.println("sending redirect");
				response.sendRedirect(request.getContextPath() + "/login");
			} else {
				System.out.println("authenticated");
				response.sendRedirect(request.getContextPath() + "/home");
			}
		}
	}

	@Override
	public void destroy() {
	}
}

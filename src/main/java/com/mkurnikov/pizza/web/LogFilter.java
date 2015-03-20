package com.mkurnikov.pizza.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LogFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String ipAddress = servletRequest.getRemoteAddr();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println("IP: " + "lalala");

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		//nothing
	}
}

package com.mkurnikov.pizza.web.filters;

import com.mkurnikov.pizza.db.gateway.UserTableGateway;
import com.mkurnikov.pizza.logic.auth.AuthService;
import com.mkurnikov.pizza.logic.auth.models.Client;
import com.mkurnikov.pizza.logic.auth.models.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedUserFilter implements Filter {
//	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String url = request.getRequestURL().toString();
		System.out.println("current url: " + url);

		if (url.contains(".")) {
			//pass
			System.out.println("resource: " + url);
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		String login = (String) request.getSession().getAttribute("login");
		if (login == null) {
			if (url.endsWith("login") || url.endsWith("register")) {
				//pass
				filterChain.doFilter(servletRequest, servletResponse);
				System.out.println("login: " + login + ", executing: filterChain.doFilter(servletRequest, servletResponse);");
			} else {
				//redirect to /login
				response.sendRedirect("/login");
				System.out.println("login: " + login + ", executing: response.sendRedirect(\"/login\");");
			}
		} else {
			//login is not null
			User user = UserTableGateway.getInstance().findUserByLogin(login);
			if (user instanceof Client) {
				if (url.endsWith("admin")) {
					//redirect tp /home
					System.out.println("login: " + login + ", executing: response.sendRedirect(\"/home\");");
					response.sendRedirect("/home");

				} else {
					//pass
					System.out.println("login: " + login + ", executing: filterChain.doFilter(servletRequest, servletResponse);");
					filterChain.doFilter(servletRequest, servletResponse);
				}
			}
		}
	}

	@Override
	public void destroy() {
	}
}

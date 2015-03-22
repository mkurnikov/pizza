package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.PizzaSystem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrdersServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/orders.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("login") != null) {
			String url = req.getRequestURL().toString();
			String login = (String) req.getSession().getAttribute("login");
			String districtName = req.getParameter("district");
			String pizza_title = req.getParameter("pizza");
			int id = 0;
			if (req.getParameter("id") != null) {
				id = Integer.parseInt(req.getParameter("id"));
			}
			if (url.endsWith("add")) {
				PizzaSystem.getInstance().createOrder(login, districtName, pizza_title);
			}
			if (url.endsWith("update")) {
				PizzaSystem.getInstance().updateOrder(id, districtName, pizza_title);
			}
			if (url.endsWith("delete")) {
				PizzaSystem.getInstance().deleteOrder(id);
			}
			req.getSession().removeAttribute("orders");
			req.getSession().setAttribute("orders", PizzaSystem.getInstance().getOrders());
			resp.sendRedirect("/orders");
		} else {
			resp.sendRedirect("/login");
		}

	}
}

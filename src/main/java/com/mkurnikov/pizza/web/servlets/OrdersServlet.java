package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;
import com.mkurnikov.pizza.db.gateway.OrderTableGateway;
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
			double id = 0;
			if (req.getParameter("id") != null) {
				id = Double.parseDouble(req.getParameter("id"));
			}
			if (url.endsWith("add")) {
				if (districtName == null || districtName.isEmpty()) {
					sendRedirectWithMessage(req, resp, "create", "Введите район проживания");
					return;
				}
				if (!DistrictTableGateway.getInstance().isDistrictExists(districtName)) {
					sendRedirectWithMessage(req, resp, "create", "Такого района проживания нет в нашей базе");
					return;
				}
				if (pizza_title == null || pizza_title.isEmpty()) {
					sendRedirectWithMessage(req, resp, "create", "Введите название пиццы");
					return;
				}
				PizzaSystem.getInstance().createOrder(login, districtName, pizza_title);
			}
			if (url.endsWith("update")) {
				if (id != (int)id) {
					sendRedirectWithMessage(req, resp, "update", "Неправильный формат для номера заказа");
					return;
				}
				if (id == 0) {
					sendRedirectWithMessage(req, resp, "update", "Введите номер заказа");
					return;
				}
				if (OrderTableGateway.getInstance().getOrderByID((int) id) == null) {
					sendRedirectWithMessage(req, resp, "update", "Заказа с таким номером не существует");
					return;
				}
				if ((districtName == null || districtName.isEmpty()) &&
						(pizza_title == null || pizza_title.isEmpty())) {
					sendRedirectWithMessage(req, resp, "update", "Введите новый район проживания, либо название пиццы");
					return;
				}
				if (districtName != null && !DistrictTableGateway.getInstance().isDistrictExists(districtName)) {
					sendRedirectWithMessage(req, resp, "update", "Такого района проживания нет в нашей базе");
					return;
				}
				PizzaSystem.getInstance().updateOrder((int)id, districtName, pizza_title);
			}
			if (url.endsWith("delete")) {
				if (id != (int)id) {
					sendRedirectWithMessage(req, resp, "delete", "Неправильный формат для номера заказа");
					return;
				}
				if (id == 0) {
					sendRedirectWithMessage(req, resp, "delete", "Введите номер заказа");
					return;
				}
				if (OrderTableGateway.getInstance().getOrderByID((int) id) == null) {
					sendRedirectWithMessage(req, resp, "delete", "Заказа с таким номером не существует");
					return;
				}
				PizzaSystem.getInstance().deleteOrder((int)id);
			}
			req.getSession().removeAttribute("orders");
			req.getSession().removeAttribute("orders_sorted");
			req.getSession().setAttribute("orders", PizzaSystem.getInstance().getOrders());
			resp.sendRedirect("/orders");
		} else {
			resp.sendRedirect("/login");
		}
	}

	private void sendRedirectWithMessage(HttpServletRequest req, HttpServletResponse resp, String module, String message) throws ServletException, IOException {
		req.setAttribute(module + "_error_message", message);
		doGet(req, resp);
	}
}

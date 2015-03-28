package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;
import com.mkurnikov.pizza.db.gateway.PathTableGateway;
import com.mkurnikov.pizza.logic.PizzaSystem;
import com.mkurnikov.pizza.logic.paths.CityMap;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PathServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CityMap.getInstance().renewVisualizer();
		req.getRequestDispatcher("/jsp/admin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String source = req.getParameter("source");
		String destination = req.getParameter("destination");
		double time = -1.0;
		if (req.getParameter("time") != null) {
			time = Double.parseDouble(req.getParameter("time"));
		}

		String url = req.getRequestURL().toString();
		if (url.endsWith("add")) {
			if (source == null || source.isEmpty()) {
				sendRedirectWithMessage(req, resp, "create", "Введите начало пути");
				return;
			}
			if (!DistrictTableGateway.getInstance().isDistrictExists(source)) {
				sendRedirectWithMessage(req, resp, "create", "Такого начала пути не существует");
				return;
			}
			if (destination == null || destination.isEmpty()) {
				sendRedirectWithMessage(req, resp, "create", "Введите окончание пути");
				return;
			}
			if (!DistrictTableGateway.getInstance().isDistrictExists(destination)) {
				sendRedirectWithMessage(req, resp, "create", "Такого окончания пути не существует");
				return;
			}
			if (time <= 0) {
				sendRedirectWithMessage(req, resp, "create", "Время введено неправильно");
				return;
			}
			CityMap.getInstance().addPath(source, destination, time);
		}
		if (url.endsWith("update")) {
			if (source == null || source.isEmpty()) {
				sendRedirectWithMessage(req, resp, "update", "Введите начало пути");
				return;
			}
			if (!DistrictTableGateway.getInstance().isDistrictExists(source)) {
				sendRedirectWithMessage(req, resp, "update", "Такого начала пути не существует");
				return;
			}
			if (destination == null || destination.isEmpty()) {
				sendRedirectWithMessage(req, resp, "update", "Введите окончание пути");
				return;
			}
			if (!DistrictTableGateway.getInstance().isDistrictExists(destination)) {
				sendRedirectWithMessage(req, resp, "update", "Такого окончания пути не существует");
				return;
			}
			if (time <= 0) {
				sendRedirectWithMessage(req, resp, "update", "Время введено неправильно");
				return;
			}
			if (!PathTableGateway.getInstance().isPathExistsByDistricts(source, destination)) {
				sendRedirectWithMessage(req, resp, "update", "Такого пути не существует");
			}
			CityMap.getInstance().updatePath(source, destination, time);
		}

		if (url.endsWith("delete")) {
			if (source == null || source.isEmpty()) {
				sendRedirectWithMessage(req, resp, "delete", "Введите начало пути");
				return;
			}
			if (!DistrictTableGateway.getInstance().isDistrictExists(source)) {
				sendRedirectWithMessage(req, resp, "delete", "Такого начала пути не существует");
				return;
			}
			if (destination == null || destination.isEmpty()) {
				sendRedirectWithMessage(req, resp, "delete", "Введите окончание пути");
				return;
			}
			if (!DistrictTableGateway.getInstance().isDistrictExists(destination)) {
				sendRedirectWithMessage(req, resp, "delete", "Такого окончания пути не существует");
				return;
			}
			if (!PathTableGateway.getInstance().isPathExistsByDistricts(source, destination)) {
				sendRedirectWithMessage(req, resp, "delete", "Такого пути не существует");
			}
			CityMap.getInstance().deletePath(source, destination);
		}
		//renew
		PizzaSystem.getInstance().renew();
		req.getSession().removeAttribute("currentPath");
		req.getSession().removeAttribute("fullPath");

		req.getSession().removeAttribute("orders");
		req.getSession().removeAttribute("orders_sorted");
		req.getSession().setAttribute("orders", PizzaSystem.getInstance().getOrders());
		resp.sendRedirect("/admin");
	}

	private void sendRedirectWithMessage(HttpServletRequest req, HttpServletResponse resp, String module, String message) throws ServletException, IOException {
		req.setAttribute(module + "_error_message", message);
		doGet(req, resp);
	}
}

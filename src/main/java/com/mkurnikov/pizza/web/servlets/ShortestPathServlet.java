package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;
import com.mkurnikov.pizza.logic.PizzaSystem;
import com.mkurnikov.pizza.logic.auth.models.Order;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import com.mkurnikov.pizza.web.utils.PathToStringConverter;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShortestPathServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String source = req.getParameter("source");
//		System.out.println("ID : " + id);
		PizzaSystem.getInstance().showOrderCompletionPath(PizzaSystem.getInstance().getOrderByID(id));
		req.getSession().setAttribute("currentPath",
				PathToStringConverter.convertShortestPath(
						PizzaSystem.getInstance().getCurrentShortestPath(), new District(source)));
		resp.sendRedirect("/home");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		District initialLocation = new District(req.getParameter("startPosition"));
		if (!DistrictTableGateway.getInstance().isDistrictExists(initialLocation.name)) {
			req.getSession().setAttribute("find_path_error_message", "Такого района не существует.");
			resp.sendRedirect("/home");
			return;
		} else {
			req.getSession().removeAttribute("find_path_error_message");
		}

		//renew graph
		PizzaSystem.getInstance().showOrderCompletionPath(null);

		List<Pair<Order, List<Path>>> pathThroughOrders = PizzaSystem.getInstance().buildPathThroughOrders(initialLocation);
		if (pathThroughOrders == null || pathThroughOrders.isEmpty()) {
			resp.sendRedirect("/home");
			return;
		}

		System.out.println("building full path");
		String fullPath = PathToStringConverter.convertPathThroughOrders(
				pathThroughOrders, initialLocation);

		req.getSession().setAttribute("fullPath", fullPath);
		req.getSession().removeAttribute("orders");
		req.getSession().setAttribute("orders_sorted", PizzaSystem.getInstance().getOrdersSorted());
		resp.sendRedirect("/home");
	}


}

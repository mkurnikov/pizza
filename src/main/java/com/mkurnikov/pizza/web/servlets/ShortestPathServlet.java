package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.PizzaSystem;
import com.mkurnikov.pizza.logic.auth.models.Order;
import com.mkurnikov.pizza.logic.paths.CityMap;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import com.mkurnikov.pizza.web.utils.OrderSorted;
import com.mkurnikov.pizza.web.utils.PathToStringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestPathServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String source = req.getParameter("source");
//		System.out.println("ID : " + id);
		PizzaSystem.getInstance().showOrderCompletionPath(PizzaSystem.getInstance().getOrderByID(id));
		req.getSession().setAttribute("currentPath",
				PathToStringConverter.convertToStringShortestPath(
						PizzaSystem.getInstance().getCurrentShortestPath(), new District(source)));
		resp.sendRedirect("/home");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		District initialLocation = new District(req.getParameter("startPosition"));
		//renew graph
		PizzaSystem.getInstance().showOrderCompletionPath(null);

		List<Pair<Order, List<Path>>> pathThroughOrders = PizzaSystem.getInstance().buildPathThroughOrders(initialLocation);
		if (pathThroughOrders == null || pathThroughOrders.isEmpty()) {
			resp.sendRedirect("/home");
			return;
		}
//		System.out.println("current location after: " + PizzaSystem.getInstance().getCurrentLocation());
		for (Pair<Order, List<Path>> pair: pathThroughOrders) {
			List<Path> path = pair.getRight();
//			System.out.println("list:" + path.toString());
//			System.out.println("path:" + PathToStringConverter.convertToStringShortestPath(path, new District("Красносельский")));
		}
		String fullPath = PathToStringConverter.convertToStringPathThroughOrders(
				pathThroughOrders, initialLocation);
//		System.out.println(fullPath);
		req.getSession().setAttribute("fullPath", fullPath);
		req.getSession().removeAttribute("orders");
//		List<OrderSorted> ordersSorted = new ArrayList<>();
		req.getSession().setAttribute("orders_sorted", PizzaSystem.getInstance().getOrdersSorted());
		resp.sendRedirect("/home");
//		String source = req.getParameter("source");
//		if (!CityMap.getInstance().isDistrictExists(source)) {
//			req.setAttribute("error_message", "source doesnt exist");
//			req.getRequestDispatcher("/home").forward(req, resp);
//			return;
//		}
//
//		String destination = req.getParameter("destination");
//		if (!CityMap.getInstance().isDistrictExists(destination)) {
//			req.setAttribute("error_message", "destination doesnt exist");
//			req.getRequestDispatcher("/home").forward(req, resp);
//			return;
//		}
//		if (source.equals(destination)) {
//			req.setAttribute("error_message", "source equals destination");
//			req.getRequestDispatcher("/home").forward(req, resp);
//			return;
//		}
//
//		List<Path> shortest = CityMap.getInstance().findShortestWay(new District(source),
//				new District(destination));
//		req.setAttribute("shortest", shortest.toString());
//		req.getRequestDispatcher("/home").forward(req, resp);
//		create visualization here
	}


}

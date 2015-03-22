package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.PizzaSystem;
import com.mkurnikov.pizza.logic.paths.CityMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setAttribute("login", req.getSession().getAttribute("login"));
//		req.setAttribute("paths", CityMap.getInstance().getAllPaths().toString());
//		req.setAttribute("districts", CityMap.getInstance().getAllDistricts());
//		CityMap.getInstance().createImageWithCityMap();
		req.getSession().setAttribute("orders", PizzaSystem.getInstance().getOrders());
		req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		boolean invalid = false;
//		String district1 = req.getParameter("district1");
//		if (!CityMap.getInstance().isDistrictExists(district1)) {
//			req.setAttribute("error_message", "district1 doesnt exist");
////			invalid = true;
//			doGet(req, resp);
//			return;
//		}
//		String district2 = req.getParameter("district2");
//		if (!CityMap.getInstance().isDistrictExists(district2)) {
//			req.setAttribute("error_message", "district2 doesnt exist");
////			invalid = true;
//			doGet(req, resp);
//			return;
//		}
//
////		if (!invalid) {
//		System.out.println(district1 + ", " + district2);
//		CityMap.getInstance().addPath(district1, district2,
//				Float.parseFloat(req.getParameter("travellingTime")));
//		resp.sendRedirect("/home");
////		}

	}
}

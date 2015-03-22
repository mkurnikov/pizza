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
		req.getSession().setAttribute("orders", PizzaSystem.getInstance().getOrders());
		req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}

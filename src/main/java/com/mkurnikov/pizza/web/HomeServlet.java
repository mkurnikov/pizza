package com.mkurnikov.pizza.web;

import com.mkurnikov.pizza.logic.paths.CityMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("login", req.getSession().getAttribute("login"));
		req.setAttribute("paths", CityMap.getInstance().getAllPaths().toString());
		req.setAttribute("districts", CityMap.getInstance().getAllDistricts());
		CityMap.getInstance().createImageWithCityMap();
		req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CityMap.getInstance().addPath(req.getParameter("district1"), req.getParameter("district2"),
				Float.parseFloat(req.getParameter("travellingTime")));
		resp.sendRedirect("/home");
	}
}

package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.PizzaSystem;
import com.mkurnikov.pizza.logic.paths.CityMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PathServlet extends HttpServlet {
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
			CityMap.getInstance().addPath(source, destination, time);
		}

		if (url.endsWith("update")) {
			System.out.println("Update path functionality called.");
		}

		if (url.endsWith("delete")) {
			CityMap.getInstance().deletePath(source, destination);
		}
		resp.sendRedirect("/admin");
	}
}

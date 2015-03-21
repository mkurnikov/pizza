package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.paths.CityMap;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShortestPathServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String source = req.getParameter("source");
		if (!CityMap.getInstance().isDistrictExists(source)) {
			req.setAttribute("error_message", "source doesnt exist");
			req.getRequestDispatcher("/home").forward(req, resp);
			return;
		}

		String destination = req.getParameter("destination");
		if (!CityMap.getInstance().isDistrictExists(destination)) {
			req.setAttribute("error_message", "destination doesnt exist");
			req.getRequestDispatcher("/home").forward(req, resp);
			return;
		}
		if (source.equals(destination)) {
			req.setAttribute("error_message", "source equals destination");
			req.getRequestDispatcher("/home").forward(req, resp);
			return;
		}

		List<Path> shortest = CityMap.getInstance().findShortestWay(new District(source),
				new District(destination));
		req.setAttribute("shortest", shortest.toString());
		req.getRequestDispatcher("/home").forward(req, resp);
//		create visualization here
	}
}

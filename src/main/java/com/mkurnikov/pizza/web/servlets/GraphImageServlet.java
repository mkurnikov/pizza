package com.mkurnikov.pizza.web.servlets;

import com.mkurnikov.pizza.logic.paths.CityMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mkurnikov on 21.03.15.
 */
public class GraphImageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = resp.getOutputStream();
		ImageIO.write(CityMap.getInstance().getCityMapGraphAsImage(), "png", out);
		out.close();
	}
}

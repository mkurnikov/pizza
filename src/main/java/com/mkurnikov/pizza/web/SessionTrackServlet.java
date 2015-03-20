package com.mkurnikov.pizza.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class SessionTrackServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		Date createTime = new Date(session.getCreationTime());
		Date lastAccessTime = new Date(session.getLastAccessedTime());

		String title = "welcome back";
		Integer visitCount = 0;
		String visitCountKey = "visit count";
		String userIDKey = "userID";
		String userID = "ABCD";

		if (session.isNew()) {
			title = "welcome for the first time";
			session.setAttribute(userIDKey, userID);
		} else {
			visitCount = (Integer) session.getAttribute(visitCountKey);
			visitCount = visitCount + 1;
			userID = (String) session.getAttribute(userIDKey);
		}
		session.setAttribute(visitCountKey, visitCount);

		PrintWriter out = resp.getWriter();
		out.println("title: " + title);
		out.println("userID: " + userID);
		out.println("visit count: " + visitCount);
		out.println("createTime: " + createTime);
		out.println("lastAccessTime: " + lastAccessTime);
	}
}



















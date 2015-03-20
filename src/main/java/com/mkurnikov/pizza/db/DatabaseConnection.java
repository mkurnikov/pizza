package com.mkurnikov.pizza.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
	public static Connection getConnection() throws SQLException {
		try {

			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/pizza");
			return ds.getConnection();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
}

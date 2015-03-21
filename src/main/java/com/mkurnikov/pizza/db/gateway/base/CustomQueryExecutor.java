package com.mkurnikov.pizza.db.gateway.base;

import com.mkurnikov.pizza.db.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomQueryExecutor {
	private Connection connection;
	private PreparedStatement preparedStatement = null;
	private String sql = null;

	final public CustomQueryExecutor init(String sql) {
		try {
			this.connection = DatabaseConnection.getConnection();
			this.sql = sql;
			this.preparedStatement = this.connection.prepareStatement(sql);
			prepareStatement(this.preparedStatement);
		} catch (SQLException e) {
			closeConnection();
			throw new RuntimeException(e);
		}
		return this;
	}
//	public CustomQueryExecutor() {
//		try {
//			this.connection = DatabaseConnection.getConnection();
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	public Connection getConnection() {
//		return this.connection;
//	}
//
//	private PreparedStatement prepareStatement(String sql) throws SQLException{
//		this.preparedStatement = connection.prepareStatement(sql);
//		return this.preparedStatement;
//	}

//	public void setPreparedStatement(PreparedStatement preparedStatement) {
//		this.preparedStatement = preparedStatement;
//	}

	public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
		//default: do nothing, just execute as-is
	}

	final public Object execute() {
		try {
			ResultSet resultSet = null;
//			System.out.println(this.sql);
//			System.out.println(this.sql.startsWith("SELECT"));
			if (this.sql.startsWith("SELECT")) {
				resultSet = this.preparedStatement.executeQuery();
			} else {
				this.preparedStatement.executeUpdate();
			}
			return processResultSet(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
	}
	public Object processResultSet(ResultSet resultSet) throws SQLException {
		//default: do nothing, return null as resultSet doesn't need to be processed
		return null;
	}

	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

package com.mkurnikov.pizza.db.gateway;

import com.mkurnikov.pizza.db.DatabaseConnection;
import com.mkurnikov.pizza.db.gateway.base.CustomQueryExecutor;
import com.mkurnikov.pizza.logic.auth.models.Admin;
import com.mkurnikov.pizza.logic.auth.models.Client;
import com.mkurnikov.pizza.logic.auth.models.User;
import com.mkurnikov.pizza.logic.auth.models.UserRole;
import org.postgresql.core.QueryExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTableGateway {
	private final String ADD_USER_QUERY =
			"INSERT INTO pizza_user(login, name, password, role) " +
					"VALUES (?, ?, ?, ?);";
	private final String FIND_USER_BY_LOGIN_QUERY =
			"SELECT * FROM pizza_user " +
					"WHERE pizza_user.login = ?;";

	private static UserTableGateway instance = null;

	private UserTableGateway() {
	}

	public static UserTableGateway getInstance() {
		if (instance == null) {
			instance = new UserTableGateway();
		}
		return instance;
	}

	public void addClient(final String username, final String login, final String password) {
		new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, login);
				preparedStatement.setString(2, username);
				preparedStatement.setString(3, password);
				preparedStatement.setInt(4, UserRole.CLIENT.ordinal());
			}
		}.init(this.ADD_USER_QUERY).execute();
	}


	public User findUserByLogin(final String login) {
		return (User) new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, login);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
//				System.out.println(resultSet.get);
//				System.out.println("I'm processing resultSet");
//				System.out.println(resultSet.next());
//				System.out.println(resultSet.getString("login"));
				if (!resultSet.next()) {
//					System.out.println();
					return null;
				} else {
					if (resultSet.getInt("role") == UserRole.ADMIN.ordinal()) {
						return new Admin(resultSet.getString("name"), resultSet.getString("login"),
								resultSet.getString("password"));
					} else {
						return new Client(resultSet.getString("name"), resultSet.getString("login"),
								resultSet.getString("password"));
					}
				}
			}
		}.init(this.FIND_USER_BY_LOGIN_QUERY).execute();
//		return null;
	}
}
//		Connection conn = null;
//		try {
//			conn = DatabaseConnection.getConnection();
//			PreparedStatement preparedStatement = conn.prepareStatement(this.FIND_USER_BY_LOGIN_QUERY);
//			preparedStatement.setString(1, login);
//			ResultSet resultSet = preparedStatement.executeQuery();
//
//			//fall if result set is empty or len > 1
//			//parse result set
//			if (!resultSet.next()) {
//				return null;
//			} else {
//				if (resultSet.getInt("role") == UserRole.ADMIN.ordinal()) {
//					return new Admin(resultSet.getString("username"), resultSet.getString("login"),
//							resultSet.getString("password"));
//				} else {
//					return new Client(resultSet.getString("username"), resultSet.getString("login"),
//							resultSet.getString("password"));
//				}
//			}
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}

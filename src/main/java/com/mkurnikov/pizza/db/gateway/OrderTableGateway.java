package com.mkurnikov.pizza.db.gateway;

import com.mkurnikov.pizza.db.gateway.base.CustomQueryExecutor;
import com.mkurnikov.pizza.logic.auth.models.Order;
import com.mkurnikov.pizza.logic.auth.models.User;
import com.mkurnikov.pizza.logic.paths.models.District;
import org.postgresql.core.QueryExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderTableGateway {
	private String ADD_ORDER_QUERY = "INSERT INTO pizza_order(client, district, pizza_title) " +
			"VALUES (?, ?, ?)";
	private String UPDATE_ORDER_PIZZA_TITLE_QUERY = "UPDATE pizza_order SET pizza_title=? WHERE order_id=?";
	private String UPDATE_ORDER_DISTRICT_QUERY = "UPDATE pizza_order SET district=? WHERE order_id=?";
	private String DELETE_ORDER_QUERY = "DELETE FROM pizza_order WHERE order_id=?";
	private String SELECT_ORDERS_QUERY = "SELECT * FROM pizza_order;";
	private String SELECT_ORDERS_BY_CLIENT_QUERY = "SELECT * FROM pizza_order WHERE client=?;";
	private String SELECT_ORDER_BY_ID_QUERY = "SELECT * FROM pizza_order WHERE order_id=?";

	private static OrderTableGateway instance = null;
	private OrderTableGateway() {}

	public static OrderTableGateway getInstance() {
		if (instance == null) {
			instance = new OrderTableGateway();
		}
		return instance;
	}

	public void addOrder(final User client, final District district, final String pizza_title) {
		new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, client.getLogin());
				preparedStatement.setString(2, district.name);
				preparedStatement.setString(3, pizza_title);
			}
		}.init(ADD_ORDER_QUERY).execute();
	}

	public void updateOrder(final int id, final District district, final String pizza_title) {
		if (district != null && !district.name.isEmpty()) {
			new CustomQueryExecutor() {
				@Override
				public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
					preparedStatement.setString(1, district.name);
					preparedStatement.setInt(2, id);
				}
			}.init(UPDATE_ORDER_DISTRICT_QUERY).execute();
		}
		if (pizza_title != null && !pizza_title.isEmpty()) {
			new CustomQueryExecutor() {
				@Override
				public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
					preparedStatement.setString(1, pizza_title);
					preparedStatement.setInt(2, id);
				}
			}.init(UPDATE_ORDER_PIZZA_TITLE_QUERY).execute();
		}
	}

	public void deleteOrder(final int id) {
		new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1, id);
			}
		}.init(DELETE_ORDER_QUERY).execute();
	}

	public List<Order> getOrderList() {
		return (List<Order>) new CustomQueryExecutor() {
			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				List<Order> orders = new ArrayList<>();
				while (resultSet.next()) {
					User user = UserTableGateway.getInstance().
							findUserByLogin(resultSet.getString("client"));
					District district = new District(resultSet.getString("district"));
					Order order = new Order(resultSet.getInt("order_id"), user, district,
							resultSet.getString("pizza_title"));
					orders.add(order);
				}
				return orders;
			}
		}.init(SELECT_ORDERS_QUERY).execute();

	}

	public List<Order> getOrderListByClient(final String login) {
		return (List<Order>) new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, login);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				List<Order> orders = new ArrayList<>();
				User user = UserTableGateway.getInstance().findUserByLogin(login);
				while (resultSet.next()) {
					District district = new District(resultSet.getString("district"));
					Order order = new Order(resultSet.getInt("order_id"), user, district,
							resultSet.getString("pizza_title"));
					orders.add(order);
				}
				return orders;
			}
		}.init(SELECT_ORDERS_BY_CLIENT_QUERY).execute();
	}

	public Order getOrderByID(final int id) {
		return (Order) new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1, id);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				resultSet.next();
				User user = UserTableGateway.getInstance().
						findUserByLogin(resultSet.getString("client"));
				return new Order(id, user, new District(resultSet.getString("district")), resultSet.getString("pizza_title"));
			}
		}.init(SELECT_ORDER_BY_ID_QUERY).execute();
	}
}

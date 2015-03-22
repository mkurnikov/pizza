package com.mkurnikov.pizza.logic;

import com.mkurnikov.pizza.db.gateway.OrderTableGateway;
import com.mkurnikov.pizza.db.gateway.UserTableGateway;
import com.mkurnikov.pizza.logic.auth.models.Order;
import com.mkurnikov.pizza.logic.auth.models.User;
import com.mkurnikov.pizza.logic.paths.CityMap;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import com.mkurnikov.pizza.web.utils.OrderSorted;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PizzaSystem {
	private static PizzaSystem instance = null;
	public static PizzaSystem getInstance() {
		if (instance == null) {
			instance = new PizzaSystem();
		}
		return instance;
	}
	private PizzaSystem() {
		cityMap = CityMap.getInstance();
		orders = OrderTableGateway.getInstance().getOrderList();
		sources = new ArrayList<>();
		currentLocation = new District("Красносельский");
	}
	public void renew() {
		cityMap.renew();
		instance = new PizzaSystem();
	}

	private CityMap cityMap;
	private District currentLocation;
	private List<Order> orders;
	private List<District> sources;
	//	private List<Order> ordersSorted;
//	private List<List<Path>> pathThroughOrders;
	private List<Pair<Order, List<Path>>> pathThroughOrders;

	public District getCurrentLocation() {
		return currentLocation;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public List<OrderSorted> getOrdersSorted() {
		List<OrderSorted> ordersSorted = new ArrayList<>();
		for (int i = 0; i < pathThroughOrders.size(); i++) {
			Order order = pathThroughOrders.get(i).getLeft();
			double overall = getPathLength(pathThroughOrders.get(i).getRight());
			District source = sources.get(i);
			ordersSorted.add(new OrderSorted(order, source, overall));
		}
		return ordersSorted;
	}

	public Order getOrderByID(int id) {
		return OrderTableGateway.getInstance().getOrderByID(id);
	}

	public double getPathLength(List<Path> path) {
		double result = 0.0;
		for (Path _path: path) {
			result += _path.travellingTime;
		}
		return result;
	}

	public void createOrder(String userLogin, String districtName, String pizza_title) {
		User user = UserTableGateway.getInstance().findUserByLogin(userLogin);
		District destination = new District(districtName);
		OrderTableGateway.getInstance().addOrder(user, destination, pizza_title);
		orders = OrderTableGateway.getInstance().getOrderList();
//		buildPathThroughOrders();
	}

	public void updateOrder(int id, String districtName, String pizza_title) {
		OrderTableGateway.getInstance().updateOrder(id, new District(districtName), pizza_title);
		orders = OrderTableGateway.getInstance().getOrderList();
	}

	public void deleteOrder(int id) {
		OrderTableGateway.getInstance().deleteOrder(id);
		orders = OrderTableGateway.getInstance().getOrderList();
	}

	public List<Pair<Order, List<Path>>> buildPathThroughOrders(District initialLocation) {
		orders = OrderTableGateway.getInstance().getOrderList();
		if (orders == null || orders.isEmpty()) {
			return null;
		}
		currentLocation = initialLocation;
		sources = new ArrayList<>();
		pathThroughOrders = new ArrayList<>();
		List<Order> orders_to_complete = getCopyOfOrders();
		while (orders_to_complete.size() != 0) {
			sources.add(currentLocation);
			District nearest = cityMap.getNearestNeighbour(currentLocation,
					getDistrictsToVisit(orders_to_complete));
			Order order = getOrderWithDistrictFromArray(nearest, orders_to_complete);
			List<Path> shortestPath = cityMap.findShortestWay(currentLocation, nearest);
			pathThroughOrders.add(new ImmutablePair<>(order, shortestPath));
			currentLocation = nearest;
			orders_to_complete.remove(order);
		}
		return pathThroughOrders;
	}

	public void showOrderCompletionPath(Order order) {
		if (order == null) {
			cityMap.setCurrentShortestPath(null);
			return;
		}
		cityMap.setCurrentShortestPath(getShortestPathByOrderInList(order));
	}

	public List<Path> getCurrentShortestPath() {
		return cityMap.getCurrentShortestPath();
	}

	public List<Path> getShortestPathByOrderInList(Order order) {
		for (Pair<Order, List<Path>> pair: pathThroughOrders) {
			if (pair.getLeft().equals(order)) {
				return pair.getRight();
			}
		}
		throw new RuntimeException("hopefully wont happen");
	}

	private List<District> getDistrictsToVisit(List<Order> orders) {
		List<District> districts = new ArrayList<>();
		for (Order order: orders) {
			districts.add(order.getDestination());
		}
		return districts;
	}

	private List<Order> getCopyOfOrders() {
		List<Order> copy = new ArrayList<>();
		for (Order order: this.orders) {
			copy.add(order);
		}
		return copy;
	}

	private Order getOrderWithDistrictFromArray(District district, List<Order> orders) {
		for (Order order: orders) {
			if (order.getDestination().equals(district)) {
				return order;
			}
		}
		throw new RuntimeException("hopefully wont happen");
	}
//
//	private List<Order> getOrdersInShortestPathChain() {
//		List<Map.Entry<Order, Pair<Integer, List<Path>>>> ordersWithPath = new ArrayList<>(pathThroughOrders.entrySet());
//		for (Map.Entry<Order, Pair<Integer, List<Path>>> orderWithPath: ordersWithPath) {
//
//		}
//	}
}

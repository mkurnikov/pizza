package com.mkurnikov.pizza.web.utils;

import com.mkurnikov.pizza.logic.auth.models.Order;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathToStringConverter {
	public static String separator = " -> ";

	public static String convertToStringShortestPath(List<Path> shortestPath, District source) {
//		String result = "";
		List<String> parts = new ArrayList<>();
		District first = source;
		parts.add(first.name);
		for (int i = 0; i < shortestPath.size(); i++) {
			Path path = shortestPath.get(i);
			if (path.firstDistrict.equals(first)) {
				first = path.secondDistrict;
			} else if (path.secondDistrict.equals(first)) {
				first = path.firstDistrict;
			} else {
				throw new RuntimeException("something wrong here, " + "\npath: " + path + ", \nfirst: " + first
						+ ", \nshortest: " + shortestPath + ", \nsource: " + source);
			}
			parts.add(Double.toString(path.travellingTime));
			parts.add(first.name);
		}

		parts.set(0, surroundWithBold(parts.get(0)));
		parts.set(parts.size() - 1, surroundWithBold(parts.get(parts.size() - 1)));
		return StringUtils.join(parts.toArray(), separator);
	}

	public static String convertToStringPathThroughOrders(List<Pair<Order, List<Path>>> pathThroughOrders, District source) {
		String result = "";
		District first = source;
		for (int i = 0; i < pathThroughOrders.size(); i++) {
			List<Path> shortestPath = pathThroughOrders.get(i).getRight();
			result = joinPathStrings(result, convertToStringShortestPath(shortestPath, first));
//			result += convertToStringShortestPath(shortestPath, first);
			first = new District(unsurroundWithBold(
					result.split(separator)[result.split(separator).length - 1]));
//			System.out.println("first:" + first.name);
//			if (i != pathThroughOrders.size() - 1) {
//				List<String> parts = new ArrayList<>(Arrays.asList(result.split(" ")));
//				parts.remove(parts.size() - 1);
//				result = StringUtils.join(parts.toArray(), " ");
//				result += " ";
//			}
		}
		return result;
	}

//	private static District getDestination(Path path, District source) {
//		if ()
//	}

	private static String joinPathStrings(String left, String right) {
		if (left == null || left.isEmpty()) {
			return right;
		}
		if (right == null || right.isEmpty()) {
			return left;
		}
		List<String> leftStrings = new ArrayList<>(Arrays.asList(left.split(" ")));
		List<String> rightStrings = new ArrayList<>(Arrays.asList(right.split(" ")));
//		System.out.println(leftStrings);
//		System.out.println(rightStrings);
		if (leftStrings.get(leftStrings.size() - 1).equals(rightStrings.get(0))) {
			leftStrings.remove(leftStrings.size() - 1);
			leftStrings.addAll(rightStrings);
			return StringUtils.join(leftStrings.toArray(), " ");
		} else {
			throw new RuntimeException("wont happen");
		}
	}

	private static String surroundWithBold(String s) {
		if (!s.startsWith("<b>") && !s.endsWith("</b>")) {
			return "<b>" + s + "</b>";
		}
		return s;
	}

	private static String unsurroundWithBold(String s) {
		return s.replace("<b>", "").replace("</b>", "");
	}
}

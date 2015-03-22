package com.mkurnikov.pizza.logic.paths.shortest;

import com.mkurnikov.pizza.logic.paths.CityMap;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;

import java.util.*;

public class DijkstraAlgorithm {
	private final List<District> nodes;
	private final List<Path> edges;

	private Set<District> settledNodes;
	private Set<District> unsettledNodes;
	private Map<District, District> predecessors;
	private Map<District, Double> distance;

	public DijkstraAlgorithm(List<District> districts, List<Path> paths) {
		this.nodes = districts;
		this.edges = paths;
	}

	public void execute(District source) {
		settledNodes = new HashSet<>();
		unsettledNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, 0.0);
		unsettledNodes.add(source);
		while (unsettledNodes.size() > 0) {
			District node = getMinimum(unsettledNodes); //get node from unsettledNodes
			// with minimal distance from source
			settledNodes.add(node);
			unsettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	public void addPath(Path path) {
		if (!edges.contains(path)) {
			edges.add(path);
		}
	}

	public void updatePath(Path path) {
		int ind = CityMap.findPathIndexInList(edges, path);
		if (ind == -1) {
			throw new RuntimeException("something wrong with deletePath()");
		}
		edges.set(ind, path);
	}

	public void deletePath(Path path) {
		int ind = CityMap.findPathIndexInList(edges, path);
		if (ind == -1) {
			throw new RuntimeException("something wrong with deletePath()");
		}
		edges.remove(ind);
	}

	public List<Path> getEdges() {
		return edges;
	}

	public void addDistrict(District district) {
		if (!nodes.contains(district)) {
			nodes.add(district);
		}
	}

	private void findMinimalDistances(District node) {
		List<District> adjacentNodes = getNeighbors(node);
		for (District target: adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node)
					+ getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node); //directions in shortest paths
				unsettledNodes.add(target);
			}
		}
	}

	private double getDistance(District node, District target) { //get distance from node to target from edges info
		for (Path edge: edges) {
			if (edge.isConnectDistricts(node, target)) {
				return edge.travellingTime;
			}
		}
		throw new RuntimeException("isolated district: " + target.toString());
	}

	private List<District> getNeighbors(District node) { //only from unsettled nodes
		List<District> neighbors = new ArrayList<>();
		for (Path edge: this.edges) {
			if (edge.firstDistrict.equals(node)
					&& !isSettled(edge.secondDistrict)) { //if we didn't observe this edge before
				neighbors.add(edge.secondDistrict);
			}
			//for undirected graph
			if (edge.secondDistrict.equals(node)
					&& !isSettled(edge.firstDistrict)) { //if we didn't observe this edge before
				neighbors.add(edge.firstDistrict);
			}
		}
		return neighbors;
	}

	private District getMinimum(Set<District> vertexes) { //get minimum distance amongst vertexes
		District minimum = null;
		for (District vertex: vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(District vertex) {
		return settledNodes.contains(vertex);
	}

	private double getShortestDistance(District destination) { //get distance to source from distance array
		Double d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	public Map<District, Double> getDistancesFromSource() {
		return distance;
	}

	public List<District> getShortestPath(District target) {

		List<District> shortestPath = new ArrayList<>();
		District step = target;
		if (predecessors.get(step) == null) {
			return null;
		}
		shortestPath.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			shortestPath.add(step);
		}
		Collections.reverse(shortestPath);
		return shortestPath;
	}
}















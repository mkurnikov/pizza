package com.mkurnikov.pizza.logic.paths;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;
import com.mkurnikov.pizza.db.gateway.PathTableGateway;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import com.mkurnikov.pizza.logic.paths.shortest.DijkstraAlgorithm;
import com.mkurnikov.pizza.logic.visualize.CityMapVisualizer;

import java.awt.image.BufferedImage;
import java.util.*;

public class CityMap {
	private static CityMap instance = new CityMap();
	public static CityMap getInstance() {
		if (instance == null) {
			instance = new CityMap();
		}
		return instance;
	}

	private List<District> districts = null;
	private List<Path> paths = null;
	private DijkstraAlgorithm dijkstraAlgorithm = null;
	private List<Path> currentShortestPath = null;

	private CityMap() {
		paths = PathTableGateway.getInstance().getAllPaths();
		districts = DistrictTableGateway.getInstance().getAllDistricts();
		dijkstraAlgorithm = new DijkstraAlgorithm(districts, paths);
	}

	//	visualizer
	private CityMapVisualizer initVisualizer() {
		CityMapVisualizer visualizer = new CityMapVisualizer();
		for (Path path: paths) {
			visualizer.addEdge(path);
		}
		if (currentShortestPath != null) {
			visualizer.setShortestPath(currentShortestPath);
		}
		return visualizer;
	}

	public BufferedImage getCityMapGraphAsImage() {
		CityMapVisualizer visualizer = initVisualizer();
		return visualizer.getAsImage();
	}

	//	paths
	public List<Path> getAllPaths() {
		if (instance == null) {
			throw new RuntimeException("map is not initialized");
		}
		return paths;
	}

	public void addPath(String firstDistrictName, String secondDistrictName, double travellingTime) {
		District first = new District(firstDistrictName);
		District second = new District(secondDistrictName);
		Path path = new Path(first, second, travellingTime);

		paths.add(path);
		dijkstraAlgorithm.addPath(path);
		path.save();
	}

	public void updatePath(String firstDistrictName, String secondDistrictName, double travellingTime) {
		District first = new District(firstDistrictName);
		District second = new District(secondDistrictName);
		Path path = new Path(first, second, travellingTime);
		paths.set(paths.indexOf(path), path);
		dijkstraAlgorithm.updatePath(path);
		path.save();
	}

	public void deletePath(String firstDistrictName, String secondDistrictName) {
		District first = new District(firstDistrictName);
		District second = new District(secondDistrictName);
		int ind = paths.indexOf(new Path(first, second, 0.0));
		paths.remove(ind);
		dijkstraAlgorithm.deletePath(new Path(first, second, 0.0));
		PathTableGateway.getInstance().deletePath(firstDistrictName, secondDistrictName);
	}

	public Path findPathCorrespondsToDistricts(District source, District destination) {
//		System.out.println("findPathDistricts(): " + source + ", " + destination);
		for (Path path: paths) {
			if (path.isConnectDistricts(source, destination)) {
				return path;
			}
		}
		throw new RuntimeException("Something is wrong with paths");
	}

	//	districts
	public List<District> getAllDistricts() {
		if (instance == null) {
			throw new RuntimeException("map is not initialized");
		}
		return districts;
	}

	public boolean isDistrictExists(String districtName) {
		return DistrictTableGateway.getInstance().isDistrictExists(districtName);
	}

	//  shortest paths
	public List<Path> findShortestWay(District source, District destination) {
		dijkstraAlgorithm.execute(source);
		List<District> districtsPath = dijkstraAlgorithm.getShortestPath(destination);
		if (districtsPath == null) {
			List<Path> paths = new ArrayList<>();
			for (Path path: PathTableGateway.getInstance().getAllPaths()) {
				if (path.isConnectDistricts(source, destination)) {
					paths.add(path);
					return paths;
				}
			}
			throw new RuntimeException("districts aren't connected");
		}
//		System.out.println(districtsPath.toString());
		List<Path> shortestWayInPaths = new ArrayList<>();

		for(int i = 0; i < districtsPath.size() - 1; i++) {
			District src = districtsPath.get(i);
			District dest = districtsPath.get(i + 1);
			Path path = findPathCorrespondsToDistricts(src, dest);
			shortestWayInPaths.add(path);
		}
//		currentShortestPath = shortestWayInPaths;
		return shortestWayInPaths;
	}

	public void setCurrentShortestPath(List<Path> path) {
		currentShortestPath = path;
	}

	public List<Path> getCurrentShortestPath() {
		return currentShortestPath;
	}

	public Map<District, Double> generateDistanceMap(District source, List<District> destinations) {
		dijkstraAlgorithm.execute(source);
		Map<District, Double> distances = dijkstraAlgorithm.getDistancesFromSource();
		distances.keySet().retainAll(destinations);
		return distances;
	}

	public District getNearestNeighbour(District source, List<District> destinations) {
		Map<District, Double> distances = generateDistanceMap(source, destinations);
		return Collections.min(distances.entrySet(), new Comparator<Map.Entry<District, Double>>() {
			@Override
			public int compare(Map.Entry<District, Double> o1, Map.Entry<District, Double> o2) {
				return o1.getValue() > o2.getValue() ? 1 : -1;
			}
		}).getKey();
	}
}

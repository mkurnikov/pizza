package com.mkurnikov.pizza.logic.paths;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;
import com.mkurnikov.pizza.db.gateway.PathTableGateway;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import com.mkurnikov.pizza.logic.paths.shortest.DijkstraAlgorithm;
import com.mkurnikov.pizza.logic.visualize.CityMapVisualizer;

import java.util.List;

public class CityMap {
	private static CityMap instance = null;
	public static CityMap getInstance() {
		if (instance == null) {
			instance = new CityMap();
		}
		return instance;
	}

	private CityMap() {
		paths = PathTableGateway.getInstance().getAllPaths();
		districts = DistrictTableGateway.getInstance().getAllDistricts();
//		dijkstraAlgorithm = new DijkstraAlgorithm(this);
	}

	private List<District> districts = null;
	private List<Path> paths = null;
	private DijkstraAlgorithm dijkstraAlgorithm;

	public void addDistrict(String name) {
		District district = new District(name);

		districts.add(district);
		dijkstraAlgorithm.addDistrict(district);
		district.save();
	}

	public void addPath(String firstDistrictName, String secondDistrictName, float travellingTime) {
		District first = new District(firstDistrictName);
		District second = new District(secondDistrictName);
		Path path = new Path(first, second, travellingTime);

		paths.add(path);
//		dijkstraAlgorithm.addPath(path);
		path.save();
	}

	public void createImageWithCityMap() {
		CityMapVisualizer visualizer = new CityMapVisualizer();
		for (Path path: paths) {
			visualizer.addEdge(path);
		}
		visualizer.saveToImage("/home/mkurnikov/_python/pizza_delivery/src/main/webapp/img/map.png");
	}

	public List<Path> getAllPaths() {
		if (instance == null) {
			throw new RuntimeException("map is not initialized");
		}
		return paths;
	}

	public List<District> getAllDistricts() {
		if (instance == null) {
			throw new RuntimeException("map is not initialized");
		}
		return districts;
	}

	public boolean isDistrictExists(String districtName) {
		return DistrictTableGateway.getInstance().isDistrictExists(districtName);
	}

	public List<Path> generateShortestWay(String place1, String place2) {
		return null;
	}
}

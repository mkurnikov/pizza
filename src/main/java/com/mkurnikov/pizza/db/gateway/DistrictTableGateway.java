package com.mkurnikov.pizza.db.gateway;

import com.mkurnikov.pizza.db.DatabaseConnection;
import com.mkurnikov.pizza.db.gateway.base.CustomQueryExecutor;
import com.mkurnikov.pizza.logic.paths.models.District;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictTableGateway {
	private final String ADD_DISTRICT_QUERY = "INSERT INTO district(name) VALUES (?)";
	private final String GET_DISTRICT_QUERY = "SELECT * FROM district WHERE name=?";
	private final String GET_ALL_DISTRICTS_QUERY = "SELECT * FROM district;";

	private static DistrictTableGateway instance = null;
	private DistrictTableGateway() {}

	public static DistrictTableGateway getInstance() {
		if (instance == null) {
			instance = new DistrictTableGateway();
		}
		return instance;
	}

	public List<District> getAllDistricts() {
		return (List<District>) new CustomQueryExecutor() {

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				List<District> districts = new ArrayList<>();
				while (resultSet.next()) {
					District district = new District(resultSet.getString("name"));
					districts.add(district);
				}
				return districts;
			}
		}.init(this.GET_ALL_DISTRICTS_QUERY).execute();
	}

	public void addDistrict(final String name) {
		new CustomQueryExecutor() {

			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, name);
			}
		}.init(this.ADD_DISTRICT_QUERY).execute();
	}


	public boolean isDistrictExists(final String name) {
		return getDistrict(name) != null;
	}


	public District getDistrict(final String name) {
		return (District) new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, name);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				if (!resultSet.next()) {
					return null;
				} else {
					return new District(resultSet.getString("name"));
				}
			}
		}.init(this.GET_DISTRICT_QUERY).execute();
	}
}

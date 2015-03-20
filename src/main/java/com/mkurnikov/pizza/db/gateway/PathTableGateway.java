package com.mkurnikov.pizza.db.gateway;

import com.mkurnikov.pizza.db.DatabaseConnection;
import com.mkurnikov.pizza.db.gateway.base.CustomQueryExecutor;
import com.mkurnikov.pizza.logic.paths.models.District;
import com.mkurnikov.pizza.logic.paths.models.Path;
import org.postgresql.core.QueryExecutor;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PathTableGateway {
	private final String ADD_PATH_QUERY = "INSERT INTO path(district_1, district_2, travelling_time) " +
			"VALUES (?, ?, ?)";
	private final String GET_ALL_PATHS_QUERY = "SELECT * FROM path;";

	private static PathTableGateway instance = null;
	private PathTableGateway() {}

	public static PathTableGateway getInstance() {
		if (instance == null) {
			instance = new PathTableGateway();
		}
		return instance;
	}

	public void addPath(final String firstDistrictName, final String secondDistrictName, final float travellingTime) {
		new CustomQueryExecutor() {

			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, firstDistrictName);
				preparedStatement.setString(2, secondDistrictName);
				preparedStatement.setFloat(3, travellingTime);
			}
		}.init(this.ADD_PATH_QUERY).execute();
	}

	public List<Path> getAllPaths() {
		return (List<Path>) new CustomQueryExecutor() {
			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				List<Path> paths = new ArrayList<Path>();
				while (resultSet.next()) {
					District district1 = DistrictTableGateway.getInstance()
							.getDistrict(resultSet.getString("district_1"));
					District district2 = DistrictTableGateway.getInstance().
							getDistrict(resultSet.getString("district_2"));
					paths.add(new Path(district1, district2, resultSet.getFloat("travelling_time")));
				}
				return paths;
			}
		}.init(this.GET_ALL_PATHS_QUERY).execute();
	}
}

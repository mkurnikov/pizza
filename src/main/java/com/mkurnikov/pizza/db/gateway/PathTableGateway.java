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
	private final String GET_PATH_QUERY = "SELECT * FROM path " +
			"WHERE ((district_1=? AND district_2=?) OR (district_2=? AND district_1=?)) AND travelling_time=?";
	private final String GET_PATH_BY_DISTRICTS_QUERY = "SELECT * FROM path " +
			"WHERE ((district_1=? AND district_2=?) OR (district_2=? AND district_1=?))";
	private final String UPDATE_PATH = "UPDATE path SET travelling_time=? " +
			"WHERE ((district_1=? AND district_2=?) OR (district_2=? AND district_1=?))";
	private final String DELETE_PATH = "DELETE FROM path " +
			"WHERE ((district_1=? AND district_2=?) OR (district_2=? AND district_1=?))";

	private static PathTableGateway instance = null;
	private PathTableGateway() {}

	public static PathTableGateway getInstance() {
		if (instance == null) {
			instance = new PathTableGateway();
		}
		return instance;
	}

	public void addPath(final String firstDistrictName, final String secondDistrictName,
	                    final double travellingTime) {
		new CustomQueryExecutor() {

			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, firstDistrictName);
				preparedStatement.setString(2, secondDistrictName);
				preparedStatement.setDouble(3, travellingTime);
			}
		}.init(this.ADD_PATH_QUERY).execute();
	}

	public boolean isPathExists(final String firstDistrictName, final String secondDistrictName,
	                            final double travellingTime) {
		return (boolean) new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, firstDistrictName);
				preparedStatement.setString(2, secondDistrictName);
				preparedStatement.setString(3, firstDistrictName);
				preparedStatement.setString(4, secondDistrictName);
				preparedStatement.setDouble(5, travellingTime);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				return resultSet.next();
			}
		}.init(GET_PATH_QUERY).execute();
	}

	public boolean isPathExistsByDistricts(final String firstDistrictName, final String secondDistrictName) {
		return (boolean) new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, firstDistrictName);
				preparedStatement.setString(2, secondDistrictName);
				preparedStatement.setString(3, firstDistrictName);
				preparedStatement.setString(4, secondDistrictName);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				return resultSet.next();
			}
		}.init(GET_PATH_BY_DISTRICTS_QUERY).execute();
	}

	public Path getPathByDistricts(final String firstD, final String secondD) {
		Object obj = new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, firstD);
				preparedStatement.setString(2, secondD);
				preparedStatement.setString(3, firstD);
				preparedStatement.setString(4, secondD);
			}

			@Override
			public Object processResultSet(ResultSet resultSet) throws SQLException {
				if (resultSet.next()) {
					return new Path(new District(resultSet.getString("district_1")), new District(resultSet.getString("district_2")),
							resultSet.getDouble("travelling_time"));
				} else {
					return null;
				}
			}
		}.init(GET_PATH_BY_DISTRICTS_QUERY).execute();
		if (obj == null) {
			return null;
		} else {
			return (Path) obj;
		}
	}

	public void updatePath(final String firstD, final String secondD, final double time) {
		new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setDouble(1, time);
				preparedStatement.setString(2, firstD);
				preparedStatement.setString(3, secondD);
				preparedStatement.setString(4, firstD);
				preparedStatement.setString(5, secondD);
			}
		}.init(UPDATE_PATH).execute();
	}

	public void deletePath(final String firstD, final String secondD) {
		new CustomQueryExecutor() {
			@Override
			public void prepareStatement(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, firstD);
				preparedStatement.setString(2, secondD);
				preparedStatement.setString(3, firstD);
				preparedStatement.setString(4, secondD);
			}
		}.init(DELETE_PATH).execute();
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

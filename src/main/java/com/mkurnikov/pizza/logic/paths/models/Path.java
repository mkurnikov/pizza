package com.mkurnikov.pizza.logic.paths.models;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;
import com.mkurnikov.pizza.db.gateway.PathTableGateway;

import java.io.Serializable;

public class Path implements Serializable {
	//		private boolean written = false;
	public District firstDistrict;
	public District secondDistrict;
	public double travellingTime;

	public Path(District first, District second, double travellingTime) {
		this.firstDistrict = first;
		this.secondDistrict = second;
		this.travellingTime = travellingTime;
	}

	public String toString() {
		return "Path: " + this.firstDistrict.toString() +
				", " + this.secondDistrict.toString() + ", " + this.travellingTime;
	}

	public boolean isConnectDistricts(District d1, District d2) {
		return (this.firstDistrict.equals(d1) && this.secondDistrict.equals(d2)) ||
				(this.firstDistrict.equals(d2) && this.secondDistrict.equals(d1));
	}

	public void save() {
		this.firstDistrict.save();
		this.secondDistrict.save();

		if (PathTableGateway.getInstance().
				isPathExistsByDistricts(this.firstDistrict.name, this.secondDistrict.name)) {
			PathTableGateway.getInstance()
					.updatePath(this.firstDistrict.name, this.secondDistrict.name, this.travellingTime);
		} else {
			PathTableGateway.getInstance().addPath(this.firstDistrict.name, this.secondDistrict.name,
					this.travellingTime);
		}
	}

	public void delete() {
		PathTableGateway.getInstance().deletePath(this.firstDistrict.name, this.secondDistrict.name);
	}

	public int hashCode() {
		return this.toString().hashCode();
	}

	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( !(o instanceof Path) ) return false;

		return ((Path) o).isConnectDistricts(this.firstDistrict, this.secondDistrict);
	}
}

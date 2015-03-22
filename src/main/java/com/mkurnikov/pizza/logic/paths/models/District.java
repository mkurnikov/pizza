package com.mkurnikov.pizza.logic.paths.models;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;

import java.io.Serializable;

public class District implements Serializable {
	public String name;
	public District(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "District: " + this.name;
	}

	public void save() {
		if (!DistrictTableGateway.getInstance().isDistrictExists(this.name)) {
			DistrictTableGateway.getInstance().addDistrict(this.name);
		}
	}

	public int hashCode() {
		return this.name.hashCode();
	}

	public boolean equals(Object district) {
		if ( this == district ) return true;
        if ( !(district instanceof District) ) return false;
		return this.name.equals(((District) district).name);
	}
}

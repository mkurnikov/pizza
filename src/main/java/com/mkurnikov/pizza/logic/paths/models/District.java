package com.mkurnikov.pizza.logic.paths.models;

import com.mkurnikov.pizza.db.gateway.DistrictTableGateway;

public class District {
	public String name;
	public District(String name) {
		this.name = name;
	}

	public String toString() {
		return "District: " + this.name;
	}

	public void save() {
		System.out.println("call district save() with" + this.toString());
		boolean condition = DistrictTableGateway.getInstance().isDistrictExists(this.name);
		System.out.println("District exists: " + condition);
		if (!condition) {
			DistrictTableGateway.getInstance().addDistrict(this.name);
		}
	}
}

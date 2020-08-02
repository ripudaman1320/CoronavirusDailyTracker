package io.ripudaman.coronavirus.models;

public class LocationStats {

	private String Province_State;
	private String country;
	private int latestTotalCases;
	private int delta;
	
	public String getProvince_State() {
		return Province_State;
	}
	public void setProvince_State(String province_State) {
		Province_State = province_State;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LocationStats{"+"state='"+Province_State+'\''+",country='"+country+'\''+",latestTotalCases="+latestTotalCases+'}';
	}
	public int getDelta() {
		return delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
	}
		
	
	
}

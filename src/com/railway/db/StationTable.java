package com.railway.db;

import java.util.ArrayList;
import java.util.List;

import com.railway.model.Station;

public class StationTable {
	private final List<Station> stations = new ArrayList<>();
	
	private StationTable() {
		
	}

	private static StationTable instance = null;
	
	public static StationTable getInstance() {
		if(instance == null) {
			instance = new StationTable();
		}
		return instance;
	}

	Station createStation(String name) {
		Station station = new Station(name);
		return station;
	}
	
	public void insertStation(Station station) {
		stations.add(station);
	}
	public boolean isStationAvailable(String station) {
		for(Station s:stations) {
			if(s.getName().equalsIgnoreCase(station))
				return true;
		}
		
		return false;
	}
	
	

}

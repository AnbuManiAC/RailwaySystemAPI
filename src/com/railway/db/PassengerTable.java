package com.railway.db;

import java.util.ArrayList;
import java.util.List;

import com.railway.train.booking.Passenger;

public class PassengerTable {
	private List<Passenger> passengers = new ArrayList<>();
	
	private PassengerTable() {
		
	}

	private static PassengerTable instance = null;
	
	public static PassengerTable getInstance() {
		if(instance == null) {
			instance = new PassengerTable();
		}
		return instance;
	}

	public Passenger createPassenger(String name, int age, String gender, String berthPreference) {
		Passenger passenger = new Passenger(name, age, gender, berthPreference);
		
		return passenger;
	}
	
	public void insertPassenger(Passenger passenger) {
		getPassenger().add(passenger);
	}
	public List<Passenger> getPassenger() {
		return passengers;
	}
	public Passenger getPassengerFromPnr(String pnr) {
		for(Passenger p:passengers) {
			if(pnr.equals(p.getPnr()))
				return p;
		}
		return null;
	}

}

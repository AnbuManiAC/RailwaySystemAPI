package com.railway.model;

import java.time.LocalDate;
import java.util.HashMap;

public class Train {
	
	private final String number;
	private final String name;
	private final Station source;
	private final Station destination;
	private HashMap<LocalDate,Availability> availability = new HashMap<>();
	
	public Train(String number, String name, Station source, Station destination) {
		this.number = number;
		this.name = name;
		this.source = source;
		this.destination = destination;
	}
	

	public void populateAvailabilities(String startDate,String endDate) {
		LocalDate start_date = LocalDate.parse(startDate); 
		LocalDate end_date = LocalDate.parse(endDate);
		for (LocalDate date = start_date; date.isBefore(end_date); date = date.plusDays(1))
		{
		    availability.put(date, new Availability());
		}
	}

	public String getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	public Station getSource() {
		return source;
	}
	public Station getDestination() {
		return destination;
	}
	public String getTrainInfo() {
		return String.format("\nTrain name : %s\nTrain number : %s\nSource : %s\nDestination : %s\n", getName(),getNumber(),getSource().getName(),getDestination().getName());
	}

	public HashMap<LocalDate, Availability> getAvailability() {
		return availability;
	}
	
	
	
	
	
}

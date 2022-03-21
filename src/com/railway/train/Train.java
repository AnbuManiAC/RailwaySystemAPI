package com.railway.train;

import java.time.LocalDate;
import java.util.HashMap;

public class Train {
	
	private String number;
	private String name;
	private Station source;
	private Station destination;
	private HashMap<LocalDate,Availability> availability = new HashMap<>();
	

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



	public void setNumber(String number) {
		this.number = number;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	


	public Station getSource() {
		return source;
	}



	public void setSource(Station source) {
		this.source = source;
	}



	public Station getDestination() {
		return destination;
	}



	public void setDestination(Station destination) {
		this.destination = destination;
	}




	public String getTrainInfo() {
		return String.format("\nTrain name : %s\nTrain number : %s\nSource : %s\nDestination : %s\n", getName(),getNumber(),getSource().getName(),getDestination().getName());
	}

	public HashMap<LocalDate, Availability> getAvailability() {
		return availability;
	}
	
	
	
	
	
}

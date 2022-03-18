package com.railway.train;

import java.time.LocalDate;
import java.util.HashMap;

public class Train {
	
	private String number;
	private String name;
	private Station source;
	private Station destination;
	private HashMap<LocalDate,Availability> availability = new HashMap<>();
	
	
	public Train(String number, String name, Station source, Station destination) {
		this.setNumber(number);;
		this.setName(name);;
		this.setSource(source);;
		this.setDestination(destination);
		this.populateAvailabilities(LocalDate.now(),3);
	}

	private void populateAvailabilities(LocalDate startDate,int offset) {
		for (LocalDate date = startDate; date.isBefore(startDate.plusDays(offset)); date = date.plusDays(1))
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

package com.railway.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.railway.train.Availability;
import com.railway.train.Station;
import com.railway.train.Train;


public class TrainTable {
	
	private List<Train> trains = new ArrayList<>();
	
	private TrainTable() {
		
	}

	private static TrainTable instance = null;
	
	public static TrainTable getInstance() {
		if(instance == null) {
			instance = new TrainTable();
		}
		return instance;
	}
	
	public List<Train> getTrains(){
		return trains;
	}

	public Train createTrain(String number, String name, Station source, Station destination, String startDate , String endDate) {
		Train train = new Train();
		train.setNumber(number);
		train.setName(name);
		train.setSource(source);
		train.setDestination(destination);
		train.populateAvailabilities(startDate, endDate);
		
		return train;
	}
	
	public void insertTrain(Train train) {
		getTrains().add(train);
	}
	
	public Train getTrainFromNumber(String trainNumber) {
		for(Train t: trains) {
			if(trainNumber.equals(t.getNumber()))
				return t;
		}
		return null;
	}
	
	
	public ArrayList<Train> searchTrain(String source, String destination, LocalDate date) {
		ArrayList<Train> searchResult = new ArrayList<>();
		for(Train train : trains) {
			if(train.getSource().getName().equalsIgnoreCase(source) && train.getDestination().getName().equalsIgnoreCase(destination) && train.getAvailability().containsKey(date)) {
				searchResult.add(train);
			}
		}
		return searchResult;
	}
	
	public int getAvailableSeatCount(Train train,LocalDate date) {
		Availability avl = train.getAvailability().get(date);
		if(avl!=null)
			return avl.getLowerBerthSeats().size() + avl.getMiddleBerthSeats().size() + avl.getUpperBerthSeats().size() + avl.getRacSeats().size() +
				avl.getWaitingListPositions().size();
		return -1;
		
	}
	
	public Availability checkAvailability(Train train,LocalDate date) {
		return train.getAvailability().get(date);
	}
	
}

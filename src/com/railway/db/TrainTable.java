package com.railway.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.railway.model.Availability;
import com.railway.model.Station;
import com.railway.model.Train;


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
		final List<Train> trainsList = Collections.unmodifiableList(trains);
		return trainsList;
	}
	
	public Train createTrain(String number, String name, Station source, Station destination, String startDate , String endDate) {
		Train train = new Train(number,name,source,destination);
		train.populateAvailabilities(startDate, endDate);
		
		return train;
	}
	
	public void insertTrain(Train train) {
		trains.add(train);
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
			return avl.getLowerBerthSeatsCount() + avl.getMiddleBerthSeatsCount() + avl.getUpperBerthSeatsCount() + avl.getRacSeatsCount() +
				avl.getWaitingListPositionsCount();
		return -1;
		
	}
	
	public Availability checkAvailability(Train train,LocalDate date) {
		return train.getAvailability().get(date);
	}
	
}

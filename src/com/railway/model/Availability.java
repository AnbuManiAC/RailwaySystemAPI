package com.railway.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Availability {
	
	private final List<Integer> lowerBerthSeats = new ArrayList<>(Arrays.asList());
    private final List<Integer> middleBerthSeats = new ArrayList<>(Arrays.asList());
    private final List<Integer> upperBerthSeats = new ArrayList<>(Arrays.asList(1));
    private final List<Integer> racSeats = new ArrayList<>(Arrays.asList(1));
    private final List<Integer> waitingListPositions = new ArrayList<>(Arrays.asList(1));
    
//    private final int lowerBerthCapacity = lowerBerthSeats.size();
//    private final int middleBerthCapacity = middleBerthSeats.size();
//    private final int upperBerthCapacity = upperBerthSeats.size();
//    private final int racCapacity = racSeats.size();
//    private final int waitingListCapacity = waitingListPositions.size();

    private final Map<String,List<Integer>> seatBerthMapping = new HashMap<>();
    
    
    
    public Availability() {
    	seatBerthMapping.put("L", lowerBerthSeats);
    	seatBerthMapping.put("M", middleBerthSeats);
    	seatBerthMapping.put("U", upperBerthSeats);
    	seatBerthMapping.put("RAC", racSeats);
    	seatBerthMapping.put("WL", waitingListPositions);

    }
    
	public Map<String, List<Integer>> getSeatBerthMapping() {
		return Collections.unmodifiableMap(seatBerthMapping);
	}
	
	


	public boolean isBerthAvailable(String berth) {
		List<Integer> seatsForBerth = seatBerthMapping.get(berth);
    	if(seatsForBerth.size()>0) {
    		return true;
    	}
    	return false;
    }


	public int getMiddleBerthSeatsCount() {
		return middleBerthSeats.size();
	}
	public int getUpperBerthSeatsCount() {
		return upperBerthSeats.size();
	}
	public int getLowerBerthSeatsCount() {
		return lowerBerthSeats.size();
	}
	public int getRacSeatsCount() {
		return racSeats.size();
	}
	public int getWaitingListPositionsCount() {
		return waitingListPositions.size();
	}
    

}

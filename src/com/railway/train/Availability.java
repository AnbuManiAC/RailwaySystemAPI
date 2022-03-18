package com.railway.train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Availability {
	
	private List<Integer> lowerBerthSeats = new ArrayList<>(Arrays.asList());
    private List<Integer> middleBerthSeats = new ArrayList<>(Arrays.asList());
    private List<Integer> upperBerthSeats = new ArrayList<>(Arrays.asList(1));
    private List<Integer> racSeats = new ArrayList<>(Arrays.asList(1));
    private List<Integer> waitingListPositions = new ArrayList<>(Arrays.asList(1));
    
    private final int lowerBerthCapacity = lowerBerthSeats.size();
    private final int middleBerthCapacity = middleBerthSeats.size();
    private final int upperBerthCapacity = upperBerthSeats.size();
    private final int racCapacity = racSeats.size();
    private final int waitingListCapacity = waitingListPositions.size();
    private final int totalCapacity = lowerBerthCapacity + middleBerthCapacity + upperBerthCapacity + racCapacity + waitingListCapacity;
    Map<String,List<Integer>> seatBerthMapping = new HashMap<>();
    
    
    
    public Availability() {
    	seatBerthMapping.put("L", lowerBerthSeats);
    	seatBerthMapping.put("M", middleBerthSeats);
    	seatBerthMapping.put("U", upperBerthSeats);
    	seatBerthMapping.put("RAC", racSeats);
    	seatBerthMapping.put("WL", waitingListPositions);

    }
    
    

    
    
	public List<Integer> getLowerBerthSeats() {
		return lowerBerthSeats;
	}





	public List<Integer> getMiddleBerthSeats() {
		return middleBerthSeats;
	}





	public List<Integer> getUpperBerthSeats() {
		return upperBerthSeats;
	}





	public List<Integer> getRacSeats() {
		return racSeats;
	}





	public List<Integer> getWaitingListPositions() {
		return waitingListPositions;
	}





	public Map<String, List<Integer>> getSeatBerthMapping() {
		return seatBerthMapping;
	}



	public void setSeatBerthMapping(Map<String, List<Integer>> seatBerthMapping) {
		this.seatBerthMapping = seatBerthMapping;
	}

	
	


	public int getLowerBerthCapacity() {
		return lowerBerthCapacity;
	}





	public int getMiddleBerthCapacity() {
		return middleBerthCapacity;
	}





	public int getUpperBerthCapacity() {
		return upperBerthCapacity;
	}





	public int getRacCapacity() {
		return racCapacity;
	}





	public int getWaitingListCapacity() {
		return waitingListCapacity;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}




	public boolean isBerthAvailable(String berth) {
		List<Integer> seatsForBerth = seatBerthMapping.get(berth);
    	if(seatsForBerth.size()>0) {
    		return true;
    	}
    	return false;
    }
    

}

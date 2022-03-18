package com.railway.db;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.railway.train.Availability;
import com.railway.train.Station;
import com.railway.train.Train;
import com.railway.train.booking.Passenger;
import com.railway.train.booking.Ticket;



public class Database {

	
	private List<User> users = new ArrayList<>();
	
	private List<Train> trains = new ArrayList<>();
	private List<Station> stations = new ArrayList<>();
	
	private List<Passenger> passenger = new ArrayList<>();
	
	private List<Ticket> berthTicket = new ArrayList<>();
	private LinkedList<Ticket> racTicket = new LinkedList<>();
	private LinkedList<Ticket> waitingList = new LinkedList<>();
		
	private Database() {
		init();
    }

	private static Database instance = null;
	
	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	private void init() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("resources/config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            Station s1 = new Station(prop.getProperty("station1"));
			Station s2 = new Station(prop.getProperty("station2"));
			Station s3 = new Station(prop.getProperty("station3"));
			
	    	stations.add(s1);
	    	stations.add(s2);
	    	stations.add(s3);
	    	
	    	trains.add(new Train(prop.getProperty("train1Number"),prop.getProperty("train1Name"),s1,s2));
	    	trains.add(new Train(prop.getProperty("train2Number"),prop.getProperty("train2Name"),s1,s3));

	    	users.add(new User(prop.getProperty("username"), prop.getProperty("password")));

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
	
	public List<User> getUsers(){
		return users;
	}
	public void insertUser(User user) {
		getUsers().add(user);		
	}
	
	public List<Train> getTrains(){
		return trains;
	}
	
	public List<Station> getStattions(){
		return stations;
	}
	
	public List<Passenger> getPassenger() {
		return passenger;
	}


	public List<Ticket> getBerthTicket() {
		return berthTicket;
	}




	public LinkedList<Ticket> getRacTicket() {
		return racTicket;
	}



	public LinkedList<Ticket> getWaitingList() {
		return waitingList;
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
	
	public Train getTrainFromNumber(String trainNumber) {
		for(Train t: trains) {
			if(trainNumber.equals(t.getNumber()))
				return t;
		}
		return null;
	}
	
	public Passenger getPassengerFromPnr(String pnr) {
		for(Passenger p:passenger) {
			if(pnr.equals(p.getPnr()))
				return p;
		}
		return null;
	}
	
	public Availability checkAvailability(Train train,LocalDate date) {
		return train.getAvailability().get(date);
	}
	
	public Ticket getTicketfromPnr(String pnr) {
		for(Ticket t:berthTicket) {
			if(t.getId().equals(pnr)) {
				return t;
			}
		}
		for(Ticket t:racTicket) {
			if(t.getId().equals(pnr)) {
				return t;
			}
		}
		for(Ticket t:waitingList) {
			if(t.getId().equals(pnr)) {
				return t;
			}
		}
		return null;
	}
	
	
	public boolean isStationAvailable(String station) {
		for(Station s:stations) {
			if(s.getName().equalsIgnoreCase(station))
				return true;
		}
		
		return false;
	}
	
	public boolean isExistingUser(String name,String pwd) {
		for(User user : users) {
			if(user.getUsername().equals(name) && user.getPassword().equals(pwd))
				return true;
		}
		return false;
	}
	
	public boolean checkUser(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username))
				return true;
		}
		return false;
	}
	
	public int getAvailableSeatCount(Train train,LocalDate date) {
		Availability avl = train.getAvailability().get(date);
		if(avl!=null)
			return avl.getLowerBerthSeats().size() + avl.getMiddleBerthSeats().size() + avl.getUpperBerthSeats().size() + avl.getRacSeats().size() +
				avl.getWaitingListPositions().size();
		return -1;
		
	}

	
}

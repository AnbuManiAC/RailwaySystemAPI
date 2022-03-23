package com.railway.model;

import java.time.LocalDate;

public class Ticket {

	private final Passenger passenger;
	private final Train train;
	private String berthAlloted;
	private int seatNumber;
	private final LocalDate date;
	private final String id;
	
	public Ticket(Passenger passenger, Train train, LocalDate date, String berthAlloted, int seatNumber) {
		this.passenger = passenger;
		this.train = train;
		this.date = date;
		this.berthAlloted = berthAlloted;
		this.seatNumber = seatNumber;
		this.id = passenger.getPnr();
	}
	
	public Passenger getPassenger() {
		return passenger;
	}
	public String getBerthAlloted() {
		return berthAlloted;
	}
    
	public Train getTrain() {
		return train;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public void generateTicket() {
		System.out.print(this.toString());
	}

	@Override
	public String toString() {
		return "Ticket ["+ passenger.toString() + ", Train number : " + train.getNumber() +", Date : "+date+", Berth alloted : " + berthAlloted
				+ ", Seat number : " +berthAlloted+seatNumber + "]";
	}
	
	
}

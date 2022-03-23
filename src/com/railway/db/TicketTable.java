package com.railway.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.railway.model.Passenger;
import com.railway.model.Ticket;
import com.railway.model.Train;

public class TicketTable {
	private final List<Ticket> berthTicket = new ArrayList<>();
	private final LinkedList<Ticket> racTicket = new LinkedList<>();
	private final LinkedList<Ticket> waitingList = new LinkedList<>();
	
	private TicketTable() {
		
	}

	private static TicketTable instance = null;
	
	public static TicketTable getInstance() {
		if(instance == null) {
			instance = new TicketTable();
		}
		return instance;
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
	
	public void insertBerthTicket(Ticket ticket) {
		berthTicket.add(ticket);
	}
	public void insertRacTicket(Ticket ticket) {
		racTicket.add(ticket);
	}
	public void insertWaitingList(Ticket ticket) {
		waitingList.add(ticket);
	}
	
	public Ticket createTicket(Passenger passenger, Train train, LocalDate date, String berthAlloted, int seatNumber) {
		Ticket ticket = new Ticket(passenger, train, date, berthAlloted, seatNumber);
		return ticket;
		
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
}

package com.railway.service;

import java.time.LocalDate;
import java.util.List;

import com.railway.db.TicketTable;
import com.railway.db.TrainTable;
import com.railway.db.UserTable;
import com.railway.model.Availability;
import com.railway.model.Passenger;
import com.railway.model.Ticket;
import com.railway.model.Train;

public class Booking implements Book{
	
	UserTable users = UserTable.getInstance();
	
	
	public Ticket bookTicket(Passenger passenger,Train train,LocalDate date) {

		TrainTable trains = TrainTable.getInstance();
		
		
		Ticket ticket = null;
		Availability avl = trains.checkAvailability(train, date);
		if(avl!=null) {
			String berthPreference = passenger.getBerthPreference();
			String berthAlloted;
			if(avl.isBerthAvailable(berthPreference)) {
				berthAlloted = berthPreference;
				
				ticket = bookBerth(ticket, passenger, train, avl, berthAlloted, date);	
			}
			else if(!berthPreference.equals("L") && avl.isBerthAvailable("L")) {
				berthAlloted = "L";
				
				ticket = bookBerth(ticket, passenger, train, avl, berthAlloted, date);
			}
			else if(!berthPreference.equals("M") && avl.isBerthAvailable("M")) {
				berthAlloted = "M";
				
				ticket = bookBerth(ticket, passenger, train, avl, berthAlloted, date);
			}
			else if(!berthPreference.equals("U") && avl.isBerthAvailable("U")) {
				berthAlloted = "U";
				ticket = bookBerth(ticket, passenger, train, avl, berthAlloted, date);

			}
			else if(avl.isBerthAvailable("RAC")) {
				berthAlloted = "RAC";
				ticket = bookBerth(ticket, passenger, train, avl, berthAlloted, date);

			}
			else if(avl.isBerthAvailable("WL")) {
				berthAlloted = "WL";
				ticket = bookBerth(ticket, passenger, train, avl, berthAlloted, date);

			}
							
		}
		
		return ticket;
	}
	
	private Ticket bookBerth(Ticket ticket, Passenger passenger, Train train, Availability avl, String berthAlloted, LocalDate date) {
		TicketTable tickets = TicketTable.getInstance();
		List<Integer> berthSeat = avl.getSeatBerthMapping().get(berthAlloted);

		ticket = tickets.createTicket(passenger, train, date, berthAlloted, berthSeat.get(0));
		
//		ticket.setSeatNumber(berthSeat.get(0));
		berthSeat.remove(0);
		if(berthAlloted.equals("RAC"))
			tickets.insertRacTicket(ticket);
		else if(berthAlloted.equals("WL"))
			tickets.insertWaitingList(ticket);
		else
			tickets.insertBerthTicket(ticket);
		addUserTicket(ticket);
		return ticket;
	}
	private void addUserTicket(Ticket t) {
		UserTable users = UserTable.getInstance();
		users.addUserAccessMapping(t);
	}

	
}
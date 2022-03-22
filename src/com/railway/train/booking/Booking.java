package com.railway.train.booking;

import java.time.LocalDate;
import java.util.List;

import com.railway.db.TicketTable;
import com.railway.db.TrainTable;
import com.railway.db.UserTable;
import com.railway.train.Availability;
import com.railway.train.Train;

public class Booking implements Book{
	
	UserTable users = UserTable.getInstance();
	
	
	public static Ticket bookTicket(Passenger passenger,Train train,LocalDate date) {

		TicketTable tickets = TicketTable.getInstance();
		TrainTable trains = TrainTable.getInstance();
		
		
		Ticket ticket = null;
		Availability avl = trains.checkAvailability(train, date);
		if(avl!=null) {
			List<Integer> waitingListPositions = avl.getWaitingListPositions();
			List<Integer> racSeats = avl.getRacSeats();
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
			else if(racSeats.size()>0) {
				berthAlloted = "RAC";
				ticket = tickets.createTicket(passenger, train, berthAlloted, date);
				ticket.setSeatNumber(racSeats.get(0));
				racSeats.remove(0);
				tickets.insertRacTicket(ticket);
				addUserTicket(ticket);

			}
			else if(waitingListPositions.size()>0) {
				berthAlloted = "WL";
				ticket = tickets.createTicket(passenger, train, berthAlloted, date);
				ticket.setSeatNumber(waitingListPositions.get(0));
				waitingListPositions.remove(0);
				tickets.insertWaitingList(ticket);
				addUserTicket(ticket);

			}
							
		}
		
		return ticket;
	}
	
	private static Ticket bookBerth(Ticket ticket, Passenger passenger, Train train, Availability avl, String berthAlloted, LocalDate date) {
		TicketTable tickets = TicketTable.getInstance();
		
		ticket = tickets.createTicket(passenger, train, berthAlloted, date);
		List<Integer> berthSeat = avl.getSeatBerthMapping().get(berthAlloted);
		
		ticket.setSeatNumber(berthSeat.get(0));
		berthSeat.remove(0);
		tickets.insertBerthTicket(ticket);
		addUserTicket(ticket);
		return ticket;
	}
	private static void addUserTicket(Ticket t) {
		UserTable users = UserTable.getInstance();
		users.getUserAccessMapping().add(t);
	}

	
}
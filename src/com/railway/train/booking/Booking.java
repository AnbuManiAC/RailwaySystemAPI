package com.railway.train.booking;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.railway.db.Database;
import com.railway.train.Availability;
import com.railway.train.Train;

public class Booking {
	

	public static Ticket bookTicket(Passenger passenger,Train train,LocalDate date) {

		Database db = Database.getInstance();

		Ticket ticket = null;
		Availability avl = db.checkAvailability(train, date);
		if(avl!=null) {
			List<Integer> waitingListPositions = avl.getWaitingListPositions();
			List<Integer> racSeats = avl.getRacSeats();
			String berthPreference = passenger.getBerthPreference();
			String berthAlloted="";
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
				ticket = new Ticket(passenger, train, "RAC",date);
				ticket.setSeatNumber(racSeats.get(0));
				racSeats.remove(0);
				db.getRacTicket().add(ticket);
			}
			else if(waitingListPositions.size()>0) {
				ticket = new Ticket(passenger, train, "WL",date);
				ticket.setSeatNumber(waitingListPositions.get(0));
				waitingListPositions.remove(0);
				db.getWaitingList().add(ticket);
			}
							
		}
		
		return ticket;
	}
	
	private static Ticket bookBerth(Ticket ticket, Passenger passenger, Train train, Availability avl, String berthAlloted, LocalDate date) {
		Database db = Database.getInstance();

		ticket = new Ticket(passenger, train, berthAlloted, date);
		List<Integer> berthSeat = avl.getSeatBerthMapping().get(berthAlloted);
		
		ticket.setSeatNumber(berthSeat.get(0));
		berthSeat.remove(0);
		db.getBerthTicket().add(ticket);
		return ticket;
	}
	
	public static boolean cancelTicket(String pnr) {
		Database db = Database.getInstance();

		
		List<Ticket> berthTicket = db.getBerthTicket();
		LinkedList<Ticket> racTicket = db.getRacTicket();
		LinkedList<Ticket> waitingList = db.getWaitingList();
		
		for(int i=0;i<berthTicket.size();i++) {
			if(berthTicket.get(i).getId().equals(pnr)) {
				return cancelBerthTicket(i,berthTicket,racTicket,waitingList);
			}
		}
		for(int i=0;i<racTicket.size();i++) {
			if(racTicket.get(i).getId() == pnr) {
				return cancelRacTicket(i,racTicket,waitingList);
			}
		}
		for(int i=0;i<waitingList.size();i++) {
			if(waitingList.get(i).getId() == pnr) {
				return cancelWaitingList(i,waitingList);
			}
		}
		return false;
		
	}

	private static boolean cancelWaitingList(int i, LinkedList<Ticket> waitingList) {
		Ticket t = waitingList.get(i);
		Train train  = t.getTrain();
		LocalDate date = t.getDate();
		Availability avl = train.getAvailability().get(date);
		List<Integer> berthSeat =  avl.getSeatBerthMapping().get(t.getBerthAlloted());
		berthSeat.add(t.getSeatNumber());
		waitingList.remove(i);
		return true;
	}

	private static boolean cancelRacTicket(int i, LinkedList<Ticket> racTicket, LinkedList<Ticket> waitingList) {
		Ticket t = racTicket.get(i);
		Train train  = t.getTrain();
		LocalDate date = t.getDate();
		Availability avl = train.getAvailability().get(date);
		List<Integer> berthSeat =  avl.getSeatBerthMapping().get(t.getBerthAlloted());
		berthSeat.add(t.getSeatNumber());
		racTicket.remove(i);
		if(waitingList.size()>0) {

			t = waitingList.poll();
			
			bookTicketforCancelledTicket(t);		
		}
		return true;
	}

	private static boolean cancelBerthTicket(int i, List<Ticket> berthTicket, LinkedList<Ticket> racTicket, LinkedList<Ticket> waitingList) {
		
		Ticket t = berthTicket.get(i);
		Train train  = t.getTrain();
		LocalDate date = t.getDate();
		Availability avl = train.getAvailability().get(date);
		List<Integer> berthSeat =  avl.getSeatBerthMapping().get(t.getBerthAlloted());
		berthSeat.add(t.getSeatNumber());
		berthTicket.remove(i);
		if(racTicket.size()>0) {
						
			t = racTicket.poll();
			
			bookTicketforCancelledTicket(t);

			
			if(waitingList.size()>0) {

				t = waitingList.poll();
				bookTicketforCancelledTicket(t);
			}
			
			
			
		}
		
		
		return true;
	}
	
	private static void bookTicketforCancelledTicket(Ticket t) {
		Train train  = t.getTrain();
		LocalDate date = t.getDate();
		Availability avl = train.getAvailability().get(date);
		List<Integer> berthSeat =  avl.getSeatBerthMapping().get(t.getBerthAlloted());
		berthSeat.add(t.getSeatNumber());
		
		
		bookTicket(t.getPassenger(), train, date);
	}
	
}
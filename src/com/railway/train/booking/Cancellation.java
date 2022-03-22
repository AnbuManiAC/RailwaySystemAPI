package com.railway.train.booking;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.railway.db.TicketTable;
import com.railway.db.UserTable;
import com.railway.train.Availability;
import com.railway.train.Train;

public class Cancellation implements Cancellable {

	public static boolean cancelTicket(String pnr) {
		
		TicketTable tickets = TicketTable.getInstance();
		UserTable users = UserTable.getInstance();
		
		Ticket t = tickets.getTicketfromPnr(pnr);
		
		if(users.getUserAccessMapping().contains(t)) {
			List<Ticket> berthTicket = tickets.getBerthTicket();
			LinkedList<Ticket> racTicket = tickets.getRacTicket();
			LinkedList<Ticket> waitingList = tickets.getWaitingList();
			
			for(int i=0;i<berthTicket.size();i++) {
				if(berthTicket.get(i).getId().equals(pnr)) {
					return cancelBerthTicket(i,berthTicket,racTicket,waitingList);
				}
			}
			for(int i=0;i<racTicket.size();i++) {
				if(racTicket.get(i).getId().equals(pnr)) {
					return cancelRacTicket(i,racTicket,waitingList);
				}
			}
			for(int i=0;i<waitingList.size();i++) {
				if(waitingList.get(i).getId().equals(pnr)) {
					return cancelWaitingList(i,waitingList);
				}
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
		cancelUserTicket(t);
		t = null;
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
		cancelUserTicket(t);
		t = null;
		racTicket.remove(i);
		
		if(waitingList.size()>0) {

			t = waitingList.get(0);
			cancelWaitingList(0, waitingList);
			
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
		cancelUserTicket(t);
		t = null;
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
		cancelUserTicket(t);
		Booking.bookTicket(t.getPassenger(), t.getTrain(), t.getDate());
		t = null;
	}
	private static void cancelUserTicket(Ticket ticket) {
		UserTable users = UserTable.getInstance();
		users.getUserAccessMapping().remove(ticket);
	}
	
}

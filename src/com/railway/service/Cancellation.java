package com.railway.service;

import java.time.LocalDate;
import java.util.List;

import com.railway.db.TicketTable;
import com.railway.db.UserTable;
import com.railway.model.Availability;
import com.railway.model.Ticket;
import com.railway.model.Train;

public class Cancellation implements Cancellable {
	
	private Booking booking = new Booking();

	public boolean cancelTicket(String pnr) {
		
		TicketTable tickets = TicketTable.getInstance();
		UserTable users = UserTable.getInstance();
		
		Ticket t = tickets.getTicketfromPnr(pnr);
		
		if(users.getUserAccessMapping().contains(t)) {
			List<Ticket> berthTicket = tickets.getBerthTicket();
			List<Ticket> racTicket = tickets.getRacTicket();
			List<Ticket> waitingList = tickets.getWaitingList();
			
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

	private boolean cancelWaitingList(int i, List<Ticket> waitingList) {

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

	private boolean cancelRacTicket(int i, List<Ticket> racTicket, List<Ticket> waitingList) {
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

	private boolean cancelBerthTicket(int i, List<Ticket> berthTicket, List<Ticket> racTicket, List<Ticket> waitingList) {
		
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
						
			t = racTicket.get(0);
			racTicket.remove(0);
			bookTicketforCancelledTicket(t);

			if(waitingList.size()>0) {

				t = waitingList.get(0);
				waitingList.remove(0);
				bookTicketforCancelledTicket(t);

			}
			
			
			
		}
		
		
		return true;
	}
	
	private void bookTicketforCancelledTicket(Ticket t) {
		Train train  = t.getTrain();
		LocalDate date = t.getDate();
		Availability avl = train.getAvailability().get(date);
		List<Integer> berthSeat =  avl.getSeatBerthMapping().get(t.getBerthAlloted());
		berthSeat.add(t.getSeatNumber());
		cancelUserTicket(t);
		booking.bookTicket(t.getPassenger(), t.getTrain(), t.getDate());
		t = null;
	}
	private static void cancelUserTicket(Ticket ticket) {
		UserTable users = UserTable.getInstance();
		users.removeUserAccessMapping(ticket);;
	}
	
}

package com.railway.train.booking;

import java.time.LocalDate;
import com.railway.train.Train;

public interface Book {
	static Ticket bookTicket(Passenger passenger, Train train, LocalDate date) {
		return new Ticket();
	}
}

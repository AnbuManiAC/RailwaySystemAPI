package com.railway.service;

import java.time.LocalDate;

import com.railway.model.Passenger;
import com.railway.model.Ticket;
import com.railway.model.Train;

public interface Book {
	Ticket bookTicket(Passenger passenger, Train train, LocalDate date);
}

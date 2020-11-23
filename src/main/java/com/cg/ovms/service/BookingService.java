package com.cg.ovms.service;

import java.util.List;

import com.cg.ovms.dto.Booking;
import com.cg.ovms.dto.Customer;

public interface BookingService {

	public Booking addBooking(Booking booking);
	public List<Booking> cancelBooking(Integer bookingId);
	public Booking updateBooking(Booking booking);
	public Booking viewBooking(Integer bookingId);
	public List<Booking> viewAllBooking(Customer customer);
	public List<Booking> viewAllBookingByDate(String bookingDate);

}

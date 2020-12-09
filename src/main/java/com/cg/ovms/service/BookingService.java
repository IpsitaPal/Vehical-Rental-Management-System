package com.cg.ovms.service;

import java.util.List;

import com.cg.ovms.dto.Booking;

public interface BookingService {

	public Booking addBooking(Booking booking);
	public List<Booking> cancelBooking(Integer bookingId);
	public Booking updateBooking(Booking booking);
	public List<Booking> viewAllBookings();
	public Booking viewBooking(Integer bookingId);
	public List<Booking> viewAllBooking(Integer customerId);
	public List<Booking> viewAllBookingByDate(String bookingDate);
	public double calculatePaymentPerDay(Booking booking);
	
}

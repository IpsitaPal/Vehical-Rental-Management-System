package com.cg.ovms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ovms.dto.Booking;
import com.cg.ovms.service.BookingService;

@CrossOrigin
@RestController
@RequestMapping(value="/ovms")
public class BookingController {
	 
	private static final Logger log = LogManager.getLogger(BookingController.class);
	
	@Autowired
	private BookingService bookingService;
	
	//adding a new Booking
	@PostMapping("/booking")
	public ResponseEntity<Booking> addBooking(@Valid @RequestBody Booking booking){
		log.info("addBooking-contoller-started");
		Booking returnBooking = bookingService.addBooking(booking);
		log.info("addBooking-contoller-ended");
		return new ResponseEntity<Booking>(returnBooking, HttpStatus.OK);
		
	}
	
	//canceling a Booking
	@DeleteMapping("/booking/{bookingId}")
	public ResponseEntity<List<Booking>> cancelBooking(@Valid @PathVariable("bookingId")Integer bookingId){
		log.info("cancelBooking-contoller-started");
		List<Booking> allBooking = bookingService.cancelBooking(bookingId);
		log.info("cancelBooking-contoller-ended");
		return new ResponseEntity<List<Booking>>(allBooking, HttpStatus.OK);
	}
	
	//Updating a booking
	@PutMapping("/booking")
	public ResponseEntity<Booking> updateBooking(@Valid @RequestBody Booking booking){
		log.info("updateBooking-contoller-started");
		Booking returnBooking= bookingService.updateBooking(booking);
		log.info("updateBooking-contoller-ended");
		return new ResponseEntity<Booking>(returnBooking, HttpStatus.OK);
	}
	
	//view a Booking
	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<Booking> viewBooking(@Valid @PathVariable("bookingId")Integer bookingId){
		log.info("viewBooking-contoller-started");
		Booking returnBooking= bookingService.viewBooking(bookingId);
		log.info("viewBooking-contoller-ended");
		return new ResponseEntity<Booking>(returnBooking, HttpStatus.OK);
	}

	//view all bookings by Customer
	@GetMapping("/bookingsby/{customerId}")
	public ResponseEntity<List<Booking>> viewAllBooking(@Valid @PathVariable("customerId")Integer customerId){
		log.info("viewAllBooking-contoller-started");
		List<Booking> allBooking = bookingService.viewAllBooking(customerId);
		log.info("viewAllBooking-contoller-ended");
		return new ResponseEntity<List<Booking>>(allBooking, HttpStatus.OK);
	}
	
	//view all bookings by booking date
	@GetMapping("/bookings/{bookingDate}")
	public ResponseEntity<List<Booking>> viewAllBookingByDate(@Valid @PathVariable("bookingDate") String bookingDate){
		log.info("viewAllBookingByDate-contoller-started");
		List<Booking> allBooking = bookingService.viewAllBookingByDate(bookingDate);
		log.info("viewAllBookingByDate-contoller-ended");
		return new ResponseEntity<List<Booking>>(allBooking, HttpStatus.OK);
	}
	
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> viewAllBookings(){
		log.info("viewAllBookings-contoller-started");
		List<Booking> allBooking = bookingService.viewAllBookings();
		log.info("viewAllBookings-contoller-ended");
		return new ResponseEntity<List<Booking>>(allBooking, HttpStatus.OK);
	}
	
	
}
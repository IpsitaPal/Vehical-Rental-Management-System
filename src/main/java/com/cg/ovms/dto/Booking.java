package com.cg.ovms.dto;

import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

public class Booking {
	
	private int bookingId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bookingDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bookedTillDate;
	
	@NotBlank(message = "bookingDescription is mandatory")
	private String bookingDescription;
	
	@DecimalMin(value = "50.00", message = "totalCost should be min of 50.00")
	private double totalCost;
	
	@DecimalMin(value = "5.00",message = "distance should be min of 5.00kms")
	private double distance;
	
	private Customer customer;
	private Vehicle vehicle;
	
	public Booking() {
		
	}
	
	public Booking(int bookingId, Customer customer, Vehicle vehicle, LocalDate bookingDate, LocalDate bookedTillDate,
			String bookingDescription, double totalCost, double distance) {
		super();
		this.bookingId = bookingId;
		this.customer = customer;
		this.vehicle = vehicle;
		this.bookingDate = bookingDate;
		this.bookedTillDate = bookedTillDate;
		this.bookingDescription = bookingDescription;
		this.totalCost = totalCost;
		this.distance = distance;
	}
	
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getBookingId() {
		return bookingId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	public LocalDate getBookedTillDate() {
		return bookedTillDate;
	}
	public void setBookedTillDate(LocalDate bookedTillDate) {
		this.bookedTillDate = bookedTillDate;
	}
	public String getBookingDescription() {
		return bookingDescription;
	}
	public void setBookingDescription(String bookingDescription) {
		this.bookingDescription = bookingDescription;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookingDate=" + bookingDate + ", bookedTillDate=" + bookedTillDate
				+ ", bookingDescription=" + bookingDescription + ", totalCost=" + totalCost + ", distance=" + distance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookedTillDate == null) ? 0 : bookedTillDate.hashCode());
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + ((bookingDescription == null) ? 0 : bookingDescription.hashCode());
		result = prime * result + bookingId;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (bookedTillDate == null) {
			if (other.bookedTillDate != null)
				return false;
		} else if (!bookedTillDate.equals(other.bookedTillDate))
			return false;
		if (bookingDate == null) {
			if (other.bookingDate != null)
				return false;
		} else if (!bookingDate.equals(other.bookingDate))
			return false;
		if (bookingDescription == null) {
			if (other.bookingDescription != null)
				return false;
		} else if (!bookingDescription.equals(other.bookingDescription))
			return false;
		if (bookingId != other.bookingId)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
	
}

package com.cg.ovms.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

public class Payment {

	private int paymentId;
	
	@NotBlank(message = "Payment Mode cannot be blank")
	private String paymentMode;
	
	@NotNull(message = "Payment Date cannot be null")
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//basic date regex - ^\d{4}-\d{2}-\d{2}$ 
	//advanced date regex - ((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date paymentDate;
	
	@NotNull(message = "Booking cannot be null")
	private Booking booking;
	
	@NotBlank(message = "Payment Status cannot be blank")
	private String paymentStatus;
	
	public Payment() {
		super();
	}
		
	public Payment(int paymentId, String paymentMode, Date paymentDate, Booking booking, String paymentStatus) {
		super();
		this.paymentId = paymentId;
		this.paymentMode = paymentMode;
		this.paymentDate = paymentDate;
		this.booking = booking;
		this.paymentStatus = paymentStatus;
	}

	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
}

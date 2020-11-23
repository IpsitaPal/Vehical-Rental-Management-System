package com.cg.ovms.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

public class Payment {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking == null) ? 0 : booking.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + paymentId;
		result = prime * result + ((paymentMode == null) ? 0 : paymentMode.hashCode());
		result = prime * result + ((paymentStatus == null) ? 0 : paymentStatus.hashCode());
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
		Payment other = (Payment) obj;
		if (booking == null) {
			if (other.booking != null)
				return false;
		} else if (!booking.equals(other.booking))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (paymentId != other.paymentId)
			return false;
		if (paymentMode == null) {
			if (other.paymentMode != null)
				return false;
		} else if (!paymentMode.equals(other.paymentMode))
			return false;
		if (paymentStatus == null) {
			if (other.paymentStatus != null)
				return false;
		} else if (!paymentStatus.equals(other.paymentStatus))
			return false;
		return true;
	}
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

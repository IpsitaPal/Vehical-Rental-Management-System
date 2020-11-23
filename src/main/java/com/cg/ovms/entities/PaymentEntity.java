package com.cg.ovms.entities;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PAYMENT")
public class PaymentEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="PAYMENT_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int paymentId;
	
    @Column(name="PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name="PAYMENT_DATE")
	private Date paymentDate;
	
	@Column(name="PAYMENT_STATUS")
	private String paymentStatus;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="BOOKING_ID")
	private BookingEntity booking;

	public PaymentEntity() {
		
	}
	
	public PaymentEntity(int paymentId, String paymentMode, Date paymentDate, BookingEntity booking, String paymentStatus) {
		super();
		this.paymentId = paymentId;
		this.paymentMode = paymentMode;
		this.paymentDate = paymentDate;
		this.booking = booking;
		this.paymentStatus = paymentStatus;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
	public Integer getPaymentId() {
		return paymentId;
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

	public BookingEntity getBooking() {
		return booking;
	}

	public void setBooking(BookingEntity booking) {
		this.booking = booking;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "PaymentEntity [paymentId=" + paymentId + ", paymentMode=" + paymentMode + ", paymentDate=" + paymentDate
				+ ", paymentStatus=" + paymentStatus + ", booking=" + booking + "]";
	}

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
		PaymentEntity other = (PaymentEntity) obj;
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
	
	
	
}

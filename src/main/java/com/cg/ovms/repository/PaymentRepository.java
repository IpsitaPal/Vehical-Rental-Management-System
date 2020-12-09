package com.cg.ovms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.ovms.entities.BookingEntity;
import com.cg.ovms.entities.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

	public PaymentEntity findByBooking(BookingEntity bookingEntity);			//(@Param("findByBooking") Booking booking);
	
	@Query("SELECT payment FROM PaymentEntity payment  WHERE payment.paymentDate BETWEEN :date1 AND :date2")
	public List<PaymentEntity> getAllBetweenTwoDates(@Param("date1")Date startDate, @Param("date2") Date tillDate);

	@Query("SELECT payment FROM PaymentEntity payment  WHERE payment.booking.customer.id = :cust_id")
	public List<PaymentEntity> getPaymentByCustomer(@Param("cust_id")int customerID);
	
	@Query("SELECT payment FROM PaymentEntity payment  WHERE payment.booking.id = :booking_id")
	public PaymentEntity findByBookingId(@Param("booking_id") int bookingId);
}

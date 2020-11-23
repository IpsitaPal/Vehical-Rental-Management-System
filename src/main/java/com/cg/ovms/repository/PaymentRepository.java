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
	
	//public PaymentEntity findByPaymentMode(String paymentMode);
	
	@Query("SELECT payment FROM PaymentEntity payment  WHERE payment.paymentDate BETWEEN :date1 AND :date2")
	public List<PaymentEntity> getAllBetweenTwoDates(@Param("date1")Date startDate, @Param("date2") Date tillDate);

	//@SuppressWarnings("unchecked")
	//public PaymentEntity saveAndFlush(PaymentEntity paymentEntity);
	
	
}

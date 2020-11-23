package com.cg.ovms.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.ovms.entities.BookingEntity;
import com.cg.ovms.entities.CustomerEntity;
//import com.cg.ovms.entities.CustomerEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity,Integer> {

	//@Query("SELECT booking FROM BookingEntity booking WHERE booking.booking_date=:byDate")
	public List<BookingEntity> findByBookingDate(@Param("byDate") LocalDate bookingDate);
	
	//SELECT booking FROM BookingEntity booking WHERE booking.customer IN (select customerId from CustomerEntity)
	@Query("SELECT booking FROM BookingEntity booking WHERE booking.customer =:pCustId")
	public List<BookingEntity> getByCustomer(@Param("pCustId") CustomerEntity customer);
	
	//public BookingEntity addBooking(BookingEntity booking);
	//public BookingEntity cancelBooking(BookingEntity booking);
	//public BookingEntity updateBooking(BookingEntity booking);
	//public BookingEntity viewBooking(BookingEntity booking);
	//public List<BookingEntity> viewAllBooking(CustomerEntity customer);
	//public List<BookingEntity> viewAllBookingByDate(LocalDate bookingDate);

}

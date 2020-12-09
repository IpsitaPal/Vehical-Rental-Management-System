package com.cg.ovms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.ovms.dto.Booking;
import com.cg.ovms.dto.Customer;
import com.cg.ovms.dto.Driver;
import com.cg.ovms.dto.Payment;
import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.BookingEntity;
import com.cg.ovms.entities.CustomerEntity;
import com.cg.ovms.entities.DriverEntity;
import com.cg.ovms.entities.PaymentEntity;
import com.cg.ovms.entities.VehicleEntity;
import com.cg.ovms.exception.DatabaseException;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.repository.PaymentRepository;
import com.cg.ovms.service.implementation.PaymentServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PaymentServiceImplTest {
	
	@MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentServiceImpl paymentService;
    
    //Creating Payment Object
    public Payment createPayment() {
    		Driver driver = new Driver(1, "John", "Doe", "9876543210", "john@doe", "Chennai", 50, "DH123");
    		Vehicle vehicle = new Vehicle(1, driver, "TN23KA9876", "AC", "Car", "White", "Chennai", "25", 10, 25);
    		Customer customer = new Customer(1, "Richa", "D'souza", "9823874576", "ri@cha", "Chennai");
    		Booking booking = new Booking(1, customer, vehicle, LocalDate.now(), LocalDate.now(), "Short Trip", 500, 20);
    		Payment payment = new Payment(1, "Cash", new Date(), booking, "Paid");
    		return payment;
    }
   
    //Creates List of Payment Entities
    @SuppressWarnings("deprecation")
	public List<PaymentEntity> createPaymentList() {
    	List<PaymentEntity> paymentEntityList = new ArrayList<PaymentEntity>();
    	
    	Date date = new Date();
    	DriverEntity driverEntity = new DriverEntity(1,"John", "Doe", "9876543210", "john@doe", "Chennai", 50, "DH123");
    	VehicleEntity vehicleEntity = new VehicleEntity(1, driverEntity, "TN23KA9876", "AC", "Car", "White", "Chennai", "25", 10, 25);
    	CustomerEntity customerEntity = new CustomerEntity(1, "Richa", "D'souza", "9823874576", "ri@cha", "Chennai");
    	
    	date.setDate(11);
    	date.setMonth(11);
    	date.setYear(2020);								//11th November 2020
    	BookingEntity firstBookingEntity = new BookingEntity(1, customerEntity, vehicleEntity, LocalDate.now(), LocalDate.now(), "Short Trip", 500, 20);
    	PaymentEntity firstPaymentEntity = new PaymentEntity(1, "Cash", date, firstBookingEntity, "Paid");

    	date.setDate(8);
    	date.setMonth(9);
    	date.setYear(2020);								//8th September 2020
    	BookingEntity secondBookingEntity = new BookingEntity(2, customerEntity, vehicleEntity, LocalDate.now(), LocalDate.now(), "Long Trip", 1000, 20);
    	PaymentEntity secondPaymentEntity = new PaymentEntity(2, "Credit", date, secondBookingEntity, "Paid");

    	date.setDate(11);
    	date.setMonth(10);
    	date.setYear(2020);								//11th October 2020
    	BookingEntity thirdBookingEntity = new BookingEntity(3, customerEntity, vehicleEntity, LocalDate.now(), LocalDate.now(), "Short Trip", 300, 10);
    	PaymentEntity thirdPaymentEntity = new PaymentEntity(3, "Cash", date, thirdBookingEntity, "Paid");
    	
    	paymentEntityList.add(firstPaymentEntity);
    	paymentEntityList.add(secondPaymentEntity);
    	paymentEntityList.add(thirdPaymentEntity);
    	
    	return paymentEntityList;
    }
    
    //Checks for error if null is sent as parameter to Add Payment method
    @Test
    public void nullAddPayment() {
    	assertThrows(DatabaseException.class, () -> paymentService.addPayment(null), "Payment can't be Null");
    }

    //Checks for error if null is sent as parameter to Update Payment method
    @Test
    public void nullUpdatePayment() {
    	assertThrows(DatabaseException.class, () -> paymentService.updatePayment(null), "PaymentId can't be Null");
    }
    
    //Checks for error if 0 is sent as parameter to Cancel Payment method
    @Test
    public void nullCancelPayment() {
    	assertThrows(RecordNotFoundException.class, () -> paymentService.cancelPayment(0), "No Data Found");
    }
    
    //Checks for error if null is sent as parameter to viewPaymentByBooking method
    @Test
	public void checkNullViewPaymentByBooking() {	
		assertThrows(DatabaseException.class, () -> paymentService.viewPaymentByBooking(null), "BookingId can't be Null");
    }
    
    //Checks for error if null is sent as parameter to calculateMonthlyRevenue method
    @Test
	public void checkNullCalculateMonthlyRevenue() {	
    	assertThrows(DatabaseException.class, () -> paymentService.calculateMonthlyRevenue(null, null), "Date can't be Null");
    }
    
    //Checks Add method when proper data is sent
    @Test
    public void checkProperAdd() {    
    	Payment payment = createPayment();   
    	payment.getBooking().setBookingId(2);
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	PaymentEntity returnPaymentEntity = paymentEntity;
    	
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	Mockito.when(paymentRepository.saveAndFlush(paymentEntity)).thenReturn(returnPaymentEntity);
    	Payment returnPayment = paymentService.addPayment(payment);
    	assertEquals(payment, returnPayment);
    }
    
    //Checks for error when booking already exists 
    @Test
    public void checkBookingExistsAdd() {					
    	Payment payment = createPayment();   
    	payment.setPaymentId(0);
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(paymentEntity);
    	assertThrows(DuplicateRecordException.class, () -> paymentService.addPayment(payment), 
    			"Payment already exists for this booking. Please update payment.");
    }
    
    //Checks for error in case payment is not added to database
    @Test
    public void checkPaymentNotAdded() {		
    	Payment payment = createPayment();   
    	payment.setPaymentId(0);
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
   
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	Mockito.when(paymentRepository.saveAndFlush(paymentEntity)).thenReturn(null);
    	assertThrows(DatabaseException.class, () -> paymentService.addPayment(payment), "Payment Not Added");
    }
    
    //Checks Update method for proper input 
    @Test
    public void checkProperUpdate() {
    	Payment payment = createPayment(); 
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	
    	Mockito.when(paymentRepository.save(paymentEntity)).thenReturn(paymentEntity);
    	Payment returnPayment = paymentService.updatePayment(payment);
    	payment = paymentService.convertEntityToDTO(paymentEntity);
    	
    	assertEquals(returnPayment, payment);
    }
    
    //Checks for error if data is not updated in database
    @Test
    public void checkPaymentNotUpdated() {
    	Payment payment = createPayment(); 
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	
    	Mockito.when(paymentRepository.save(paymentEntity)).thenReturn(null);
    	assertThrows(DatabaseException.class, () -> paymentService.updatePayment(payment), "Payment Not Updated");
    }
    
    //Checks Cancel Payment method for proper data
    @Test
    public void checkProperDelete() {
    	Payment payment = createPayment(); 
    	int paymentId = 1;
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	
    	Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paymentEntity));
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(paymentEntity);
    	Mockito.when(paymentRepository.save(paymentEntity)).thenReturn(paymentEntity);
		payment = paymentService.cancelPayment(1);
		assertEquals("Cancelled", payment.getPaymentStatus());
    }
    
    //Checks for error if invalid Id is sent to Cancel payment method
    @Test
    public void checkRecordDoesNotExistDelete() {
    	int paymentId = 1;
    	Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.ofNullable(null));
    	assertThrows(RecordNotFoundException.class, () -> paymentService.cancelPayment(paymentId), 
    			"Payment doesn't exist in database. Add payment first.");
    }
     
    //Checks for error in cancel payment when Booking does not exist
    @Test
    public void checkBookingDoesNotExistDelete() {
    	Payment payment = createPayment(); 
    	int paymentId = 1;
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	
    	Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paymentEntity));
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	assertThrows(RecordNotFoundException.class, () -> paymentService.cancelPayment(paymentId), "Data Not Found");
    }
    
    //Checks for data if Records exist in database
    @Test
    public void checkProperViewAllPayments() {
    	List<PaymentEntity> paymentEntityList = createPaymentList();
    	
    	Mockito.when(paymentRepository.findAll()).thenReturn(paymentEntityList);
    	List<Payment> paymentList = paymentService.viewAllPayments();
    	assertEquals(3, paymentList.size());
    }
    
    //Checks for error if no records exist in database
    @Test						
	public void checkViewAllPaymentNoRecord() {	
    	Mockito.when(paymentRepository.findAll()).thenReturn(new ArrayList<PaymentEntity>());
		assertThrows(RecordNotFoundException.class, () -> paymentService.viewAllPayments(), "No Data Found");
    }
     
    //Checks for view payment if payment doesn't exist
    @Test
    public void checkViewPaymentByNonExistentBooking() {
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(createPayment()); 
    	
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	assertThrows(RecordNotFoundException.class, () -> paymentService.viewPaymentByBooking(paymentEntity.getBooking().getBookingId()), "No Data Found");
    }
    
    //Checks for calculateMonthlyRevenue method with valid data dates as input
    @SuppressWarnings("deprecation")
	@Test
    public void checkProperCalculateMonthlyRevenue() {
    	Date fromDate = new Date();
    	Date tillDate = new Date();
    	
    	fromDate.setDate(1);
    	fromDate.setMonth(9);
    	fromDate.setYear(2020);	
    	
    	tillDate.setDate(30);
    	tillDate.setMonth(11);
    	tillDate.setYear(2020);	
    	
    	Mockito.when(paymentRepository.getAllBetweenTwoDates(fromDate, tillDate)).thenReturn(createPaymentList());
    	double sum = paymentService.calculateMonthlyRevenue(fromDate, tillDate);
    	assertEquals(1800, sum);
    }
    
    //Checks for calculateMonthlyRevenue method for dates with no data in database
    @Test
	public void checkNoDataCalculateMonthlyRevenue() {	
    	assertThrows(RecordNotFoundException.class, () -> paymentService.calculateMonthlyRevenue(new Date(), new Date()), "No Records Found");
    }
    
}


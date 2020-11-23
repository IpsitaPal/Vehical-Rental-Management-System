package com.cg.ovms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.ovms.dto.Booking;
import com.cg.ovms.dto.Customer;
import com.cg.ovms.dto.Driver;
import com.cg.ovms.dto.Payment;
import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.PaymentEntity;
import com.cg.ovms.exception.DatabaseException;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.repository.PaymentRepository;
import com.cg.ovms.service.implementation.PaymentServiceImpl;

//@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PaymentServiceImplTest {
	
	@Mock
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentServiceImpl paymentService;
    
    Driver driver = new Driver(1, "John", "Doe", "9876543210", "john@doe", "Chennai", 50, "DH123");
    Vehicle vehicle = new Vehicle(1, driver, "TN23KA9876", "AC", "Car", "White", "Chennai", "25", 10, 25);
    Customer customer = new Customer(1, "Richa", "D'souza", "9823874576", "ri@cha", "Chennai");
    Booking booking = new Booking(1, customer, vehicle, LocalDate.now(), LocalDate.now(), "Short Trip", 500, 20);
    Payment payment = new Payment(1, "Cash", new Date(), booking, "Paid");
    
    @Test
    public void nullAddPayment() {
    	assertThrows(NullPointerException.class, () -> paymentService.addPayment(null), "Payment can't be Null");
    }
    
    @Test
    public void nullUpdatePayment() {
    	assertThrows(NullPointerException.class, () -> paymentService.updatePayment(null), "PaymentId can't be Null");
    }
    
    @Test
    public void nullCancelPayment() {
    	assertThrows(RecordNotFoundException.class, () -> paymentService.cancelPayment(0), "No Data Found");
    }
    
    @Test						
	public void checkViewAllPaymentNoRecord() {	
    	
    	Mockito.when(paymentRepository.findAll()).thenReturn(new ArrayList<PaymentEntity>());
		assertThrows(RecordNotFoundException.class, () -> paymentService.viewAllPayments(), "No Data Found");
    }
    
    @Test
	public void checkNullViewPaymentByBooking() {	
		assertThrows(RecordNotFoundException.class, () -> paymentService.viewPaymentByBooking(null), "No Data Found");
    }
    
    @Test
	public void checkNullCalculateMonthlyRevenue() {	
		assertEquals(paymentService.calculateMonthlyRevenue(new Date(), new Date()), 0);
    }
    /*
    @Test
    public void checkProperAdd() {
    	   
    	payment.setPaymentId(0);
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	PaymentEntity returnPaymentEntity = paymentEntity;
    	returnPaymentEntity.setPaymentId(1);
    	
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	
    	Mockito.when(paymentRepository.saveAndFlush(paymentEntity)).thenReturn(returnPaymentEntity);
    	System.out.println(returnPaymentEntity.toString());
    	
    	Payment returnPayment = paymentService.addPayment(payment);
    	
    	System.out.println("1");
    	assertEquals(payment, returnPayment);
    }
    
    @Test
    public void checkBookingExistsAdd() {
    	   
    	payment.setPaymentId(0);
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	PaymentEntity returnPaymentEntity = paymentEntity;
    	returnPaymentEntity.setPaymentId(1);
    	
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(returnPaymentEntity);
    	assertThrows(DuplicateRecordException.class, () -> paymentService.addPayment(payment), 
    			"Payment already exists for this booking. Please update payment.");
    }
    
    @Test
    public void checkProperAdd() {
    	   
    	payment.setPaymentId(0);
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
   
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	Mockito.when(paymentRepository.saveAndFlush(paymentEntity)).thenReturn(paymentEntity);
    	assertThrows(DatabaseException.class, () -> paymentService.addPayment(payment), "Payment Not Added");
    }
    
    @Test
    public void checkProperUpdate() {
    	
    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	PaymentEntity returnPaymentEntity = paymentEntity;
    	returnPaymentEntity.setPaymentMode("Credit");
    	returnPaymentEntity.setPaymentStatus("Not Paid");
    	
    	Mockito.when(paymentRepository.save(paymentEntity)).thenReturn(returnPaymentEntity);
    	Payment returnPayment = paymentService.updatePayment(payment);
    	assertEquals(returnPayment, paymentService.convertEntityToDTO(returnPaymentEntity));
    }*/
    																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																							
}

//public Payment addPayment(Payment payment) throws DuplicateRecordException;
//public Payment updatePayment(Payment payment);
//public Payment cancelPayment(int paymentId) throws RecordNotFoundException;
//public List<Payment> viewAllPayments();
//public Payment viewPaymentByBooking(Integer bookingId) throws RecordNotFoundException;
//public double calculateMonthlyRevenue(Date date1, Date date2);


    /*
    @Test
    public void properAdd() {
    	   
        Driver driver = new Driver(1, "John", "Doe", "9876543210", "john@doe", "Chennai", 50, "DH123");
        Vehicle vehicle = new Vehicle(1, driver, "TN23KA9876", "AC", "Car", "White", "Chennai", "25", 10, 25);
        Customer customer = new Customer(1, "Richa", "D'souza", "9823874576", "ri@cha", "Chennai");
        Booking booking = new Booking(1, customer, vehicle, LocalDate.now(), LocalDate.now(), "Short Trip", 500, 20);
        Payment payment = new Payment(0, "Cash", new Date(), booking, "Paid");

    	PaymentEntity paymentEntity = paymentService.convertDTOtoEntity(payment);
    	PaymentEntity returnPaymentEntity = paymentEntity;
    	returnPaymentEntity.setPaymentId(1);
    	payment.setPaymentId(1);
    	
    	Mockito.when(paymentRepository.findByBooking(paymentEntity.getBooking())).thenReturn(null);
    	System.out.println("5");
    	Mockito.when(paymentRepository.saveAndFlush(paymentEntity)).thenReturn(returnPaymentEntity);
    	System.out.println("3");
    	System.out.println(returnPaymentEntity.toString());
    	Payment returnPayment = paymentService.addPayment(payment);
    	System.out.println("1");
    	assertEquals(payment, returnPayment);
    }*/

//public Payment addPayment(Payment payment) throws DuplicateRecordException;
//public Payment updatePayment(Payment payment);
//public Payment cancelPayment(int paymentId) throws RecordNotFoundException;
//public List<Payment> viewAllPayments();
//public Payment viewPaymentByBooking(Integer bookingId) throws RecordNotFoundException;
//public double calculateMonthlyRevenue(Date date1, Date date2);

//null, proper, duplicate

//Driver(int driverId, String firstName, String lastName, String contactNumber, String email, String address, double chargesPerDay, String licenseNo)    
//Vehicle(int vehicleId, Driver driver, String vehicleNumber, String type, String category, String description, String location, String capacity, double chargesPerKM, double fixedCharges) {
//Customer(int customerId, String firstName, String lastName, String mobileNumber, String emailId, String address)
//Booking(int bookingId, Customer customer, Vehicle vehicle, LocalDate bookingDate, LocalDate bookedTillDate, String bookingDescription, double totalCost, double distance) 
//Payment(int paymentId, String paymentMode, Date paymentDate, Booking booking, String paymentStatus)      	

/*  DriverEntity driverEntity = new DriverEntity(1,"John", "Doe", "9876543210", "john@doe", "Chennai", 50, "DH123");
VehicleEntity vehicleEntity = new VehicleEntity(1, driverEntity, "TN23KA9876", "AC", "Car", "White", "Chennai", "25", 10, 25);
CustomerEntity customerEntity = new CustomerEntity(1, "Richa", "D'souza", "9823874576", "ri@cha", "Chennai");
BookingEntity bookingEntity = new BookingEntity(1, customerEntity, vehicleEntity, LocalDate.now(), LocalDate.now(), "Short Trip", 500, 20);
PaymentEntity paymentEntity = new PaymentEntity(1, "Cash", new Date(), bookingEntity, "Paid");
*/

/* 	try {
Mockito.when(paymentService.viewAllPayments()).thenThrow(new RecordNotFoundException("No Data Found"));
} catch (RecordNotFoundException dbException) {
assertEquals(dbException.getMessage(), "No Data Found");
}*/

/*@Before
public void setUp() {
	Mockito.mock(PaymentRepository.class);
}*/
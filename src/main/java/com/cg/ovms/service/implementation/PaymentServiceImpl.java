package com.cg.ovms.service.implementation;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cg.ovms.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	static Logger log = Logger.getLogger(PaymentServiceImpl.class.getName());
	
	@Autowired
	private PaymentRepository paymentDAO;
	
	
	//If payment with same bookingId exists, throws duplicate record error. Else, saves data to database. 
	//If data is not saved in database, throws database exception. Else, returns data back to controller layer
	@Override
	public Payment addPayment(Payment paymentDTO) throws DuplicateRecordException, DatabaseException {				
		
		log.info("- addPayment - Entry");
		
		if(paymentDTO == null)
		{
			throw new DatabaseException("Payment can't be Null");
		}
		
		PaymentEntity paymentEntity = convertDTOtoEntity(paymentDTO);
		PaymentEntity checkEntity = paymentDAO.findByBooking(paymentEntity.getBooking());
		if(checkEntity != null) 
		{
			throw new DuplicateRecordException("Payment already exists for this booking. Please update payment.");
		} 
		
		paymentEntity = paymentDAO.saveAndFlush(paymentEntity);
		
		if(paymentEntity == null)
		{
			throw new DatabaseException("Payment Not Added");
		}
		
		paymentDTO = convertEntityToDTO(paymentEntity);
		
		log.info("- addPayment - Exit");
		return paymentDTO;
		
	}

	//If data is not updated in database, throws database exception. Else, returns data back to controller layer
	@Override
	public Payment updatePayment(Payment paymentDTO) {
		
		log.info("updatePayment - Entry");
		
		if(paymentDTO == null)
		{
			throw new DatabaseException("PaymentId can't be Null");
		}
		
		PaymentEntity paymentEntity = convertDTOtoEntity(paymentDTO);
		paymentEntity = paymentDAO.save(paymentEntity);
		
		if(paymentEntity == null)
		{
			throw new DatabaseException("Payment Not Updated");
		}
		
		paymentDTO = convertEntityToDTO(paymentEntity);
	
		log.info("- updatePayment - Exit");
		
		return paymentDTO;
	} 

	//If payment with input Id is not found, throws record not found Exception. 
	//If payment with same bookingId exists, throws duplicate record error. Else, saves data to database. 
	//If data is not updated in database, throws database exception. Else, returns data back to controller layer
	@Override
	public Payment cancelPayment(int paymentId) throws RecordNotFoundException {				
		
		log.info("cancelPayment - Entry");
		
		Optional<PaymentEntity> optionalPaymentEntity = paymentDAO.findById(paymentId);
		if(!optionalPaymentEntity.isPresent())
		{
			throw new RecordNotFoundException("Payment doesn't exist in database. Add payment first.");
		}
			
		PaymentEntity paymentEntity = optionalPaymentEntity.get();
		paymentEntity = paymentDAO.findByBooking(paymentEntity.getBooking());
		
		if(paymentEntity == null)
		{
			throw new RecordNotFoundException("Data Not Found");
		}
		
		paymentEntity.setPaymentStatus("Cancelled");
		paymentEntity.setPaymentMode("None");
		paymentEntity.setPaymentDate(new Date());
			
		paymentEntity = paymentDAO.save(paymentEntity);
		
		if(paymentEntity == null)
		{
			throw new DatabaseException("Payment Not Deleted");
		}
			
		log.info("- cancelPayment - Exit");
			
		return convertEntityToDTO(paymentEntity);
		
	}
	
	//Retrieves all data from database. Throws record not found error if no data found.
	@Override
	public List<Payment> viewAllPayments() {						
	
		log.info("viewAllPayment - Entry");
		
		List<PaymentEntity> paymentEntityList = paymentDAO.findAll();	
		
		if(paymentEntityList.isEmpty()) 
		{
			throw new RecordNotFoundException("No Data Found");
		}
		
		List<Payment> paymentDTOList = new ArrayList<Payment>();
		Payment paymentDTO = null;
		
		for(PaymentEntity paymentEntityItr: paymentEntityList)
		{
			paymentDTO = convertEntityToDTO(paymentEntityItr);
			paymentDTOList.add(paymentDTO);
		}
		
		log.info("- viewAllPayment - Exit");
		
		return paymentDTOList;
	}
		
	//Retrieves single record with input bookingId from database. Throws record not found error if no data found.
	@Override
	public Payment viewPaymentByBooking(int bookingId) throws RecordNotFoundException {
		
		log.info("viewPaymentByBooking - Entry");
		
		PaymentEntity paymentEntity = paymentDAO.findByBookingId(bookingId);
		
		if(paymentEntity == null)
		{
			throw new RecordNotFoundException("Data Not Found");
		}
		
		Payment paymentDTO = convertEntityToDTO(paymentEntity);
	
		log.info("- viewPaymentByBooking - Exit");
		
		return paymentDTO;		
	}
	
	//Retrieves all data from database with paymentDate between the input dates.
	//Throws error if none found
	@Override
	public double calculateMonthlyRevenue(Date date1, Date date2) {		
		
		log.info("calculateMonthlyRevenue - Entry");
		
		if(date1 == null || date2 == null)
		{
			throw new DatabaseException("Date can't be Null");
		}
		
		double sum = 0.0;
		List<PaymentEntity> resultPaymentList = paymentDAO.getAllBetweenTwoDates(date1, date2); 
		
		if(resultPaymentList.isEmpty())
		{
			throw new RecordNotFoundException("No Records Found");
		}
		
		for(PaymentEntity paymentEntityItr: resultPaymentList)			
		{
			sum += paymentEntityItr.getBooking().getTotalCost();
		}
		
		log.info("- calculateMonthlyRevenue - Exit");
		
		return sum;
	}

	//Retrieves all data from database with same customer ID.
	@Override
	public List<Payment> viewPaymentByCustomer(int customerId) throws RecordNotFoundException {
		
		if(customerId == 0)
		{
			throw new DatabaseException("CustomerId can't be Null");
		}
		
		List<PaymentEntity> paymentEntity = paymentDAO.getPaymentByCustomer(customerId);
		
		if(paymentEntity == null)
		{
			throw new RecordNotFoundException("Data Not Found");
		}
		
		List<Payment> paymentDTOList = new ArrayList<Payment>();
		for(PaymentEntity paymentEntityItr: paymentEntity)
		{
			paymentDTOList.add(convertEntityToDTO(paymentEntityItr));
		}
		log.info("- viewPaymentByBooking - Exit");
		
		return paymentDTOList;		
	}
	
	@Override
	public double calculateTotalRevenue() {
		log.info("calculateTotalRevenue - Entry");
		
		double sum = 0.0;
		List<PaymentEntity> resultPaymentList = paymentDAO.findAll();
		
		if(resultPaymentList.isEmpty())
		{
			throw new RecordNotFoundException("No Records Found");
		}
		
		for(PaymentEntity paymentEntityItr: resultPaymentList)			
		{
			if(paymentEntityItr.getPaymentStatus() != "Cancelled")
			{
				log.info(sum);
				sum += paymentEntityItr.getBooking().getTotalCost();
			}
		}
		
		log.info("- calculateTotalRevenue - Exit");
		
		return sum;
	}
	
	//Converts the input DTO Object to an Entity Object
	public PaymentEntity convertDTOtoEntity(Payment paymentDTO) 
	{
		PaymentEntity paymentEntity = new PaymentEntity();
		
		paymentEntity.setPaymentId(paymentDTO.getPaymentId());
		paymentEntity.setPaymentMode(paymentDTO.getPaymentMode());
		paymentEntity.setPaymentDate(paymentDTO.getPaymentDate());
		paymentEntity.setPaymentStatus(paymentDTO.getPaymentStatus());
		
		BookingEntity bookingEntity = new BookingEntity();
		
		bookingEntity.setBookingId(paymentDTO.getBooking().getBookingId());
		bookingEntity.setBookingDate(paymentDTO.getBooking().getBookingDate());
		bookingEntity.setBookedTillDate(paymentDTO.getBooking().getBookedTillDate());
		bookingEntity.setBookingDescription(paymentDTO.getBooking().getBookingDescription());
		bookingEntity.setTotalCost(paymentDTO.getBooking().getTotalCost());
		bookingEntity.setDistance(paymentDTO.getBooking().getDistance());
		
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setCustomerId(paymentDTO.getBooking().getCustomer().getCustomerId());
		customerEntity.setFirstName(paymentDTO.getBooking().getCustomer().getFirstName());
		customerEntity.setLastName(paymentDTO.getBooking().getCustomer().getLastName());
		customerEntity.setMobileNumber(paymentDTO.getBooking().getCustomer().getMobileNumber());
		customerEntity.setEmailId(paymentDTO.getBooking().getCustomer().getEmailId());
		customerEntity.setAddress(paymentDTO.getBooking().getCustomer().getAddress());
		//customerEntity.setListOfBookings(paymentDTO.getBooking().getCustomer().getListOfBookings());
		
		VehicleEntity vehicleEntity = new VehicleEntity();
		
		vehicleEntity.setVehicleId(paymentDTO.getBooking().getVehicle().getVehicleId());
		vehicleEntity.setVehicleNumber(paymentDTO.getBooking().getVehicle().getVehicleNumber());
		vehicleEntity.setType(paymentDTO.getBooking().getVehicle().getType());
		vehicleEntity.setCategory(paymentDTO.getBooking().getVehicle().getCategory());
		vehicleEntity.setDescription(paymentDTO.getBooking().getVehicle().getDescription());
		vehicleEntity.setLocation(paymentDTO.getBooking().getVehicle().getLocation());
		vehicleEntity.setCapacity(paymentDTO.getBooking().getVehicle().getCapacity());
		vehicleEntity.setChargesPerKM(paymentDTO.getBooking().getVehicle().getChargesPerKM());
		vehicleEntity.setFixedCharges(paymentDTO.getBooking().getVehicle().getFixedCharges());
		//vehicleEntity.setListOfBookings(paymentDTO.getBooking().getVehicle().getListOfBookings());
		
		DriverEntity driverEntity = new DriverEntity();
		
		driverEntity.setDriverId(paymentDTO.getBooking().getVehicle().getDriver().getDriverId());
		driverEntity.setFirstName(paymentDTO.getBooking().getVehicle().getDriver().getFirstName());
		driverEntity.setLastName(paymentDTO.getBooking().getVehicle().getDriver().getLastName());
		driverEntity.setEmail(paymentDTO.getBooking().getVehicle().getDriver().getEmail());
		driverEntity.setContactNumber(paymentDTO.getBooking().getVehicle().getDriver().getContactNumber());
		driverEntity.setAddress(paymentDTO.getBooking().getVehicle().getDriver().getAddress());
		driverEntity.setChargesPerDay(paymentDTO.getBooking().getVehicle().getDriver().getChargesPerDay());
		driverEntity.setLicenseNo(paymentDTO.getBooking().getVehicle().getDriver().getLicenseNo());
		
		vehicleEntity.setDriver(driverEntity);
		bookingEntity.setCustomer(customerEntity);
		bookingEntity.setVehicle(vehicleEntity);
		paymentEntity.setBooking(bookingEntity);
		
		return paymentEntity;
	}
	
	//Converts the input Entity Object to DTO Object
	public Payment convertEntityToDTO(PaymentEntity paymentEntity)
	{
		Payment paymentDTO = new Payment();
		
		paymentDTO.setPaymentId(paymentEntity.getPaymentId());
		paymentDTO.setPaymentMode(paymentEntity.getPaymentMode());
		paymentDTO.setPaymentDate(paymentEntity.getPaymentDate());
		paymentDTO.setPaymentStatus(paymentEntity.getPaymentStatus());
		
		Booking bookingDTO = new Booking();
		
		bookingDTO.setBookingId(paymentEntity.getBooking().getBookingId());
		bookingDTO.setBookingDate(paymentEntity.getBooking().getBookingDate());
		bookingDTO.setBookedTillDate(paymentEntity.getBooking().getBookedTillDate());
		bookingDTO.setBookingDescription(paymentEntity.getBooking().getBookingDescription());
		bookingDTO.setTotalCost(paymentEntity.getBooking().getTotalCost());
		bookingDTO.setDistance(paymentEntity.getBooking().getDistance());
		
		Customer customerDTO = new Customer();
		
		customerDTO.setCustomerId(paymentEntity.getBooking().getCustomer().getCustomerId());
		customerDTO.setFirstName(paymentEntity.getBooking().getCustomer().getFirstName());
		customerDTO.setLastName(paymentEntity.getBooking().getCustomer().getLastName());
		customerDTO.setMobileNumber(paymentEntity.getBooking().getCustomer().getMobileNumber());
		customerDTO.setEmailId(paymentEntity.getBooking().getCustomer().getEmailId());
		customerDTO.setAddress(paymentEntity.getBooking().getCustomer().getAddress());
		//customerDTO.setListOfBookings(paymentEntity.getBooking().getCustomer().getListOfBookings());
		
		Vehicle vehicleDTO = new Vehicle();
		
		vehicleDTO.setVehicleId(paymentEntity.getBooking().getVehicle().getVehicleId());
		vehicleDTO.setVehicleNumber(paymentEntity.getBooking().getVehicle().getVehicleNumber());
		vehicleDTO.setType(paymentEntity.getBooking().getVehicle().getType());
		vehicleDTO.setCategory(paymentEntity.getBooking().getVehicle().getCategory());
		vehicleDTO.setDescription(paymentEntity.getBooking().getVehicle().getDescription());
		vehicleDTO.setLocation(paymentEntity.getBooking().getVehicle().getLocation());
		vehicleDTO.setCapacity(paymentEntity.getBooking().getVehicle().getCapacity());
		vehicleDTO.setChargesPerKM(paymentEntity.getBooking().getVehicle().getChargesPerKM());
		vehicleDTO.setFixedCharges(paymentEntity.getBooking().getVehicle().getFixedCharges());
		//vehicleDTO.setListOfBookings(paymentEntity.getBooking().getVehicle().getListOfBookings());
		
		Driver driverDTO = new Driver();
		
		driverDTO.setDriverId(paymentEntity.getBooking().getVehicle().getDriver().getDriverId());
		driverDTO.setFirstName(paymentEntity.getBooking().getVehicle().getDriver().getFirstName());
		driverDTO.setLastName(paymentEntity.getBooking().getVehicle().getDriver().getLastName());
		driverDTO.setEmail(paymentEntity.getBooking().getVehicle().getDriver().getEmail());
		driverDTO.setContactNumber(paymentEntity.getBooking().getVehicle().getDriver().getContactNumber());
		driverDTO.setAddress(paymentEntity.getBooking().getVehicle().getDriver().getAddress());
		driverDTO.setChargesPerDay(paymentEntity.getBooking().getVehicle().getDriver().getChargesPerDay());
		driverDTO.setLicenseNo(paymentEntity.getBooking().getVehicle().getDriver().getLicenseNo());
		
		vehicleDTO.setDriver(driverDTO);
		bookingDTO.setCustomer(customerDTO);
		bookingDTO.setVehicle(vehicleDTO);
		paymentDTO.setBooking(bookingDTO);
		
		return paymentDTO;
	}

	

}
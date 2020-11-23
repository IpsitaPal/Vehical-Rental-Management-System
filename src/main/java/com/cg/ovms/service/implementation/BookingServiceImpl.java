package com.cg.ovms.service.implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ovms.dto.Booking;
import com.cg.ovms.dto.Customer;
import com.cg.ovms.dto.Driver;
import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.BookingEntity;
import com.cg.ovms.entities.CustomerEntity;
import com.cg.ovms.entities.DriverEntity;
import com.cg.ovms.entities.VehicleEntity;
import com.cg.ovms.exception.DatabaseException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.repository.BookingRepository;
import com.cg.ovms.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	static final Logger log = LogManager.getLogger(BookingServiceImpl.class);
	
	@Autowired
	private BookingRepository bookingRepository;
	
	//add booking method 
	//except 0 if already exist duplicate
	@Override
	public Booking addBooking(Booking bookingDTO) {
		log.info("addBooking-service-started");
		BookingEntity bookingEntity = convertDTOtoEntity(bookingDTO);
		
		bookingRepository.saveAndFlush(bookingEntity);
		Optional<BookingEntity> returnBooking = bookingRepository.findById(bookingEntity.getBookingId());
		if(!returnBooking.isPresent()){
			log.error("exception-adding booking in service layer");
			throw new DatabaseException("BookingEntity did not get added"); 
		}
		
		Booking returnBookingDTO = convertEntityToDTO(returnBooking.get());
		log.info("addBooking-service-ended");
		
		return returnBookingDTO;
	}
	
	@Override
	public List<Booking> cancelBooking(Integer bookingId) {
		log.info("cancelBooking-service-started");
		List<Booking> bookingDTOList=new ArrayList<Booking>();	
		
		Optional<BookingEntity> checkBooking =bookingRepository.findById(bookingId);
		if(!checkBooking.isPresent()){
			log.error("exception-cancel booking in service layer");
			throw new RecordNotFoundException("BookingEntity with id " + bookingId + " not found"); 
		}
		
		bookingRepository.deleteById(bookingId);
		List<BookingEntity> bookingEntityList = bookingRepository.findAll();		
		if(bookingEntityList==null ||  bookingEntityList.isEmpty() ) {
			log.warn("no bookings avialble after cancel booking in service layer");
			throw new RecordNotFoundException("No Bookings avaliable");
		}
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("cancelBooking-service-ended");
		return bookingDTOList;
	}
	
	@Override
	public Booking updateBooking(Booking bookingDTO) {
		log.info("updateBooking-service-started");
		BookingEntity bookingEntity=convertDTOtoEntity(bookingDTO);
		
		Optional<BookingEntity> checkBooking =bookingRepository.findById(bookingEntity.getBookingId());
		if(!checkBooking.isPresent()){
			log.error("exception-update booking in service layer");
			throw new RecordNotFoundException("BookingEntity with id " + bookingEntity.getBookingId() + " not found"); 
		}
		
		bookingRepository.save(bookingEntity);
		
		Optional<BookingEntity> returnBooking =bookingRepository.findById(bookingEntity.getBookingId());
		
		Booking returnBookingDTO =convertEntityToDTO(returnBooking.get());
		if(!returnBooking.isPresent()){
			log.error("exception-couldnot add updated booking in service layer");
			throw new DatabaseException("updated booking didnot got added"); 
		}
		log.info("updateBooking-service-ended");
		return returnBookingDTO;
		
		
	}
	
	@Override
	public Booking viewBooking(Integer bookingId) {
		log.info("viewBooking-service-started");
		Optional<BookingEntity> returnBooking =bookingRepository.findById(bookingId);
		
		if(!returnBooking.isPresent()){
			log.error("exception-view bookingById in service layer");
			throw new RecordNotFoundException("BookingEntity with id " + bookingId + " not found");
		}
		
			Booking returnBookingDTO =convertEntityToDTO(returnBooking.get());
			
			log.info("viewBooking-service-ended");
			return returnBookingDTO;

	}
	
	@Override
	public List<Booking> viewAllBooking(Customer customerDTO) {
		log.info("viewAllBooking-service-started");
		List<Booking> bookingDTOList=new ArrayList<Booking>();
		
		CustomerEntity customerEntity=convertCustomerDTOtoEntity(customerDTO);
		
		List<BookingEntity> bookingEntityList= bookingRepository.getByCustomer(customerEntity);
		if(bookingEntityList.isEmpty()){
			log.error("exception-No booking by given customer in service layer");
			throw new RecordNotFoundException("BookingEntity with cutomerid " + customerEntity.getCustomerId() + " not found");
		}
		
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("viewAllBooking-service-ended");
		return bookingDTOList;
		
		
		
	}

	@Override
	public List<Booking> viewAllBookingByDate(String bookingDate) {
		log.info("viewAllBookingByDate-service-started");
		List<Booking> bookingDTOList=new ArrayList<Booking>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localBookingDate = LocalDate.parse(bookingDate, formatter);
		
		List<BookingEntity> bookingEntityList=bookingRepository.findByBookingDate(localBookingDate);
		if(bookingEntityList.isEmpty()){
			log.error("exception-No booking on given date in service layer");
			throw new RecordNotFoundException("BookingEntity with date: " + localBookingDate + " not found");
		}
		
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("viewAllBookingByDate-service-ended");
		return bookingDTOList;
		
	}
	
	public CustomerEntity convertCustomerDTOtoEntity(Customer customerDTO){
		log.info("convertCustomerDTOtoEntity-started");
		CustomerEntity customerEntity=new CustomerEntity();
		customerEntity.setCustomerId(customerDTO.getCustomerId());
		customerEntity.setFirstName(customerDTO.getFirstName());
		customerEntity.setLastName(customerDTO.getLastName());
		customerEntity.setMobileNumber(customerDTO.getMobileNumber());
		customerEntity.setEmailId(customerDTO.getEmailId());
		customerEntity.setAddress(customerDTO.getAddress());
		customerEntity.setListOfBookings(null);
		
		log.info("convertCustomerDTOtoEntity-ended");
		return customerEntity;
	}
	
	public BookingEntity convertDTOtoEntity(Booking bookingDTO) 
	{
		log.info("convertDTOtoEntity-started");
		BookingEntity bookingEntity = new BookingEntity();
		
		bookingEntity.setBookingId(bookingDTO.getBookingId());
		bookingEntity.setBookingDate(bookingDTO.getBookingDate());
		bookingEntity.setBookedTillDate(bookingDTO.getBookedTillDate());
		bookingEntity.setBookingDescription(bookingDTO.getBookingDescription());
		bookingEntity.setTotalCost(bookingDTO.getTotalCost());
		bookingEntity.setDistance(bookingDTO.getDistance());
		
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setCustomerId(bookingDTO.getCustomer().getCustomerId());
		customerEntity.setFirstName(bookingDTO.getCustomer().getFirstName());
		customerEntity.setLastName(bookingDTO.getCustomer().getLastName());
		customerEntity.setMobileNumber(bookingDTO.getCustomer().getMobileNumber());
		customerEntity.setEmailId(bookingDTO.getCustomer().getEmailId());
		customerEntity.setAddress(bookingDTO.getCustomer().getAddress());
		
		VehicleEntity vehicleEntity = new VehicleEntity();
		
		vehicleEntity.setVehicleId(bookingDTO.getVehicle().getVehicleId());
		vehicleEntity.setVehicleNumber(bookingDTO.getVehicle().getVehicleNumber());
		vehicleEntity.setType(bookingDTO.getVehicle().getType());
		vehicleEntity.setCategory(bookingDTO.getVehicle().getCategory());
		vehicleEntity.setDescription(bookingDTO.getVehicle().getDescription());
		vehicleEntity.setLocation(bookingDTO.getVehicle().getLocation());
		vehicleEntity.setCapacity(bookingDTO.getVehicle().getCapacity());
		vehicleEntity.setChargesPerKM(bookingDTO.getVehicle().getChargesPerKM());
		vehicleEntity.setFixedCharges(bookingDTO.getVehicle().getFixedCharges());
		
		DriverEntity driverEntity = new DriverEntity();
		
		driverEntity.setDriverId(bookingDTO.getVehicle().getDriver().getDriverId());
		driverEntity.setFirstName(bookingDTO.getVehicle().getDriver().getFirstName());
		driverEntity.setLastName(bookingDTO.getVehicle().getDriver().getLastName());
		driverEntity.setEmail(bookingDTO.getVehicle().getDriver().getEmail());
		driverEntity.setContactNumber(bookingDTO.getVehicle().getDriver().getContactNumber());
		driverEntity.setAddress(bookingDTO.getVehicle().getDriver().getAddress());
		driverEntity.setChargesPerDay(bookingDTO.getVehicle().getDriver().getChargesPerDay());
		driverEntity.setLicenseNo(bookingDTO.getVehicle().getDriver().getLicenseNo());
		
		vehicleEntity.setDriver(driverEntity);
		vehicleEntity.setListOfBookings(null);
		customerEntity.setListOfBookings(null);
		bookingEntity.setCustomer(customerEntity);
		bookingEntity.setVehicle(vehicleEntity);
		
		log.info("convertDTOtoEntity-ended");
		return bookingEntity;
	}
	
	public Booking convertEntityToDTO(BookingEntity bookingEntity)
	{
		log.info("convertEntityToDTO-started");
		
		Booking bookingDTO = new Booking();
		
		bookingDTO.setBookingId(bookingEntity.getBookingId());
		bookingDTO.setBookingDate(bookingEntity.getBookingDate());
		bookingDTO.setBookedTillDate(bookingEntity.getBookedTillDate());
		bookingDTO.setBookingDescription(bookingEntity.getBookingDescription());
		bookingDTO.setTotalCost(bookingEntity.getTotalCost());
		bookingDTO.setDistance(bookingEntity.getDistance());
		
		Customer customerDTO = new Customer();
		
		CustomerEntity customerEntity=bookingEntity.getCustomer();
		customerDTO.setCustomerId(customerEntity.getCustomerId());
		customerDTO.setFirstName(customerEntity.getFirstName());
		customerDTO.setLastName(customerEntity.getLastName());
		customerDTO.setMobileNumber(customerEntity.getMobileNumber());
		customerDTO.setEmailId(customerEntity.getEmailId());
		customerDTO.setAddress(customerEntity.getAddress());
		
		Vehicle vehicleDTO = new Vehicle();
		
		VehicleEntity vehicleEntity=bookingEntity.getVehicle();
		vehicleDTO.setVehicleId(vehicleEntity.getVehicleId());
		vehicleDTO.setVehicleNumber(vehicleEntity.getVehicleNumber());
		vehicleDTO.setType(vehicleEntity.getType());
		vehicleDTO.setCategory(vehicleEntity.getCategory());
		vehicleDTO.setDescription(vehicleEntity.getDescription());
		vehicleDTO.setLocation(vehicleEntity.getLocation());
		vehicleDTO.setCapacity(vehicleEntity.getCapacity());
		vehicleDTO.setChargesPerKM(vehicleEntity.getChargesPerKM());
		vehicleDTO.setFixedCharges(vehicleEntity.getFixedCharges());
		
		Driver driverDTO = new Driver();
		
		driverDTO.setDriverId(bookingEntity.getVehicle().getDriver().getDriverId());
		driverDTO.setFirstName(bookingEntity.getVehicle().getDriver().getFirstName());
		driverDTO.setLastName(bookingEntity.getVehicle().getDriver().getLastName());
		driverDTO.setEmail(bookingEntity.getVehicle().getDriver().getEmail());
		driverDTO.setContactNumber(bookingEntity.getVehicle().getDriver().getContactNumber());
		driverDTO.setAddress(bookingEntity.getVehicle().getDriver().getAddress());
		driverDTO.setChargesPerDay(bookingEntity.getVehicle().getDriver().getChargesPerDay());
		driverDTO.setLicenseNo(bookingEntity.getVehicle().getDriver().getLicenseNo());
		
	
		vehicleDTO.setDriver(driverDTO);
		vehicleDTO.setListOfBookings(null);
		customerDTO.setListOfBookings(null);
		bookingDTO.setCustomer(customerDTO);
		bookingDTO.setVehicle(vehicleDTO);
		
		log.info("convertEntityToDTO-ended");
		return bookingDTO;
	}

}

package com.cg.ovms.service.implementation;

import java.time.LocalDate;
import java.time.Period;
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
import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.BookingEntity;
import com.cg.ovms.entities.CustomerEntity;
import com.cg.ovms.entities.VehicleEntity;
import com.cg.ovms.exception.DatabaseException;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.repository.BookingRepository;
import com.cg.ovms.repository.CustomerRepository;
import com.cg.ovms.repository.VehicleRepository;
import com.cg.ovms.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	private static final Logger log = LogManager.getLogger(BookingServiceImpl.class);
	
	@Autowired
	private BookingRepository iBookingRepository;
	
	@Autowired
	private VehicleRepository vehicleDao;
	
	@Autowired
	private CustomerRepository customerDao;
	
	Convert convertor = new Convert();
	 
	//adding a new booking
	@Override
	public Booking addBooking(Booking bookingDTO) {
		log.info("addBooking-service-started");
		if(bookingDTO==null) {
			throw new DatabaseException("Booking cannot be null");
		}
		
		
		if(!vehicleDao.existsById(bookingDTO.getVehicle().getVehicleId())){
			throw new DatabaseException("Vehicle Doesn't exist");
		}
		
		if(!customerDao.existsById(bookingDTO.getCustomer().getCustomerId())){
			throw new DatabaseException("Customer Doesn't exist");
		}
		
		Optional<VehicleEntity> vehicleEntity = vehicleDao.findById(bookingDTO.getVehicle().getVehicleId());
		Vehicle vehicle = convertor.convertVehicleEntityToDTO(vehicleEntity.get());
		bookingDTO.setVehicle(vehicle);
		
		Optional<CustomerEntity> customerEntity = customerDao.findById(bookingDTO.getCustomer().getCustomerId());
		Customer customer = convertor.convertCustomerEntityToDTO(customerEntity.get());
		bookingDTO.setCustomer(customer);
		int days = findDifference(bookingDTO);
				
		double totalBookingCost = calculatePaymentPerDay(bookingDTO)* days;
		bookingDTO.setTotalCost(totalBookingCost);
		
		BookingEntity bookingEntity=convertor.convertBookingDTOtoEntity(bookingDTO);
		
		if(bookingEntity.getBookingId()!=0 && iBookingRepository.existsById(bookingEntity.getBookingId())) {

			throw new DuplicateRecordException("Duplicate Record Found");
		}
		
		iBookingRepository.saveAndFlush(bookingEntity);
		Optional<BookingEntity> returnBooking = iBookingRepository.findById(bookingEntity.getBookingId());
		if(!returnBooking.isPresent()){
			log.error("exception-adding booking in service layer");
			throw new DatabaseException("Booking didnot got added"); 
		}
		
		
		Booking returnBookingDTO = convertor.convertBookingEntityToDTO(returnBooking.get());
		log.info("addBooking-service-ended");
		return returnBookingDTO;
		
	}
	
	//canceling a booking
	@Override
	public List<Booking> cancelBooking(Integer bookingId) {
		log.info("cancelBooking-service-started");
		if(bookingId == null) {
			throw new DatabaseException("Booking cannot be null");
		}
		List<Booking> bookingDTOList=new ArrayList<Booking>();	
		
		boolean checkBooking = iBookingRepository.existsById(bookingId);
		if(!checkBooking){
			log.error("exception-cancel booking in service layer");
			throw new RecordNotFoundException("Booking with the given id doesnot exists"); 
		}
		
		iBookingRepository.deleteById(bookingId);
		List<BookingEntity> bookingEntityList = iBookingRepository.findAll();		
		if(bookingEntityList==null ||  bookingEntityList.isEmpty() ) {
			log.warn("no bookings avialble after cancel booking in service layer");
			throw new RecordNotFoundException("No Bookings avaliable");
		}
		
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertor.convertBookingEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("cancelBooking-service-ended");
		return bookingDTOList;
		
		
	}
	
	//updating a booking
	@Override
	public Booking updateBooking(Booking bookingDTO) {
		log.info("updateBooking-service-started");
		if(bookingDTO == null) {
			throw new DatabaseException("Booking cannot be null");
		}
		
		if(!vehicleDao.existsById(bookingDTO.getVehicle().getVehicleId())){
			throw new DatabaseException("Vehicle Doesn't exist");
		}
		
		if(!customerDao.existsById(bookingDTO.getCustomer().getCustomerId())){
			throw new DatabaseException("Customer Doesn't exist");
		}
		
		Optional<VehicleEntity> vehicleEntity = vehicleDao.findById(bookingDTO.getVehicle().getVehicleId());
		Vehicle vehicle = convertor.convertVehicleEntityToDTO(vehicleEntity.get());
		bookingDTO.setVehicle(vehicle);
		
		Optional<CustomerEntity> customerEntity = customerDao.findById(bookingDTO.getCustomer().getCustomerId());
		Customer customer = convertor.convertCustomerEntityToDTO(customerEntity.get());
		bookingDTO.setCustomer(customer);
		
		int days = findDifference(bookingDTO);
		double totalBookingCost = calculatePaymentPerDay(bookingDTO)* days;
		bookingDTO.setTotalCost(totalBookingCost);
		BookingEntity bookingEntity = convertor.convertBookingDTOtoEntity(bookingDTO);
		
		Boolean checkBooking =iBookingRepository.existsById(bookingEntity.getBookingId());
		if(!checkBooking){
			log.error("exception-update booking in service layer");
			throw new RecordNotFoundException("Booking with the given id doesn't exist"); 
		}
		
		
		iBookingRepository.save(bookingEntity);
		
		Optional<BookingEntity> returnBooking =iBookingRepository.findById(bookingEntity.getBookingId());
		
		if(!returnBooking.isPresent()){
			log.error("exception-couldnot add updated booking in service layer");
			throw new DatabaseException("updated booking didnot got added"); 
		}
		
		Booking returnBookingDTO = convertor.convertBookingEntityToDTO(returnBooking.get());
		log.info("updateBooking-service-ended");
		return returnBookingDTO;
		
		
	}
	
	//viewBooking by id
	@Override
	public Booking viewBooking(Integer bookingId) {
		log.info("viewBooking-service-started");
		if(bookingId == null) {
			throw new DatabaseException("BookingId cannot be null"); 
		}
		
		Optional<BookingEntity> returnBooking =iBookingRepository.findById(bookingId);
		
		if(!returnBooking.isPresent()){
			log.error("exception-view bookingById in service layer");
			throw new RecordNotFoundException("Booking with the given id is not found");
		}
		
			Booking returnBookingDTO = convertor.convertBookingEntityToDTO(returnBooking.get());
			
			log.info("viewBooking-service-ended");
			return returnBookingDTO;

	}
	
	//view bookings by customer
	@Override
	public List<Booking> viewAllBooking(Integer customerId) {
		log.info("viewAllBooking-service-started");
		if(customerId == null) {
			throw new DatabaseException("customerid cannot be null");
		}
		Customer customerviewDTO = new Customer();
		customerviewDTO.setCustomerId(customerId);
		
		List<Booking> bookingDTOList=new ArrayList<Booking>();
		CustomerEntity customerEntity = convertor.convertCustomerDTOtoEntity(customerviewDTO);
		
		List<BookingEntity> bookingEntityList= iBookingRepository.getByCustomer(customerEntity);
		if(bookingEntityList == null || bookingEntityList.isEmpty()){
			log.error("exception-No booking by given customer in service layer");
			throw new RecordNotFoundException("Booking with given cutomerid not found");
		}
		
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertor.convertBookingEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("viewAllBooking-service-ended");
		return bookingDTOList;
			
	}
	
	//view bookings by booking date
	@Override
	public List<Booking> viewAllBookingByDate(String bookingDate) {
		if(bookingDate == null) {
			throw new DatabaseException("Date Cannot be null");
		}
		log.info("viewAllBookingByDate-service-started");
		List<Booking> bookingDTOList=new ArrayList<Booking>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localBookingDate = LocalDate.parse(bookingDate, formatter);
		
		List<BookingEntity> bookingEntityList=iBookingRepository.findByBookingDate(localBookingDate);
		if(bookingEntityList == null || bookingEntityList.isEmpty()){
			log.error("exception-No booking on given date in service layer");
			throw new RecordNotFoundException("Booking with given date not found");
		}
		
		
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertor.convertBookingEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("viewAllBookingByDate-service-ended");
		return bookingDTOList;
		
	}
	
	@Override
	public List<Booking> viewAllBookings() {
		
		log.info("viewAllBookings-service-started");
		List<Booking> bookingDTOList=new ArrayList<Booking>();
				
		List<BookingEntity> bookingEntityList= iBookingRepository.findAll();
		if(bookingEntityList == null || bookingEntityList.isEmpty()){
			log.error("exception-No bookings in service layer");
			throw new RecordNotFoundException("No Bookings Found");
		}
		
		Booking bookingDTO;
		for(BookingEntity booking:bookingEntityList){
			bookingDTO = convertor.convertBookingEntityToDTO(booking);
			bookingDTOList.add(bookingDTO);
		}
		
		log.info("viewAllBookings-service-ended");
		return bookingDTOList;
		
	}
	
	public double calculatePaymentPerDay(Booking booking) 
	{
		double driverCharge = booking.getVehicle().getDriver().getChargesPerDay();
		double fixedCharge = booking.getVehicle().getFixedCharges();
		double chargePerKM = booking.getVehicle().getChargesPerKM();
		double distance = booking.getDistance();
		double chargePerDay = driverCharge + fixedCharge + (distance*chargePerKM); 
		return chargePerDay;
	}
	
	public int findDifference(Booking booking){ 
		
	Period diff = Period.between(booking.getBookingDate(),booking.getBookedTillDate()); 
	int days = diff.getDays() + diff.getMonths()*30 + diff.getYears()*365;
	if(days == 0) {
		days = 1;
	}
	return days;
	}


	


	

}

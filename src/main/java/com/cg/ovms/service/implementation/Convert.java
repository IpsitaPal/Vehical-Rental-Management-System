package com.cg.ovms.service.implementation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cg.ovms.dto.Booking;
import com.cg.ovms.dto.Customer;
import com.cg.ovms.dto.Driver;
import com.cg.ovms.dto.User;
import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.BookingEntity;
import com.cg.ovms.entities.CustomerEntity;
import com.cg.ovms.entities.DriverEntity;
import com.cg.ovms.entities.UserEntity;
import com.cg.ovms.entities.VehicleEntity;

public class Convert {

	static final Logger log = LogManager.getLogger(Convert.class);
    public UserEntity convertDtoToEntity(User userDto) {

    	UserEntity user = new UserEntity();

        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        return user;
    }

    public User convertEntityToDto(UserEntity user) {

    	User userDto = new User();
    	
        userDto.setUserId(user.getUserId());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());

        return userDto;
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
		//customerEntity.setAllBookings(null);
		
		log.info("convertCustomerDTOtoEntity-ended");
		return customerEntity;
	}
	
    public Customer convertCustomerEntityToDTO(CustomerEntity customerEntity){
		log.info("convertCustomerEntityToDTO-started");
		Customer customerDTO=new Customer();
		customerDTO.setCustomerId(customerEntity.getCustomerId());
		customerDTO.setFirstName(customerEntity.getFirstName());
		customerDTO.setLastName(customerEntity.getLastName());
		customerDTO.setMobileNumber(customerEntity.getMobileNumber());
		customerDTO.setEmailId(customerEntity.getEmailId());
		customerDTO.setAddress(customerEntity.getAddress());
		//customerEntity.setAllBookings(null);
		
		log.info("convertCustomerEntityToDTO-ended");
		return customerDTO;
	}
    
	public BookingEntity convertBookingDTOtoEntity(Booking bookingDTO) 
	{
		log.info("convertDTOtoEntity-started");
		BookingEntity bookingEntity = new BookingEntity();
		
		bookingEntity.setBookingId(bookingDTO.getBookingId());
		bookingEntity.setBookingDate(bookingDTO.getBookingDate());
		bookingEntity.setBookedTillDate(bookingDTO.getBookedTillDate());
		bookingEntity.setBookingDescription(bookingDTO.getBookingDescription());
		bookingEntity.setTotalCost(bookingDTO.getTotalCost());
		bookingEntity.setDistance(bookingDTO.getDistance());
		//bookingEntity.setTotalBookingCost(bookingDTO.getTotalBookingCost());
		
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
		//vehicleEntity.setAllBookingsByVehicle(null);
		//customerEntity.setAllBookings(null);
		bookingEntity.setCustomer(customerEntity);
		bookingEntity.setVehicle(vehicleEntity);
		
		log.info("convertDTOtoEntity-ended");
		return bookingEntity;
	}
	
	
	public Booking convertBookingEntityToDTO(BookingEntity bookingEntity)
	{
		log.info("convertEntityToDTO-started");
		
		Booking bookingDTO = new Booking();
		
		bookingDTO.setBookingId(bookingEntity.getBookingId());
		bookingDTO.setBookingDate(bookingEntity.getBookingDate());
		bookingDTO.setBookedTillDate(bookingEntity.getBookedTillDate());
		bookingDTO.setBookingDescription(bookingEntity.getBookingDescription());
		bookingDTO.setTotalCost(bookingEntity.getTotalCost());
		bookingDTO.setDistance(bookingEntity.getDistance());
		//bookingDTO.setTotalBookingCost(bookingEntity.getTotalBookingCost());
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
		//vehicleDTO.setAllBookingsByVehicle(null);
		//customerDTO.setAllBookings(null);
		bookingDTO.setCustomer(customerDTO);
		bookingDTO.setVehicle(vehicleDTO);
		
		log.info("convertEntityToDTO-ended");
		return bookingDTO;
	}
    
	Vehicle convertVehicleEntityToDTO(VehicleEntity vehicleEntity) {

		Vehicle vehicleDTO = new Vehicle();

		vehicleDTO.setVehicleId(vehicleEntity.getVehicleId());
		vehicleDTO.setVehicleNumber(vehicleEntity.getVehicleNumber());
		vehicleDTO.setType(vehicleEntity.getType());
		vehicleDTO.setCategory(vehicleEntity.getCategory());
		vehicleDTO.setDescription(vehicleEntity.getDescription());
		vehicleDTO.setLocation(vehicleEntity.getLocation());
		vehicleDTO.setCapacity(vehicleEntity.getCapacity());
		vehicleDTO.setChargesPerKM(vehicleEntity.getChargesPerKM());
		vehicleDTO.setFixedCharges(vehicleEntity.getFixedCharges());
		vehicleDTO.setListOfBookings(null);

		Driver driverDTO = new Driver();

		driverDTO.setDriverId(vehicleEntity.getDriver().getDriverId());
		driverDTO.setFirstName(vehicleEntity.getDriver().getFirstName());
		driverDTO.setLastName(vehicleEntity.getDriver().getLastName());
		driverDTO.setEmail(vehicleEntity.getDriver().getEmail());
		driverDTO.setContactNumber(vehicleEntity.getDriver().getContactNumber());
		driverDTO.setAddress(vehicleEntity.getDriver().getAddress());
		driverDTO.setChargesPerDay(vehicleEntity.getDriver().getChargesPerDay());
		driverDTO.setLicenseNo(vehicleEntity.getDriver().getLicenseNo());
		
		vehicleDTO.setDriver(driverDTO);
		
		return vehicleDTO;
	}
	
	
}
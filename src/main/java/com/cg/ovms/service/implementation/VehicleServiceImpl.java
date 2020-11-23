package com.cg.ovms.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ovms.dto.Driver;
import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.DriverEntity;
import com.cg.ovms.entities.VehicleEntity;
import com.cg.ovms.exception.DatabaseException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.repository.VehicleRepository;
import com.cg.ovms.service.VehicleService;


@Service
public class VehicleServiceImpl implements VehicleService {

	 static Logger Log = Logger.getLogger(VehicleServiceImpl.class.getName());

	@Autowired
	private VehicleRepository vehicleDao;
	
	
	
	// addVehicle Service Implementation
	@Override
	public Vehicle addVehicle(Vehicle vehicleDTO)
	{
		Log.info("Add Vehicle Service Started");
		VehicleEntity vehicleEntity = convertDTOtoEntity(vehicleDTO);
		
		vehicleEntity = vehicleDao.saveAndFlush(vehicleEntity);
		Optional<VehicleEntity> vehicles = vehicleDao.findById(vehicleEntity.getVehicleId());
		
		if (!vehicles.isPresent()) 
		{
			Log.error("exception of adding vehicle in service layer");
			throw new DatabaseException("Vehicle did not get added");
		}

		Log.info("Add Vehicle Service Completed");
		
		return convertEntityToDTO(vehicleEntity);
	}

	
	
	// removeVehicle Service Implementation
	@Override
	public List<Vehicle> removeVehicle(Integer vehicleId)
	{
		Log.info("Remove Vehicle Service Started");

		Optional<VehicleEntity> remove = vehicleDao.findById(vehicleId);

		if (!remove.isPresent())
		{
			Log.error("exception of removing vehiclein service layer");
			throw new RecordNotFoundException("Vehicle with id " + vehicleId + " not found");
		}
		
		vehicleDao.deleteById(vehicleId);
		List<VehicleEntity> vehicleEntityList = vehicleDao.findAll();
		
		if (vehicleEntityList == null || vehicleEntityList.isEmpty())
		{
			Log.warn("no vehicles availble after cancelling in service layer");
			throw new RecordNotFoundException("No Vehicles avaliable");
		}
		
		List<Vehicle> vehicleDTOList = new ArrayList<Vehicle>();
		for(VehicleEntity vehicleItr: vehicleEntityList)
		{
			vehicleDTOList.add(convertEntityToDTO(vehicleItr));
		}
		
		Log.info("Remove Vehicle Service Completed");
		return vehicleDTOList;
	}
	
	
	
	// ViewVehicle Service Implementation
	@Override
	public Vehicle viewVehicle(Vehicle vehicle)
	{

		Log.info("View Vehicle Service Started");
	
		Optional<VehicleEntity> vehicles = vehicleDao.findById(vehicle.getVehicleId());

		if (!vehicles.isPresent())
		{
			Log.error("exception-view VehicleById in service layer");
			throw new RecordNotFoundException("Vehicle with id " + vehicle.getVehicleId() + " not found");
		}

		vehicle = convertEntityToDTO(vehicles.get());
		
		Log.info("view Vehicle service completed");
		return vehicle;
	}

	
	
	// UpdateVehicle Service Implementation
	@Override
	public Vehicle updateVehicle(Vehicle vehicleDTO) {

		Log.info("Update Vehicle Service Started");
		VehicleEntity vehicleEntity = convertDTOtoEntity(vehicleDTO);
		
		Optional<VehicleEntity> vehicleObj = vehicleDao.findById(vehicleDTO.getVehicleId());

		if (!vehicleObj.isPresent())
		{
			Log.error("exception of update vehicle in service layer");
			throw new RecordNotFoundException("Vehicle with id" + vehicleDTO.getVehicleId() + " not found");
		}
		 
		vehicleEntity = vehicleDao.save(vehicleEntity);
		
		vehicleObj = vehicleDao.findById(vehicleEntity.getVehicleId());

		if (!vehicleObj.isPresent())
		{
			Log.error("exception of update vehicle in service layer");
			throw new RecordNotFoundException("Updated Vehicle not added");
		}

		vehicleDTO = convertEntityToDTO(vehicleObj.get());
		
		Log.info("Update Vehicle service completed");
		return vehicleDTO;
	}

	
	
	// ViewAllVehicle Service Implementation
	@Override
	public List<Vehicle> viewAllVehicle() {

		Log.info("View All Vehicle service started");

		List<VehicleEntity> vehicleEntityList = vehicleDao.findAll();
		
		if(vehicleEntityList.isEmpty()) {
			Log.error("exception of viewAllVehicle in service layer");
			throw new RecordNotFoundException("No Vehicles available");
		}
		
		List<Vehicle> vehicleDTOList = new ArrayList<Vehicle>();
		for(VehicleEntity vehicleItr: vehicleEntityList)
		{
			vehicleDTOList.add(convertEntityToDTO(vehicleItr));
		}
		
		Log.info("View All Vehicle service completed");
		return vehicleDTOList;

	}

	
	
	/* //ViewAllBookingByVehicle Logic
	 
	  @Override public List<BookingEntity> viewAllBookingByVehicle(VehicleEntity vehicle) {
	  
	 Log.info("ViewAllBookingByVehicle Service Started");
	 
	 List<BookingEntity> bookingList = new ArrayList<BookingEntity>();
	  List<Booking> bookings=iVehicleDao.getByVehicle(bookingList);
	  
	  if(bookings.isEmpty())
	  {
	  Log.error("exception-No booking by given vehicle in service layer"); 
	  
	  throw new RecordNotFoundException("Booking with vehicleid not found");
	  }
	  Log.info("viewAllBookingByVehicle Service Completed");
	 
	  return bookingList;
	 
	 }
	 */
	 

	private VehicleEntity convertDTOtoEntity(Vehicle vehicleDTO) {

		VehicleEntity vehicleEntity = new VehicleEntity();

		vehicleEntity.setVehicleId(vehicleDTO.getVehicleId());
		vehicleEntity.setVehicleNumber(vehicleDTO.getVehicleNumber());
		vehicleEntity.setType(vehicleDTO.getType());
		vehicleEntity.setCategory(vehicleDTO.getCategory());
		vehicleEntity.setDescription(vehicleDTO.getDescription());
		vehicleEntity.setLocation(vehicleDTO.getLocation());
		vehicleEntity.setCapacity(vehicleDTO.getCapacity());
		vehicleEntity.setChargesPerKM(vehicleDTO.getChargesPerKM());
		vehicleEntity.setFixedCharges(vehicleDTO.getFixedCharges());
		vehicleEntity.setListOfBookings(null);

		DriverEntity driverEntity = new DriverEntity();
		
		driverEntity.setDriverId(vehicleDTO.getDriver().getDriverId());
		driverEntity.setFirstName(vehicleDTO.getDriver().getFirstName());
		driverEntity.setLastName(vehicleDTO.getDriver().getLastName());
		driverEntity.setEmail(vehicleDTO.getDriver().getEmail());
		driverEntity.setContactNumber(vehicleDTO.getDriver().getContactNumber());
		driverEntity.setAddress(vehicleDTO.getDriver().getAddress());
		driverEntity.setChargesPerDay(vehicleDTO.getDriver().getChargesPerDay());
		driverEntity.setLicenseNo(vehicleDTO.getDriver().getLicenseNo());

		vehicleEntity.setDriver(driverEntity);

		return vehicleEntity;
	}

	private Vehicle convertEntityToDTO(VehicleEntity vehicleEntity) {

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

package com.cg.ovms.service;

import java.util.List;

import com.cg.ovms.dto.Vehicle;


public interface VehicleService {

	public Vehicle addVehicle(Vehicle vehicle);
	public Vehicle viewVehicle(Vehicle vehicle); 
	public Vehicle updateVehicle(Vehicle vehicle);
	public List<Vehicle> removeVehicle(Integer vehicleId); 
	public List<Vehicle> viewAllVehicle(); 
	//public List<Vehicle> viewAllVehicle(String vtype); 
	//public List<BookingEntity> viewAllBookingByVehicle(VehicleEntity vehicle);  
	
}

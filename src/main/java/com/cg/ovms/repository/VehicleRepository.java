package com.cg.ovms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.VehicleEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer>  {
   
	public List<Vehicle> findByType(String vtype);
  
	// public List<Vehicle> findById(Integer vehicleId);
/*	@Query("SELECT booking FROM BookingEntity booking WHERE booking.vehicle = :pVhclId")
	public List<BookingEntity> getByVehicle(@Param("pVhclId") VehicleEntity vehicle);*/
	
}

package com.cg.ovms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ovms.dto.Vehicle;
import com.cg.ovms.entities.VehicleEntity;
import com.cg.ovms.service.VehicleService;

@RestController
@RequestMapping("/ovms")
public class VehicleController
{
	
	   static Logger Log = Logger.getLogger(VehicleController.class.getName());

		@Autowired
		private VehicleService vehicleService;

		
		// addVehicle Controller
		@PostMapping("/vehicle")
		public ResponseEntity<Vehicle> addVehicle(@Valid @RequestBody Vehicle vehicle)
		{
			Log.info("addVehicle Contoller Started");
			
			vehicle = vehicleService.addVehicle(vehicle);
				
			Log.info("addVehicle Contoller Completed");
			
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
		}

		

		// removeVehicle Controller
		@DeleteMapping("/vehicle/{vehicleId}")
		public ResponseEntity<List<Vehicle>> removeVehicle(@Valid @PathVariable("vehicleId") Integer vehicleId)
		{
			Log.info("RemoveVehicle Contoller Started");
			
			VehicleEntity vehicle = new VehicleEntity();
			vehicle.setVehicleId(vehicleId);
			
			List<Vehicle> vehicleEntity = vehicleService.removeVehicle(vehicleId);
			
			Log.info("addVehicle Contoller Completed");
				
			return new ResponseEntity<List<Vehicle>>(vehicleEntity, HttpStatus.OK);
		}

	
		
		// VehicleUpdate Controller
		@PutMapping("/vehicle")
		public ResponseEntity<Vehicle> updateVehicle(@Valid @RequestBody Vehicle vehicle) 
		{
			Log.info("UpdateVehicle Contoller Started");
			
			vehicle  = vehicleService.updateVehicle(vehicle);
			
			Log.info("UpdateVehicle Contoller Completed");

			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
		}

	
		
		// ViewAllVehicle Controller
		@GetMapping("/vehicle")
		public ResponseEntity<List<Vehicle>> viewAllVehicle() 
		{
			Log.info("ViewAllVehicle Contoller Started");
			
			List<Vehicle> vehicleList = vehicleService.viewAllVehicle();
			
			Log.info("ViewAllVehicle Contoller Completed");

			return new ResponseEntity<List<Vehicle>>(vehicleList, HttpStatus.OK);
		}
	
	
		
		// ViewVehicle Controller
		@GetMapping("/vehicle/{vehicleId}")
		public ResponseEntity<Vehicle> viewVehicle(@Valid @PathVariable("vehicleId") Integer vehicleId)
		{
			Log.info("ViewVehicle Contoller Started");
			
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleId(vehicleId);
			Vehicle vehicleDTO = vehicleService.viewVehicle(vehicle);
				
			Log.info("ViewVehicle Contoller Completed");
			
			return new ResponseEntity<Vehicle>(vehicleDTO, HttpStatus.OK);
		}


	/*	// viewAllBookingByVehicle Controller
	   @GetMapping("/vehicle")
		public ResponseEntity<List<BookingEntity>> viewAllBookingByVehicle(@Valid @RequestBody VehicleEntity vehicle)
		{
			Log.info("viewAllBookingByVehicle Contoller Started");
			//List<BookingEntity> bookingList = new ArrayList<BookingEntity>();
			
		//	VehicleEntity vehicles=new VehicleEntity();
			//vehicles.setVehicleId(vehicle);
			List<BookingEntity> allBooking = iVehicleService.viewAllVehicle(vehicle);
			
			Log.info("viewAllBookingByVehicle Contoller Completed");	
			return new ResponseEntity<List<BookingEntity>>(allBooking, HttpStatus.OK);         //removeafter
		}
	 */
}

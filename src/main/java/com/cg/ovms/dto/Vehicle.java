package com.cg.ovms.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Vehicle {
		
	private  int vehicleId;
	
	@NotNull
	private Driver driver;
	
	@NotBlank
	@Pattern(regexp="^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message="VehicleNumber length must be 10") 
	private String vehicleNumber;
	
	@NotBlank
	private String type;	//car//bus
	
	@NotBlank
	private String category ; //ac or nonac
	
	@NotBlank(message= "Description must be entered")
	@Size(max = 100)
	private String description;
	
	@NotBlank
	private String location;
	
	@NotBlank
	@Size(max = 5)
	private String capacity;

	@DecimalMax(value="10.00", message = "chargeperkm should max of 10.00")
	private double chargesPerKM;

	@DecimalMax(value="50.00", message = "fixedCharges should max of 50.00")
	private double fixedCharges;
	
	@JsonIgnore
	private List<Booking> listOfBookings = new ArrayList<Booking>();
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(int vehicleId, Driver driver, String vehicleNumber, String type, String category, String description, 
			String location, String capacity, double chargesPerKM, double fixedCharges) {
		super();
		this.vehicleId = vehicleId;
		this.driver = driver;
		this.vehicleNumber = vehicleNumber;
		this.type = type;
		this.category = category;
		this.description = description;
		this.location = location;
		this.capacity = capacity;
		this.chargesPerKM = chargesPerKM;
		this.fixedCharges = fixedCharges;
	}

	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public double getChargesPerKM() {
		return chargesPerKM;
	}
	public void setChargesPerKM(double chargesPerKM) {
		this.chargesPerKM = chargesPerKM;
	}
	public double getFixedCharges() {
		return fixedCharges;
	}
	public void setFixedCharges(double fixedCharges) {
		this.fixedCharges = fixedCharges;
	}
	public List<Booking> getListOfBookings() {
		return listOfBookings;
	}
	public void setListOfBookings(List<Booking> listOfBookings) {
		this.listOfBookings = listOfBookings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		long temp;
		temp = Double.doubleToLongBits(chargesPerKM);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		temp = Double.doubleToLongBits(fixedCharges);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((listOfBookings == null) ? 0 : listOfBookings.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + vehicleId;
		result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (capacity == null) {
			if (other.capacity != null)
				return false;
		} else if (!capacity.equals(other.capacity))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (Double.doubleToLongBits(chargesPerKM) != Double.doubleToLongBits(other.chargesPerKM))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (Double.doubleToLongBits(fixedCharges) != Double.doubleToLongBits(other.fixedCharges))
			return false;
		if (listOfBookings == null) {
			if (other.listOfBookings != null)
				return false;
		} else if (!listOfBookings.equals(other.listOfBookings))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (vehicleId != other.vehicleId)
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		return true;
	}
	
}
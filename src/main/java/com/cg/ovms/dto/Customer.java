package com.cg.ovms.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer {

	private int customerId;
	
	@NotNull
	@Size(min = 2, message = "Firstname should be valid")
	private String firstName;
	
	@NotNull
	@Size(min = 2, message = "Lastname should be valid")
	private String lastName;
	
	@NotNull
	@Size(min = 10, message = "MobileNumber should be valid")
	private String mobileNumber;
	
	@NotNull
	@Email(message = "Enter valid email")
	private String emailId;
	
	@NotNull
	@Size(min = 5, message = "Enter valid address")
	private String address;
	
	@JsonIgnore
	private List<Booking> listOfBookings;
	
	public Customer() {
		super();
	}
	
	public Customer(int customerId, String firstName, String lastName, String mobileNumber, String emailId, String address) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.address = address;
	}

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + customerId;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((listOfBookings == null) ? 0 : listOfBookings.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
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
		Customer other = (Customer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (customerId != other.customerId)
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (listOfBookings == null) {
			if (other.listOfBookings != null)
				return false;
		} else if (!listOfBookings.equals(other.listOfBookings))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		return true;
	}	
	
}

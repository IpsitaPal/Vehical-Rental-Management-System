package com.cg.ovms.entities;

import javax.persistence.Column;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DRIVER")
public class DriverEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DRIVER_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int driverId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "MOBILE_NUMBER")
	private String contactNumber;
	
	@Column(name = "EMAIL_ID")
	private String email;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "CHARGES_PER_DAY")
	private double chargesPerDay;
	
	@Column(name = "LICENSE_NO")
	private String licenseNo;
	
	public DriverEntity() {
		
	}
	
	public DriverEntity(int driverId, String firstName, String lastName, String contactNumber, String email, String address,
			double chargesPerDay, String licenseNo) {
		super();
		this.driverId = driverId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.address = address;
		this.chargesPerDay = chargesPerDay;
		this.licenseNo = licenseNo;
	}

	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
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
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getChargesPerDay() {
		return chargesPerDay;
	}
	public void setChargesPerDay(double chargesPerDay) {
		this.chargesPerDay = chargesPerDay;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	@Override
	public String toString() {
		return "Driver [driverId=" + driverId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", contactNumber=" + contactNumber + ", email=" + email + ", address=" + address + ", chargesPerDay="
				+ chargesPerDay + ", licenseNo=" + licenseNo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		long temp;
		temp = Double.doubleToLongBits(chargesPerDay);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result + driverId;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((licenseNo == null) ? 0 : licenseNo.hashCode());
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
		DriverEntity other = (DriverEntity) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (Double.doubleToLongBits(chargesPerDay) != Double.doubleToLongBits(other.chargesPerDay))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (driverId != other.driverId)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (licenseNo == null) {
			if (other.licenseNo != null)
				return false;
		} else if (!licenseNo.equals(other.licenseNo))
			return false;
		return true;
	}

	
}

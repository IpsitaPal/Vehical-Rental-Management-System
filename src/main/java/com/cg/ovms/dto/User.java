package com.cg.ovms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {


	@NotNull
	@Email(message = "Enter valid email")
	private String userId;
	
	@NotBlank
	//@Pattern(regexp="^(?=.*\\d).{4,8}$",message="Password length must be between 4-8 characters ,should contain uppercase letter and number")
	@Size(min=8,max=13,message="Password length must be between 8-13 characters")
	private String password;
	
	@NotBlank
	@Size(min=5,max=15,message="Role must be min 5 chars and max 15)")
	private String role;
	
	//
	//private Customer customer;

	public User() {
		
	}
	
	public User(String userId, String password, String role) {
		super();
		this.userId = userId;
		this.password = password;
		this.role = role; 
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	/*public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}*/
	

}

package com.cg.ovms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ovms.dto.Customer;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.service.CustomerService;

@RestController
@CrossOrigin//(origins = "*", allowedHeaders = "*")
@RequestMapping("/ovms")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	static Logger log = Logger.getLogger(CustomerController.class.getName());

	@PostMapping("/customer")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		
		log.info("Controller layer - Entry - addCustomer");
		customer = customerService.addCustomer(customer);
		log.info("Controller layer - Exit - addCustomer");
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<Customer> removeCustomer(@PathVariable("customerId") Integer customerId) {
		
		log.info("Controller layer - Entry - removeCustomer");
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customer = customerService.removeCustomer(customer);
		log.info("Controller layer - Exit - removeCustomer");
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Customer> viewCustomerBycustomerId(@PathVariable("customerId") Integer customerId)
			throws RecordNotFoundException { 
		
		log.info("Controller layer - Entry - viewCustomerBycustomerId");
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		Customer customer1 = customerService.viewCustomer(customer);
		log.info("Controller layer - Exit - viewCustomerByCustomerId");
		return new ResponseEntity<Customer>(customer1, HttpStatus.OK);
	}

	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer)
			throws RecordNotFoundException {

		customer = customerService.updateCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@GetMapping("/viewcustomer/{FirstName}")
	public ResponseEntity<List<Customer>> viewAllCustomer(@PathVariable("FirstName") String FirstName)
			throws RecordNotFoundException {

		Customer customer = new Customer();
		customer.setFirstName(FirstName);
		List<Customer> customerList = customerService.viewAllCustomer(FirstName);
		return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
	}
	
	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getAllCustomer() throws RecordNotFoundException {
		List<Customer> customerList = customerService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
	}
	
	@GetMapping("/getcustomer/{email}")
	public ResponseEntity<Customer> getCustomerByEmail(@PathVariable("email") String email) throws RecordNotFoundException {
		Customer customer = customerService.getCustomerByEmail(email);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

}

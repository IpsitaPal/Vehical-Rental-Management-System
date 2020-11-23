package com.cg.ovms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.cg.ovms.dto.Customer;
import com.cg.ovms.exception.RecordNotFoundException;

@Service
public interface CustomerService {

	public Customer addCustomer(Customer customer);

	public Customer removeCustomer(Customer customer);

	public Customer viewCustomer(Customer customer) throws RecordNotFoundException;

	public Customer updateCustomer(Customer customer) throws RecordNotFoundException;

	public List<Customer> viewAllCustomer(String type) throws RecordNotFoundException;

}

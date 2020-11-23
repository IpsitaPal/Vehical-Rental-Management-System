package com.cg.ovms.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ovms.dto.Customer;
import com.cg.ovms.entities.CustomerEntity;
import com.cg.ovms.repository.CustomerRepository;
import com.cg.ovms.service.CustomerService;
import com.cg.ovms.exception.RecordNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	static Logger log = Logger.getLogger(CustomerServiceImpl.class.getName());
	
	@Autowired
	private CustomerRepository customerDao;

	@Override
	public Customer addCustomer(Customer customerDTO) {
		log.info("Service Layer - Entry - addCustomer");

		CustomerEntity customerEntity = convertDTOtoEntity(customerDTO);
		customerEntity = customerDao.saveAndFlush(customerEntity);
		customerDTO = convertEntityToDTO(customerEntity);

		if (customerDTO == null)
			return null;
		log.info("Service Layer - Exit - addCustomer");
		return customerDTO;
	}

	public Customer removeCustomer(Customer customerDTO) {

		log.info("Service Layer - Entry - removeCustomer");
		
		Optional<CustomerEntity> checkCustomer = customerDao.findById(customerDTO.getCustomerId());
		if(!checkCustomer.isPresent())
		{
			throw new RecordNotFoundException("Record Not Found");
		}
		
		CustomerEntity customerEntity = checkCustomer.get();
		customerDao.delete(customerEntity);					
		customerDTO = convertEntityToDTO(customerEntity);
		
		log.info("Service Layer - Exit - removeCustomer");

		return customerDTO;
	}

	@Override
	public Customer updateCustomer(Customer customerDTO) throws RecordNotFoundException {

		log.info("Service Layer - Entry - updateCustomer");
		Optional<CustomerEntity> checkCustomer = customerDao.findById(convertDTOtoEntity(customerDTO).getCustomerId());
		if (checkCustomer.isEmpty()) {
			throw new RecordNotFoundException("Given customer is not found");
		}

		CustomerEntity customerEntity = convertDTOtoEntity(customerDTO);
		customerEntity = customerDao.save(customerEntity);
		customerDTO = convertEntityToDTO(customerEntity);
		log.info("Service Layer - Exit - updateCustomer");
		return customerDTO;
	}

	@Override
	public List<Customer> viewAllCustomer(String firstName) throws RecordNotFoundException {

		log.info("Service Layer - Entry - viewAllCustomer");

		List<Customer> customerDTOList = new ArrayList<Customer>();
		List<CustomerEntity> customerEntityList = customerDao.findByFirstName(firstName);
		Customer customerDTO;

		for (CustomerEntity customerEntityItr : customerEntityList) {
			customerDTO = convertEntityToDTO(customerEntityItr);
			customerDTOList.add(customerDTO);
		}
		if(customerDTOList.isEmpty())
		{
			throw new RecordNotFoundException("No customer found");
		}
		log.info("Service Layer - Exit- viewAllCustomer");
		return customerDTOList;
	}

	@Override
	public Customer viewCustomer(Customer customer1) throws RecordNotFoundException {
		CustomerEntity customerEntity = null;
		Optional<CustomerEntity> customer = customerDao.findById(customer1.getCustomerId());

		if (customer.isEmpty()) {
			throw new RecordNotFoundException(" Customer Not Found");
		}
			customerEntity = customer.get();
		customer1 = convertEntityToDTO(customerEntity);
		return customer1;
	}

	public CustomerEntity convertDTOtoEntity(Customer customerDTO) {
		CustomerEntity customerEntity = new CustomerEntity();

		customerEntity.setCustomerId(customerDTO.getCustomerId());
		customerEntity.setFirstName(customerDTO.getFirstName());
		customerEntity.setLastName(customerDTO.getLastName());
		customerEntity.setMobileNumber(customerDTO.getMobileNumber());
		customerEntity.setEmailId(customerDTO.getEmailId());
		customerEntity.setAddress(customerDTO.getAddress());

		return customerEntity;
	}

	public Customer convertEntityToDTO(CustomerEntity customerEntity) {
		Customer customerDTO = new Customer();

		customerDTO.setCustomerId(customerEntity.getCustomerId());
		customerDTO.setFirstName(customerEntity.getFirstName());
		customerDTO.setLastName(customerEntity.getLastName());
		customerDTO.setMobileNumber(customerEntity.getMobileNumber());
		customerDTO.setEmailId(customerEntity.getEmailId());
		customerDTO.setAddress(customerEntity.getAddress());
		return customerDTO;

	}

}

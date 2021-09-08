package com.jumia.exercise.service;

import com.jumia.exercise.controller.CustomerController;
import com.jumia.exercise.entity.Customer;
import com.jumia.exercise.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;

/**
 * @author Hany
 * 
 * CustomerServiceImpl is the implementation class for {@link CustomerService}
 * provides the required functionality service layer for the controller {@link CustomerController}
 * 
 * It contains the service to find all customers
 * Find a specific Customer by ID
 * Find customers with pagination
 * Create new Customer service
 * Update existing Customer
 * Delete existing Customer
 *
 */

@Service
@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Finds all customers and returns them to CustomerController as list
	 * 
	 * @return list of customers exist in DB
	 */
	public List<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}

	/**
	 * Find specific customer by id in DB
	 * @return the customer found in DB for given ID
	 */
	public Customer findById(Integer id) throws EntityNotFoundException {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			logger.warn("Cannot find Customer with id: " + id);
			throw new EntityNotFoundException("Cannot find Customer with id: " + id);
		} else
			return customer;
	}
	
	/**
	 * Create new Customer
	 * 
	 * @param customer
	 * @return a success message if created successfully or failed message if the
	 *         customer already exists in DB
	 */
	@Transactional
	public Customer createCustomer(Customer customer) throws Exception {

		if (!customerRepository.existsByPhone(customer.getPhone())) {
			customer.setId(null == customerRepository.findMaxId() ? 0 : customerRepository.findMaxId() + 1);
			customerRepository.save(customer);
		} else {
			logger.warn("Customer already exists in the database.");
			new Exception("Customer already exists in the database.");
		}
		return customer;
	}

	/**
	 * Update customer in DB
	 * 
	 * @param customer
	 * @return a success message if customer updated successfully or failed message
	 *         if the customer does not exist
	 */
	@Transactional
	public String updateCustomer(Customer customer) {
		if (customerRepository.existsByPhone(customer.getPhone())) {
			try {
				List<Customer> customers = customerRepository.findByPhone(customer.getPhone());
				customers.stream().forEach(s -> {
					Customer customerToBeUpdate = customerRepository.findById(s.getId()).get();
					customerToBeUpdate.setName(customer.getName());
					customerToBeUpdate.setPhone(customer.getPhone());
					customerRepository.save(customerToBeUpdate);
				});
				return "Customer record updated.";
			} catch (Exception e) {
				logger.error("Cannot update Customer with id: " + customer.getId());
				throw e;
			}
		} else {
			logger.error("Customer with ID " + customer.getId() + " does not exists in the database.");
			return "Customer does not exists!";
		}
	}

	/**
	 * Delete desired customer from DB
	 * 
	 * @param customer
	 * @return a success message if deleted successfully or failed message if the
	 *         customer does not exist
	 */
	@Transactional
	public String deleteCustomer(Customer customer) {
		if (customerRepository.existsByPhone(customer.getPhone())) {
			try {
				List<Customer> customers = customerRepository.findByPhone(customer.getPhone());
				customers.stream().forEach(s -> {
					customerRepository.delete(s);
				});
				return "Customer record deleted successfully.";
			} catch (Exception e) {
				logger.error("Cannot delete Customer with id: " + customer.getId());
				throw e;
			}

		} else {
			logger.error("Customer with ID " + customer.getId() + " does not exists in the database.");
			return "Customer does not exist";
		}
	}
}

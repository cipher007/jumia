package com.jumia.exercise.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.jumia.exercise.entity.Customer;

/**
 * 
 * @author hany
 *
 * The service interface contract
 */
public interface CustomerService {

	List<Customer> findAllCustomers();

	Customer findById(Integer id) throws EntityNotFoundException;

	Customer createCustomer(Customer customer) throws Exception;

	String updateCustomer(Customer customer);

	String deleteCustomer(Customer customer);
}

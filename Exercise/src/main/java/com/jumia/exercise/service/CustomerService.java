package com.jumia.exercise.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

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
	
	Page<Customer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}

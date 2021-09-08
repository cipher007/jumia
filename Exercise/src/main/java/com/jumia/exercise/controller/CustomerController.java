package com.jumia.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jumia.exercise.dto.CustomerDTO;
import com.jumia.exercise.entity.Customer;
import com.jumia.exercise.service.CustomerService;
import com.jumia.exercise.validation.CustomersFilter;
import com.jumia.exercise.validation.PhoneNumberValidator;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PhoneNumberValidator phoneNumberValidator;

	@Autowired
	private CustomersFilter customersFilter;

	@Value("${msg.title}")
	private String title;

	/**
	 * Retrieves all customers provided by the service and return them to the view for
	 * display
	 * 
	 * @param model
	 * @param pageNumber
	 * @return Customers list page
	 */
	@GetMapping(value = { "/", "/index", "/customers"})
	public String getCustomers(Model model, @Param("country") String country, @Param("status") String status) {

		// find all customers in DB
		List<Customer> customers = customerService.findAllCustomers();

		// Categorize customers by code to decide which country and whether phone number
		// is valid
		List<CustomerDTO> customersDto = phoneNumberValidator.categorize(customers);

		// Filter customers by country and/or status
		List<CustomerDTO> result = customersFilter.filter(customersDto, country, status);

		// add model attributes
		model.addAttribute("customers", result);
		model.addAttribute("country", country);
		model.addAttribute("status", status);
		model.addAttribute("title", title);

		// go to customers list page
		return "customer-list";
	}

}

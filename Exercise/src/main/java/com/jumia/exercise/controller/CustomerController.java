package com.jumia.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * Retrieves all customers provided by the service and return them to the view
	 * for display, this is the index or root mapping the directs to customers list page
	 * 
	 * @param model the model
	 * @param country filter by country name
	 * @param status filter by phone number validation status
	 * @return Customers list page
	 */
	@GetMapping(value = { "/", "/index" })
	public String getCustomers(Model model, @Param("country") String country, @Param("status") String status) {

		return findPaginated(1, "id", "asc", model, country, status);
	}

	/**
	 * Retrieves all customers provided by the service and return them to the view
	 * for display, the method filters result after form submission with filter criteria (country name, phone status)
	 * 
	 * @param model the model
	 * @param country filter by country name
	 * @param status filter by phone number validation status
	 * @return Customers list page
	 */
	@GetMapping(value = { "/filtered" })
	public String getCustomersFilter(Model model, @Param("country") String country, @Param("status") String status) {

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

	/**
	 * Retrieves all customers provided by the service and return them to the view
	 * for display in pages
	 * 
	 * @param pageNo current page number
	 * @param sortField the sort criteria
	 * @param sortDir sorting direction
	 * @param model the model
	 * @param country filter by country name
	 * @param status filter by phone number validation status
	 * @return Customers list page paginated
	 */
	@GetMapping("/customers/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model, @Param("country") String country,
			@Param("status") String status) {
		int pageSize = 10;

		// find all customers in DB paginated
		Page<Customer> page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Customer> customers = page.getContent();
		
		// Categorize customers by code to decide which country and whether phone number
		// is valid
		List<CustomerDTO> result = phoneNumberValidator.categorize(customers);

		// add model attributes
		model.addAttribute("customers", result);
		model.addAttribute("country", "");
		model.addAttribute("status", "");
		model.addAttribute("title", title);

		//page no, total pages, and total number of elements in page
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		// sorting attributes
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		return "customer-list";
	}

}

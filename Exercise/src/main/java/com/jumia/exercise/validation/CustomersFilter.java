package com.jumia.exercise.validation;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jumia.exercise.dto.CustomerDTO;

@Component("customersFilter")
public class CustomersFilter {

	/**
	 * Default constructor
	 */
	public CustomersFilter() {}
	
	/**
	 * The method filters list of customers by to identifies 'Country' and 'Status'
	 * 
	 * Normally filtering happens by SQL query criteria, but not all information available in DB "code, country, status"
	 * Besides filtering on the service level saves DB travel and hits.
	 * 
	 * @param customersDto
	 * @param country
	 * @param status
	 * @return The list of customers filtered if the user provides Country or Status
	 */
	public List<CustomerDTO> filter(List<CustomerDTO> customersDto, String country, String status) {
		List<CustomerDTO> result = customersDto;
		
		// Filter customers by country (Cameroon/Morocco/Ethiopia/Uganda/Mozambique)
		if (country != null && country.length() > 0) {
			Predicate<CustomerDTO> customerFilterCountry = c -> c.getCountry().equalsIgnoreCase(country.trim());
			result = customersDto.stream().filter(customerFilterCountry).collect(Collectors.toList());
		}

		// Filter customer is by status (valid/invalid)
		if (status != null && status.length() > 0) {
			Predicate<CustomerDTO> customerFilterStatus = c -> c.getStatus().equalsIgnoreCase(status.trim());
			result = result.stream().filter(customerFilterStatus).collect(Collectors.toList());
		}
		
		// return the result of customers after filtering
		return result;
		
	}

}

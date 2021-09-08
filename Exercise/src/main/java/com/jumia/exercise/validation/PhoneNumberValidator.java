package com.jumia.exercise.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jumia.exercise.dto.Country;
import com.jumia.exercise.dto.CustomerDTO;
import com.jumia.exercise.entity.Customer;

@Service
@Component("phoneNumberValidator")
public class PhoneNumberValidator {

	/**
	 * The categorize method receives customers list, iterate through them then extracts the code, and phone 
	 * Uses Country Enum to match the code, the country name associated with it and retrieves the regex pattern.
	 * 
	 * using the pattern it validates the customers phone number and decides if the phone is valid or invalid.
	 * At the end, it sets all Customer Data Transfer Object and add it to the list to return
	 * @param customers
	 * @return
	 */
	public List<CustomerDTO> categorize(List<Customer> customers) {
		String code;
		String phone;
		String country;
		String status;

		// Initialize customersDto with same customers list size
		List<CustomerDTO> customersDto = new ArrayList<CustomerDTO>(customers.size());

		// go through all customers in DB to extract and separate code from phone
		// then determine what code belongs to which country using Country Enum.
		// construct CustomerDTO which contains extra information than Customer entity has (status, code, country), 
		// set it's attributes, then add it to the list of customersDTOs
		for (Customer customer : customers) {
			code = customer.getPhone().substring(0, 5);
			phone = customer.getPhone().substring(5);
			Pattern pattern;
			Matcher matcher;

			CustomerDTO customerDto = new CustomerDTO();

			// matches the extracted code with predefined codes to choose the which country it belongs to
			// then choose the regex pattern to validate against
			if (Country.CAMEROON.getCode().equals(code)) {
				// country is Cameroon
				country = Country.CAMEROON.getName();
				// choose cameroon pattern
				pattern = Pattern.compile(Country.CAMEROON.getRegex());
				matcher = pattern.matcher(customer.getPhone());
			} else if (Country.ETHIOPIA.getCode().equals(code)) {
				// country is Ethiopia
				country = Country.ETHIOPIA.getName();
				// choose Ethiopia pattern
				pattern = Pattern.compile(Country.ETHIOPIA.getRegex());
				matcher = pattern.matcher(customer.getPhone());
			} else if (Country.MOROCCO.getCode().equals(code)) {
				// country is Morocco
				country = Country.MOROCCO.getName();
				pattern = Pattern.compile(Country.MOROCCO.getRegex());
				// choose Morocco pattern
				matcher = pattern.matcher(customer.getPhone());
			} else if (Country.MOZAMBIQUE.getCode().equals(code)) {
				// country is Mozambique
				country = Country.MOZAMBIQUE.getName();
				// choose Mozambique pattern
				pattern = Pattern.compile(Country.MOZAMBIQUE.getRegex());
				matcher = pattern.matcher(customer.getPhone());
			} else if (Country.UGANDA.getCode().equals(code)) {
				// country is Uganda
				country = Country.UGANDA.getName();
				// choose Uganda pattern
				pattern = Pattern.compile(Country.UGANDA.getRegex());
				matcher = pattern.matcher(customer.getPhone());
			} else {
				code = "NA";
				country = "NONE";
				matcher = null;
			}

			// matches the regular expression pattern for status 
			// to determine whether the phone is valid on invalid
			if (matcher != null && matcher.matches()) {
				status = "VALID";
			} else {
				status = "INVALID";
			}

			// setting the properties of customer
			customerDto.setId(customer.getId());
			customerDto.setName(customer.getName());
			customerDto.setCode(code);
			customerDto.setPhone(phone);
			customerDto.setCountry(country);
			customerDto.setStatus(status);

			//Add customer to customers list
			customersDto.add(customerDto);
		}

		return customersDto;
	}
}
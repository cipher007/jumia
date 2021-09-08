package com.jumia.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jumia.exercise.dto.CustomerDTO;
import com.jumia.exercise.entity.Customer;
import com.jumia.exercise.repository.CustomerRepository;
import com.jumia.exercise.validation.CustomersFilter;
import com.jumia.exercise.validation.PhoneNumberValidator;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = ExerciseApplication.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
class SQLiteUnitTest {

	@Autowired
	private CustomerRepository customerRepository;

	private PhoneNumberValidator phoneNumberValidator;
	
	private CustomersFilter customersFilter;

	@Test
	@DatabaseSetup("sample.db")
	void whenInitializedByDbUnit_thenFindsById() {
		Optional<Customer> customer = customerRepository.findById(40);
		assertThat(customer).isNotNull();
	}

	@Test
	void whenInitializedByDbUnit_thenFindAll() {
		List<Customer> customersList = customerRepository.findAll();
		assertThat(customersList).hasSize(41);
		
		phoneNumberValidator = new PhoneNumberValidator();
		
		List<CustomerDTO> customersDto = phoneNumberValidator.categorize(customersList);
		
		// Should not match
		assertThat(customersDto).isNotEqualTo(customersList);
		
		// But should have the same exact size
		assertThat(customersDto).hasSameSizeAs(customersList);
		
		customersFilter = new CustomersFilter();
		
		//list size should be 7
		List<CustomerDTO> result = customersFilter.filter(customersDto, "Cameroon", "valid");
		assertThat(result).hasSize(7);
		
		//List size should be 3
		result = customersFilter.filter(customersDto, "Morocco", "invalid");
		assertThat(result).hasSize(3);
		
		//List size after filter should be 8
		result = customersFilter.filter(customersDto, "MOZAMBIQUE", null);
		assertThat(result).hasSize(8);
		
		//List size after filter should be 9
		result = customersFilter.filter(customersDto, "EthIOPia", null);
		assertThat(result).hasSize(9);
		
		//List size after filter should be 7
		result = customersFilter.filter(customersDto, "uganda", null);
		assertThat(result).hasSize(7);
		
		//List size after filter should be 27
		result = customersFilter.filter(customersDto, "", "valid");
		assertThat(result).hasSize(27);
		
		//List size after filter should be 14
		result = customersFilter.filter(customersDto, "", "invalid");
		assertThat(result).hasSize(14);
		
		//List size after filter should be 0 - not a valid country filter keyword
		result = customersFilter.filter(customersDto, "test", "");
		assertThat(result).isEmpty();
		
		//List size after clear filter should be 41 (no filter)
		result = customersFilter.filter(customersDto, null, null);
		assertThat(result).hasSize(41);
	}

}
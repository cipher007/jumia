package com.jumia.exercise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jumia.exercise.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public boolean existsByPhone(String phone);

	public List<Customer> findByPhone(String phone);

	@Query("SELECT max(s.id) FROM Customer s")
	public Integer findMaxId();

}

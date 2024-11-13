package com.alessandragodoy.customerms.repository;

import com.alessandragodoy.customerms.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	List<Customer> findAll();
	Optional<Customer> findById(int id);

}

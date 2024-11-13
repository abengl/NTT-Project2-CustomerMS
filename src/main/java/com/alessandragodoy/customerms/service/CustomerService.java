package com.alessandragodoy.customerms.service;

import com.alessandragodoy.customerms.controller.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

 public interface CustomerService {
	 List<CustomerDTO> getAllCustomers();
	 Optional<CustomerDTO> getCustomerById(int id);
	 Optional<CustomerDTO> updateCustomerById(int id, CustomerDTO customerDTO);
	 CustomerDTO createCustomer(CustomerDTO customerDTO);
	 Optional<CustomerDTO> deleteCustomerById(int id);

}

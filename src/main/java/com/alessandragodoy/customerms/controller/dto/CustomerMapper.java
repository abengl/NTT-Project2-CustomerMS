package com.alessandragodoy.customerms.controller.dto;

import com.alessandragodoy.customerms.model.entity.Customer;

public class CustomerMapper {
	public static CustomerDTO entityToDTO(Customer customer) {
		return new CustomerDTO(
				customer.getId(),
				customer.getFirstName(),
				customer.getLastName(),
				customer.getDni(),
				customer.getEmail()
		);
	}

	public static Customer dtoUpdateToEntity(CustomerDTO customerDTO) {
		return Customer.builder()
				.id(customerDTO.id())
				.firstName(customerDTO.firstName())
				.lastName(customerDTO.lastName())
				.dni(customerDTO.dni())
				.email(customerDTO.email())
				.build();
	}

	public static Customer dtoCreateToEntity(CustomerDTO customerDTO) {
		return Customer.builder()
				.firstName(customerDTO.firstName())
				.lastName(customerDTO.lastName())
				.dni(customerDTO.dni())
				.email(customerDTO.email())
				.build();
	}
}

package com.alessandragodoy.customerms.configuration;

import com.alessandragodoy.customerms.model.entity.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) {
		if (customerRepository.count() == 0) {
			System.out.println("No customers found, creating initial customers...");
			List<Customer> initialCustomers = List.of(
					Customer.builder().firstName("John").lastName("Doe").dni("12345678")
							.email("john.doe@example.com").build(),
					Customer.builder().firstName("Jane").lastName("Smith").dni("87654321")
							.email("jane.smith@example.com").build(),
					Customer.builder().firstName("Alice").lastName("Brown").dni("11223344")
							.email("alice.brown@example.com").build(),
					Customer.builder().firstName("Bob").lastName("White").dni("44332211")
							.email("bob.white.@example.com").build(),
					Customer.builder().firstName("Charlie").lastName("Black").dni("33221144")
							.email("charlie.black@example.com").build()
			);

			customerRepository.saveAll(initialCustomers);
			System.out.println("Initial customers added to the database.");
		} else {
			System.out.println("Customers already exist, skipping initialization.");
		}
	}
}

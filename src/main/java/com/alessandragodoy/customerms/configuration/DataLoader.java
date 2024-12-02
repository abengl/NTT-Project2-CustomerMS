package com.alessandragodoy.customerms.configuration;

import com.alessandragodoy.customerms.model.Customer;
import com.alessandragodoy.customerms.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataLoader is a component that initializes the database with initial customer data
 * if no customers are found in the repository.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private final CustomerRepository customerRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

	@Override
	public void run(String... args) {
		if (customerRepository.count() == 0) {
			LOGGER.info("No customers found, creating initial customers...");
			List<Customer> initialCustomers = List.of(
					Customer.builder().firstName("Julia").lastName("Mendez").dni("11111111")
					.email("jmendez@mail.com").build(),
					Customer.builder().firstName("Alicia").lastName("Ramirez").dni("22222222")
					.email("aramirez@mail.com").build(),
					Customer.builder().firstName("Jose").lastName("Melendez").dni("33333333")
					.email("jmelendez@mail.com").build(),
					Customer.builder().firstName("Carla").lastName("Gomez").dni("44444444")
					.email("cgomez@mail.com").build(),
					Customer.builder().firstName("Juan").lastName("Perez").dni("55555555")
							.email("jperez@mail.com").build()
					);

			customerRepository.saveAll(initialCustomers);
			LOGGER.info("Initial customers added to the database.");
		} else {
			LOGGER.info("Customers already exist, skipping initialization.");
		}
	}
}

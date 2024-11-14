package com.alessandragodoy.customerms.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Banking system - Customer Microservice",
				description = "API for managing customers with CRUD operations",
				version = "1.0.0",
				contact = @Contact(
						name = "Alessandra Godoy",
						email = "dev@alessandragodoy.com"
				)
		))
public class OpenApiConfig {
}

package com.alessandragodoy.customerms.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Entity class representing a Customer.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Integer customerId;
	@NotNull
	@Column(name = "first_name")
	private String firstName;
	@NotNull
	@Column(name = "last_name")
	private String lastName;
	@NotNull
	@Column(unique = true)
	private String dni;
	@NotNull
	private String email;
}

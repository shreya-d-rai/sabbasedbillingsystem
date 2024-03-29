package com.webtree.slabbasedbillingsystem.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
	
	Integer customerId;
	
	@NotEmpty(message = "Customer name must not be empty")
	@NotNull(message = "Customer name must not be null")
	String customerName;
	
	@Pattern(regexp="\\d{10}", message="Contact number must be 10 digits")
	String contactNumber;
	
	Address address;

}

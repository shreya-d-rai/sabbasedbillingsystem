package com.webtree.slabbasedbillingsystem.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
	
	Integer addressId;
    
	@NotNull(message = "Street name must not be null")
    String street;
	
	@NotNull(message = "City must not be null")
    String city;

	@NotNull(message = "Zipcode must not be null")
    String zipCode;

}

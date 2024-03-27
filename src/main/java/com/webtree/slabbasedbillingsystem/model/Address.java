package com.webtree.slabbasedbillingsystem.model;

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
    
    String street;

    String city;

    String zipCode;

}

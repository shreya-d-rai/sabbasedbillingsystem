package com.webtree.slabbasedbillingsystem.model;

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
	
	String customerName;
	
	Long contactNumber;
	
	Address address;

}

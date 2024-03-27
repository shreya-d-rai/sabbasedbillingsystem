package com.webtree.slabbasedbillingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.service.SlabBasedBillingService;

@RestController
public class SlabBasedBillingController {

	@Autowired
	SlabBasedBillingService slabBasedBillingservice;
	
	@PostMapping("/customer")
	public ResponseEntity<String> addProduct(@RequestBody Customer customer){
		return new ResponseEntity<>(slabBasedBillingservice.saveCustomer(customer),HttpStatus.CREATED);		
	}
}

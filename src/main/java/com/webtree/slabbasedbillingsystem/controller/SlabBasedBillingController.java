package com.webtree.slabbasedbillingsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webtree.slabbasedbillingsystem.exception.SlabBasedBillingException;
import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.model.MeterReading;
import com.webtree.slabbasedbillingsystem.model.PriceSlab;
import com.webtree.slabbasedbillingsystem.service.SlabBasedBillingService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/slabbilling")
public class SlabBasedBillingController {

	@Autowired
	SlabBasedBillingService slabBasedBillingservice;
	
	@Operation(summary = "Register customer")
	@PostMapping("/customer")
	public ResponseEntity<String> registerCustomer(@RequestBody @Valid Customer customer){
		return new ResponseEntity<>(slabBasedBillingservice.saveCustomer(customer),HttpStatus.CREATED);		
	}
	
	@Operation(summary = "Define Price Slab")
	@PostMapping("/priceslab")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<PriceSlab> definePriceSlab(@RequestBody @Valid PriceSlab priceSlab){
		return new ResponseEntity<>(slabBasedBillingservice.savePriceSlab(priceSlab),HttpStatus.CREATED);	
	}
	
	@Operation(summary = "Record Current Reading")
	@PostMapping("/meterreading")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<MeterReading> recordMeterReading(@RequestBody @Valid MeterReading meterReading) throws SlabBasedBillingException {
		return new ResponseEntity<>(slabBasedBillingservice.saveMeterReading(meterReading),HttpStatus.CREATED);	
	}
	
	@Operation(summary = "Monthly bill")
	@GetMapping("/bill/{customerId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Double> calculateBill(@PathVariable Integer customerId,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws SlabBasedBillingException{
		List<MeterReading> readings = slabBasedBillingservice.getReadingsBetweenDates(customerId, startDate, endDate);
		int totalUnitsConsumed = readings.stream().mapToInt(MeterReading::getUnitsConsumed).sum();
		System.out.println("Units consumed: "+totalUnitsConsumed);
		double billAmount = slabBasedBillingservice.calculateBill(totalUnitsConsumed, endDate);
		return ResponseEntity.ok(billAmount);
	}
}

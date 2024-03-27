package com.webtree.slabbasedbillingsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webtree.slabbasedbillingsystem.entity.MeterReadingEntity;
import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.model.MeterReading;
import com.webtree.slabbasedbillingsystem.model.PriceSlab;
import com.webtree.slabbasedbillingsystem.service.SlabBasedBillingService;
import com.webtree.slabbasedbillingsystem.service.SlabBasedBillingServiceImpl;

@RestController
public class SlabBasedBillingController {

	@Autowired
	SlabBasedBillingService slabBasedBillingservice;
	
	@PostMapping("/customer")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
		return new ResponseEntity<>(slabBasedBillingservice.saveCustomer(customer),HttpStatus.CREATED);		
	}
	
	@PostMapping("/priceslab")
	public ResponseEntity<PriceSlab> definePriceSlab(@RequestBody PriceSlab priceSlab){
		return new ResponseEntity<>(slabBasedBillingservice.savePriceSlab(priceSlab),HttpStatus.CREATED);	
	}
	
	@PostMapping("/meterreading")
	public ResponseEntity<MeterReading> recordMeterReading(@RequestBody MeterReading meterReading){
		return new ResponseEntity<>(slabBasedBillingservice.saveMeterReading(meterReading),HttpStatus.CREATED);	
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Double> calculateBill(@PathVariable Integer customerId,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<MeterReading> readings = slabBasedBillingservice.getReadingsBetweenDates(customerId, startDate, endDate);
		int totalUnitsConsumed = readings.stream().mapToInt(MeterReading::getUnitsConsumed).sum();
		System.out.println("Units consumed: "+totalUnitsConsumed);
		double billAmount = slabBasedBillingservice.calculateBill(totalUnitsConsumed, endDate);
		return ResponseEntity.ok(billAmount);
	}
}

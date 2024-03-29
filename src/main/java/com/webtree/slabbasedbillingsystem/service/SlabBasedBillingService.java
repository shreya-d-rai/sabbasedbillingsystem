package com.webtree.slabbasedbillingsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.webtree.slabbasedbillingsystem.exception.SlabBasedBillingException;
import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.model.MeterReading;
import com.webtree.slabbasedbillingsystem.model.PriceSlab;

public interface SlabBasedBillingService {
	
	String saveCustomer(Customer customer);
	
	PriceSlab savePriceSlab(PriceSlab priceSlab);
	
	MeterReading saveMeterReading(MeterReading meterReading) throws SlabBasedBillingException ;
	
	List<MeterReading> getReadingsBetweenDates(Integer customerId, LocalDate startDate, LocalDate endDate) throws SlabBasedBillingException;
	
	double calculateBill(int unitsConsumed, LocalDate readingDate) throws SlabBasedBillingException;

}

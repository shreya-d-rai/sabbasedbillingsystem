package com.webtree.slabbasedbillingsystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webtree.slabbasedbillingsystem.entity.AddressEntity;
import com.webtree.slabbasedbillingsystem.entity.CustomerEntity;
import com.webtree.slabbasedbillingsystem.entity.MeterReadingEntity;
import com.webtree.slabbasedbillingsystem.entity.PriceSlabEntity;
import com.webtree.slabbasedbillingsystem.exception.SlabBasedBillingException;
import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.model.MeterReading;
import com.webtree.slabbasedbillingsystem.model.PriceSlab;
import com.webtree.slabbasedbillingsystem.repository.CustomerRepository;
import com.webtree.slabbasedbillingsystem.repository.MeterReadingRepository;
import com.webtree.slabbasedbillingsystem.repository.PriceSlabRepository;

@Service
public class SlabBasedBillingServiceImpl implements SlabBasedBillingService{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PriceSlabRepository priceSlabRepository;
	
	@Autowired
	MeterReadingRepository meterReadingRepository;

	@Override
	public String saveCustomer(Customer customer) {
		
		AddressEntity addEntity = AddressEntity.builder()
				.street(customer.getAddress().getStreet()).city(customer.getAddress().getCity()).zipCode(customer.getAddress().getZipCode()).build();
		CustomerEntity custEntity = CustomerEntity.builder()
				.customerName(customer.getCustomerName()).contactNumber(customer.getContactNumber()).address(addEntity).build();
		CustomerEntity cEntity = customerRepository.save(custEntity);
		return "Customer registered successfully! Your customer ID is "+cEntity.getCustomerId();
	}

	@Override
	public PriceSlab savePriceSlab(PriceSlab priceSlab) {
		PriceSlabEntity priceSlabEntity = PriceSlabEntity.builder()
				.startDate(priceSlab.getStartDate()).endDate(priceSlab.getEndDate()).slabRate(priceSlab.getSlabRate()).build();
		PriceSlabEntity priceSlabSave =  priceSlabRepository.save(priceSlabEntity);
		priceSlab.setPriceSlabId(priceSlabSave.getPriceSlabId());
		return priceSlab;
	}

	@Override
	public MeterReading saveMeterReading(MeterReading meterReading) throws SlabBasedBillingException {
		
		CustomerEntity customerEntity = customerRepository.findById(meterReading.getCustomerId()).orElseThrow(()-> new SlabBasedBillingException("Invalid Customer ID"));
		MeterReadingEntity meterReadingEntity = MeterReadingEntity.builder()
				.readingDate(meterReading.getReadingDate())
				.unitsConsumed(meterReading.getUnitsConsumed())
				.customer(customerEntity).build();
		MeterReadingEntity meterReadingSave = meterReadingRepository.save(meterReadingEntity);
		meterReading.setMeterReadingId(meterReadingSave.getMeterReadingId());
		return meterReading;
	}
	
	public List<MeterReading> getReadingsBetweenDates(Integer customerId, LocalDate startDate, LocalDate endDate) throws SlabBasedBillingException {
		List<MeterReadingEntity> unitConsumedEntity =  meterReadingRepository.findByCustomerAndReadingDateBetween(customerId, startDate, endDate);
		if(unitConsumedEntity.isEmpty()) {
			throw new SlabBasedBillingException("No unit data available for the given inputs");
		}
		List<MeterReading> unitConsumed = new ArrayList<>();
		unitConsumedEntity.forEach(u -> {
			MeterReading meter = MeterReading.builder()
					.customerId(customerId).meterReadingId(u.getMeterReadingId())
					.readingDate(u.getReadingDate()).unitsConsumed(u.getUnitsConsumed()).build();
			unitConsumed.add(meter);
		});
		return unitConsumed;
	}
	
	public double calculateBill(int unitsConsumed, LocalDate readingDate) throws SlabBasedBillingException {
        PriceSlabEntity priceSlab = priceSlabRepository.findByStartDateBeforeAndEndDateAfter(readingDate, readingDate);
        if (priceSlab == null) {
            throw new SlabBasedBillingException("Price slab not found for the given date");
        }
        return unitsConsumed * priceSlab.getSlabRate();
    }

}

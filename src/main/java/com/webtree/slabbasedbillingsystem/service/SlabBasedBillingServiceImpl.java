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
		customerRepository.save(custEntity);
		return "Customer registered successfully";
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
	public MeterReading saveMeterReading(MeterReading meterReading) {
		
		CustomerEntity customerEntity = customerRepository.findById(meterReading.getCustomerId()).get();
//		AddressEntity addressEntity = AddressEntity.builder()
//				.street(meterReading.getCustomer().getAddress().getStreet())
//				.city(meterReading.getCustomer().getAddress().getCity())
//				.zipCode(meterReading.getCustomer().getAddress().getZipCode()).build();
//		CustomerEntity customerEntity = CustomerEntity.builder()
//				.customerName(meterReading.getCustomer().getCustomerName())
//				.contactNumber(meterReading.getCustomer().getContactNumber())
//				.address(addressEntity).build();
		MeterReadingEntity meterReadingEntity = MeterReadingEntity.builder()
				.readingDate(meterReading.getReadingDate())
				.unitsConsumed(meterReading.getUnitsConsumed())
				.customer(customerEntity).build();
//		customerRepository.save(customerEntity);
		 MeterReadingEntity meterReadingSave = meterReadingRepository.save(meterReadingEntity);
		meterReading.setMeterReadingId(meterReadingSave.getMeterReadingId());
		return meterReading;
	}
	
	public List<MeterReading> getReadingsBetweenDates(Integer customerId, LocalDate startDate, LocalDate endDate) {
		List<MeterReadingEntity> unitConsumedEntity =  meterReadingRepository.findByCustomerAndReadingDateBetween(customerId, startDate, endDate);
		List<MeterReading> unitConsumed = new ArrayList<>();
		unitConsumedEntity.forEach(u -> {
			MeterReading meter = MeterReading.builder()
					.customerId(customerId).meterReadingId(u.getMeterReadingId())
					.readingDate(u.getReadingDate()).unitsConsumed(u.getUnitsConsumed()).build();
			unitConsumed.add(meter);
		});
		return unitConsumed;
	}
	
	public double calculateBill(int unitsConsumed, LocalDate readingDate) {
        // Fetch applicable slab for the given reading date
        PriceSlabEntity priceSlab = priceSlabRepository.findByStartDateBeforeAndEndDateAfter(readingDate, readingDate);
        if (priceSlab == null) {
            throw new RuntimeException("Price slab not found for the given date");
        }
        return unitsConsumed * priceSlab.getSlabRate();
    }

}

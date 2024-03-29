package com.webtree.slabbasedbillingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.webtree.slabbasedbillingsystem.entity.AddressEntity;
import com.webtree.slabbasedbillingsystem.entity.CustomerEntity;
import com.webtree.slabbasedbillingsystem.entity.MeterReadingEntity;
import com.webtree.slabbasedbillingsystem.entity.PriceSlabEntity;
import com.webtree.slabbasedbillingsystem.exception.SlabBasedBillingException;
import com.webtree.slabbasedbillingsystem.model.Address;
import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.model.MeterReading;
import com.webtree.slabbasedbillingsystem.model.PriceSlab;
import com.webtree.slabbasedbillingsystem.repository.CustomerRepository;
import com.webtree.slabbasedbillingsystem.repository.MeterReadingRepository;
import com.webtree.slabbasedbillingsystem.repository.PriceSlabRepository;

@SpringBootTest
public class SlabBasedBillingServiceImplTest {
	
	@Mock
    CustomerRepository customerRepository;

    @Mock
    PriceSlabRepository priceSlabRepository;

    @Mock
    MeterReadingRepository meterReadingRepository;

    @InjectMocks
    SlabBasedBillingServiceImpl slabBasedBillingService;
    
    Customer customer = new Customer();
    Address address = new Address();
    
//    @BeforeAll
//    void setUp() {
//    	address = Address.builder().addressId(1).street("Mockstreet").zipCode("1234").city("Mockcity").build();
//    	customer = Customer.builder().customerId(1).contactNumber("1234567890").customerName("Mockname").address(address).build();
//    }

    @Test
    public void saveCustomerTest() {
    	Address address = Address.builder().addressId(1).street("Mockstreet").zipCode("1234").city("Mockcity").build();
    	Customer customer = Customer.builder().customerId(1).contactNumber("1234567890").customerName("Mockname").address(address).build();
    	AddressEntity addEntity = AddressEntity.builder()
				.street(customer.getAddress().getStreet()).city(customer.getAddress().getCity()).zipCode(customer.getAddress().getZipCode()).build();
		CustomerEntity custEntity = CustomerEntity.builder()
				.customerName(customer.getCustomerName()).contactNumber(customer.getContactNumber()).address(addEntity).build();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(custEntity);
        String result = slabBasedBillingService.saveCustomer(customer);
        assertTrue(result.contains("Customer registered successfully! Your customer ID is"));
    }
    
    @Test
    public void savePriceSlabTest() {
    	PriceSlab priceSlab = PriceSlab.builder().priceSlabId(1).startDate(LocalDate.of(2024,1,8)).endDate(LocalDate.of(2024,1,14)).slabRate(40D).build();
    	PriceSlabEntity priceSlabEntity = PriceSlabEntity.builder().priceSlabId(priceSlab.getPriceSlabId()).startDate(priceSlab.getStartDate()).endDate(priceSlab.getEndDate()).build();
    	when(priceSlabRepository.save(any(PriceSlabEntity.class))).thenReturn(priceSlabEntity);
    	PriceSlab result = slabBasedBillingService.savePriceSlab(priceSlab);
        assertTrue(result.getSlabRate().equals(40D));		
    }
    
    @Test
    public void saveMeterReadingTest() throws SlabBasedBillingException {
    	Address address = Address.builder().addressId(1).street("Mockstreet").zipCode("1234").city("Mockcity").build();
    	Customer customer = Customer.builder().customerId(1).contactNumber("1234567890").customerName("Mockname").address(address).build();
    	AddressEntity addEntity = AddressEntity.builder()
				.street(customer.getAddress().getStreet()).city(customer.getAddress().getCity()).zipCode(customer.getAddress().getZipCode()).build();
		CustomerEntity custEntity = CustomerEntity.builder()
				.customerName(customer.getCustomerName()).contactNumber(customer.getContactNumber()).address(addEntity).build();
		MeterReading meterReading = MeterReading.builder().meterReadingId(1).readingDate(LocalDate.of(2024, 1, 1)).unitsConsumed(123).customerId(1).build();
		
		MeterReadingEntity meterReadingEntity = MeterReadingEntity.builder()
				.readingDate(meterReading.getReadingDate())
				.unitsConsumed(meterReading.getUnitsConsumed())
				.customer(custEntity).build();
    	when(customerRepository.findById(anyInt())).thenReturn(Optional.of(custEntity));
    	when(meterReadingRepository.save(any(MeterReadingEntity.class))).thenReturn(meterReadingEntity);
    	MeterReading result = slabBasedBillingService.saveMeterReading(meterReading);
        assertTrue(result.getUnitsConsumed().equals(123)); 
    	
    	assertThrows(SlabBasedBillingException.class, () -> {
             slabBasedBillingService.saveMeterReading(new MeterReading());
         });
    }
    
    @Test
    public void saveMeterReadingNegativeTest() {
    	when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
    	 assertThrows(SlabBasedBillingException.class, () -> {
             slabBasedBillingService.saveMeterReading(new MeterReading());
         });
    }
    
    @Test
    public void getReadingsBetweenDatesTest() throws SlabBasedBillingException {
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 3, 31);
        List<MeterReadingEntity> mockMeterReadings = new ArrayList<>();
        mockMeterReadings.add(new MeterReadingEntity());
        when(meterReadingRepository.findByCustomerAndReadingDateBetween(anyInt(), eq(startDate), eq(endDate)))
                .thenReturn(mockMeterReadings);
        List<MeterReading> result = slabBasedBillingService.getReadingsBetweenDates(1, startDate, endDate);
        assertFalse(result.isEmpty());
    }

    @Test
    public void getReadingsBetweenDatesNegativeTest() {
        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 3, 31);
        when(meterReadingRepository.findByCustomerAndReadingDateBetween(anyInt(), eq(startDate), eq(endDate)))
                .thenReturn(new ArrayList<>());
        assertThrows(SlabBasedBillingException.class, () -> {
            slabBasedBillingService.getReadingsBetweenDates(1, startDate, endDate);
        });
    }
    
    @Test
    public void calculateBillTest() throws SlabBasedBillingException {
        PriceSlabEntity mockPriceSlab = new PriceSlabEntity();
        mockPriceSlab.setSlabRate(10.0);
        when(priceSlabRepository.findByStartDateBeforeAndEndDateAfter(any(), any())).thenReturn(mockPriceSlab);
        
        double result = slabBasedBillingService.calculateBill(100, LocalDate.of(2024, 1, 1));
        assertEquals(1000.0, result);
    }
    
    @Test
    public void calculateBillNegativeTest() {
        when(priceSlabRepository.findByStartDateBeforeAndEndDateAfter(any(), any())).thenReturn(null);
        SlabBasedBillingException exception = assertThrows(SlabBasedBillingException.class, () -> {
            slabBasedBillingService.calculateBill(100, LocalDate.of(2024, 1, 1));
        });
        assertEquals("Price slab not found for the given date", exception.getMessage());
    }

}

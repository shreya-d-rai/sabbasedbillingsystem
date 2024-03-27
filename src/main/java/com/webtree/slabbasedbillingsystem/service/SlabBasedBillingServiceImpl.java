package com.webtree.slabbasedbillingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webtree.slabbasedbillingsystem.entity.AddressEntity;
import com.webtree.slabbasedbillingsystem.entity.CustomerEntity;
import com.webtree.slabbasedbillingsystem.model.Customer;
import com.webtree.slabbasedbillingsystem.repository.CustomerRepository;

@Service
public class SlabBasedBillingServiceImpl implements SlabBasedBillingService{
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public String saveCustomer(Customer customer) {
		
		AddressEntity addEntity = AddressEntity.builder().addressId(customer.getAddress().getAddressId())
				.street(customer.getAddress().getStreet()).city(customer.getAddress().getCity()).zipCode(customer.getAddress().getZipCode()).build();
		CustomerEntity custEntity = CustomerEntity.builder().customerId(customer.getCustomerId())
				.customerName(customer.getCustomerName()).contactNumber(customer.getContactNumber()).address(addEntity).build();
		customerRepository.save(custEntity);
		return "Customer registered successfully";
	}

}

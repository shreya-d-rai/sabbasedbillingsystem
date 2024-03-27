package com.webtree.slabbasedbillingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtree.slabbasedbillingsystem.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>{

}

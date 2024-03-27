package com.webtree.slabbasedbillingsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webtree.slabbasedbillingsystem.entity.MeterReadingEntity;


public interface MeterReadingRepository extends JpaRepository<MeterReadingEntity, Integer>{
	
	@Query("select p from MeterReadingEntity p where p.customer.customerId = ?1 and readingDate between ?2 and ?3")
	List<MeterReadingEntity> findByCustomerAndReadingDateBetween(Integer customerId, LocalDate startDate, LocalDate endDate);
	
	

}

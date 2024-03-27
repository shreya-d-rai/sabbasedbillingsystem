package com.webtree.slabbasedbillingsystem.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtree.slabbasedbillingsystem.entity.PriceSlabEntity;

public interface PriceSlabRepository extends JpaRepository<PriceSlabEntity, Integer>{
	
	PriceSlabEntity findByStartDateBeforeAndEndDateAfter(LocalDate readingDate,LocalDate readingDate1);

}

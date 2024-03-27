package com.webtree.slabbasedbillingsystem.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "price_slab")
public class PriceSlabEntity {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "price_slab_id")
	 Integer priceSlabId;
	 
	 @Column(name = "start_date")
	 LocalDate startDate;
	 
	 @Column(name = "end_date")
	 LocalDate endDate;
	 
	 @Column(name = "slab_rate")
	 Double slabRate;

}

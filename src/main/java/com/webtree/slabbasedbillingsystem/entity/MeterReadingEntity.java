package com.webtree.slabbasedbillingsystem.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "meter_reading")
public class MeterReadingEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer meterReadingId;

	@Column(name = "reading_data")
	LocalDate readingDate;
	
	@Column(name = "units_consumed")
    Integer unitsConsumed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    CustomerEntity customer;
}

package com.webtree.slabbasedbillingsystem.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterReading {
	
	Integer meterReadingId;

	LocalDate readingDate;
	
    Integer unitsConsumed;

    Integer customerId;

}

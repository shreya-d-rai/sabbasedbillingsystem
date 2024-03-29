package com.webtree.slabbasedbillingsystem.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
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
	
	@NotNull(message = "Units consumed must not be null")
    Integer unitsConsumed;

	@NotNull(message = "Customer ID must not be null")
    Integer customerId;

}

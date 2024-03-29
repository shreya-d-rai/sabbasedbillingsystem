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
public class PriceSlab {
	
	 Integer priceSlabId;

	 LocalDate startDate;

	 LocalDate endDate;

	 @NotNull(message = "Slab Rate must not be null")
	 Double slabRate;

}

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
public class PriceSlab {
	
	 Integer priceSlabId;

	 LocalDate startDate;

	 LocalDate endDate;

	 Double slabRate;

}

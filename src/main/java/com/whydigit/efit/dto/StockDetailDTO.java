package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailDTO {
	private Long refPsId;
	private Long orgId;
	private int emitterStoreDays;
	private int emitterLineDays;
	private int inTransitDays;
	private int receiverLineStorageDays;
	private int receiverManufacturingLineDays;
	private int otherStorageDays;
	private int reverseLogisticsDay;
	private int totalCycleTime;
}

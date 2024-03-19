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
	private Integer emitterStoreDays;
	private Integer emitterLineDays;
	private Integer inTransitDays;
	private Integer receiverLineStorageDays;
	private Integer receiverManufacturingLineDays;
	private Integer otherStorageDays;
	private Integer reverseLogisticsDay;
	private Integer totalCycleTime;
}

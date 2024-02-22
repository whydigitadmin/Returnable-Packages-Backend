package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailDTO {
	private Long refPsId;
	private String partStudyId;
	private long orgId;
	private String emitterStoreDays;
	private String emitterLineDays;
	private String inTransitDays;
	private String endUserLineStorageDays;
	private String endUserManufacturingLineDays;
	private String otherStorageDays;
	private String totalCycleTime;
	private String emptyPackagingReverseDays;
}

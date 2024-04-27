package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsDTO {
	private Long refPsId;
	private Long orgId;
	private int avgLotSize;
	private int dispatchFrequency;
	private String diapatchTo;
}

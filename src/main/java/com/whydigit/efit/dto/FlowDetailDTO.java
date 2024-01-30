package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowDetailDTO {
	private String kitName;
	private String emitter;
	private long partNumber;
	private String partName;
	private String cycleTime;
	private String subReceiver;
	private boolean active;
	// private String fixedRentalCharge;
	// private String dhr;
	// private String issueCharge;
	// private String returnCharge;
	// private FlowDTO flowDTO;
	// privat e String itemGroup;
	// private String productToPack;
	// private int quantity;
	// private String rentalTerm;

}

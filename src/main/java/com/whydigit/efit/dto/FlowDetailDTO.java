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
	
	private int id;
	private String itemGroup;
	private String productToPack;
	private int quantity;
	private String rentalTerm;
	private String cycleTime;
    private boolean active;
    private String fixedRentalCharge;
    private String dhr;
    private String issueCharge;
    private String returnCharge;
    private FlowDTO flowDTO;
    
    

}


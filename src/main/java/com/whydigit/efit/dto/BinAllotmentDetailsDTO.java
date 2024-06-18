package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinAllotmentDetailsDTO {
	
	private String tagCode;
	private String rfId;
	private String asset;
	private String assetCode;
	private int qty;

}

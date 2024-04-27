package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinInwardDetailsDTO {
	
	private String asset;
	private String assetCode;
	private int recievedQty;
}

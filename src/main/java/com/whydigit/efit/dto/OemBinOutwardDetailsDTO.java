package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinOutwardDetailsDTO {
	
	private Long id;
	private String asset;
	private String assetCode;
	private String category;
	private int availqty;
	private int outQty;

}

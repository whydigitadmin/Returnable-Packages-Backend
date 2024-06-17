package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GathereingEmptyDetailsDTO {
	
	private String assetName;
	private String assetCode;
	private String category;
	private int availqty;
	private int EmptyQty;
}

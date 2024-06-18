package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GathereingEmptyDetailsDTO {
	private String assetType;
	private String assetCode;
	private int expQty;
	private int emptyQty;
}

package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitAssetDTO {
	private String assetCategory;
	private long assetCodeId;
	private String assetName;
	private String quantity;
	
}

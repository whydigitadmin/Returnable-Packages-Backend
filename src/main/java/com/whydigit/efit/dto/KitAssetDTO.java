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
	private Long id;
	private String assetType;
	private String assetCategory;
	private String categoryCode;
	private String assetCodeId;
	private String assetDesc;
	private int quantity;
}

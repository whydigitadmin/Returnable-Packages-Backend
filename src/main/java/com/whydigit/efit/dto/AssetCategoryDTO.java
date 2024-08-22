package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryDTO {

	private Long id;
	private boolean active;
	private Long orgId;
	private String assetType;
	private String category;
	private String categoryCode;
	private String modifiedBy;

}

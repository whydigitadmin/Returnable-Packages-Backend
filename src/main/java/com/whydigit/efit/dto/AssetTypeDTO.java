package com.whydigit.efit.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTypeDTO {
	
	private Long id;
	private Long orgId;
	private String assetType;
	private String typeCode;
	private String modifiedby;
	private boolean active;
	
	

}

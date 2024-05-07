package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetInwardDTO {

	private Long orgId;
	private LocalDate docDate;
	private String sourceFrom; 
	private String stockBranch;
	
	private List<AssetInwardDetailDTO> assetInwardDetailDTO;
}

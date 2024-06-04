package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

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
	private String createdBy;
	private String category;
	private String assetCode;
	private int qty;
	
	private List<AssetInwardDetailDTO> assetInwardDetailDTO;
}

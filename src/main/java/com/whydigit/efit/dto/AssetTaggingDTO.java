package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTaggingDTO {
	
	private String docId;
	private LocalDate docDate;
	private String assetCode;
	private String asset;
	private int seqFrom;
	private int seqTo;
	private Long orgId;
	private String createdBy;
	
	private List<AssetTaggingDetailsDTO>taggingDetailsDTO;
	

}

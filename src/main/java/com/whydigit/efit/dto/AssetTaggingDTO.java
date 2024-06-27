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
public class AssetTaggingDTO {
	
	private String docId;
	private LocalDate docDate;
	private String category;
	private String assetCode;
	private String asset;
	private int seqFrom;
	private int seqTo;
	private Long orgId;
	private String createdBy;
	private String poNo;
	private LocalDate poDate;
	
	private List<AssetTaggingDetailsDTO>taggingDetailsDTO;
	

}

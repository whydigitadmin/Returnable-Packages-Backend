package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockBranchDTO {
	
	private Long id;
	private String branch;
	private String branchCode;
	private Long orgId;
	private String createdby;
	private boolean active;
}

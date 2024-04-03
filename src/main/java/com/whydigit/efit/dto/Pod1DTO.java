package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pod1DTO {
	private Long podiId;
	private String assetCode;
	private String description;
	private int allotQty;
	private int acceptQty;
	
}

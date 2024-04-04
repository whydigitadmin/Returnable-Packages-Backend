package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pod2DTO {
	private Long pod2Id;
	private String assetCode;
	private String description;
	private int rejectedQty;
	private int returnQty;

}

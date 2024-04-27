package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinInwardDetailsDTO {

	private String tagCode;

	private String asset;

	private String assetCode;

	private int allotQty;

	private int recQty;

}


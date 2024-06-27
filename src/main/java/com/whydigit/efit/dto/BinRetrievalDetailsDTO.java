package com.whydigit.efit.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinRetrievalDetailsDTO {

	private String category;
	private String assetCode;
	private String asset;
	private int invqty;
	private int recqty;

}

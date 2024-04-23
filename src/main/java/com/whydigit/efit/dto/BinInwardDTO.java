package com.whydigit.efit.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinInwardDTO {

	private Long orgId;
	
	private Long binInwardId;
	
    private String allotmentNo;
	
	private String reqNo;
	
	private String flow;
	
	private String finYr;
	
	private String kitCode;
	
	private int allotedQty; 
	
	private List<BinInwardDetailsDTO> binInwardDetailsDTO;
}

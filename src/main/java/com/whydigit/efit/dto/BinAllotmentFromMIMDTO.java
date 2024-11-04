package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinAllotmentFromMIMDTO {
	
	private String createdby;
	private String binReqNo;
	private LocalDate binReqDate;
	private String emitter;
	private Long emitterId;
	private Long orgId;
	private String flow;
	private Long flowId;
	private String kitCode;
	private int reqKitQty;
	private int allotKitQty;
	
	

}

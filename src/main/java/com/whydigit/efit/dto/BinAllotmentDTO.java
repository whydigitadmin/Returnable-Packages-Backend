package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinAllotmentDTO {

	private String createdby;
	private LocalDate docDate;
	private String binReqNo;
	private String stockBranch;
	private LocalDate binReqDate;
	private Long emitterId;
	private String partName;
	private Long orgId;
	private String flow;
	private Long flowId;
	private String partCode;
	private String kitCode;
	private int reqKitQty;
	private int avlKitQty;
	private int allotKitQty;

	private List<BinAllotmentDetailsDTO> binAllotmentDetailsDTO;

}

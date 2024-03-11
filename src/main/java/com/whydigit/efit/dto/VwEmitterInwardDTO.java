package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VwEmitterInwardDTO {
	private long requestId;
	private long ordId;
	private LocalDate requestedDate;
	private LocalDate demandDate;
	private long emitterId;
	private String flowTo;
	private String flowName;
	private long kitQty;
	private String partName;
	private long partQty;
	private LocalDate reachedDate;
	private long issueItemStatus;
	private long issuedQty;
	private long netQtyRecieved;
	private long returnQty;
	private String status;
}

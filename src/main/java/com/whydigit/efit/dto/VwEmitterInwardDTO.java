package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private String flowId;
	private String kitName;
	private String flowName;
	private long kitQty;
	private String partName;
	private long partQty;
	private LocalDate reachedDate;
	private long issueItemStatus;
	private long issuedQty;
	private long netQtyRecieved;
	private long returnQty;
	private long issueItemInwardId;
	private long itemId;
	private String partNo;
	private String status;
	private LocalDateTime approvedDate;
	private boolean approvedStatus;
	private boolean netRecAcceptStatus;
}

package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequestQtyApprovelDTO {
	private Long issueRequestId;
	private Long issueItemId;
	private int issuedQty;
	private Long approvedId;
	private String approverName;
}

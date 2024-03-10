package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueItemDTO {

	private String kitName;
	private int kitQty;
	private String partNo;
	private int partQty;
	private int issueItemStatus;
	private String remark;

}

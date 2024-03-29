package com.whydigit.efit.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequestQtyApprovelDTO {
	private Long issueRequestId;
	List<IssueRequestItemApprovelDTO> issueRequestItemApprovelDTO= new ArrayList<>();
	private Long approvedId;
	private String approverName;
}

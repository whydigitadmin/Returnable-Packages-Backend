package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestDTO {

	private long emitterId;
	private long orgId;
	private long reqAddressId;
	private Long flowTo;
	private LocalDate demandDate;
	private IssueRequestType irType;
	private String remark;
	private List<IssueItemDTO> issueItemDTO = new ArrayList<>();
}

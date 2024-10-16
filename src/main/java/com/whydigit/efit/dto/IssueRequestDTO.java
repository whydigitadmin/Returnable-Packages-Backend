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

	private Long emitterId;
	private LocalDate requestDate;
	private Long orgId;
	private long reqAddressId;
	private Long flowTo;
	private LocalDate demandDate;
	private IssueRequestType irType;
	private String remark;
	private String createdBy;
	private List<IssueItemDTO> issueItemDTO = new ArrayList<>();
}

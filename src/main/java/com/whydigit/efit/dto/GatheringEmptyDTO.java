package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatheringEmptyDTO {
	private String docId;
	private Long orgId;
	private LocalDate docDate;
	private String stockBranch;
	private String createdBy;
	private String modifiedBy;
	private Long receiverId;
	private List<GathereingEmptyDetailsDTO> gathereingEmptyDetailsDTO;

}

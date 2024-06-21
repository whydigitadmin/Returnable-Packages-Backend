package com.whydigit.efit.dto;

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
	private String stockBranch;
	private String createdBy;
	private String modifiedBy;
	private List<GathereingEmptyDetailsDTO> gathereingEmptyDetailsDTO;

}

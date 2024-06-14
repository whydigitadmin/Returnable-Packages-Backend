package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinOutwardDTO {

	private Long id;
	private LocalDate docDate;
	private String stockBranch;
	private String createdby;
	private Long orgId;
	private Long emitterId;
	private List<OemBinOutwardDetailsDTO> oemBinOutwardDetails;
}

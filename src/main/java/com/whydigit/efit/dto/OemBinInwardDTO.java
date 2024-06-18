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
public class OemBinInwardDTO {
	private String docId;
	private LocalDate docDate;
	private Long flowId;
	private String dispatchId;
	private String invoiceNo;
	private Long orgId;
	private String createdBy;
	private LocalDate invoiceDate;
	private Long emitterId;
	// private String updatedBy;

	private List<OemBinInwardDetailsDTO> oemBinInwardDetails;
}

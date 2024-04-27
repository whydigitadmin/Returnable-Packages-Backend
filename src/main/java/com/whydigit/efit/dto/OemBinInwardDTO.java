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
	private LocalDate docDate;
	private String kitno;
	private int recievedKitQty;
	private String createdBy;
	private Long orgId;

	private List<OemBinInwardDetailsDTO> oemBinInwardDetails;
}

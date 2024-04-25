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
	private String kit;
	private int outwardKitQty;
	private String createdBy;
	private Long orgId;

	private List<OemBinOutwardDetailsDTO> oemBinOutwardDetails;
}

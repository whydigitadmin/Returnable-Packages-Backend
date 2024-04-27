package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProofOfDeliveryDTO {

	private String docId;
	private long orgId;
	private LocalDate docDate;
	private String rfNo;
	private LocalDate rfDate;
	private String kitCode;
	private int kitQty;
	private int kitRQty;
	private String createdBy;
		
}

package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinOutwardDTO {
	private LocalDate docDate;
	private Long flowid;
	private String kitNo;
	private int outwardKitQty;
	private String createdBy;
	private Long orgId;
	private String destination;
	private String receiver;
	private String orgin;
	private Long emitterId;
	private String emitter;
	private String partCode;
	private String partName;

	private List<BinOutwardDetailsDTO> binOutwardDetailsDTO;

	

	
}

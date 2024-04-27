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
public class BinOutwardDTO {
	private LocalDate docDate;
	private String flow;
	private String kit;
	private int outwardKitQty;
	private String createdBy;
	private Long orgId;
	private String destination;
	private String reciever;
	private String orgin;
	private Long emitterId;
	private String emitter;

	private List<BinOutwardDetailsDTO> binOutwardDetailsDTO;

	
}

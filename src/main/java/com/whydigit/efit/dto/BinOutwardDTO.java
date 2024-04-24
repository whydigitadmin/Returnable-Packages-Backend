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
	private LocalDate docdate;
	private String flow;
	private String kit;
	private int outwardkitqty;

	private List<BinOutwardDetailsDTO> binOutwardDetails;
}

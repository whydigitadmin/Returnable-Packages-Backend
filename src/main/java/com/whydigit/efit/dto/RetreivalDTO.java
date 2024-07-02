package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetreivalDTO {

	private LocalDate docDate;
	private String fromStockBranch;
	private String toStockBranch;
	private String createdby;
	private Long orgId;
	private Long receiverId;
	private List<RetreivalDetailsDTO> retreivalDetailsDTO;

}

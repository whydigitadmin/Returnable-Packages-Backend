package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DmapDTO {
	
	private String finYear;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate extDate;
	private Long orgId;
	
	
	private List<DmapDetailsDTO> dmapDetailsDTO;

}

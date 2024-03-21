package com.whydigit.efit.controller;

import java.time.LocalDate;
import java.util.List;

import com.whydigit.efit.dto.DmapDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DmapDTO {
	private long orgId;
	private char finYear;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate extendedDate;
	
	private List<DmapDetailsDTO> dmapDetailsDTO;
}

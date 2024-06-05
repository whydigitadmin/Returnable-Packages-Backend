package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDetailsDTO {
	
	private Long id;
	private String binOutDocid;
	private LocalDate binOutDocDate;
	private String partName;
	private String partNo;
	private String kitNo;
	private int qty;
	

}

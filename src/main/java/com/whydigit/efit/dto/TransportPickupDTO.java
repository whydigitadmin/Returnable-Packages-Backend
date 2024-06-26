package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportPickupDTO {
	
	private LocalDate docDate;
	private String fromStockBranch;
	private String toStockBranch;
	private String transPortDocNo;
	private Long transPorterId;
	private String transPorter;
	private String handoverby;
	private String driverName;
	private String driverPhoneNo;
	private String vechicleNo;
	private String rmNo;
	private LocalDate rmDate;
	private String createdby;
	private Long orgId;
	private Long receiverId;
	
	private List<TransportPickupDetailsDTO> transportPickupDetailsDTO;

}

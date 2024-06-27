package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import com.whydigit.efit.entity.BinRetrievalDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinRetrievalDTO {
	
	private LocalDate docDate;
	private String retrievalWarehouse;
	private String fromStockBranch;
	private String toStockBranch;
	private String pickupDocId;
	private LocalDate pickupDate;
	private String transPortDocNo;
	private String transPorter;
	private String driverName;
	private String handOverBy;
	private String driverPhoneNo;
	private String vechicleNo;
	private String createdby;
	private Long orgId;
	
	private List<BinRetrievalDetailsDTO>binRetrievalDetailsDTO;

}

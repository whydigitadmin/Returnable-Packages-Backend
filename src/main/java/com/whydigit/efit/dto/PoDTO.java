package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoDTO {
	private Long poId;
	private String sCode; 
	private Long orgId;
	private String company;
	private String address;
	private String selfGst;
	private String poNo;
	private LocalDate date;
	private String apId;
	private String apAddress;
	private String apGst;
	private String shipTo;
	private String shipToRemarks;
	private String gstType;
	private Float iGst;
	private Float cGst;
	private Float sGst;
	private String terms;
	private int total;
	private String ap;
	private boolean cancel;
	private boolean active;
	
	private List<Po1DTO> po1DTO;

}

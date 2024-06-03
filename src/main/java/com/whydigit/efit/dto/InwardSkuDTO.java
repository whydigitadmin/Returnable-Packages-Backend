package com.whydigit.efit.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InwardSkuDTO {
	private Long id;
	private Long retManifestNo;
	private Date rmDate;
	private String kitNo;
	private String flowName;
	private String transporter;
	private String wayBillNo;
	private Long dehireQty;
	private Long netRecdQty;
	private Long repair;
	private Long scrap;
	private Long tat;
	private String partName;
	private String partNo;
}

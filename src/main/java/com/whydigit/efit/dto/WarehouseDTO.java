package com.whydigit.efit.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {
	private Long warehouseId;
	private long orgId;
	@NotEmpty(message = "Location name is required")
	private String locationName;
	private String address;
	private String state;
	private String pincode;
	@NotEmpty(message = "Unit is required")
	private String unit;
	private String code;
	private String city;
	private String country;
	private String createdBy;
	private String gst;
	private boolean active;
	private String stockBranch;
	
}

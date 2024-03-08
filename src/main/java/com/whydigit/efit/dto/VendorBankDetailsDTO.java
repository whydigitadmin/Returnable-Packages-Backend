package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetailsDTO {
	private Long id;
	private Long orgId;
	private String bank;
	private String displayName;
	private String ifscCode;
	private Long accountNum;
	private String branch;

}

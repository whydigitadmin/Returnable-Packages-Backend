package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankDetailsDTO {
	private Long id;
	private String bank;
	private String accountName;
	private String ifscCode;
	private String accountNo;
	private String branch;
	private Long vendorId;

}

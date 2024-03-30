package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersBankDetailsDTO {

	private Long id;
	private String bank;
	private String accountName;
	private String ifscCode;
	private String branch;
	private Long accountNo;
	private boolean isDefault;
	private String customerid;
}

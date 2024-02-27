package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersDTO {

	private Long id;
	private long orgId;
	private int customerType;
	private String customerCode;
	private String entityLegalName;
	private String displayName;
	private String email;
	private long phoneNumber;
	private boolean customerActivatePortal;
	private boolean active;
}

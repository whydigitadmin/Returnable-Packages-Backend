package com.whydigit.efit.dto;

import java.util.List;

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
	private String entityLegalName;
	private String displayName;
	private String email;
	private String phoneNumber;
	private boolean customerActivatePortal;
	private boolean active;
	
	private List<CustomersAddressDTO>customerAddressDTO;
	
	private List<CustomersBankDetailsDTO>customerBankDetailsDTO;
}

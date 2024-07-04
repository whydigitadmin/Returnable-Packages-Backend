package com.whydigit.efit.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
	private Long id;
	private long orgId;
	private String venderType;
	private String displyName;
	private String phoneNumber;
	private String entityLegalName;
	private String email;
	private String country;
	private boolean venderActivePortal;
	private boolean active;
	private String createdBy;
	private String modifiedby;
	
	
	private List<VendorAddressDTO>vendorAddressDTO;
	
	private List<VendorBankDetailsDTO>vendorBankDetailsDTO;

}

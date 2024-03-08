package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorAddressDTO {
	private Long id;
	private long orgId;
	private String gstRegistrationStatus;
	private String street1;
	private String street2;
	private String state;
	private String city;
	private String pincode;
	private String contactName;
	private String phone;
	private String destination;
	private long gstNumber;
	private String eMail;
	private Long vendorId;
}

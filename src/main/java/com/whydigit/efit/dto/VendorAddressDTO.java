package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorAddressDTO {
	private Long id;
	private String gstRegistrationStatus;
	private String street1;
	private String street2;
	private String pinCode;
	private String phoneNumber;
	private String gstNumber;
	private String city;
	private String contactName;
	private String state;
	private String email;
	private String designation;
	private String vendorid;
	private String country;
}

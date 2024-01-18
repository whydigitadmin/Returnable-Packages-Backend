package com.whydigit.efit.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {

	@NotBlank(message = "Organization name is required")
	private String orgName;
	private String phoneNumber;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String orgLogo;
 	
}

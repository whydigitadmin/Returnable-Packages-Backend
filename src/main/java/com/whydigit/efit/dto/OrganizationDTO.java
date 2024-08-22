package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {

	private Long id;

	private String createdby;

	private String orgName;

	private String phoneNumber;

	private String city;

	private String state;

	private String pinCode;

	private String country;

	private String orgLogo;

	private String code;

	private String email;

	private String address;

	private String password;

	private boolean active;
	
	private String adminFirstName;

}

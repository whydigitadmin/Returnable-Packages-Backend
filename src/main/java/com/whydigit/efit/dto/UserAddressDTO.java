package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDTO {

	private String location;
	private String address1;
	private String address2;
	private String country;
	private String state;
	private String pin;
	private String pNum;
}

package com.whydigit.efit.dto;

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
	private boolean venderActivePortal;
	private boolean active;

}

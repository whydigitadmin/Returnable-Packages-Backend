package com.whydigit.efit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserFormDTO {

	@NotBlank(message = "First Name is required")
	private String firstName;
	private Long userId;
	private String lastName;
	private Long emitterId;
	private String userName;
	private Long[] accessaddId;
	private Long[] accessWarehouse;
	private Long[] accessFlowId;
	private Long pNo;
	private String createdBy;
	private boolean isActive;

	@NotBlank(message = "Email is required")
	@Size(max = 30)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 100, message = "Password is required")
	private String password;

	private Role role;

	private long orgId;

	private UserAddressDTO userAddressDTO;

	private long accessRightsRoleId;
}

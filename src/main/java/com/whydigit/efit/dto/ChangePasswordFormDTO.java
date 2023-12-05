
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
public class ChangePasswordFormDTO {

	@NotBlank(message = "Email is Required")
	@Size(max = 30)
	@Email
	private String userName;

	@NotBlank
	@Size(min = 6, max = 100, message = "Old Password is required")
	private String oldPassword;

	@NotBlank
	@Size(min = 6, max = 100, message = "New Password is required")
	private String newPassword;
}

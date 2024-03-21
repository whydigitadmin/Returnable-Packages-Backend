package com.whydigit.efit.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.whydigit.efit.entity.AccessRightsVO;
import com.whydigit.efit.entity.UserAddressVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private long userId;
	private long orgId;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private boolean loginStatus;
	private boolean isActive;
	@Enumerated(EnumType.STRING)
	private Role role;
	private Date accountRemovedDate;
	private String token;
	private String tokenId;
	private String lastLogin;
	private AccessRightsVO accessRightsVO;
	private UserAddressVO userAddressVO;
}

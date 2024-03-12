package com.whydigit.efit.service;

import java.util.Optional;

import com.whydigit.efit.dto.ChangePasswordFormDTO;
import com.whydigit.efit.dto.CreateOrganizationFormDTO;
import com.whydigit.efit.dto.CreateUserFormDTO;
import com.whydigit.efit.dto.LoginFormDTO;
import com.whydigit.efit.dto.RefreshTokenDTO;
import com.whydigit.efit.dto.ResetPasswordFormDTO;
import com.whydigit.efit.dto.UserResponseDTO;
import com.whydigit.efit.entity.UserVO;
import com.whydigit.efit.exception.ApplicationException;

public interface AuthService {

	public void signup(CreateOrganizationFormDTO createOrganizationFormDTO);

	public UserResponseDTO login(LoginFormDTO loginRequest);

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;

	public void createUser(CreateUserFormDTO createUserFormDTO) throws ApplicationException;

	public Optional<UserVO> getUserById(Long userId);

	public UserVO updateUser(CreateUserFormDTO createUserFormDTO) throws ApplicationException;

}

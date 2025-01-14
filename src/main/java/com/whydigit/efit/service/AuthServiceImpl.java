package com.whydigit.efit.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.ChangePasswordFormDTO;
import com.whydigit.efit.dto.CreateOrganizationFormDTO;
import com.whydigit.efit.dto.CreateUserFormDTO;
import com.whydigit.efit.dto.LoginFormDTO;
import com.whydigit.efit.dto.OrganizationDTO;
import com.whydigit.efit.dto.RefreshTokenDTO;
import com.whydigit.efit.dto.ResetPasswordFormDTO;
import com.whydigit.efit.dto.Role;
import com.whydigit.efit.dto.UserAddressDTO;
import com.whydigit.efit.dto.UserResponseDTO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.OrganizationVO;
import com.whydigit.efit.entity.TokenVO;
import com.whydigit.efit.entity.UserAddressVO;
import com.whydigit.efit.entity.UserVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AccessRightsRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.OrganizationRepo;
import com.whydigit.efit.repo.TokenRepo;
import com.whydigit.efit.repo.UserActionRepo;
import com.whydigit.efit.repo.UserAddressRepo;
import com.whydigit.efit.repo.UserRepo;
import com.whydigit.efit.security.TokenProvider;
import com.whydigit.efit.util.CryptoUtils;

@Service
public class AuthServiceImpl implements AuthService {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserActionRepo userActionRepo;

	@Autowired
	TokenProvider tokenProvider;
	
	@Autowired
	FlowRepo flowRepo;

	@Autowired
	TokenRepo tokenRepo;

	@Autowired
	UserService userService;

	@Autowired
	OrganizationRepo organizationRepo;

	@Autowired
	UserAddressRepo userAddressRepo;

	@Autowired
	AccessRightsRepo accessRightsRepo;
	@Autowired
	CustomersRepo customersRepo;

	@Transactional
	@Override
	public void signup(CreateOrganizationFormDTO createOrganizationFormDTO) {
		String methodName = "createUser()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(createOrganizationFormDTO) || StringUtils.isBlank(createOrganizationFormDTO.getEmail())
				|| StringUtils.isBlank(createOrganizationFormDTO.getOrganizationDTO().getOrgName())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_ORGANIZATION_REGISTER_INFORMATION);
		} else if (userRepo.existsByEmail(createOrganizationFormDTO.getEmail())) {
			throw new ApplicationContextException(
					UserConstants.ERRROR_MSG_ORGANIZATION_USER_INFORMATION_ALREADY_REGISTERED);
		} else if (organizationRepo.existsByName(createOrganizationFormDTO.getOrganizationDTO().getOrgName())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_ORGANIZATION_INFORMATION_ALREADY_REGISTERED);
		}
		UserVO userVO = getUserVOFromCreateOrganizationFormDTO(createOrganizationFormDTO);
		userVO.setAccessRightsRoleId(1);
		userVO.setOrganizationVO(
				organizationRepo.save(getOrganizationVOFromCreateOrganizationFormDTO(createOrganizationFormDTO)));
		userVO.setUserAddressVO(
				userAddressRepo.save(getAddressVOFromCreateOrganizationFormDTO(createOrganizationFormDTO)));
		userRepo.save(userVO);
		userService.createUserAction(userVO.getEmail(), userVO.getUserId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	private UserAddressVO getAddressVOFromCreateOrganizationFormDTO(
			CreateOrganizationFormDTO createOrganizationFormDTO) {
		UserAddressVO userAddressVO = new UserAddressVO();
		UserAddressDTO userAddressDTO = createOrganizationFormDTO.getUserAddressDTO();
		userAddressVO.setAddress1(userAddressDTO.getAddress1());
		userAddressVO.setAddress2(userAddressDTO.getAddress2());
		userAddressVO.setCountry(userAddressDTO.getCountry());
		userAddressVO.setLocation(userAddressDTO.getLocation());
		userAddressVO.setPin(userAddressDTO.getPin());
		userAddressVO.setCity((userAddressDTO.getCity()));
		userAddressVO.setState(userAddressDTO.getState());
		return userAddressVO;
	}

	private UserAddressVO getAddressVOFromCreateUserFormDTO(CreateUserFormDTO createUserFormDTO) {
		UserAddressVO userAddressVO = new UserAddressVO();
		UserAddressDTO userAddressDTO = createUserFormDTO.getUserAddressDTO();
		userAddressVO.setAddress1(userAddressDTO.getAddress1());
		userAddressVO.setAddress2(userAddressDTO.getAddress2());
		userAddressVO.setCountry(userAddressDTO.getCountry());
		userAddressVO.setLocation(userAddressDTO.getLocation());
		userAddressVO.setPin(userAddressDTO.getPin());
		userAddressVO.setCity(userAddressDTO.getCity());
		userAddressVO.setState(userAddressDTO.getState());
		return userAddressVO;
	}

	private OrganizationVO getOrganizationVOFromCreateOrganizationFormDTO(
			CreateOrganizationFormDTO createOrganizationFormDTO) {
		OrganizationVO organizationVO = new OrganizationVO();
		OrganizationDTO organizationDTO = createOrganizationFormDTO.getOrganizationDTO();
		organizationVO.setName(organizationDTO.getOrgName());
		organizationVO.setCity(organizationDTO.getCity());
		organizationVO.setCountry(organizationDTO.getCountry());
		organizationVO.setOrgLogo(organizationDTO.getOrgLogo());
		organizationVO.setActive(true);
		organizationVO.setPhoneNumber(organizationDTO.getPhoneNumber());
		organizationVO.setPostalCode(organizationDTO.getPostalCode());
		organizationVO.setState(organizationDTO.getState());
		organizationVO.setStreet(organizationDTO.getStreet());
		organizationVO.setSubscriptionType(CommonConstant.SUBSCRIPTION_TYPE_DEMO);
		return organizationVO;
	}

	private UserVO getUserVOFromCreateOrganizationFormDTO(CreateOrganizationFormDTO createOrganizationFormDTO) {
		UserVO userVO = new UserVO();
		userVO.setFirstName(createOrganizationFormDTO.getFirstName());
		userVO.setLastName(createOrganizationFormDTO.getLastName());
		userVO.setUserName(createOrganizationFormDTO.getUserName());
		userVO.setEmail(createOrganizationFormDTO.getEmail());
		try {
			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createOrganizationFormDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		userVO.setRole(Role.ROLE_ADMIN);
		userVO.setActive(true);
		return userVO;
	}

	@Override
	public UserResponseDTO login(LoginFormDTO loginRequest) {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(loginRequest) || StringUtils.isBlank(loginRequest.getUserName())
				|| StringUtils.isBlank(loginRequest.getPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGIN_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(loginRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			if(userVO.isActive())
			{
				if (compareEncodedPasswordWithEncryptedPassword(loginRequest.getPassword(), userVO.getPassword())) {
//					if(!userVO.isLoginStatus()) {
						updateUserLoginInformation(userVO);
//					}
//					else {
//						throw new ApplicationContextException(UserConstants.ERRROR_MSG_LOGIN_STATUS);
//					}
				
				} else {
					throw new ApplicationContextException(UserConstants.ERRROR_MSG_PASSWORD_MISMATCH);
				}
			}
			else
			{
				throw new ApplicationContextException(UserConstants.ACCOUNT_INACTIVE_MESSAGE);
			}
		} else {
			throw new ApplicationContextException(
					UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND_AND_ASKING_SIGNUP);
		} 
		UserResponseDTO userResponseDTO = mapUserVOToDTO(userVO);
		TokenVO tokenVO = tokenProvider.createToken(userVO.getUserId(), loginRequest.getUserName());
		userResponseDTO.setToken(tokenVO.getToken());
		userResponseDTO.setTokenId(tokenVO.getId());
		userResponseDTO.setAccessRightsVO(accessRightsRepo.findById(userVO.getAccessRightsRoleId()).orElse(null));
		updateLastLoginByUserId(userVO.getUserId());
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userResponseDTO;
	}

	private void updateLastLoginByUserId(Long userId) {
		try {
			userRepo.updateLastLoginByUserId(userId,LocalDateTime.now().toString());
		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, "updateLastLoginByUserId()", userId,
					e.getMessage());
		}
	}

	/**
	 * @param encryptedPassword -> Data from user;
	 * @param encodedPassword   ->Data from DB;
	 * @return
	 */
	private boolean compareEncodedPasswordWithEncryptedPassword(String encryptedPassword, String encodedPassword) {
		boolean userStatus = false;
		try {
			userStatus = encoder.matches(CryptoUtils.getDecrypt(encryptedPassword), encodedPassword);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		return userStatus;
	}

	/**
	 * @param userVO
	 */
	private void updateUserLoginInformation(UserVO userVO1) {
		try {
			userVO1.setLoginStatus(true);
			userRepo.save(userVO1);
			userService.createUserAction(userVO1.getUserName(), userVO1.getUserId(),
					UserConstants.USER_ACTION_TYPE_LOGIN);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	@Override
	public void logout(String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isBlank(userName)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGOUT_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(userName);
		if (ObjectUtils.isNotEmpty(userVO)) {
			userService.updateUserLogOutInformation(userVO);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void changePassword(ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(changePasswordRequest) || StringUtils.isBlank(changePasswordRequest.getUserName())
				|| StringUtils.isBlank(changePasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_CHANGE_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(changePasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			try {
					userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(changePasswordRequest.getNewPassword())));
				} catch (Exception e) {
					throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
				}
				userRepo.save(userVO);
				userService.createUserAction(userVO.getUserName(), userVO.getUserId(),
						UserConstants.USER_ACTION_TYPE_CHANGE_PASSWORD);
			
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(resetPasswordRequest) || StringUtils.isBlank(resetPasswordRequest.getUserName())
				|| StringUtils.isBlank(resetPasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_RESET_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(resetPasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(resetPasswordRequest.getNewPassword())));
			} catch (Exception e) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getUserId(),
					UserConstants.USER_ACTION_TYPE_RESET_PASSWORD);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	public static UserResponseDTO mapUserVOToDTO(UserVO userVO) {
		UserResponseDTO userDTO = new UserResponseDTO();
		userDTO.setUserId(userVO.getUserId());
		userDTO.setFirstName(userVO.getFirstName());
		userDTO.setLastName(userVO.getLastName());
		userDTO.setEmail(userVO.getEmail());
		userDTO.setUserName(userVO.getUserName());
		userDTO.setLoginStatus(userVO.isLoginStatus());
		userDTO.setActive(userVO.isActive());
		userDTO.setRole(userVO.getRole());
		userDTO.setAccountRemovedDate(userVO.getAccountRemovedDate());
		userDTO.setLastLogin(userVO.getLastLogin());
		userDTO.setUserAddressVO(userVO.getUserAddressVO());
		userDTO.setOrgId(userVO.getOrganizationVO().getId());
		return userDTO;
	}

	@Override
	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException {
		UserVO userVO = userRepo.findByUserName(userName);
		RefreshTokenDTO refreshTokenDTO = null;
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		TokenVO tokenVO = tokenRepo.findById(tokenId).orElseThrow(() -> new ApplicationException("Invalid Token Id."));
		if (tokenVO.getExpDate().compareTo(new Date()) > 0) {
			tokenVO = tokenProvider.createRefreshToken(tokenVO, userVO);
			refreshTokenDTO = RefreshTokenDTO.builder().token(tokenVO.getToken()).tokenId(tokenVO.getId()).build();
		} else {
			tokenRepo.delete(tokenVO);
			throw new ApplicationException(UserConstants.REFRESH_TOKEN_EXPIRED_MESSAGE);
		}
		return refreshTokenDTO;
	}

	@Override
	public void createUser(CreateUserFormDTO createUserFormDTO) throws ApplicationException {
		String methodName = "createUser()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(createUserFormDTO) || StringUtils.isBlank(createUserFormDTO.getEmail())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_REGISTER_INFORMATION);
		} else if (userRepo.existsByEmail(createUserFormDTO.getEmail())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_ALREADY_REGISTERED);
		}
		UserVO userVO = getUserVOFromCreateUserFormDTO(createUserFormDTO);  
		userVO.setOrganizationVO(organizationRepo.findById(createUserFormDTO.getOrgId())
				.orElseThrow(() -> new ApplicationException("No orginaization found.")));
		userVO.setUserAddressVO(userAddressRepo.save(getAddressVOFromCreateUserFormDTO(createUserFormDTO)));
		if (ObjectUtils.isNotEmpty(createUserFormDTO.getEmitterId())) {
			CustomersVO customersVO = customersRepo.findById(createUserFormDTO.getEmitterId()).orElse(null);
			userVO.setCustomersVO(customersVO);
		}
		userRepo.save(userVO);
		userService.createUserAction(userVO.getEmail(), userVO.getUserId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	private UserVO getUserVOFromCreateUserFormDTO(CreateUserFormDTO createUserFormDTO) {
		UserVO userVO = new UserVO();
		userVO.setFirstName(createUserFormDTO.getFirstName());
		userVO.setLastName(createUserFormDTO.getLastName());
		userVO.setUserName(createUserFormDTO.getUserName());
		userVO.setEmail(createUserFormDTO.getEmail());
		userVO.setAccessRightsRoleId(createUserFormDTO.getAccessRightsRoleId());
		userVO.setPNo(createUserFormDTO.getPNo());
		userVO.setCreatedBy(createUserFormDTO.getCreatedBy());
		userVO.setUpdatedBy(createUserFormDTO.getCreatedBy());
		try {
			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(createUserFormDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		String addIds = ObjectUtils.isNotEmpty(createUserFormDTO.getAccessaddId()) ? Arrays
				.stream(createUserFormDTO.getAccessaddId()).map(String::valueOf).collect(Collectors.joining(","))
				: StringUtils.EMPTY;
		String warehouseIds = ObjectUtils.isNotEmpty(createUserFormDTO.getAccessWarehouse()) ? Arrays
				.stream(createUserFormDTO.getAccessWarehouse()).map(String::valueOf).collect(Collectors.joining(","))
				: StringUtils.EMPTY;
		String flowIds = ObjectUtils.isNotEmpty(createUserFormDTO.getAccessFlowId()) ? Arrays
				.stream(createUserFormDTO.getAccessFlowId()).map(String::valueOf).collect(Collectors.joining(","))
				: StringUtils.EMPTY;
		
		if (ObjectUtils.isNotEmpty(createUserFormDTO.getAccessFlowId())) {
	        List<Long> flowIdList = Arrays.stream(createUserFormDTO.getAccessFlowId())
	                .map(Long::valueOf)
	                .collect(Collectors.toList());

	        List<FlowVO> flows = flowRepo.findAllById(flowIdList);
	        flows.forEach(flow -> flow.setEflag(false));
	        flowRepo.saveAll(flows);
	    }
		userVO.setAccessFlowId(flowIds);
		userVO.setAccessaddId(addIds);
		userVO.setAccessWarehouse(warehouseIds);
		userVO.setRole(createUserFormDTO.getRole());
		userVO.setActive(true);
		return userVO;
	}
 
	@Override
	public Optional<UserVO> getUserById(Long userId) {
		
		return userRepo.findById(userId);
	}

	@Override
	public UserVO updateUser(CreateUserFormDTO createUserFormDTO) throws ApplicationException {
		UserVO userVO = new UserVO();
		userVO = userRepo.findById(createUserFormDTO.getUserId())
				.orElseThrow(() -> new ApplicationException("Invalid  user details"));
		getUserVOFromCreateUserFormDTO(createUserFormDTO, userVO);
		userVO.setUserAddressVO(userAddressRepo.save(getAddressVOFromCreateUserFormDTO(createUserFormDTO)));   // for set addressVO in userVO
		return userRepo.save(userVO);
	}  

	private void getUserVOFromCreateUserFormDTO(CreateUserFormDTO createUserFormDTO, UserVO userVO) {
		String addIds = ObjectUtils.isNotEmpty(createUserFormDTO.getAccessaddId()) ? Arrays
				.stream(createUserFormDTO.getAccessaddId()).map(String::valueOf).collect(Collectors.joining(","))
				: StringUtils.EMPTY;
		String warehouseIds = ObjectUtils.isNotEmpty(createUserFormDTO.getAccessWarehouse()) ? Arrays
				.stream(createUserFormDTO.getAccessWarehouse()).map(String::valueOf).collect(Collectors.joining(","))
				: StringUtils.EMPTY;
		String flowIds = ObjectUtils.isNotEmpty(createUserFormDTO.getAccessFlowId()) ? Arrays
				.stream(createUserFormDTO.getAccessFlowId()).map(String::valueOf).collect(Collectors.joining(","))
				: StringUtils.EMPTY;
		userVO.setAccessFlowId(flowIds);
		userVO.setAccessaddId(addIds);
		userVO.setAccessWarehouse(warehouseIds);
		userVO.setFirstName(createUserFormDTO.getFirstName());
		userVO.setLastName(createUserFormDTO.getLastName());
		userVO.setPNo(createUserFormDTO.getPNo());
		userVO.setActive(createUserFormDTO.isActive());
		userVO.setRole(createUserFormDTO.getRole());
		userVO.setUpdatedBy(createUserFormDTO.getCreatedBy());
		userVO.setAccessRightsRoleId(createUserFormDTO.getAccessRightsRoleId());
		 
	}

	@Override 
	public List<UserVO> getUserByOrgId(Long orgId) {
		
		return userRepo.findByOrgId(orgId);
	}
}

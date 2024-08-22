package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.OrganizationDTO;
import com.whydigit.efit.dto.Role;
import com.whydigit.efit.entity.OrganizationVO;
import com.whydigit.efit.entity.UserVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.OrganizationRepo;
import com.whydigit.efit.repo.UserRepo;
import com.whydigit.efit.util.CryptoUtils;

@Service
public class CompanyServiceImpl implements CompanyService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

	@Autowired
	OrganizationRepo organizationRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepo userRepo;

	@Override
	public OrganizationVO createCompany(OrganizationDTO organizationDTO) throws ApplicationException {

		OrganizationVO vo = new OrganizationVO();

		if (organizationRepo.existsByName(organizationDTO.getOrgName())) {

			throw new ApplicationException("Comapany Name Already Exits");

		}
		if (organizationRepo.existsByCode(organizationDTO.getCode())) {

			throw new ApplicationException("Comapany Code Already Exits");

		}
		if (organizationRepo.existsByEmail(organizationDTO.getEmail())) {

			throw new ApplicationException("Email Already Exits");

		}

		getOrganizationVOFromOrganizationDTO(vo, organizationDTO);
		organizationRepo.save(vo);
		UserVO userVO = new UserVO();
		userVO.setEmail(vo.getEmail());
		userVO.setFirstName(vo.getAdminFirstName());
		userVO.setRole(Role.ROLE_ADMIN);
		userVO.setUserName(vo.getEmail());
		userVO.setActive(true);
		userVO.setCreatedBy(vo.getCreatedby());
		userVO.setUpdatedBy(vo.getCreatedby());
		userVO.setLoginStatus(false);
		userVO.setOrganizationVO(vo);

		try {
			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(organizationDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		userRepo.save(userVO);

		return vo;
	}

	private void getOrganizationVOFromOrganizationDTO(OrganizationVO vo, OrganizationDTO organizationDTO) {

		vo.setName(organizationDTO.getOrgName());
		vo.setCode(organizationDTO.getCode());
		vo.setEmail(organizationDTO.getEmail());
		vo.setPhoneNumber(organizationDTO.getPhoneNumber());
		vo.setAddress(organizationDTO.getAddress());
		vo.setCountry(organizationDTO.getCountry());
		vo.setState(organizationDTO.getState());
		vo.setCity(organizationDTO.getCity());
		vo.setPinCode(organizationDTO.getPinCode());
		vo.setActive(organizationDTO.isActive());
		vo.setCreatedby(organizationDTO.getCreatedby());
		vo.setModifiedby(organizationDTO.getCreatedby());
		vo.setAdminFirstName(organizationDTO.getAdminFirstName());
		try {
			vo.setPassword(encoder.encode(CryptoUtils.getDecrypt(organizationDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}

	}

	@Override
	public List<OrganizationVO> getAllCompany() {
		return organizationRepo.findAll();
	}

	@Override
	public Optional<OrganizationVO> getAllCompanyByOrgId(Long id) {
		
		return organizationRepo.findCompanyById(id);
	}

}

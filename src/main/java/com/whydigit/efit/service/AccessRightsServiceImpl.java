package com.whydigit.efit.service;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.entity.AccessRightsVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AccessRightsRepo;
import com.whydigit.efit.repo.OrganizationRepo;

@Service
public class AccessRightsServiceImpl implements AccessRightsService {
	public static final Logger LOGGER = LoggerFactory.getLogger(AccessRightsServiceImpl.class);
	@Autowired
	AccessRightsRepo accessRightsRepo;

	@Autowired
	OrganizationRepo organizationRepo;

	@Override
	public AccessRightsVO createAccessRight(@Valid AccessRightsVO accessRightsVO) throws ApplicationException {
		String methodName = "createAccessRight()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String action = accessRightsVO.getId() == 0 ? CommonConstant.ACTION_CREATE : CommonConstant.ACTION_UPDATE;
		if (!organizationRepo.existsById(accessRightsVO.getOrgId())) {
			throw new ApplicationException("Organization not found.");
		}
		if (StringUtils.equalsAnyIgnoreCase(action, CommonConstant.ACTION_UPDATE)
				&& !accessRightsRepo.existsById(accessRightsVO.getId())) {
			throw new ApplicationException("Invalid role id.");
		} else if (!StringUtils.equalsAnyIgnoreCase(action, CommonConstant.ACTION_UPDATE)
				&& accessRightsRepo.existsByRoleAndOrgId(accessRightsVO.getRole(), accessRightsVO.getOrgId())) {
			throw new ApplicationException("Role already exist. Please try another one.");
		}
		try {
			accessRightsVO = accessRightsRepo.save(accessRightsVO);
		} catch (Exception e) {
			throw new ApplicationException(
					new StringBuilder("Failed to ").append(action).append("AccessRight").toString());
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return accessRightsVO;
	}

	@Override
	public List<AccessRightsVO> getAccessRightByOrgId(long orgId) throws ApplicationException {
		if (!organizationRepo.existsById(orgId)) {
			throw new ApplicationException("Organization not found.");
		}
		return accessRightsRepo.findByOrgId(orgId);
	}

	@Override
	public AccessRightsVO getAccessRightById(long roleId) throws ApplicationException {
		return accessRightsRepo.findById(roleId).orElseThrow(() -> new ApplicationException("Role not found."));
	}

}

package com.whydigit.efit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.AccessRightsVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface AccessRightsService {

	AccessRightsVO createAccessRight(AccessRightsVO accessRightsVO) throws ApplicationException;

	List<AccessRightsVO> getAccessRightByOrgId(long orgId) throws ApplicationException;

	AccessRightsVO getAccessRightById(long roleId) throws ApplicationException;

}

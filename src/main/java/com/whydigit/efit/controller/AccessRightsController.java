package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.OrganizationConstant;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.AccessRightsVO;
import com.whydigit.efit.service.AccessRightsService;

@RestController
@RequestMapping("/api/access")
public class AccessRightsController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AccessRightsController.class);

	@Autowired
	AccessRightsService accessRightsService;

	@PostMapping("/createAccessRight")
	public ResponseEntity<ResponseDTO> createAccessRight(@Valid @RequestBody AccessRightsVO accessRightsVO) {
		String methodName = "createAccessRight()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			accessRightsVO = accessRightsService.createAccessRight(accessRightsVO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(OrganizationConstant.ERROR_MSG_METHOD_NAME_WITH_ORG_ID, methodName, accessRightsVO.getOrgId(),
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					OrganizationConstant.ACCESS_RIGHT_REGISTERED_SUCCESS_MESSAGE);
			responseObjectsMap.put(OrganizationConstant.ACCESS_RIGHTS_VO, accessRightsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					OrganizationConstant.ACCESS_RIGHT_REGISTERED_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAccessRightByOrgId")
	public ResponseEntity<ResponseDTO> getAccessRightByOrgId(@RequestParam long orgId) {
		String methodName = "getAccessRightByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AccessRightsVO> accessRightsVO = new ArrayList<>();
		try {
			accessRightsVO = accessRightsService.getAccessRightByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(OrganizationConstant.ERROR_MSG_METHOD_NAME_WITH_ORG_ID, methodName, orgId, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					OrganizationConstant.GET_ACCESS_RIGHT_INFORMATION_SUCCESS_MESSAGE);
			responseObjectsMap.put(OrganizationConstant.ACCESS_RIGHTS_VO, accessRightsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					OrganizationConstant.GET_ACCESS_RIGHT_INFORMATION_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAccessRightById")
	public ResponseEntity<ResponseDTO> getAccessRightById(@RequestParam long roleId) {
		String methodName = "getAccessRightById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AccessRightsVO accessRightsVO = null;
		try {
			accessRightsVO = accessRightsService.getAccessRightById(roleId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(OrganizationConstant.ERROR_MSG_METHOD_NAME_WITH_ROLE_ID, methodName, roleId, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					OrganizationConstant.GET_ACCESS_RIGHT_INFORMATION_SUCCESS_MESSAGE);
			responseObjectsMap.put(OrganizationConstant.ACCESS_RIGHTS_VO, accessRightsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					OrganizationConstant.GET_ACCESS_RIGHT_INFORMATION_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}

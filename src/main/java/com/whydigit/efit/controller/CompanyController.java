package com.whydigit.efit.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.OrganizationDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.OrganizationVO;
import com.whydigit.efit.service.CompanyService;

@CrossOrigin
@RestController
@RequestMapping("api/company")
public class CompanyController  extends BaseController{
	

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);

	@Autowired
	private CompanyService companyService;

	@PostMapping("/createCompany")
	public ResponseEntity<ResponseDTO> createCompany(@RequestBody OrganizationDTO organizationDTO) {
		String methodName = "createCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			OrganizationVO organizationVO = companyService.createCompany(organizationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company created successfully");
			responseObjectsMap.put("organizationVO", organizationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Company creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

}

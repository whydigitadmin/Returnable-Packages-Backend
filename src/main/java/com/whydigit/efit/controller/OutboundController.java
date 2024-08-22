package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.DcVendorVO;
import com.whydigit.efit.service.OutboundService;

@RestController
@RequestMapping("/api/DcVendor")
public class OutboundController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);

	@Autowired
	OutboundService outboundService;

	@GetMapping("/view")
	public ResponseEntity<ResponseDTO> getAllDcVendorVO() {
		String methodName = "getAllDcVendorVO";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcVendorVO> dcVendorVO = new ArrayList<>();
		try {
			dcVendorVO = outboundService.getAllDcVendorVO();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcVendor information get successfully");
			responseObjectsMap.put("DcVendorVO", dcVendorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DcVendor information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/view/{id}")
	public Optional<DcVendorVO> getbyId(@PathVariable int id) {
		return outboundService.getDcVendorVOById(id);

	}

	@PostMapping("/view")
	public ResponseEntity<ResponseDTO> createDcVendorVO(@RequestBody DcVendorVO dcVendorVO) {
		String methodName = "createDcVendorVO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			DcVendorVO dcvendorVO = outboundService.createDcVendorVO(dcVendorVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcVendor created successfully");
			responseObjectsMap.put("DcVendorVO", dcvendorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "DcVendor creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/view")
	public ResponseEntity<ResponseDTO> updateDcVendorVO(@RequestBody DcVendorVO dcVendorVO) {
		String methodName = "updateDcVendorVO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			DcVendorVO dcvendorVO = outboundService.updateDcVendorVO(dcVendorVO).orElse(null);
			if (dcvendorVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcVendor updated successfully");
				responseObjectsMap.put("DcVendor", dcvendorVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse not found for ID: " + dcVendorVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "DcVendor update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/view/{id}")
	public ResponseEntity<ResponseDTO> deleteDcVendorVO(@PathVariable int id) {
		String methodName = "deleteDcVendorVO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			outboundService.deleteDcVendorVO(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcVendor deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "DcVendor deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}

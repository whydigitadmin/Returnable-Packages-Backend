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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.InwardSkuConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.InwardSkuDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.InwardSkuVO;
import com.whydigit.efit.service.InwardSkuService;

@RestController
@CrossOrigin
@RequestMapping("/api/inwardSku")
public class InwardSkuController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardSkuController.class);

	@Autowired
	private InwardSkuService InwardSkuService;

	@GetMapping("/getAllInwardSku")
	public ResponseEntity<ResponseDTO> getAllInwardSku() {
		String methodName = "getAllInwardSku()";
		LOGGER.debug(InwardSkuConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InwardSkuVO> inwardSkuVO = new ArrayList<>();
		try {
			inwardSkuVO = InwardSkuService.getAllInwardSku();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(InwardSkuConstant.STRING_MESSAGE, "inwardSku information get successfully");
			responseObjectsMap.put("inwardSku", inwardSkuVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "inwardSku information receive failed",
					errorMsg);
		}
		LOGGER.debug(InwardSkuConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInward/{id}")
	public ResponseEntity<ResponseDTO> getInwardSkuById(@PathVariable Long id) {
		String methodName = "getInwardSkuById()";
		LOGGER.debug(InwardSkuConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InwardSkuVO inwardSkuVO = null;
		try {
			inwardSkuVO = InwardSkuService.getInwardSkuById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(InwardSkuConstant.STRING_MESSAGE, "inwardSku found by ID");
			responseObjectsMap.put("inwardSku", inwardSkuVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "inwardSku not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "inwardSku not found", errorMsg);
		}
		LOGGER.debug(InwardSkuConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createInwardSku")
	public ResponseEntity<ResponseDTO> createInwardSku(@RequestBody InwardSkuDTO InwardSkuDTO) {
		String methodName = "createInwardSku()";
		LOGGER.debug(InwardSkuConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			InwardSkuVO createdInwardSkuVO = InwardSkuService.createInwardSku(InwardSkuDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "inwardSku created successfully");
			responseObjectsMap.put("InwardSku", createdInwardSkuVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(InwardSkuConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "inwardSku creation failed", errorMsg);
		}
		LOGGER.debug(InwardSkuConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateInwardSku")
	public ResponseEntity<ResponseDTO> updateInwardSku(@RequestBody InwardSkuDTO InwardSkuDTO) {
		String methodName = "updateInwardSku()";
		LOGGER.debug(InwardSkuConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Optional<InwardSkuVO> optionalUpdatedInwardSkuVO = Optional
					.ofNullable(InwardSkuService.updateInwardSku(InwardSkuDTO));
			InwardSkuVO updatedInwardSkuVO = optionalUpdatedInwardSkuVO.orElse(null);
			if (updatedInwardSkuVO != null) {
				responseObjectsMap.put(InwardSkuConstant.STRING_MESSAGE, "inwardSku updated successfully");
				responseObjectsMap.put("inwardSkuVO", updatedInwardSkuVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "inwardSkuVO not found for ID: " + InwardSkuDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "inwardSku update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(InwardSkuConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "inwardSku update failed", errorMsg);
		}
		LOGGER.debug(InwardSkuConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
}

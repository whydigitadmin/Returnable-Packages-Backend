package com.whydigit.efit.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.service.OemService;

@RestController
@RequestMapping("/api/oem")
public class OemController extends BaseController{

	@Autowired
	OemService oemService;
	
	//OEM Bin Inward
	@PostMapping("/oemBinInward")
	public ResponseEntity<ResponseDTO> createOemBinInward(@RequestBody OemBinInwardDTO oemBinInwardDTO) {
		String methodName = "createOemBinInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		OemBinInwardVO oemBinInwardVO = new OemBinInwardVO();
		try {
			oemBinInwardVO = oemService.createOemBinInward(oemBinInwardDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Bin Inward Created Successfully");
			responseObjectsMap.put("oemBinInwardVO", oemBinInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Oem Bin Inward Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	//OEM Bin Outward
	@PutMapping("/updateCreateOemBinOutward")
	public ResponseEntity<ResponseDTO> updateCreateOemBinOutward(@RequestBody OemBinOutwardDTO oemBinOutwardDTO) {
		String methodName = "updateCreateOemBinOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			OemBinOutwardVO updatedOemBinOutwardVO = oemService.updateCreateOemBinOutward(oemBinOutwardDTO);
			if (updatedOemBinOutwardVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OEM BinOutward updated successfully");
				responseObjectsMap.put("OEMBinOutwardVO", updatedOemBinOutwardVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "OEM BinOutward update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}

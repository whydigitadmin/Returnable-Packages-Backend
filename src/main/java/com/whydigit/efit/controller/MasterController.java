package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.service.MasterService;

@CrossOrigin
@RestController
@RequestMapping("api/master")

public class MasterController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);
	
	@Autowired
	MasterService masterService;
	
	@GetMapping("/asset")
	public ResponseEntity<ResponseDTO>getAllAsset(){
		String methodName = "getAllAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetVO> assetVO = new ArrayList<>();
		try {
			assetVO = masterService.getAllAsset();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset information get successfully");
			responseObjectsMap.put("assetVO", assetVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
		
	}
	
	@PostMapping("/asset")
	public ResponseEntity<ResponseDTO>createWarehouseVO(@RequestBody AssetVO assetVO){
		String methodName = "createAssetVO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetVO createdAssetVO = masterService.createAssetVO(assetVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset created successfully");
			responseObjectsMap.put("assetVO", createdAssetVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
		
	}
	
	@PutMapping("/asset")
	public ResponseEntity<ResponseDTO> updateAssetVO(@RequestBody AssetVO assetVO) {
		String methodName = "updateAssetVO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetVO updateAssetVO = masterService.updateAssetVO(assetVO).orElse(null);
			if (updateAssetVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset updated successfully");
				responseObjectsMap.put("assetVO", updateAssetVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Asset not found for ID: " + assetVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Asset update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@DeleteMapping("/asset/{id}")
	public ResponseEntity<ResponseDTO> deleteAsset(@PathVariable int id) {
		String methodName = "deleteAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteAsset(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

	
	

}

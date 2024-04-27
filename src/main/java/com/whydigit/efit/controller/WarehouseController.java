package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.dto.WarehouseDTO;
import com.whydigit.efit.entity.WarehouseVO;
import com.whydigit.efit.service.WarehouseService;

@CrossOrigin
@RestController
@RequestMapping("api/warehouse")

public class WarehouseController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);

	@Autowired
	WarehouseService warehouseService;

	@GetMapping("/view")
	public ResponseEntity<ResponseDTO> getAllWarehouse() {
		String methodName = "getAllWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseVO> warehousevo = new ArrayList<>();
		try {
			warehousevo = warehouseService.getAllWarehouse();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse information get successfully");
			responseObjectsMap.put("WarehouseVO", warehousevo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getWarehouseById/{id}")
	public ResponseEntity<ResponseDTO> getWarehouseById(@PathVariable Long id) {
		String methodName = "getWarehouseById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		WarehouseVO warehouseVO = null;
		try {
			warehouseVO = warehouseService.getWarehouseById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse found by ID");
			responseObjectsMap.put("warehouse", warehouseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Warehouse not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}


	@GetMapping("/getWarehouseLocationByOrgID")
	public ResponseEntity<ResponseDTO> getWarehouseLocationByOrgID(@RequestParam(required = false) Long orgId) {
		String methodName = "getWarehouseLocationByOrgID()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> warehousevo = new HashSet<>();
		try {
			warehousevo = warehouseService.getWarehouseLocationByOrgID(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>> location = getWarehouseLocation(warehousevo);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse location information get successfully");
			responseObjectsMap.put("WarehouseLocation", location);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse location information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, Object>> getWarehouseLocation(Set<Object[]> warehousevo) {
		List<Map<String, Object>> location = new ArrayList<>();
		for (Object[] w : warehousevo) {
			Map<String, Object> warehouse = new HashMap<>();
			warehouse.put("warehouseId", w[1]);
			warehouse.put("warehouseLocation", w[0].toString());
			location.add(warehouse);
		}
		return location;
	}

	@PutMapping("/view")
	public ResponseEntity<ResponseDTO> updateWarehouseVo(@RequestBody WarehouseVO warehousevo) {
		String methodName = "updateWarehouseVo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseVO updateWarehousevo = warehouseService.updateWarehouseVo(warehousevo).orElse(null);
			if (updateWarehousevo != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse updated successfully");
				responseObjectsMap.put("WarehouseVO", updateWarehousevo);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse not found for ID: " + warehousevo.getWarehouseId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWarehouseByUserID")
	public ResponseEntity<ResponseDTO> getWarehouseByUserID(@RequestParam (required = true)long userId) {
		String methodName = "getWarehouseByUserID()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseVO> warehouseVO = new ArrayList<>();
		try {
			warehouseVO = warehouseService.getWarehouseByUserID(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse found successfully.");
			responseObjectsMap.put("warehouseVO", warehouseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Flow not found for the user.";
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@DeleteMapping("/view/{id}")
	public ResponseEntity<ResponseDTO> deleteWarehouse(@PathVariable Long id) {
		String methodName = "deleteWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			warehouseService.deleteWarehouse(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateWarehouse")
	public ResponseEntity<ResponseDTO> updateCreateWarehouse(@Valid @RequestBody WarehouseDTO warehouseDTO) {
		String methodName = "updateCreateWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseVO updatedWarehouseVO = warehouseService.updateCreateWarehouse(warehouseDTO);
			if (updatedWarehouseVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse updated successfully");
				responseObjectsMap.put("updatedWarehouse", updatedWarehouseVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse not found for ID: " + warehouseDTO.getWarehouseId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}

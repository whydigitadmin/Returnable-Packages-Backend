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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.LogisticsVO;
import com.whydigit.efit.entity.PackingDetailVO;
import com.whydigit.efit.entity.StockDetailVO;
import com.whydigit.efit.service.PartStudyService;

@CrossOrigin
@RestController
@RequestMapping("/api/partStudy")
public class PartStudyController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);

	@Autowired
	private PartStudyService partStudyService;

	@GetMapping("/basicDetails")
	public ResponseEntity<ResponseDTO> getAllBasicDetail(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllBasicDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BasicDetailVO> basicDetailVO = new ArrayList<>();
		try {
			basicDetailVO = partStudyService.getAllBasicDetail(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BasicDetail information get successfully");
			responseObjectsMap.put("basicDetailVO", basicDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "BasicDetail information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/basicDetails/{id}")
	public ResponseEntity<ResponseDTO> getBasicDetailById(@PathVariable int id) {
		String methodName = "getBasicDetailById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BasicDetailVO basicDetailVO = null;
		try {
			basicDetailVO = partStudyService.getBasicDetailById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BasicDetail found by ID");
			responseObjectsMap.put("basicDetailVO", basicDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "BasicDetail not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "BasicDetail not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/basicDetails")
	public ResponseEntity<ResponseDTO> createBasicDetail(@RequestBody BasicDetailVO basicDetailVO) {
		String methodName = "createBasicDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BasicDetailVO createdBasicDetailVO = partStudyService.createBasicDetail(basicDetailVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BasicDetail created successfully");
			responseObjectsMap.put("basicDetailVO", createdBasicDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "BasicDetail creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/basicDetails")
	public ResponseEntity<ResponseDTO> updateBasicDetail(@RequestBody BasicDetailVO basicDetailVO) {
		String methodName = "updateCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BasicDetailVO updatedBasicDetailVO = partStudyService.updateBasicDetail(basicDetailVO).orElse(null);
			if (updatedBasicDetailVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BasicDetail updated successfully");
				responseObjectsMap.put("basicDetailVO", updatedBasicDetailVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "BasicDetail not found for ID: " + basicDetailVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "BasicDetail update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "BasicDetail update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/basicDetails/{id}")
	public ResponseEntity<ResponseDTO> deleteBasicDetail(@PathVariable int id) {
		String methodName = "deleteBasicDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			partStudyService.deleteBasicDetail(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BasicDetail deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "BasicDetail deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// packageDetail

	@GetMapping("/packageDetail")
	public ResponseEntity<ResponseDTO> getAllpackingDetail(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllPackingDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PackingDetailVO> packingDetailVO = new ArrayList<>();
		try {
			packingDetailVO = partStudyService.getAllpackingDetail(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingDetail information get successfully");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PackingDetail information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/packageDetail/{id}")
	public ResponseEntity<ResponseDTO> getPackingDetailById(@PathVariable int id) {
		String methodName = "getPackingDetailById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.getPackingDetailById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingDetail found by ID");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "PackingDetail not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "PackingDetail not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/packageDetail")
	public ResponseEntity<ResponseDTO> createPackingDetail(@RequestBody PackingDetailVO packingDetailVO) {
		String methodName = "createPackingDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PackingDetailVO createdPackingDetailVO = partStudyService.createPackingDetail(packingDetailVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingDetail created successfully");
			responseObjectsMap.put("packingDetailVO", createdPackingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "PackingDetail creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/packageDetail")
	public ResponseEntity<ResponseDTO> updatePackingDetail(@RequestBody PackingDetailVO packingDetailVO) {
		String methodName = "updatePackingDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PackingDetailVO updatedPackingDetailVO = partStudyService.updatePackingDetail(packingDetailVO).orElse(null);
			if (updatedPackingDetailVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingDetail updated successfully");
				responseObjectsMap.put("packingDetailVO", updatedPackingDetailVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "PackingDetail not found for ID: " + packingDetailVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "PackingDetail update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "PackingDetail update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/packageDetail/{id}")
	public ResponseEntity<ResponseDTO> deletePackingDetail(@PathVariable int id) {
		String methodName = "deletePackingDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			partStudyService.deletePackingDetail(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingDetail deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "PackingDetail deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Add logistics
	@GetMapping("/logistics")
	public ResponseEntity<ResponseDTO> getAllLogistics(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllLogistics()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<LogisticsVO> logisticsVO = new ArrayList<>();
		try {
			logisticsVO = partStudyService.getAllLogistics(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Logistics information get successfully");
			responseObjectsMap.put("logisticsVO", logisticsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Logistics information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/logistics/{id}")
	public ResponseEntity<ResponseDTO> getLogisticsById(@PathVariable int id) {
		String methodName = "getLogisticsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		LogisticsVO logisticsVO = null;
		try {
			logisticsVO = partStudyService.getLogisticsById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Logistics found by ID");
			responseObjectsMap.put("logisticsVO", logisticsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Logistics not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Logistics not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/logistics")
	public ResponseEntity<ResponseDTO> createLogistics(@RequestBody LogisticsVO logisticsVO) {
		String methodName = "createLogistics()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			LogisticsVO createdLogisticsVO = partStudyService.createLogistics(logisticsVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Logistics created successfully");
			responseObjectsMap.put("logisticsVO", createdLogisticsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Logistics creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/logistics")
	public ResponseEntity<ResponseDTO> updateLogistics(@RequestBody LogisticsVO logisticsVO) {
		String methodName = "updateLogistics()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			LogisticsVO updatedLogisticsVO = partStudyService.updateLogistics(logisticsVO).orElse(null);
			if (updatedLogisticsVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Logistics updated successfully");
				responseObjectsMap.put("logisticsVO", updatedLogisticsVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Logistics not found for ID: " + logisticsVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Logistics update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Logistics update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/logistics/{id}")
	public ResponseEntity<ResponseDTO> deleteLogistics(@PathVariable int id) {
		String methodName = "deleteLogistics()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			partStudyService.deleteLogistics(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Logistics deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Logistics deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Add stock details

	@GetMapping("/stockDetail")
	public ResponseEntity<ResponseDTO> getAllStockDetail(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllStockDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StockDetailVO> stockDetailVO = new ArrayList<>();
		try {
			stockDetailVO = partStudyService.getAllStockDetail(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail information get successfully");
			responseObjectsMap.put("stockDetailVO", stockDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/stockDetail/{id}")
	public ResponseEntity<ResponseDTO> getStockDetailById(@PathVariable int id) {
		String methodName = "getStockDetailById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		StockDetailVO stockDetailVO = null;
		try {
			stockDetailVO = partStudyService.getStockDetailById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail found by ID");
			responseObjectsMap.put("StockDetail", stockDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "StockDetail not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/stockDetail")
	public ResponseEntity<ResponseDTO> createStockDetail(@RequestBody StockDetailVO stockDetailVO) {
		String methodName = "createStockDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StockDetailVO createdStockDetailVO = partStudyService.createStockDetail(stockDetailVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail created successfully");
			responseObjectsMap.put("stockDetailVO", createdStockDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/stockDetail")
	public ResponseEntity<ResponseDTO> updateStockDetail(@RequestBody StockDetailVO stockDetailVO) {
		String methodName = "updateStockDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StockDetailVO updatedStockDetailVO = partStudyService.updateStockDetail(stockDetailVO).orElse(null);
			if (updatedStockDetailVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail updated successfully");
				responseObjectsMap.put("stockDetailVO", updatedStockDetailVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "StockDetail not found for ID: " + stockDetailVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/stockDetail/{id}")
	public ResponseEntity<ResponseDTO> deleteStockDetail(@PathVariable int id) {
		String methodName = "deleteStockDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			partStudyService.deleteStockDetail(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}

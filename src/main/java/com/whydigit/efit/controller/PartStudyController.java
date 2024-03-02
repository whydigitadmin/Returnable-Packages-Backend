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
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.BasicDetailDTO;
import com.whydigit.efit.dto.LogisticsDTO;
import com.whydigit.efit.dto.PDAttachmentType;
import com.whydigit.efit.dto.PackingDetailDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.dto.StockDetailDTO;
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
	public ResponseEntity<ResponseDTO> getBasicDetailById(@PathVariable Long id) {
		String methodName = "getBasicDetailById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BasicDetailVO basicDetailVO = null;
		try {
			basicDetailVO = partStudyService.getBasicDetailById(id);
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
	public ResponseEntity<ResponseDTO> createBasicDetail(@RequestBody BasicDetailDTO basicDetailDTO) {
		String methodName = "createBasicDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BasicDetailVO createdBasicDetailVO = partStudyService.createBasicDetail(basicDetailDTO);
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
	public ResponseEntity<ResponseDTO> updateBasicDetail(@RequestBody BasicDetailDTO basicDetailDTO) {
		String methodName = "updateCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BasicDetailVO updatedBasicDetailVO = partStudyService.updateBasicDetail(basicDetailDTO);
			if (updatedBasicDetailVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BasicDetail updated successfully");
				responseObjectsMap.put("basicDetailVO", updatedBasicDetailVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "BasicDetail not found for ID: " + basicDetailDTO.getRefPsId();
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
	public ResponseEntity<ResponseDTO> deleteBasicDetail(@PathVariable Long id) {
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
	public ResponseEntity<ResponseDTO> getPackingDetailById(@PathVariable Long id) {
		String methodName = "getPackingDetailById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.getPackingDetailById(id);
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


	@PutMapping("/updatePackingDetail")
	public ResponseEntity<ResponseDTO> updatePackingDetail(@RequestBody PackingDetailDTO packingDetailDTO) {
		String methodName = "updatePackingDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PackingDetailVO updatedPackingDetailVO = partStudyService.updatePackingDetail(packingDetailDTO);
			if (updatedPackingDetailVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingDetail updated successfully");
				responseObjectsMap.put("packingDetailVO", updatedPackingDetailVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "PackingDetail not found for ID: " + packingDetailDTO.getRefPsId();
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
	
	@PostMapping("/saveAttachments")
	public ResponseEntity<ResponseDTO> saveAttachments(@RequestParam MultipartFile[] files, @RequestParam PDAttachmentType type, @RequestParam Long refPsId){			
		String methodName = "saveAttachments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
	     partStudyService.saveAttachments(files,type,refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, new StringBuilder(type.name()).append(" attachments saved successfully").toString());
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, new StringBuilder(type.name()).append(" attachments saved failed. please try Again.").toString(),
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/packageDetail/{id}")
	public ResponseEntity<ResponseDTO> deletePackingDetail(@PathVariable Long id) {
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
	public ResponseEntity<ResponseDTO> getLogisticsById(@PathVariable Long id) {
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

	@PutMapping("/logistics")
	public ResponseEntity<ResponseDTO> updateLogistics(@RequestBody LogisticsDTO logisticsDTO) {
		String methodName = "updateLogistics()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			LogisticsVO updatedLogisticsVO = partStudyService.updateLogistics(logisticsDTO);
			if (updatedLogisticsVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Logistics updated successfully");
				responseObjectsMap.put("logisticsVO", updatedLogisticsVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Logistics not found for ID: " + logisticsDTO.getRefPsId();
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
	public ResponseEntity<ResponseDTO> deleteLogistics(@PathVariable Long id) {
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
	public ResponseEntity<ResponseDTO> getStockDetailById(@PathVariable Long id) {
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

	@PutMapping("/stockDetail")
	public ResponseEntity<ResponseDTO> updateStockDetail(@RequestBody StockDetailDTO stockDetailDTO) {
		String methodName = "updateStockDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StockDetailVO updatedStockDetailVO = partStudyService.updateStockDetail(stockDetailDTO);
			if (updatedStockDetailVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail updated successfully");
				responseObjectsMap.put("stockDetailVO", updatedStockDetailVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "StockDetail not found for ID: " + stockDetailDTO.getRefPsId();
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
	public ResponseEntity<ResponseDTO> deleteStockDetail(@PathVariable Long id) {
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
	
	@GetMapping("/generatePartStudyId")
	public ResponseEntity<ResponseDTO> generatePartStudyId(@RequestParam(required = false) String refPsId) {
		String methodName = "generatePartStudyId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		boolean status =false;
		try {
			status = partStudyService.generatePartStudyId(refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Generate PartStudyId information get successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Generate PartStudyId information information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/searchPartStudyById")
	public ResponseEntity<ResponseDTO> searchPartStudyById(@RequestParam(required = false) Long emitterId,
			@RequestParam(required = false) Long refPsId,@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String partName,@RequestParam(required = false) String partNumber){			
		String methodName = "searchPartStudyById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Object> basicDetailVO = new HashMap<>();
		try {      
			basicDetailVO = partStudyService.searchPartStudyById(emitterId, refPsId, orgId, partName,partNumber);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "partStudyId information get successfully");
			responseObjectsMap.put("basicDetailVO", basicDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "partStudyId information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}

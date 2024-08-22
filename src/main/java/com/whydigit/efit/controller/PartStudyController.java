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
	PartStudyService partStudyService;

	Map<String, Object> responseObjectsMap = new HashMap<>();

	@GetMapping("/basicDetails")
	public ResponseEntity<ResponseDTO> getAllBasicDetail(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllBasicDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
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

	@GetMapping("/ActivebasicDetails")
	public ResponseEntity<ResponseDTO> getAllActiveBasicDetail(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllActiveBasicDetail()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		List<BasicDetailVO> basicDetailVO = new ArrayList<>();
		try {
			basicDetailVO = partStudyService.getAllActiveBasicDetail(orgId);
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
	public ResponseEntity<ResponseDTO> saveAttachments(@RequestParam MultipartFile[] files,
			@RequestParam PDAttachmentType type, @RequestParam Long refPsId) {
		String methodName = "saveAttachments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			partStudyService.saveAttachments(files, type, refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					new StringBuilder(type.name()).append(" attachments saved successfully").toString());
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					new StringBuilder(type.name()).append(" attachments saved failed. please try Again.").toString(),
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

//	@DeleteMapping("/stockDetail/{id}")
//	public ResponseEntity<ResponseDTO> deleteStockDetail(@PathVariable Long id) {
//		String methodName = "deleteStockDetail()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		try {
//			partStudyService.deleteStockDetail(id);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetail deleted successfully");
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetail deletion failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	@GetMapping("/generatePartStudyId")
	public ResponseEntity<ResponseDTO> generatePartStudyId(@RequestParam(required = false) String refPsId) {
		String methodName = "generatePartStudyId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		boolean status = false;
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
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Generate PartStudyId information information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/searchPartStudyById")
	public ResponseEntity<ResponseDTO> searchPartStudy(@RequestParam(required = false) Long emitterId,
			@RequestParam(required = false) Long refPsId, @RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String partName, @RequestParam(required = false) String partNumber) {
		String methodName = "searchPartStudy()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Object> basicDetailVO = new HashMap<>();
		try {
			basicDetailVO = partStudyService.searchPartStudy(emitterId, refPsId, orgId, partName, partNumber);
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

	@PostMapping("/uploadPartImage")
	public ResponseEntity<ResponseDTO> uploadPartImage(@RequestParam Long id,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadPartImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = partStudyService.uploadPartImage(id, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Part Image Uploaded Sucessfully");
			responseObjectsMap.put("partImageUpload", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Part Image Uploaded Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadPartDrawing")
	public ResponseEntity<ResponseDTO> uploadPartDrawing(@RequestParam Long id,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadPartDrawing()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = partStudyService.uploadPartDrawing(id, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Part Drawing Uploaded Sucessfully");
			responseObjectsMap.put("partImageUpload", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Part Drawing Uploaded Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadExPackageImage")
	public ResponseEntity<ResponseDTO> uploadExPackageImage(@RequestParam Long id,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadExPackageImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = partStudyService.uploadExPackageImage(id, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Existing Package Image Uploaded Sucessfully");
			responseObjectsMap.put("partImageUpload", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Existing Package Uploaded Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadApprovedCommercialImage")
	public ResponseEntity<ResponseDTO> uploadApprovedCommercialImage(@RequestParam Long id,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadApprovedCommercialImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = partStudyService.uploadApprovedCommercial(id, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Approved Commercial Image Uploaded Sucessfully");
			responseObjectsMap.put("partImageUpload", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Approved Commercial Uploaded Failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadApprovedTechnicalDrawing")
	public ResponseEntity<ResponseDTO> uploadApprovedTechnicalDrawing(@RequestParam Long id,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadApprovedTechnicalDrawing()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = partStudyService.uploadApprovedTechnicalDrawing(id, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Approved Technical Drawing Uploaded Sucessfully");
			responseObjectsMap.put("partImageUpload", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Approved Technical Drawing Uploaded Failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadPartImageInBloob")
	public ResponseEntity<ResponseDTO> uploadPartImageInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long refPsId) {
		String methodName = "uploadPartImageInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.uploadPartImageInBloob(file, refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartImage Successfully Upload");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PartImage Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadExistingPackingImageInBloob")
	public ResponseEntity<ResponseDTO> uploadExistingPackingImageInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long refPsId) {
		String methodName = "uploadExistingPackingImageInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.uploadExistingPackingImageInBloob(file, refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload ExistingPackingImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ExistingPackingImage Successfully Upload");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ExistingPackingImage Upload Failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadpartdrawingInBloob")
	public ResponseEntity<ResponseDTO> uploadpartdrawingInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long refPsId) {
		String methodName = "uploadpartdrawingInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.uploadpartdrawingInBloob(file, refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload partdrawing", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "partdrawing Successfully Upload");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "partdrawing Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadApprovedCommercialContractInBloob")
	public ResponseEntity<ResponseDTO> uploadApprovedCommercialContractInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long refPsId) {
		String methodName = "uploadApprovedCommercialContractInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.uploadApprovedCommercialContractInBloob(file, refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload ApprovedCommercialContract", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ApprovedCommercialContract Successfully Upload");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ApprovedCommercialContract Upload Failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadTechnicalDrawingInBloob")
	public ResponseEntity<ResponseDTO> uploadCommercialInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long refPsId) {
		String methodName = "uploadCommercialInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingDetailVO packingDetailVO = null;
		try {
			packingDetailVO = partStudyService.uploadCommercialInBloob(file, refPsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload Commercial", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Commercial Successfully Upload");
			responseObjectsMap.put("packingDetailVO", packingDetailVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Commercial Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	@PostMapping("/uploadPartImageInBloob")
//    public CompletableFuture<ResponseEntity<ResponseDTO>> uploadPartImageInBlob(@RequestParam("file") MultipartFile file, @RequestParam Long refPsId) {
//        return handleUpload(file, refPsId, "uploadPartImageInBloob()", partStudyService::uploadPartImageInBloob, "PartImage");
//    }
//
//    @PostMapping("/uploadExistingPackingImageInBloob")
//    public CompletableFuture<ResponseEntity<ResponseDTO>> uploadExistingPackingImageInBlob(@RequestParam("file") MultipartFile file, @RequestParam Long refPsId) {
//        return handleUpload(file, refPsId, "uploadExistingPackingImageInBloob()", partStudyService::uploadExistingPackingImageInBloob, "ExistingPackingImage");
//    }
//
//    @PostMapping("/uploadpartdrawingInBloob")
//    public CompletableFuture<ResponseEntity<ResponseDTO>> uploadPartDrawingInBlob(@RequestParam("file") MultipartFile file, @RequestParam Long refPsId) {
//        return handleUpload(file, refPsId, "uploadpartdrawingInBloob()", partStudyService::uploadpartdrawingInBloob, "PartDrawing");
//    }
//
//    @PostMapping("/uploadApprovedCommercialContractInBloob")
//    public CompletableFuture<ResponseEntity<ResponseDTO>> uploadApprovedCommercialContractInBlob(@RequestParam("file") MultipartFile file, @RequestParam Long refPsId) {
//        return handleUpload(file, refPsId, "uploadApprovedCommercialContractInBloob()", partStudyService::uploadApprovedCommercialContractInBloob, "ApprovedCommercialContract");
//    }
//
//    @PostMapping("/uploadTechnicalDrawingInBloob")
//    public CompletableFuture<ResponseEntity<ResponseDTO>> uploadCommercialInBlob(@RequestParam("file") MultipartFile file, @RequestParam Long refPsId) {
//        return handleUpload(file, refPsId, "uploadTechnicalDrawingInBloob()", partStudyService::uploadCommercialInBloob, "Commercial");
//    }
//
//    private CompletableFuture<ResponseEntity<ResponseDTO>> handleUpload(MultipartFile file, Long refPsId, String methodName, UploadFunction uploadFunction, String uploadType) {
//        LOGGER.debug("Starting method: {}", methodName);
//       
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        return CompletableFuture.supplyAsync(() -> {
//            PackingDetailVO packingDetailVO = null;
//            ResponseDTO responseDTO = null;
//            String errorMsg = null;
//            try {
//                packingDetailVO = uploadFunction.upload(file, refPsId);
//            } catch (Exception e) {
//                errorMsg = e.getMessage();
//                LOGGER.error("Unable to upload {}: {}", uploadType, errorMsg);
//            }
//
//            if (errorMsg == null) {
//                responseObjectsMap.put("message", uploadType + " successfully uploaded");
//                responseObjectsMap.put("packingDetailVO", packingDetailVO);
//                responseDTO = createServiceResponse(responseObjectsMap);
//            } else {
//                responseDTO = createServiceResponseError(responseObjectsMap, uploadType + " upload failed", errorMsg);
//            }
//
//            LOGGER.debug("Ending method: {}", methodName);
//            return ResponseEntity.ok().body(responseDTO);
//        });
//    }
//
//    @FunctionalInterface
//    private interface UploadFunction {
//        PackingDetailVO upload(MultipartFile file, Long refPsId) throws IOException;
//    }

}

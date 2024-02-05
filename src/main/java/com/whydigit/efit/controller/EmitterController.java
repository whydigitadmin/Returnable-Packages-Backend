package com.whydigit.efit.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
import com.whydigit.efit.common.EmitterConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.service.EmitterService;

@RestController
@RequestMapping("/api/emitter")
public class EmitterController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(EmitterController.class);

	@Autowired
	EmitterService emitterService;

	@PostMapping("/createIssueRequest")
	public ResponseEntity<ResponseDTO> createIssueRequest(@RequestBody IssueRequestDTO issueRequestDTO) {
		String methodName = "createIssueRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IssueRequestVO issueRequestVO = null;
		try {
			issueRequestVO = emitterService.createIssueRequest(issueRequestDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, EmitterConstant.ISSUE_REQUEST_SUCCESS_MESSAGE);
			responseObjectsMap.put(EmitterConstant.ISSUE_REQUEST_VO, issueRequestVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.ISSUE_REQUEST_REGISTERED_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssueRequest")
	public ResponseEntity<ResponseDTO> getIssueRequest(@RequestParam(required = false) Long emitterId,
			@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		String methodName = "getIssuseRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueRequestVO> issueRequestVO = new ArrayList<>();
		try {
			issueRequestVO = emitterService.getIssueRequest(emitterId, orgId, startDate, endDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, EmitterConstant.ISSUE_REQUEST_SUCCESS_MESSAGE);
			responseObjectsMap.put(EmitterConstant.ISSUE_REQUEST_VO, issueRequestVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.ISSUE_REQUEST_REGISTERED_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmitterAddress")
	public ResponseEntity<ResponseDTO> getEmitterAddress(@RequestParam(required = false) Long orgId) {
		String methodName = "getEmitterAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmitterAddressDTO> emitterAddressDTO = new ArrayList<>();
		try {
			emitterAddressDTO = emitterService.getEmitterAddress(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, EmitterConstant.EMITTER_ADRESS_SUCCESS_MESSAGE);
			responseObjectsMap.put(EmitterConstant.EMITTER_ADDRESS_VO, emitterAddressDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.EMITTER_ADDRESS_REGISTERED_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/updateIssueQty")
	public  ResponseEntity<ResponseDTO>updateIssueQty(@RequestParam Long issueRequestId,@RequestParam Long issueItemId,@RequestParam int issuedQty)
			 {
		String methodName = "updateIssueQty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
	ResponseDTO responseDTO = null;
		IssueRequestVO issueRequestVO = new IssueRequestVO();
		try {
			issueRequestVO = emitterService.updateIssueQty(issueRequestId,issueItemId,issuedQty);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, EmitterConstant.UPDATE_ISSUE_QTY_SUCCESS_MESSAGE);
			responseObjectsMap.put(EmitterConstant.ISSUE_REQUEST_VO, issueRequestVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.UPDATE_ISSUE_QTY_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	//emitter inward
	@GetMapping("/emitterInward")
	public ResponseEntity<ResponseDTO> getAllEmitterInward(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllEmitterInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmitterInwardVO> emitterInwardVO = new ArrayList<>();
		try {
			emitterInwardVO = emitterService.getAllEmitterInward(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterInward information get successfully");
			responseObjectsMap.put("emitterInwardVO", emitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "emitterInward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/emitterInward/{id}")
	public ResponseEntity<ResponseDTO> getEmitterInwardById(@PathVariable int id) {
		String methodName = "getEmitterInwardById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EmitterInwardVO emitterInwardVO = null;
		try {
			emitterInwardVO = emitterService.getEmitterInwardById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterInward found by ID");
			responseObjectsMap.put("emitterInwardVO", emitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "EmitterInward not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterInward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/emitterInward")
	public ResponseEntity<ResponseDTO> createEmitterInward(@RequestBody EmitterInwardVO emitterInwardVO) {
		String methodName = "createEmitterInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			EmitterInwardVO createdEmitterInwardVO = emitterService.createEmitterInward(emitterInwardVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterInward created successfully");
			responseObjectsMap.put("emitterInwardVO", createdEmitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterInward creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/emitterInward")
	public ResponseEntity<ResponseDTO> updateEmitterInward(@RequestBody EmitterInwardVO emitterInwardVO) {
		String methodName = "updateEmitterInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			EmitterInwardVO updateEmitterInwardVO = emitterService.updateEmitterInward(emitterInwardVO).orElse(null);
			if (updateEmitterInwardVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterInward updated successfully");
				responseObjectsMap.put("emitterInwardVO", updateEmitterInwardVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "EmitterInward not found for ID: " + emitterInwardVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "EmitterInward update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterInward update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/emitterInward/{id}")
	public ResponseEntity<ResponseDTO> deleteEmitterInward(@PathVariable int id) {
		String methodName = "deleteEmitterInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			emitterService.deleteEmitterInward(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterInward deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterInward deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//emitter outward
	@GetMapping("/emitterOutward")
	public ResponseEntity<ResponseDTO> getAllEmitterOutward(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllEmitterOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmitterOutwardVO> emitterOutwardVO = new ArrayList<>();
		try {
			emitterOutwardVO = emitterService.getAllEmitterOutward(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward information get successfully");
			responseObjectsMap.put("emitterOutwardVO", emitterOutwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "emitterOutward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/emitterOutward/{id}")
	public ResponseEntity<ResponseDTO> getEmitterOutwardById(@PathVariable int id) {
		String methodName = "getEmitterOutwardById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EmitterOutwardVO emitterOutwardVO = null;
		try {
			emitterOutwardVO = emitterService.getEmitterOutwardById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward found by ID");
			responseObjectsMap.put("emitterOutwardVO", emitterOutwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "EmitterOutward not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterOutward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/emitterOutward")
	public ResponseEntity<ResponseDTO> createEmitterOutward(@RequestBody EmitterOutwardVO emitterOutwardVO) {
		String methodName = "createEmitterOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			EmitterOutwardVO createdEmitterOutwardVO = emitterService.createEmitterOutward(emitterOutwardVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward created successfully");
			responseObjectsMap.put("emitterOutwardVO", createdEmitterOutwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterOutward creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/emitterOutward")
	public ResponseEntity<ResponseDTO> updateEmitterOutward(@RequestBody EmitterOutwardVO emitterOutwardVO) {
		String methodName = "updateEmitterOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			EmitterOutwardVO updateEmitterOutwardVO = emitterService.updateEmitterOutward(emitterOutwardVO)
					.orElse(null);
			if (updateEmitterOutwardVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward updated successfully");
				responseObjectsMap.put("emitterOutwardVO", updateEmitterOutwardVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "EmitterOutward not found for ID: " + emitterOutwardVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "EmitterOutward update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterOutward update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/emitterOutward/{id}")
	public ResponseEntity<ResponseDTO> deleteEmitterOutward(@PathVariable int id) {
		String methodName = "deleteEmitterOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			emitterService.deleteEmitterOutward(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterOutward deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}

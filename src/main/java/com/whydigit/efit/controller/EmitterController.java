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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.EmitterConstant;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.ResponseDTO;
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
	public  ResponseEntity<ResponseDTO> getIssueRequest(@RequestParam(required = false) Long emitterId,
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

}

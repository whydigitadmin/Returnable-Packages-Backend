package com.whydigit.efit.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.EmitterConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.BinAllotmentDTO;
import com.whydigit.efit.dto.BinOutwardDTO;
import com.whydigit.efit.dto.DispatchDTO;
import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.InwardDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.IssueRequestQtyApprovelDTO;
import com.whydigit.efit.dto.OutwardKitDetailsDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.AssetTaggingDetailsVO;
import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinOutwardVO;
import com.whydigit.efit.entity.DispatchVO;
import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;
import com.whydigit.efit.entity.InwardVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.entity.OutwardKitDetailsVO;
import com.whydigit.efit.entity.OutwardView;
import com.whydigit.efit.entity.VwEmitterInwardVO;
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
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String warehouseLocation,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Long warehouseLoacationId) {
		String methodName = "getIssuseRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueRequestVO> issueRequestVO = new ArrayList<>();
		try {
			issueRequestVO = emitterService.getIssueRequest(emitterId, warehouseLocation, orgId, startDate, endDate,
					warehouseLoacationId);
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

	@GetMapping("/cancelIssueRequest")
	public ResponseEntity<ResponseDTO> CancelIssueRequest(@RequestParam(required = false) Long issueRequestId,
			@RequestParam(required = false) Long issueRequestItemId) {
		String methodName = "CancelIssueRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			emitterService.cancelIssueRequest(issueRequestId, issueRequestItemId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, EmitterConstant.ISSUE_REQUEST_CANCEL_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.ISSUE_REQUEST_CANCEL_FAILED_MESSAGE, errorMsg);
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

	@PostMapping("/issueRequestQtyApprovel")
	public ResponseEntity<ResponseDTO> issueRequestQtyApprovel(
			@RequestBody IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO) {
		String methodName = "issueRequestQtyApprovel()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IssueRequestVO issueRequestVO = new IssueRequestVO();
		try {
			issueRequestVO = emitterService.issueRequestQtyApprovel(issueRequestQtyApprovelDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					EmitterConstant.ISSUE_REQUEST_QTY_APPROVEL_SUCCESS_MESSAGE);
			responseObjectsMap.put(EmitterConstant.ISSUE_REQUEST_VO, issueRequestVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.ISSUE_REQUEST_QTY_APPROVEL_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// emitter inward
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

	@GetMapping("/getEmitterDispatchByFlowId")
	public ResponseEntity<ResponseDTO> getEmitterDispatchByFlowId(@RequestParam Long orgid, @RequestParam Long flowId,
			@RequestParam Long emitterId) {
		String methodName = "getEmitterDispatchByFlowId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Object[]> outward = new ArrayList<>();
		try {
			outward = emitterService.getEmitterDispatchByFlowId(orgid, flowId, emitterId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, Object>> dispatch = findBinOutward(outward);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Emitter Dispatch found by ID");
			responseObjectsMap.put("EmitterDispatch", dispatch);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Emitter Dispatch not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, Object>> findBinOutward(List<Object[]> outward) {
		List<Map<String, Object>> dispatch = new ArrayList<>();
		for (Object[] ps : outward) {
			Map<String, Object> part = new HashMap<>();
			part.put("BinOutId", ps[0] != null ? ps[0].toString() : "");
			part.put("Date", ps[1] != null ? ps[1].toString() : "");
			part.put("Qty", ps[2] != null ? Integer.parseInt(ps[2].toString()) : 0);
			part.put("Part", ps[3] != null ? ps[3].toString() : "");
			part.put("Kit", ps[4] != null ? ps[4].toString() : "");
			dispatch.add(part);
		}
		return dispatch;
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
	public ResponseEntity<ResponseDTO> updateEmitterInward(@RequestBody InwardDTO inwardDTO) {
		String methodName = "updateEmitterInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			InwardVO updateInwardVO = emitterService.updateEmitterInward(inwardDTO);
			if (updateInwardVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Inward updated successfully");
				responseObjectsMap.put("inwardVO", updateInwardVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "EmitterInward not found for ID: " + inwardDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Inward update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Inward update failed", errorMsg);
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

	@GetMapping("/getViewEmitter")
	public ResponseEntity<ResponseDTO> getVwEmtInwardByOrgIdAndEmtId(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) Long emitterId) {
		String methodName = "getVwEmtInwardByOrgIdAndEmtId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VwEmitterInwardVO> ewEmitterInwardVO = new ArrayList<>();
		try {
			ewEmitterInwardVO = emitterService.getVwEmtInwardByOrgIdAndEmtId(orgId, emitterId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Emitter inward found successfully.");
			responseObjectsMap.put("ewEmitterInwardVO", ewEmitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Emitter inward not found for the user.";
			responseDTO = createServiceResponseError(responseObjectsMap, "Emitter inward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getViewEmitterByFlow")
	public ResponseEntity<ResponseDTO> getVwEmtInwardByOrgIdAndEmtIdAndFlow(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) Long emitterId, @RequestParam(required = true) Long flowid) {
		String methodName = "getVwEmtInwardByOrgIdAndEmtIdAndFlow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VwEmitterInwardVO> ewEmitterInwardVO = new ArrayList<>();
		try {
			ewEmitterInwardVO = emitterService.getVwEmtInwardByOrgIdAndEmtIdAndFlow(orgId, emitterId, flowid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Emitter inward found successfully.");
			responseObjectsMap.put("ewEmitterInwardVO", ewEmitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Emitter inward not found for the user.";
			responseDTO = createServiceResponseError(responseObjectsMap, "Emitter inward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getViewEmitterByWarehouse")
	public ResponseEntity<ResponseDTO> getVwEmtInwardByOrgIdAndWarehouse(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) Long warehouseid) {
		String methodName = "getVwEmtInwardByOrgIdAndWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VwEmitterInwardVO> ewEmitterInwardVO = new ArrayList<>();
		try {
			ewEmitterInwardVO = emitterService.getVwEmtInwardByOrgIdAndWarehouse(orgId, warehouseid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Emitter inward found successfully.");
			responseObjectsMap.put("ewEmitterInwardVO", ewEmitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Emitter inward not found for the user.";
			responseDTO = createServiceResponseError(responseObjectsMap, "Emitter inward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/viewEmitterInward")
	public ResponseEntity<ResponseDTO> getAllViewEmitterInward(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) Long emitterId, @RequestParam(required = false) Long flowId,
			@RequestParam(required = false) Long warehouseLocationId) {
		String methodName = "getAllViewEmitterInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Object> vwEmitterInwardVO = new HashMap<>();
		try {
			vwEmitterInwardVO = emitterService.getAllViewEmitterInward(orgId, emitterId, flowId, warehouseLocationId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Emitter inward information get successfully");
			responseObjectsMap.put("vwEmitterInwardVO", vwEmitterInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Emitter inward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//emitter  bin outward
	@GetMapping("/getAllBinOutward")
	public ResponseEntity<ResponseDTO> getAllBinOutward(@RequestParam(required = false) Long orgId,@RequestParam Long emitterId) {
		String methodName = "getAllBinOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BinOutwardVO> binOutwardVO = new ArrayList<>();
		try {
			binOutwardVO = emitterService.getAllBinOutward(orgId,emitterId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward information get successfully");
			responseObjectsMap.put("binOutwardVO", binOutwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "emitterOutward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getAllBinOutwardByDocId")
	public ResponseEntity<ResponseDTO> getAllBinOutwardByDocId(@RequestParam(required = false) String docId) {
		String methodName = "getAllBinOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BinOutwardVO> binOutwardVO = new ArrayList<>();
		try {
			binOutwardVO = emitterService.getAllBinOutwardByDocId(docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward information get successfully");
			responseObjectsMap.put("binOutwardVO", binOutwardVO);
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
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOut0w00 ard deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "EmitterOutward deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Get Emitter By WarehouseId
	@GetMapping("/getemitterByWarehouseId")
	public ResponseEntity<ResponseDTO> getEmitterByWarehouseId(@RequestParam Long orgid,
			@RequestParam Long warehouseid) {
		String methodName = "getemitterByWarehouseId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> emitter = new HashSet<>();
		try {
			emitter = emitterService.getEmitterByWarehouseId(orgid, warehouseid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allEmitter = findEmitter(emitter);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Emitters found by ID");
			responseObjectsMap.put("emitters", allEmitter);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Emitters not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Emitters not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findEmitter(Set<Object[]> emitter) {
		List<Map<String, String>> allEmitter = new ArrayList<>();
		for (Object[] emi : emitter) {
			Map<String, String> emiter = new HashMap<>();
			emiter.put("emitterId", emi[0].toString());
			emiter.put("emitterName", emi[1].toString());
			allEmitter.add(emiter);
		}
		return allEmitter;
	}

	@GetMapping("/maxPartQtyPerKit")
	public ResponseEntity<ResponseDTO> getAllMaxPartQtyPerKit(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) Long emitterId, @RequestParam(required = false) Long flowId,
			@RequestParam(required = false) String partNumber

	) {
		String methodName = "getAllMaxPartQtyPerKit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Object> maxPartQtyPerKitVO = new HashMap<>();
		try {
			maxPartQtyPerKitVO = emitterService.getAllMaxPartQtyPerKit(orgId, emitterId, flowId, partNumber);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Maximum part qty per kit information get successfully");
			responseObjectsMap.put("maxPartQtyPerKitVO", maxPartQtyPerKitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Maximum part qty per kit information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/emitterOutward/v1")
	public ResponseEntity<ResponseDTO> getAllEmitterOutwardView(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) Long flowId) {
		String methodName = "getAllEmitterOutwardView()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<OutwardView> outwardView = new ArrayList<>();
		try {
			outwardView = emitterService.getAllEmitterOutwardView(orgId, flowId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmitterOutward information get successfully");
			responseObjectsMap.put("emitterOutwardVO", outwardView);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "emitterOutward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/updateOutwardKitQty")
	public ResponseEntity<ResponseDTO> updateOutwardKitQty(@RequestBody OutwardKitDetailsDTO OutwardKitDetailsDTO) {
		String methodName = "updateOutwardKitQty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		OutwardKitDetailsVO outwardKitDetailsVO = new OutwardKitDetailsVO();
		try {
			outwardKitDetailsVO = emitterService.updateOutwardKitQty(OutwardKitDetailsDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					EmitterConstant.UPDATE_OUTWARD_KIT_QUANTITY_SUCCESS_MESSAGE);
			responseObjectsMap.put(EmitterConstant.ISSUE_REQUEST_VO, outwardKitDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					EmitterConstant.UPDATE_OUTWARD_KIT_QUANTITY_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdByBinOutward")
	public ResponseEntity<ResponseDTO> getDocIdByBinOutward() {
		String methodName = "getDocIdByBinOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String binOutwardDocId = null;
		try {
			binOutwardDocId = emitterService.getDocIdByBinOutward();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Inward DocId found success");
			responseObjectsMap.put("binOutwardDocId", binOutwardDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Outward DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Bin Allotment

	@PostMapping("/binAllotment")
	public ResponseEntity<ResponseDTO> createBinAllotment(@RequestBody BinAllotmentDTO binAllotmentDTO) {
		String methodName = "createBinAllotment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BinAllotmentNewVO binAllotmentVO = new BinAllotmentNewVO();
		try {
			binAllotmentVO = emitterService.createBinAllotment(binAllotmentDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment Created Successfully");
			responseObjectsMap.put("binAllotmentVO", binAllotmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdByBinallotment")
	public ResponseEntity<ResponseDTO> getDocIdByBinallotment() {
		String methodName = "getDocIdByBinallotment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String allotDocId = null;
		try {
			allotDocId = emitterService.getDocIdByBinallotment();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment DocId found success");
			responseObjectsMap.put("allotDocId", allotDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getReqDetailsByOrgId")
	public ResponseEntity<ResponseDTO> getReqDetailsByOrgId(@RequestParam Long orgid, @RequestParam String reqNo) {
		String methodName = "getReqDetailsByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> partstudy = new HashSet<>();
		try {
			partstudy = emitterService.getReqDetailsByOrgId(orgid, reqNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, Object>> binReqDetails = findPartStudy(partstudy);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment found by ID");
			responseObjectsMap.put("BinAllotment", binReqDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, Object>> findPartStudy(Set<Object[]> partstudy) {
		List<Map<String, Object>> binReqDetails = new ArrayList<>();
		for (Object[] ps : partstudy) {
			Map<String, Object> part = new HashMap<>();
			part.put("reqNo", ps[0] != null ? ps[0].toString() : "");
			part.put("reqDate", ps[1] != null ? ps[1].toString() : "");
			part.put("emitter", ps[2] != null ? ps[2].toString() : "");
			part.put("emitterid", ps[3] != null ? ps[3].toString() : "");
			part.put("kitcode", ps[4] != null ? ps[4].toString() : "");
			part.put("reqKitQty", ps[5] != null ? Integer.parseInt(ps[5].toString()) : 0);
			part.put("partno", ps[6] != null ? ps[6].toString() : "");
			part.put("partname", ps[7] != null ? ps[7].toString() : "");
			part.put("flow", ps[8] != null ? ps[8].toString() : "");
			part.put("flowid", ps[9] != null ? ps[9].toString() : "");
			binReqDetails.add(part);
		}
		return binReqDetails;
	}

	@GetMapping("/getAllBinAllotmentByOrgId")
	public ResponseEntity<ResponseDTO> getAllBinAllotment(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllBinAllotment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BinAllotmentNewVO> binAllotmentNewVO = new ArrayList<>();
		try {
			binAllotmentNewVO = emitterService.getAllBinAllotment(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BinAllotment get successfully");
			responseObjectsMap.put("binAllotmentNewVO", binAllotmentNewVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "BinAllotment information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getTaggingDetailsByRfId")
	public ResponseEntity<ResponseDTO> getTaggingDetailsByRfId(@RequestParam String rfId) {
		String methodName = "getTaggingDetailsByRfId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<AssetTaggingDetailsVO> assetTaggingDetailsVO = null;
		try {
			assetTaggingDetailsVO = emitterService.getTaggingDetailsByRfId(rfId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TaggingDetails found by RfID");
			responseObjectsMap.put("assetTaggingDetailsVO", assetTaggingDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "TaggingDetails not found for RfID: " + rfId;
			responseDTO = createServiceResponseError(responseObjectsMap, "TaggingDetails not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Bin Outward

	@PostMapping("/binOutward")
	public ResponseEntity<ResponseDTO> createBinOutward(@RequestBody BinOutwardDTO binOutwardDTO) {
		String methodName = "createBinOutward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BinOutwardVO binOutwardVO = new BinOutwardVO();
		try {
			binOutwardVO = emitterService.createBinOutward(binOutwardDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Outward Created Successfully");
			responseObjectsMap.put("binOutwardVO", binOutwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Outward Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTaggingDetailsByTagCode")
	public ResponseEntity<ResponseDTO> getTaggingDetailsByTagCode(@RequestParam String tagCode) {
		String methodName = "getTaggingDetailsByTagCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<AssetTaggingDetailsVO> assetTaggingDetailsVO = null;
		try {
			assetTaggingDetailsVO = emitterService.getTaggingDetailsByTagCode(tagCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TaggingDetails found by TagCode");
			responseObjectsMap.put("assetTaggingDetailsVO", assetTaggingDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "TaggingDetails not found for TagCode: " + tagCode;
			responseDTO = createServiceResponseError(responseObjectsMap, "TaggingDetails not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllAllotmentById")
	public ResponseEntity<ResponseDTO> getAllAllotmentById(@RequestParam(required = false) String docId) {
		String methodName = "getAllAllotmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BinAllotmentNewVO> binAllotmentNewVO = new ArrayList<>();
		try {
			binAllotmentNewVO = emitterService.getAllAllotmentById(docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Allotment information get successfully");
			responseObjectsMap.put("binAllotmentNewVO", binAllotmentNewVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Allotment information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getkitAssetDetailsByKitId")
	public ResponseEntity<ResponseDTO> getkitAssetDetailsByKitId(@RequestParam(required = false) String kitCode,
			@RequestParam(required = false) int quantity) {
		String methodName = "getkitAssetDetailsByKitId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> kitAssetVO = new HashSet<>();
		try {
			kitAssetVO = emitterService.getkitAssetDetailsByKitId(kitCode, quantity);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>> getAssetDetails = findAsset(kitAssetVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit Asset information get successfully");
			responseObjectsMap.put("kitAssetVO", getAssetDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit Asset information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, Object>> findAsset(Set<Object[]> kitAssetVO) {
		List<Map<String, Object>> getAssetDetails = new ArrayList<>();
		for (Object[] asset : kitAssetVO) {
			Map<String, Object> part = new HashMap<>();
			part.put("assetCategory", asset[0] != null ? asset[0].toString() : "");
			part.put("asset", asset[1] != null ? asset[1].toString() : "");
			part.put("assetCode", asset[2] != null ? asset[2].toString() : "");
			part.put("qty", asset[3] != null ? Integer.parseInt(asset[3].toString()) : 0);
			getAssetDetails.add(part);
		}
		return getAssetDetails;
	}

	@GetMapping("/getIssueRequestreportByOrgId")
	public ResponseEntity<ResponseDTO> getIssueRequestreportByOrgId(@RequestParam(required = false) Long OrgId,
			@RequestParam(required = false) Long userId) {
		String methodName = "getIssueRequestreportByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> issueRequestVO = new HashSet<>();
		try {
			issueRequestVO = emitterService.getIssueRequestreportByOrgId(OrgId, userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>> getIssueReport = findIssueRequest(issueRequestVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IssueRequest  information get successfully");
			responseObjectsMap.put("issueRequestVO", getIssueReport);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "IssueRequest  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, Object>> findIssueRequest(Set<Object[]> issueRequestVO) {
		List<Map<String, Object>> getIssueReport = new ArrayList<>();
		for (Object[] issueReport : issueRequestVO) {
			Map<String, Object> issue = new HashMap<>();
			issue.put("reqNo", issueReport[0] != null ? issueReport[0].toString() : "");
			issue.put("reqDate", issueReport[1] != null ? issueReport[1].toString() : "");
			issue.put("emitter", issueReport[2] != null ? issueReport[2].toString() : "");
			issue.put("emitterId", issueReport[3] != null ? issueReport[3].toString() : "");
			issue.put("kitCode", issueReport[4] != null ? issueReport[4].toString() : "");
			issue.put("reqKitQty", issueReport[5] != null ? Integer.parseInt(issueReport[5].toString()) : 0);
			issue.put("partNo", issueReport[6] != null ? issueReport[6].toString() : "");
			issue.put("partName", issueReport[7] != null ? issueReport[7].toString() : "");
			issue.put("flow", issueReport[8] != null ? issueReport[8].toString() : "");
			issue.put("flowId", issueReport[9] != null ? issueReport[9].toString() : "");
			getIssueReport.add(issue);
		}
		return getIssueReport;
	}

	@PostMapping("/uploadPodFilePath")
	public ResponseEntity<ResponseDTO> uploadPodFilePath(@RequestParam("file") MultipartFile file,
			@RequestParam String allotNo) {
		String methodName = "uploadPodFilePath()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = emitterService.uploadPodFilePath(file, allotNo);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pod File upload successfully");
			responseObjectsMap.put("EmitterInwardVO", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Pod File upload failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getCustomizedAllotmentDetailsByEmitter")
	public ResponseEntity<ResponseDTO> getCustomizedAllotmentDetailsByEmitter(
			@RequestParam(required = false) String kitCode, @RequestParam(required = false) String flow,
			@RequestParam(required = true) Long emitterId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAllotDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAllotDate) {
		String methodName = "getCustomizedAllotmentDetailsByEmitter()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BinAllotmentNewVO> binAllotmentNewVO = new ArrayList<>();
		try {
			binAllotmentNewVO = emitterService.getCustomizedAllotmentDetailsByEmitter(kitCode, flow, emitterId,
					startAllotDate, endAllotDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment Details Get Successfully");
			responseObjectsMap.put("binAllotmentVO", binAllotmentNewVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment Details Get Successfully",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/createDispatch")
	public ResponseEntity<ResponseDTO> createDispatch(@RequestBody DispatchDTO  dispatchDTO) {
		String methodName = "createDispatch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DispatchVO dipatchVO = null;
		try {
			dipatchVO = emitterService.createDispatch(dispatchDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Dispatch Saved successfully");
			responseObjectsMap.put("dipatchVO", dipatchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Dispatch Save failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
}

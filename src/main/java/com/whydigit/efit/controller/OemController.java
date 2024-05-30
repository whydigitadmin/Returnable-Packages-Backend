package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.BinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
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
	
	@GetMapping("/getFlowByUserId")
	public ResponseEntity<ResponseDTO> getFlowByUserId(@RequestParam Long userId ,@RequestParam Long orgId) {
		String methodName = "getFlowByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> outwardDetails = new HashSet<>();
		try {
			outwardDetails = oemService.getFlowByUserId(userId,orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, Object>> flow = findFlowDetails(outwardDetails);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OutwardDetails Details found by emitter");
			responseObjectsMap.put("flowDetails", flow);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "OutwardDetails Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, Object>> findFlowDetails(Set<Object[]> outwardDetails) {
		List<Map<String, Object>> flow = new ArrayList<>();
		for (Object[] f : outwardDetails) {
			Map<String, Object> f1 = new HashMap<>();
			f1.put("flowId", f[0] != null ? Integer.parseInt(f[0].toString()):0);
			f1.put("flow", f[1] != null ? f[1].toString() : "");
//			f1.put("flowId", f[2] != null ? f[2].toString() : "");
//			f1.put("flow", f[3] != null ? f[3].toString() : "");
//			f1.put("kitNo", f[4] != null ? f[4].toString() : "");
//			f1.put("emitterOutwardDate", f[5] != null ? f[5].toString() : "");
			flow.add(f1);
		}
		return flow;
	}
	
	@GetMapping("/getOutwardDetailsByFlow")
	public ResponseEntity<ResponseDTO> getOutwardDetailsByFlow(@RequestParam Long flowId ,@RequestParam Long orgId) {
		String methodName = "getOutwardDetailsByFlow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> outwardDetails1 = new HashSet<>();
		try {
			outwardDetails1 = oemService.getOutwardDetailsByFlow(flowId,orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, Object>> flowD = findOutwardDetails(outwardDetails1);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OutwardDetails Details found by emitter");
			responseObjectsMap.put("outwardDe", flowD);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "OutwardDetails Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, Object>> findOutwardDetails(Set<Object[]> outwardDetails1) {
		List<Map<String, Object>> flowD = new ArrayList<>();
		for (Object[] f : outwardDetails1) {
			Map<String, Object> f1 = new HashMap<>();
			f1.put("docId", f[0] != null ? f[0].toString() : "");
			f1.put("outwardKitQty", f[1] != null ? Integer.parseInt(f[1].toString()):0);
			f1.put("kitNo", f[2] != null ? f[2].toString() : "");
			f1.put("docDate", f[3] != null ? f[3].toString() : "");
			flowD.add(f1);
		}
		return flowD;
	}
	
	@GetMapping("/getAllOemBinInward")
	public ResponseEntity<ResponseDTO> getAllOemBinInward(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllOemBinInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<OemBinInwardVO> oemBinInwardVOs = new ArrayList<>();
		try {
			oemBinInwardVOs = oemService.getAllOemBinInward(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OemBinInward information get successfully");
			responseObjectsMap.put("oemBinInwardVOs", oemBinInwardVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "OemBinInward information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
}



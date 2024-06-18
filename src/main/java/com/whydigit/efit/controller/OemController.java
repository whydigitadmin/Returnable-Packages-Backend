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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.GatheringEmptyDTO;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.GatheringEmptyVO;
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
			f1.put("partName", f[4] != null ? f[4].toString() : "");
			f1.put("partNo", f[5] != null ? f[5].toString() : "");
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
	
	@GetMapping("/getAllOemBinInwardByDocId")
	public ResponseEntity<ResponseDTO> getAllOemBinInwardByDocId(@RequestParam(required = false) String docId) {
		String methodName = "getAllOemBinInwardByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		OemBinInwardVO oemBinInwardVOs = null;
		try {
			oemBinInwardVOs = oemService.getAllOemBinInwardByDocId(docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OemBinInward information get successfully By DocID");
			responseObjectsMap.put("oemBinInwardVOs", oemBinInwardVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "OemBinInward information receive failed By DocID", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	//OEM BIN OUTWARD
	
	//OEM Bin Inward
		@PostMapping("/oemBinOutward")
		public ResponseEntity<ResponseDTO> createOemBinOutward(@RequestBody OemBinOutwardDTO binOutwardDTO) {
			String methodName = "createOemBinOutward()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			OemBinOutwardVO oemBinOutwardVO = new OemBinOutwardVO();
			try {
				oemBinOutwardVO = oemService.createOemBinOutward(binOutwardDTO);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Bin Outward Created Successfully");
				responseObjectsMap.put("binOutwardDTO", binOutwardDTO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem Bin Outward Failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
	
		@GetMapping("/getDocIdByOemBinInward")
		public ResponseEntity<ResponseDTO> getDocIdByOemBinInward() {
			String methodName = "getDocIdByOemBinInward()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();  
			ResponseDTO responseDTO = null;
			String oemBinInwardDocId = null;
			try {
				oemBinInwardDocId = oemService.getDocIdByOemBinInward();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Bin Inward DocId found success");
				responseObjectsMap.put("oemBinInwardDocId", oemBinInwardDocId);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = " not found for ID: ";
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem BinInward DocId not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		

		@GetMapping("/getOemStockBranchByUserId")
		public ResponseEntity<ResponseDTO> getOemStockBranchByUserId(@RequestParam Long orgId,@RequestParam Long userId) {
			String methodName = "getOemStockBranchByUserId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String,Object>> stockbr = new ArrayList<>();
			try {
				stockbr = oemService.getOemStockBranchByUserId(orgId, userId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem StockBranch List found Success");
				responseObjectsMap.put("branch", stockbr);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = " not found for ID: ";
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem StockBranch List not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getOemStockDetailsForBinOutward")
		public ResponseEntity<ResponseDTO> getOemStockDetailsForBinOutward(@RequestParam String stockBranch) {
			String methodName = "getOemStockDetailsForBinOutward()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String,Object>> stockDeatils = new ArrayList<>();
			try {
				stockDeatils = oemService.getOemStockDeatilsForOemOutward(stockBranch);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Stock Details found Success");
				responseObjectsMap.put("stockDetails", stockDeatils);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = " not found for ID: ";
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem Stock Details not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		
		@GetMapping("/getDocIdByOemBinOutward")
		public ResponseEntity<ResponseDTO> getDocIdByOemBinOutward() {
			String methodName = "getDocIdByOemBinOutward()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();  
			ResponseDTO responseDTO = null;
			String oemBinOutwardDocId = null;
			try {
				oemBinOutwardDocId = oemService.getDocIdByOemBinOutward();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Bin Outward DocId found success");
				responseObjectsMap.put("oemBinOutwardDocId", oemBinOutwardDocId);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = " not found for ID: ";
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem Bin Outward DocId not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		//Gathering Empty
		
		@PostMapping("/createGatheringEmpty")
		public ResponseEntity<ResponseDTO> createGatheringEmpty(@RequestBody GatheringEmptyDTO gatheringEmptyDTO) {
			String methodName = "createGatheringEmpty()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			GatheringEmptyVO gatheringEmptyVO = new GatheringEmptyVO();
			try {
				gatheringEmptyVO = oemService.createGatheringEmpty(gatheringEmptyDTO);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gathering Empty Created Successfully");
				responseObjectsMap.put("gatheringEmptyVO", gatheringEmptyVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Gathering Empty Creation Failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getAllGathering")
		public ResponseEntity<ResponseDTO> getAllGathering(@RequestParam(required = false) Long orgId) {
			String methodName = "getAllGathering()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<GatheringEmptyVO> gatheringEmptyVOs = new ArrayList<>();
			try {
				gatheringEmptyVOs = oemService.getAllGathering(orgId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gathering Empty information get successfully");
				responseObjectsMap.put("gatheringEmptyVOs", gatheringEmptyVOs);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Gathering Empty information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);

		}
		
		@GetMapping("/getAllOemBinOutward")
		public ResponseEntity<ResponseDTO> getAllOemBinOutward(@RequestParam(required = true) Long orgId) {
			String methodName = "getAllOemBinOutward()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<OemBinOutwardVO> oemBinOutwardVO = new ArrayList<>();
			try {
				oemBinOutwardVO = oemService.getAllOemBinOutward(orgId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem BinOutward information get successfully");
				responseObjectsMap.put("oemBinOutwardVO", oemBinOutwardVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem BinOutward  information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);

		}
		
		@GetMapping("/getEmptyAssetDetailsForGathering")
		public ResponseEntity<ResponseDTO> getEmptyAssetDetailsForGathering(@RequestParam(required = true) String stockBranch,@RequestParam(required = true) Long orgId) {
			String methodName = "getEmptyAssetDetailsForGathering()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String,Object>> oemEmptyDetails = new ArrayList<>();
			try {
				oemEmptyDetails = oemService.getOemEmptyDeatilsForEmptyGathering(stockBranch, orgId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Empty Details information get successfully");
				responseObjectsMap.put("oemEmptyDetails", oemEmptyDetails);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Oem Empty Details  information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);

		}
		
}



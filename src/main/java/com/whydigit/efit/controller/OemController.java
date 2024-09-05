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
import com.whydigit.efit.dto.GatheringEmptyDTO;
import com.whydigit.efit.dto.IssueManifestProviderDTO;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.dto.RetreivalDTO;
import com.whydigit.efit.dto.RetrievalManifestProviderDTO;
import com.whydigit.efit.dto.TransportPickupDTO;
import com.whydigit.efit.entity.DeclarationAndNotesVO;
import com.whydigit.efit.entity.GatheringEmptyVO;
import com.whydigit.efit.entity.IssueManifestProviderVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.entity.RetreivalVO;
import com.whydigit.efit.entity.RetrievalManifestProviderVO;
import com.whydigit.efit.entity.TransportPickupVO;
import com.whydigit.efit.service.OemService;

@RestController
@RequestMapping("/api/oem")
public class OemController extends BaseController {

	@Autowired
	OemService oemService;

	// OEM Bin Inward
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
	public ResponseEntity<ResponseDTO> getFlowByUserId(@RequestParam Long userId, @RequestParam Long orgId) {
		String methodName = "getFlowByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> outwardDetails = new HashSet<>();
		try {
			outwardDetails = oemService.getFlowByUserId(userId, orgId);
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
			f1.put("flowId", f[0] != null ? Integer.parseInt(f[0].toString()) : 0);
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
	public ResponseEntity<ResponseDTO> getOutwardDetailsByFlow(@RequestParam Long flowId, @RequestParam Long orgId) {
		String methodName = "getOutwardDetailsByFlow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> outwardDetails1 = new HashSet<>();
		try {
			outwardDetails1 = oemService.getOutwardDetailsByFlow(flowId, orgId);
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
			f1.put("outwardKitQty", f[1] != null ? Integer.parseInt(f[1].toString()) : 0);
			f1.put("kitNo", f[2] != null ? f[2].toString() : "");
			f1.put("docDate", f[3] != null ? f[3].toString() : "");
			f1.put("partName", f[4] != null ? f[4].toString() : "");
			f1.put("partNo", f[5] != null ? f[5].toString() : "");
			flowD.add(f1);
		}
		return flowD;
	}

	@GetMapping("/getAllOemBinInward")
	public ResponseEntity<ResponseDTO> getAllOemBinInward(@RequestParam(required = false) Long orgId,@RequestParam(required = false) Long receiverId) {
		String methodName = "getAllOemBinInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<OemBinInwardVO> oemBinInwardVOs = new ArrayList<>();
		try {
			oemBinInwardVOs = oemService.getAllOemBinInward(orgId,receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OemBinInward information get successfully");
			responseObjectsMap.put("oemBinInwardVOs", oemBinInwardVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "OemBinInward information receive failed",
					errorMsg);
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
			responseDTO = createServiceResponseError(responseObjectsMap,
					"OemBinInward information receive failed By DocID", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// OEM BIN OUTWARD
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
			responseObjectsMap.put("oemBinOutwardVO", oemBinOutwardVO);
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
	public ResponseEntity<ResponseDTO> getOemStockBranchByUserId(@RequestParam Long orgId, @RequestParam Long userId) {
		String methodName = "getOemStockBranchByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockbr = new ArrayList<>();
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
		List<Map<String, Object>> stockDeatils = new ArrayList<>();
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

	// Gathering Empty

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
			responseDTO = createServiceResponseError(responseObjectsMap, "Gathering Empty information receive failed",
					errorMsg);
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
			responseDTO = createServiceResponseError(responseObjectsMap, "Oem BinOutward  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getEmptyAssetDetailsForGathering")
	public ResponseEntity<ResponseDTO> getEmptyAssetDetailsForGathering(
			@RequestParam(required = true) String stockBranch, @RequestParam(required = true) Long orgId) {
		String methodName = "getEmptyAssetDetailsForGathering()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> oemEmptyDetails = new ArrayList<>();
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
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Oem Empty Details  information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDocIdByGatheringEmpty")
	public ResponseEntity<ResponseDTO> getDocIdByGatheringEmpty() {
		String methodName = "getDocIdByGatheringEmpty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String gatheringdocid = null;
		try {
			gatheringdocid = oemService.getDocIdByGatheringEmpty();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "gathering Empty DocId found success");
			responseObjectsMap.put("gatheringDocId", gatheringdocid);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "gathering Empty DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Oem Outwarddetails for Retreival
	@GetMapping("/getOemOutwardDetailsForRetreival")
	public ResponseEntity<ResponseDTO> getOemOutwardDetailsForRettreival(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) Long receiverId, @RequestParam(required = true) String stockBranch) {
		String methodName = "getOemOutwardDetailsForRetreival()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> oemOutwardDetails = new ArrayList<>();
		try {
			oemOutwardDetails = oemService.getOemOutwardDeatilsForRetreival(orgId, receiverId, stockBranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Oem Outward Details information get successfully");
			responseObjectsMap.put("oemOutwardDetails", oemOutwardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Oem Outward Details  information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDocIdByRetreival")
	public ResponseEntity<ResponseDTO> getDocIdByRetreival() {
		String methodName = "getDocIdByRetreival()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String retreivalDocid = null;
		try {
			retreivalDocid = oemService.getDocIdByRetreival();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Retreival DocId found success");
			responseObjectsMap.put("retreivalDocid", retreivalDocid);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Retreival DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createRetreival")
	public ResponseEntity<ResponseDTO> createRetreival(@RequestBody RetreivalDTO retreivalDTO) {
		String methodName = "createRetreival()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RetreivalVO retreivalVO = new RetreivalVO();
		try {
			retreivalVO = oemService.CreateRetreival(retreivalDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Retreival Created Successfully");
			responseObjectsMap.put("retreivalVO", retreivalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Retreival Creation Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRetrievalDetails")
	public ResponseEntity<ResponseDTO> getRetrievalDetailsByReceiverId(@RequestParam Long orgId,
			@RequestParam Long receiverId) {
		String methodName = "getOemStockBranchByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> retrievalDetails = new ArrayList<>();
		try {
			retrievalDetails = oemService.getRetrievalDeatilsForPendingPickup(orgId, receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Retrieval Details found Success");
			responseObjectsMap.put("retrievalDetails", retrievalDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Retrieval Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRetrievalDeatilsforPickupFillgrid")
	public ResponseEntity<ResponseDTO> getRetrievalDeatilsforPickupFillgrid(@RequestParam Long orgId,
			@RequestParam String rmNo) {
		String methodName = "getRetrievalDeatilsforPickupFillgrid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> retrievalDetailsforPickup = new ArrayList<>();
		try {
			retrievalDetailsforPickup = oemService.getRetrievalDeatilsByRmNoforPickupFillgrid(orgId, rmNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Retrieval Details for Pickup Fillgrid found Success");
			responseObjectsMap.put("retrievalDetails", retrievalDetailsforPickup);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Retrieval Details for Pickup Fillgrid not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdByTransportPickup")
	public ResponseEntity<ResponseDTO> getDocIdByTransportPickup() {
		String methodName = "getDocIdByTransportPickup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String transportPickupDocid = null;
		try {
			transportPickupDocid = oemService.getDocIdByTransportPickup();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "transportPickup DocId found success");
			responseObjectsMap.put("transportPickupDocid", transportPickupDocid);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "transportPickup DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createTransportPickp")
	public ResponseEntity<ResponseDTO> createTransportPickp(@RequestBody TransportPickupDTO transportPickupDTO) {
		String methodName = "createTransportPickp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TransportPickupVO transportPickupVO = new TransportPickupVO();
		try {
			transportPickupVO = oemService.createTransPickup(transportPickupDTO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TransportPickup Created Successfully");
			responseObjectsMap.put("transportPickupVO", transportPickupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "TransportPickup Creation Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// GET ALL BININWARD BY RECEIVERID

	@GetMapping("/getAllOemBinInwardByReceiverId")
	public ResponseEntity<ResponseDTO> getAllOemBinInwardByReceiverId(@RequestParam(required = false) Long receiverId) {
		String methodName = "getAllOemBinInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<OemBinInwardVO> oemBinInwardVOs = new ArrayList<>();
		try {
			oemBinInwardVOs = oemService.getAllOemBinInwardByReceiverId(receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OemBinInward information get successfully");
			responseObjectsMap.put("oemBinInwardVOs", oemBinInwardVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "OemBinInward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// GET ALL BININWARD BY RECEIVERID

	@GetMapping("/getAllOemBinOutwardByReceiverId")
	public ResponseEntity<ResponseDTO> getAllOemBinOutwardByReceiverId(
			@RequestParam(required = false) Long receiverId) {
		String methodName = "getAllOemBinOutwardByReceiverId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<OemBinOutwardVO> oemBinOutwardVOs = new ArrayList<>();
		try {
			oemBinOutwardVOs = oemService.getAllOemBinOutwardByReceiverId(receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OemBinOutward information get successfully");
			responseObjectsMap.put("OemBinOutward", oemBinOutwardVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "OemBinOutward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllGatheringEmptyByReceiverId")
	public ResponseEntity<ResponseDTO> getAllGatheringEmptyByReceiverId(
			@RequestParam(required = false) Long receiverId) {
		String methodName = "getAllGatheringEmptyByReceiverId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GatheringEmptyVO> gatheringEmptyVOs = new ArrayList<>();
		try {
			gatheringEmptyVOs = oemService.getAllGatheringEmptyByReceiverId(receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatheringEmpty information get successfully");
			responseObjectsMap.put("gatheringEmptyVO", gatheringEmptyVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GatheringEmpty information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllReterivalByReceiverId")
	public ResponseEntity<ResponseDTO> getAllReterivalByReceiverId(@RequestParam(required = false) Long receiverId) {
		String methodName = "getAllReterivalByReceiverId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RetreivalVO> retreivalVOs = new ArrayList<>();
		try {
			retreivalVOs = oemService.getAllReterivalByReceiverId(receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Reterival Manifest information get successfully");
			responseObjectsMap.put("retreivalVO", retreivalVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Reterival Manifest information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllTranportPickupByReceiverId")
	public ResponseEntity<ResponseDTO> getAllTranportPickupByReceiverId(
			@RequestParam(required = false) Long receiverId) {
		String methodName = "getAllReterivalByReceiverId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TransportPickupVO> transportPickupVOs = new ArrayList<>();
		try {
			transportPickupVOs = oemService.getAllTranportPickupByReceiverId(receiverId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Transport Pickup information get successfully");
			responseObjectsMap.put("transportPickupVO", transportPickupVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Transport Pickup information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getReterivalManifestHeaderPdf")
	public ResponseEntity<ResponseDTO> getReterivalManifestHeaderPdf(@RequestParam(required = false) String docId,
			@RequestParam(required = false) Long orgId) {
		String methodName = "getReterivalManifestHeaderPdf()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> reterivalManifestDetails = new ArrayList<>();
		try {
			reterivalManifestDetails = oemService.getReterivalManifestHeaderPdf(docId, orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Reterival Manifest Header information get successfully");
			responseObjectsMap.put("reterivalManifestDetailsPdf", reterivalManifestDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Reterival Manifest Hesader information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PutMapping("/createUpdateIssuemanifest")
	public ResponseEntity<ResponseDTO> createUpdateIssuemanifest(@RequestBody IssueManifestProviderDTO issueManifestProviderDTO) {
	    String methodName = "createUpdateIssuemanifest()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> issueManifestProviderVO = oemService.createUpdateIssuemanifest(issueManifestProviderDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, issueManifestProviderVO.get("message"));
	        responseObjectsMap.put("issueManifestProviderVO", issueManifestProviderVO.get("issueManifestProviderVO"));
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	    	String errorMsg =  e.getMessage(); 
	        LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}


	@GetMapping("/getAllIssueManifestProvider")
	public ResponseEntity<ResponseDTO> getAllIssueManifestProvider() {
		String methodName = "getAllIssueManifestProvider()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueManifestProviderVO> IssueManifestProviderVO =new ArrayList<IssueManifestProviderVO>();
		try {
			IssueManifestProviderVO = oemService.getAllIssueManifestProvider();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IssueManifestProvider information get successfully");
			responseObjectsMap.put("IssueManifestProviderVO", IssueManifestProviderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "IssueManifestProvider information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}


	@GetMapping("/getAllIssueManifestProviderById")
	public ResponseEntity<ResponseDTO> getAllIssueManifestProviderById(Long id) {
		String methodName = "getAllIssueManifestProvider()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IssueManifestProviderVO IssueManifestProviderVO =null;
		try {
			IssueManifestProviderVO = oemService.getAllIssueManifestProviderById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IssueManifestProvider information get successfully");
			responseObjectsMap.put("IssueManifestProviderVO", IssueManifestProviderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "IssueManifestProvider information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	
	//RETRIEVALMANIFEST

	@PutMapping("/createUpdateRetrievalManifest")
	public ResponseEntity<ResponseDTO> createUpdateRetrievalManifest(@RequestBody RetrievalManifestProviderDTO retrievalManifestProviderDTO) {
	    String methodName = "createUpdateRetrievalManifest()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> retrievalManifestProviderVO = oemService.createUpdateRetrievalManifest(retrievalManifestProviderDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, retrievalManifestProviderVO.get("message"));
	        responseObjectsMap.put("issueManifestProviderVO", retrievalManifestProviderVO.get("retrievalManifestProviderVO"));
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	    	String errorMsg =  e.getMessage();
	        LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllRetrievalManifestProvider")
	public ResponseEntity<ResponseDTO> getAllRetrievalManifestProvider() {
		String methodName = "getAllRetrievalManifestProvider()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RetrievalManifestProviderVO> retrievalManifestProviderVOs =new ArrayList<RetrievalManifestProviderVO>();
		try {
			retrievalManifestProviderVOs = oemService.getAllRetrievalManifestProvider();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RetrievalManifest information get successfully");
			responseObjectsMap.put("retrievalManifestProviderVOs", retrievalManifestProviderVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RetrievalManifest information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}


	@GetMapping("/getRetrievalManifestProviderById")
	public ResponseEntity<ResponseDTO> getRetrievalManifestProviderById(Long id) {
		String methodName = "getRetrievalManifestProviderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RetrievalManifestProviderVO retrievalManifestProviderVO =null;
		try {
			retrievalManifestProviderVO = oemService.getRetrievalManifestProviderById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RetrievalManifest information get successfully By Id");
			responseObjectsMap.put("retrievalManifestProviderVO", retrievalManifestProviderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RetrievalManifest information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	//DECLARATION AND NOTES
	
	@PostMapping("/createDeclarationAndNotes")
	public ResponseEntity<ResponseDTO> createDeclarationAndNotes(@RequestBody DeclarationAndNotesVO declarationAndNotesVO) {
		String methodName = "createDeclarationAndNotes()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DeclarationAndNotesVO declarationAndNotesVO1 = new DeclarationAndNotesVO();
		try {
			declarationAndNotesVO1 = oemService.createDeclarationAndNotes(declarationAndNotesVO);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeclarationAndNotes Created Successfully");
			responseObjectsMap.put("declarationAndNotesVO1", declarationAndNotesVO1);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Oem Bin Inward Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDeclarationAndNotes")
	public ResponseEntity<ResponseDTO> getAllDeclarationAndNotes() {
		String methodName = "getAllDeclarationAndNotesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DeclarationAndNotesVO> declarationAndNotesVO =new ArrayList<DeclarationAndNotesVO>();
		try {
			declarationAndNotesVO = oemService.getAllDeclarationAndNotes();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeclarationAndNotes information get successfully By Id");
			responseObjectsMap.put("declarationAndNotesVO", declarationAndNotesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DeclarationAndNotes information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
}

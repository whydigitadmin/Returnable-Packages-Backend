package com.whydigit.efit.controller;

import java.time.LocalDate;
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
import org.springframework.format.annotation.DateTimeFormat;
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
import com.whydigit.efit.common.EmitterConstant;
import com.whydigit.efit.common.UserConstants;
import com.whydigit.efit.dto.AssetInwardDTO;
import com.whydigit.efit.dto.AssetTaggingDTO;
import com.whydigit.efit.dto.BinInwardDTO;
import com.whydigit.efit.dto.CnoteDTO;
import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.CustomersDTO;
import com.whydigit.efit.dto.DmapDTO;
import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.dto.KitDTO;
import com.whydigit.efit.dto.KitResponseDTO;
import com.whydigit.efit.dto.PoDTO;
import com.whydigit.efit.dto.PodDTO;
import com.whydigit.efit.dto.ProofOfDeliveryDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.dto.ServiceDTO;
import com.whydigit.efit.dto.StockBranchDTO;
import com.whydigit.efit.dto.TermsAndConditionsDTO;
import com.whydigit.efit.dto.VendorDTO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetInwardVO;
import com.whydigit.efit.entity.AssetTaggingVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinInwardVO;
import com.whydigit.efit.entity.CnoteVO;
import com.whydigit.efit.entity.CustomersAddressVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.DmapVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.entity.KitVO;
import com.whydigit.efit.entity.ManufacturerProductVO;
import com.whydigit.efit.entity.ManufacturerVO;
import com.whydigit.efit.entity.PoVO;
import com.whydigit.efit.entity.PodVO;
import com.whydigit.efit.entity.ProofOfDeliveryVO;
import com.whydigit.efit.entity.ServiceVO;
import com.whydigit.efit.entity.StockBranchVO;
import com.whydigit.efit.entity.TermsAndConditionsVO;
import com.whydigit.efit.entity.UnitVO;
import com.whydigit.efit.entity.VendorAddressVO;
import com.whydigit.efit.entity.VendorBankDetailsVO;
import com.whydigit.efit.entity.VendorVO;
import com.whydigit.efit.service.MasterService;

@CrossOrigin
@RestController
@RequestMapping("api/master")

public class MasterController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	MasterService masterService;

	@GetMapping("/asset")
	public ResponseEntity<ResponseDTO> getAllAsset(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetVO> assetVO = new ArrayList<>();
		try {
			assetVO = masterService.getAllAsset(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset information get successfully");
			responseObjectsMap.put("assetVO", assetVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAssetByOrgId")
	public ResponseEntity<ResponseDTO> getAssetByOrgId(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String assetId) {
		String methodName = "getAssetByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AssetVO assetVO = null;
		try {
			assetVO = masterService.getAssetByOrgId(orgId, assetId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset information get successfully");
			responseObjectsMap.put("assetVO", assetVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/asset")
	public ResponseEntity<ResponseDTO> createAsset(@RequestBody AssetVO assetVO) {
		String methodName = "createAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetVO createdAssetVO = masterService.createAsset(assetVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset created successfully");
			responseObjectsMap.put("assetVO", createdAssetVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/asset")
	public ResponseEntity<ResponseDTO> updateAsset(@RequestBody AssetVO assetVO) {
		String methodName = "updateAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetVO updateAssetVO = masterService.updateAsset(assetVO).orElse(null);
			if (updateAssetVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset updated successfully");
				responseObjectsMap.put("assetVO", updateAssetVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Asset not found for ID: " + assetVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Asset update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/asset/{id}")
	public ResponseEntity<ResponseDTO> deleteAsset(@PathVariable Long id) {
		String methodName = "deleteAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteAsset(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//asset group

	@GetMapping("/assetGroup")
	public ResponseEntity<ResponseDTO> getAllAssetGroup(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String assetCategory, @RequestParam(required = false) String assetName,
			@RequestParam(required = false) String assetCodeId, @RequestParam(required = false) String manufacturer) {
		String methodName = "getAllAssetGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Object> assetGroupVO = new HashMap<>();
		try {
			assetGroupVO = masterService.getAllAssetGroup(orgId, assetCategory, assetName, assetCodeId, manufacturer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup information get successfully");
			responseObjectsMap.put("assetGroupVO", assetGroupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAssetGroupByCategoryType")
	public ResponseEntity<ResponseDTO> getAssetGroupByCategoryType(@RequestParam(required = false) Long orgId) {
		String methodName = "getAssetGroupByCategoryType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, Map<String, List<AssetGroupVO>>> assetGroupVO = new HashMap<>();
		try {
			assetGroupVO = masterService.getAssetGroupByCategoryType(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup information get successfully");
			responseObjectsMap.put("assetGroupVO", assetGroupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAssetGroupByAssetCode")
	public ResponseEntity<ResponseDTO> getAssetGroupByAssetCode(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String assetCodeId) {
		String methodName = "getAssetGroupByAssetCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AssetGroupVO assetGroupVO = null;
		try {
			assetGroupVO = masterService.getAssetGroupByAssetCode(orgId, assetCodeId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup information get successfully");
			responseObjectsMap.put("assetGroupVO", assetGroupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/assetGroup/{id}")
	public ResponseEntity<ResponseDTO> getAssetGroupById(@PathVariable String id) {
		String methodName = "getAssetGroupById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AssetGroupVO assetGroupVO = null;
		try {
			assetGroupVO = masterService.getAssetGroupById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup found by ID");
			responseObjectsMap.put("assetGroupVO", assetGroupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetGroup not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/assetGroup")
	public ResponseEntity<ResponseDTO> createAssetGroup(@Valid @RequestBody AssetGroupVO assetGroupVO) {
		String methodName = "createAssetGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetGroupVO createdAssetGroupVO = masterService.createAssetGroup(assetGroupVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup created successfully");
			responseObjectsMap.put("assetGroupVO", createdAssetGroupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/assetGroup")
	public ResponseEntity<ResponseDTO> updateAssetGroup(@RequestBody AssetGroupVO assetGroupVO) {
		String methodName = "updateAssetGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetGroupVO updatedAssetGroupVO = masterService.updateAssetGroup(assetGroupVO).orElse(null);
			if (updatedAssetGroupVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup updated successfully");
				responseObjectsMap.put("assetGroupVO", updatedAssetGroupVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "AssetGroup not found for AssetCode ID: " + assetGroupVO.getAssetCodeId();
				responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// customers

	@GetMapping("/customers")
	public ResponseEntity<ResponseDTO> getAllCustomers(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllCustomers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CustomersVO> customersVO = new ArrayList<>();
		try {
			customersVO = masterService.getAllCustomers(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers information get successfully");
			responseObjectsMap.put("customersVO", customersVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAddressByCustomerId")
	public ResponseEntity<ResponseDTO> getCustomerAddressByCustomerId(@RequestParam(required = false) Long customerId) {
		String methodName = "getCustomerAddressByCustomerId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CustomersAddressVO> customersAddressVO = new ArrayList<>();
		try {
			customersAddressVO = masterService.getCustomerAddressByCustomerId(customerId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " Customer address get successfully");
			responseObjectsMap.put("CustomersAddress", customersAddressVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Customer address get failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<ResponseDTO> getCustomersById(@PathVariable Long id) {
		String methodName = "getCustomersById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CustomersVO customersVO = null;
		try {
			customersVO = masterService.getCustomersById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers found by ID");
			responseObjectsMap.put("customersVO", customersVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Customers not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomersList")
	public ResponseEntity<ResponseDTO> getCustomersType(@RequestParam Long orgId) {
		String methodName = "getCustomersType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Map<String, List<CustomersVO>> customersVO = null;
		try {
			customersVO = masterService.CustomersType(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomersType Get Successfully");
			responseObjectsMap.put("customersVO", customersVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "CustomersType Get Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/customers")
	public ResponseEntity<ResponseDTO> createCustomer(@RequestBody CustomersDTO customersDTO) {
		String methodName = "createCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CustomersVO createdCustomersVO = masterService.createCustomers(customersDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers created successfully");
			responseObjectsMap.put("customersVO", createdCustomersVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/customersAttachmentDoc")
	public ResponseEntity<ResponseDTO> uploadCustomerAttachmentDoc(@RequestParam MultipartFile[] files,
			CustomerAttachmentType type, @RequestParam Long customerId) {
		String methodName = "uploadCustomerAttachmentDoc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.uploadCustomerAttachmentDoc(files, type, customerId);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers attachment upload successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Customers attachment upload failed. Please try again.", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/customers")
	public ResponseEntity<ResponseDTO> updateCustomers(@RequestBody CustomersDTO customersDTO) {
		String methodName = "updateCustomers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CustomersVO updatedCustomersVO = masterService.updateCustomers(customersDTO);
			if (updatedCustomersVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers updated successfully");
				responseObjectsMap.put("customersVO", updatedCustomersVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Customers not found for ID: " + customersDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Customers update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers" + " update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/CustomersBankDetails/{id}")
	public ResponseEntity<ResponseDTO> deleteCustomersBankDetails(@PathVariable Long id) {
		String methodName = "deleteCustomersBankDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteCustomersBankDetails(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers BankDetails deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers BankDetails deleted failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/CustomersAddress/{id}")
	public ResponseEntity<ResponseDTO> deleteCustomersAddress(@PathVariable Long id) {
		String methodName = "deleteCustomersAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteCustomersAddress(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers Address deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers Address deleted failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<ResponseDTO> deleteCustomers(@PathVariable Long id) {
		String methodName = "deleteAssetGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteCustomers(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// flow
	@GetMapping("/flow")
	public ResponseEntity<ResponseDTO> getAllflow(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) Long emitterId) {
		String methodName = "getAllflow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FlowVO> flowVO = new ArrayList<>();
		try {
			flowVO = masterService.getAllFlow(orgId, emitterId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flow information get successfully");
			responseObjectsMap.put("flowVO", flowVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getKitDetailsByEmitter")
	public ResponseEntity<ResponseDTO> getKitDetailsByEmitter(@RequestParam String emitter ,@RequestParam Long orgId) {
		String methodName = "getKitDetailsByEmitter()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> flowVO = new HashSet<>();
		try {
			flowVO = masterService.getKitDetailsByEmitter(emitter,orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> flow = findkitdetails(flowVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit Details found by emitter");
			responseObjectsMap.put("flow", flow);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findkitdetails(Set<Object[]> flowVO) {
		List<Map<String, String>> flowDetails = new ArrayList<>();
		for (Object[] f : flowVO) {
			Map<String, String> f1 = new HashMap<>();
			f1.put("emitter", f[0] != null ? f[0].toString() : "");
			f1.put("flow", f[1] != null ? f[1].toString() : "");
			f1.put("reciever", f[2] != null ? f[2].toString() : "");
			f1.put("recieverid", f[3] != null ? f[3].toString() : "");
			f1.put("partname", f[4] != null ? f[4].toString() : "");
			f1.put("kitcode", f[5] != null ? f[5].toString() : "");
			f1.put("partno", f[6] != null ? f[6].toString() : "");
			f1.put("partqty", f[7] != null ? f[7].toString() : "");
			flowDetails.add(f1);
		}
		return flowDetails;
	}

	@GetMapping("/getFlowDetailsByFlowId")
	public ResponseEntity<ResponseDTO> getFlowDetailsByFlowId(@RequestParam Long flowId) {
		String methodName = "getFlowDetailsByFlowId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> flowVO = new HashSet<>();
		try {
			flowVO = masterService.getFlowDetailsByFlowId(flowId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> flow = findflowdetails(flowVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flow Details found by FlowId");
			responseObjectsMap.put("flow", flow);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findflowdetails(Set<Object[]> flowVO) {
		List<Map<String, String>> flowDetails = new ArrayList<>();
		for (Object[] f : flowVO) {
			Map<String, String> f1 = new HashMap<>();
			f1.put("receiver", f[0] != null ? f[0].toString() : "");
			f1.put("destination", f[1] != null ? f[1].toString() : "");
			f1.put("orgin", f[2] != null ? f[2].toString() : "");

			flowDetails.add(f1);
		}
		return flowDetails;
	}

	@GetMapping("/getAllFlowName")
	public ResponseEntity<ResponseDTO> getFlowNameByOrgID(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) Long emitterId) {
		String methodName = "getFlowNameByOrgID()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> flowVO = new HashSet<>();
		try {
			flowVO = masterService.getFlowNameByOrgID(orgId, emitterId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>> location = getWarehouseLocation(flowVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flow name information get successfully");
			responseObjectsMap.put("Flows", location);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow name information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, Object>> getWarehouseLocation(Set<Object[]> flowVO) {
		List<Map<String, Object>> location = new ArrayList<>();
		for (Object[] w : flowVO) {
			Map<String, Object> warehouse = new HashMap<>();
			warehouse.put("flowid", w[0]);
			warehouse.put("emitter", w[1].toString());
			warehouse.put("flow", w[2].toString());
			location.add(warehouse);
		}
		return location;
	}

	@GetMapping("/flow/{id}")
	public ResponseEntity<ResponseDTO> getFlowById(@PathVariable long id) {
		String methodName = "getFlowById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		FlowVO flowVO = null;
		try {
			flowVO = masterService.getFlowById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "flow found by ID");
			responseObjectsMap.put("flowVO", flowVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Flow not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/flow")
	public ResponseEntity<ResponseDTO> createFlow(@RequestBody FlowDTO flowDTO) {
		String methodName = "createFlow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			FlowVO createdFlowVO = masterService.createFlow(flowDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flow created successfully");
			responseObjectsMap.put("flowVO", createdFlowVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/flow")
	public ResponseEntity<ResponseDTO> updateFlow(@RequestBody FlowVO flowVO) {
		String methodName = "updateFlow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			FlowVO updatedFlowVO = masterService.updateFlow(flowVO).orElse(null);
			if (updatedFlowVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flow updated successfully");
				responseObjectsMap.put("flowVO", updatedFlowVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Flow not found for ID: " + flowVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Flow update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "FlowVO" + " update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/flow/{id}")
	public ResponseEntity<ResponseDTO> deleteFlow(@PathVariable long id) {
		String methodName = "deleteFlow()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteFlow(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flow deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/flow/getFlowByUserId")
	public ResponseEntity<ResponseDTO> getFlowByUserId(@RequestParam long userId) {
		String methodName = "getFlowByUserId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FlowVO> flowVO = new ArrayList<>();
		try {
			flowVO = masterService.getFlowByUserId(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "flow found successfully.");
			responseObjectsMap.put("flowVO", flowVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Flow not found for the user.";
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Vendor

	@GetMapping("/Vendor")
	public ResponseEntity<ResponseDTO> getAllVendor() {
		String methodName = "getAllVendor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VendorVO> vendorVO = new ArrayList<>();
		try {
			vendorVO = masterService.getAllVendor();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor information get successfully");
			responseObjectsMap.put("vendorVO", vendorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/Vendor/{id}")
	public ResponseEntity<ResponseDTO> getVendorById(@PathVariable Long id) {
		String methodName = "getVendorById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		VendorVO vendorVO = null;
		try {
			vendorVO = masterService.getVendorById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor found by ID");
			responseObjectsMap.put("vendorVO", vendorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Manufacturer not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/Vendor")
	public ResponseEntity<ResponseDTO> updateCreateVendor(@Valid @RequestBody VendorDTO vendorDTO) {
		String methodName = "updateCreateVendor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			VendorVO updatedVendorVO = masterService.updateCreateVendor(vendorDTO);
			if (updatedVendorVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor updated successfully");
				responseObjectsMap.put("updatedVendorVO", updatedVendorVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Vendor not found for ID: " + vendorDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Vendor update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getVendorByOrgId")
	public ResponseEntity<ResponseDTO> getVendorByOrgId(@RequestParam Long orgId) {
		String methodName = "getVendorByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VendorVO> vendorVO = new ArrayList<>();
		try {
			vendorVO = masterService.getVendorByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor found by IDS");
			responseObjectsMap.put("vendorVO", vendorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Flow not found for ID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/vendor/{id}")
	public ResponseEntity<ResponseDTO> deletevendor(@PathVariable Long id) {
		String methodName = "deletevendor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deletevendor(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// VendorAddress

//	@PutMapping("/vendorAddress")
//	public ResponseEntity<ResponseDTO> updateCreateVendorAddress(@RequestBody VendorAddressDTO vendorAddressDTO) {
//		String methodName = "updateCreateVendorAddress()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		try {
//			VendorAddressVO updatedVendorAddressVO = masterService.updateCreateVendorAddress(vendorAddressDTO);
//			if (updatedVendorAddressVO != null) {
//				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor updated successfully");
//				responseObjectsMap.put("VendorAddressVO", updatedVendorAddressVO);
//				responseDTO = createServiceResponse(responseObjectsMap);
//			} else {
//				errorMsg = "Vendor not found for ID: " + vendorAddressDTO.getId();
//				responseDTO = createServiceResponseError(responseObjectsMap, "Vendor Address update failed", errorMsg);
//			}
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor Address update failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	@GetMapping("/vendorAddress/{id}")
	public ResponseEntity<ResponseDTO> getVendorAddressById(@PathVariable Long id) {
		String methodName = "getVendorAddressById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		VendorAddressVO vendorAddressVO = null;
		try {
			vendorAddressVO = masterService.getVendorAddressById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor address found by ID");
			responseObjectsMap.put("vendorAddressVO", vendorAddressVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Vendor address not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor address not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/vendorAddress/{id}")
	public ResponseEntity<ResponseDTO> deletevendorAddress(@PathVariable Long id) {
		String methodName = "deletevendorAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deletevendorAddress(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "vendorAddress deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "vendorAddress deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/vendorBankDetails/{id}")
	public ResponseEntity<ResponseDTO> getVendorBankDetailsById(@PathVariable Long id) {
		String methodName = "getVendorBankDetailsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		VendorBankDetailsVO vendorBankDetailsVO = null;
		try {
			vendorBankDetailsVO = masterService.getVendorBankDetailsById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor bank details found by ID");
			responseObjectsMap.put("vendorBankDetailsVO", vendorBankDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Vendor bank details not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor bank details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/vendorBankDetails/{id}")
	public ResponseEntity<ResponseDTO> deletevendorBankDetails(@PathVariable Long id) {
		String methodName = "deletevendorBankDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deletevendorBankDetails(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor BankDetails deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Vendor BankDetails deletion failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Manufacturer

	@GetMapping("/manufacturer")
	public ResponseEntity<ResponseDTO> getAllManufacturer(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllManufacturer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ManufacturerVO> manufacturerVO = new ArrayList<>();
		try {
			manufacturerVO = masterService.getAllManufacturer(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Manufacturer information get successfully");
			responseObjectsMap.put("manufacturerVO", manufacturerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Manufacturer information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/manufacturerVO/{id}")
	public ResponseEntity<ResponseDTO> getManufacturerById(@PathVariable int id) {
		String methodName = "getManufacturerById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ManufacturerVO manufacturerVO = null;
		try {
			manufacturerVO = masterService.getManufacturerById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Manufacturer found by ID");
			responseObjectsMap.put("manufacturerVO", manufacturerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Manufacturer not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Manufacturer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/manufacturer")
	public ResponseEntity<ResponseDTO> createManufacturer(@RequestBody ManufacturerVO manufacturerVO) {
		String methodName = "createManufacturer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ManufacturerVO createdManufacturerVO = masterService.createManufacturer(manufacturerVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Manufacturer created successfully");
			responseObjectsMap.put("manufacturerVO", createdManufacturerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Manufacturer creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/manufacturer")
	public ResponseEntity<ResponseDTO> updateManufacturer(@RequestBody ManufacturerVO manufacturerVO) {
		String methodName = "updateManufacturer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ManufacturerVO updatedManufacturerVO = masterService.updateManufacturer(manufacturerVO).orElse(null);
			if (updatedManufacturerVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Manufacturer updated successfully");
				responseObjectsMap.put("manufacturerVO", updatedManufacturerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Flow not found for ID: " + manufacturerVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Manufacturer update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Manufacturer" + " update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/manufacturer/{id}")
	public ResponseEntity<ResponseDTO> deleteManufacturer(@PathVariable int id) {
		String methodName = "deleteManufacturer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteManufacturer(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Manufacturer deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Manufacturer deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/manufacturerProduct")
	public ResponseEntity<ResponseDTO> createManufactureProduct(
			@RequestBody ManufacturerProductVO manufacturerProductVO) {
		String methodName = "createManufactureProduct()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ManufacturerProductVO createdManufacturerProductVO = masterService
					.createManufacturerProduct(manufacturerProductVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ManufacturerProduct created successfully");
			responseObjectsMap.put("manufacturerProductVO", createdManufacturerProductVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "ManufacturerProduct creation failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/manufacturerProduct")
	public ResponseEntity<ResponseDTO> getAllManufacturerProduct(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllManufacturerProduct()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ManufacturerProductVO> manufacturerProductVO = new ArrayList<>();
		try {
			manufacturerProductVO = masterService.getAllManufacturerProduct(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ManufacturerProduct information get successfully");
			responseObjectsMap.put("manufacturerProductVO", manufacturerProductVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ManufacturerProduct information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// add asset Category

	@PostMapping("/addAssetCategory")
	public ResponseEntity<ResponseDTO> createAssetCategory(@RequestBody AssetCategoryVO assetCategoryVO) {
		String methodName = "createAssetCategory()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetCategoryVO createdAssetCategoryVO = masterService.createAssetCategory(assetCategoryVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetCategory created successfully");
			responseObjectsMap.put("assetCategoryVO", createdAssetCategoryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetCategory creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllAssetCategory")
	public ResponseEntity<ResponseDTO> getAllAssetCategory(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String assetCategoryName) {
		String methodName = "getAllAssetCategory()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetCategoryVO> assetCategoryVO = new ArrayList<>();
		try {
			assetCategoryVO = masterService.getAllAssetCategory(orgId, assetCategoryName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetCategory information get successfully");
			responseObjectsMap.put("assetCategoryVO", assetCategoryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetCategory information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/unit")
	public ResponseEntity<ResponseDTO> getAllUnit(@RequestParam Long orgId) {
		String methodName = "getAllUnit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UnitVO> unitVO = new ArrayList<>();
		try {
			unitVO = masterService.getAllUnit(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit information get successfully");
			responseObjectsMap.put("unitVO", unitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Unit information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/unit/{id}")
	public ResponseEntity<ResponseDTO> getUnitById(@PathVariable Long id) {
		String methodName = "getUnitById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UnitVO unitVO = null;
		try {
			unitVO = masterService.getUnitById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "UnitVO found by ID");
			responseObjectsMap.put("unitVO", unitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Unit not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Unit not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/unit")
	public ResponseEntity<ResponseDTO> createUnit(@RequestBody UnitVO unitVO) {
		String methodName = "createUnit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			UnitVO createdUnitVO = masterService.createUnit(unitVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit created successfully");
			responseObjectsMap.put("unitVO", createdUnitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unit creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/unit")
	public ResponseEntity<ResponseDTO> updateUnit(@RequestBody UnitVO unitVO) {
		String methodName = "updateUnit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			UnitVO updatedUnitVO = masterService.updateUnit(unitVO).orElse(null);
			if (updatedUnitVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit updated successfully");
				responseObjectsMap.put("unitVO", updatedUnitVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Unit not found for ID: " + unitVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Unit update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Unit" + " update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/unit/{id}")
	public ResponseEntity<ResponseDTO> deleteunit(@PathVariable Long id) {
		String methodName = "deleteUnit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteUnit(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/assetGroupByCSV")
	public ResponseEntity<ResponseDTO> createAssetGroupByCSV(@RequestParam("assetFile") MultipartFile assetFile) {
		String methodName = "createAssetGroupByCSV()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			List<AssetGroupVO> createdAssetGroupVO = masterService.createAssetGroupByCSV(assetFile);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup created successfully");
			responseObjectsMap.put("assetGroupVO", createdAssetGroupVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Create KIT

	@GetMapping("/getallkit")
	public ResponseEntity<ResponseDTO> getAllKit(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllKit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<KitResponseDTO> kitResponseDTO = new ArrayList<>();
		try {
			kitResponseDTO = masterService.getAllKit(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit information get successfully");
			responseObjectsMap.put("KitVO", kitResponseDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/kit/{id}")
	public ResponseEntity<ResponseDTO> getKitById(@PathVariable Long id) {
		String methodName = "getKitById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		KitVO kitVO = null;
		try {
			kitVO = masterService.getKitById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit found by ID");
			responseObjectsMap.put("KitVO", kitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "CreateKit not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/kitDetails")
	public ResponseEntity<ResponseDTO> getKitByKitCode(@RequestParam String kitName) {
		String methodName = "getKitByKitCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		KitVO kitVO = null;
		try {
			kitVO = masterService.getKitByKitCode(kitName).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit found by ID");
			responseObjectsMap.put("KitVO", kitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "CreateKit not found for KitCode: " + kitName;
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createkit")
	public ResponseEntity<ResponseDTO> createkit(@RequestBody KitDTO kitDTO) {
		String methodName = "createkit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			KitVO createdKit = masterService.createkit(kitDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit created successfully");
			responseObjectsMap.put("KitVO", createdKit);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateKit")
	public ResponseEntity<ResponseDTO> updateKit(@RequestBody KitVO kitVO) {
		String methodName = "updateKit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			KitVO updatedKit = masterService.updatedKit(kitVO).orElse(null);
			if (updatedKit != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit updated successfully");
				responseObjectsMap.put("KitVO", updatedKit);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "LocalCurrency not found for ID: " + kitVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Kit update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/delteKit/{id}")
	public ResponseEntity<ResponseDTO> deleteKit(@PathVariable Long id) {
		String methodName = "deleteKit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteKit(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kit deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Kit deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/loadKitQty")
	public ResponseEntity<ResponseDTO> loadKitQty(@RequestParam(required = false) Long irItemId,
			@RequestParam(required = false) Long kitQty) {
		String methodName = "loadKitQty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int kitResponseDTO = 0;
		try {
			kitResponseDTO = masterService.loadKitQty(irItemId, kitQty);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "KitQty information Update successfully");
			responseObjectsMap.put("irItemId", irItemId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "KitQty information Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// DMAP

	@PostMapping("/dmap")
	public ResponseEntity<ResponseDTO> createDmap(@RequestBody DmapDTO dmapDTO) {
		String methodName = "createDmap()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			DmapVO creatDmapVO = masterService.createDmapVO(dmapDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Dmap created successfully");
			responseObjectsMap.put("dMapVO", creatDmapVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Dmap creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Service

	@GetMapping("/Services")
	public ResponseEntity<ResponseDTO> getAllServices(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllServices()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ServiceVO> serviceVO = new ArrayList<>();
		try {
			serviceVO = masterService.getAllServiceByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Service information get successfully");
			responseObjectsMap.put("Services", serviceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Service information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateService")
	public ResponseEntity<ResponseDTO> updateCreateService(@RequestBody ServiceDTO serviceDTO) {
		String methodName = "updateCreateService()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ServiceVO updatedServiceVO = masterService.updateCreateService(serviceDTO);
			if (updatedServiceVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Service updated successfully");
				responseObjectsMap.put("serviceVO", updatedServiceVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Service not found for ID: " + serviceDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Service update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Service update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Cnote

	@PutMapping("/updateCreateCnote")
	public ResponseEntity<ResponseDTO> updateCreateCnote(@RequestBody CnoteDTO cnoteDTO) {
		String methodName = "updateCreateCnote()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CnoteVO updatedCnoteVO = masterService.updateCreateCnote(cnoteDTO);
			if (updatedCnoteVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Cnote updated successfully");
				responseObjectsMap.put("cnoteVO", updatedCnoteVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Cnote not found for ID: " + cnoteDTO.getCnoteId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Cnote update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Cnote update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// AssetInward
	@PostMapping("/assetInward")
	public ResponseEntity<ResponseDTO> createAssetInward(@RequestBody AssetInwardDTO assetInwardDTO) {
		String methodName = "createAssetInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetInwardVO createdAssetInwardVO = masterService.createAssetInward(assetInwardDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetInward created successfully");
			responseObjectsMap.put("assetInwardVO", createdAssetInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetInward creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllAssetInwardOrgId")
	public ResponseEntity<ResponseDTO> getAllAssetInwardOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllAssetInwardOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetInwardVO> assetInwardVO = new ArrayList<>();
		try {
			assetInwardVO = masterService.getAllAssetInwardOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetInward information get successfully");
			responseObjectsMap.put("assetInwardVO", assetInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetInward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAssetInwardDocId")
	public ResponseEntity<ResponseDTO> getAssetInwardDocId(@RequestParam(required = false) String docId) {
		String methodName = "getAssetInwardDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AssetInwardVO assetInwardVO = null;
		try {
			assetInwardVO = masterService.getAssetInwardByDocId(docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetInward information get successfully");
			responseObjectsMap.put("assetInwardVO", assetInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetInward information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// Stock branch
	@PostMapping("/stockbranch")
	public ResponseEntity<ResponseDTO> createStockBranch(@RequestBody StockBranchDTO stockBranchDTO) {
		String methodName = "createStockBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StockBranchVO stockBranchVO = masterService.createStockBranch(stockBranchDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Branch Created successfully");
			responseObjectsMap.put("StockBranchVO", stockBranchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Stock Branch Creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Update Stock branch

	@PutMapping("/updateStockBranch")
	public ResponseEntity<ResponseDTO> updateStockBranch(@RequestBody StockBranchDTO stockBranchDTO) {
		String methodName = "updateStockBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StockBranchVO updateStockBranchVO = masterService.updateStockBranch(stockBranchDTO);
			if (updateStockBranchVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Branch updated successfully");
				responseObjectsMap.put("stockBranchVO", updateStockBranchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Stock Branch not found for ID: " + updateStockBranchVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Stock Branch update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Stock Branch update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/stockbranchByOrgId")
	public ResponseEntity<ResponseDTO> getStockBranchByOrgId(@RequestParam Long orgId) {
		String methodName = "getStockBranchByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StockBranchVO> branchVOs = new ArrayList<>();
		try {
			branchVOs = masterService.getAllStockBranchByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Branch information get successfully");
			responseObjectsMap.put("branch", branchVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Stock Branch information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Asset Tagging

	@PostMapping("/assettagging")
	public ResponseEntity<ResponseDTO> createAssetTagging(@RequestBody AssetTaggingDTO assetTaggingDTO) {
		String methodName = "createStockBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			AssetTaggingVO assetTaggingVO = masterService.createTagging(assetTaggingDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tagging Created successfully");
			responseObjectsMap.put("AssetTaggingVO", assetTaggingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Tagging Creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllAssetTaggingOrgId")
	public ResponseEntity<ResponseDTO> getAllAssetTaggingOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllAssetTaggingOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetTaggingVO> assetTaggingVO = new ArrayList<>();
		try {
			assetTaggingVO = masterService.getAllAsetTaggingByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetTagging information get successfully");
			responseObjectsMap.put("assetTaggingVO", assetTaggingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetTagging information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/Tagcode")
	public ResponseEntity<ResponseDTO> getTagCodeByAsset(@RequestParam String assetcode, @RequestParam String asset,
			@RequestParam int startno, @RequestParam int endno) {
		String methodName = "getTagCodeByAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> tagcode = new HashSet<>();
		try {
			tagcode = masterService.getTagCodeByAsset(assetcode, asset, startno, endno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> assetTagCode = TagCodes(tagcode);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tag Code get information successfully");
			responseObjectsMap.put("tagcode", assetTagCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Tag Code information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> TagCodes(Set<Object[]> tagcode) {
		List<Map<String, String>> assetTagCode = new ArrayList<>();
		for (Object[] tag : tagcode) {
			Map<String, String> assetcode = new HashMap<>();
			assetcode.put("AssetCode", tag[0].toString());
			assetcode.put("Asset", tag[1].toString());
			assetcode.put("TagCode", tag[2].toString());
			assetTagCode.add(assetcode);
		}
		return assetTagCode;
	}

	@GetMapping("/getDocIdByAssetTagging")
	public ResponseEntity<ResponseDTO> getDocIdByAssetTagging() {
		String methodName = "getDocIdByAssetTagging()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String assetTagDocId = null;
		try {
			assetTagDocId = masterService.getDocIdByAssetTagging();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Asset Tagging DocId found success");
			responseObjectsMap.put("assetTagDocId", assetTagDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Asset Tagging DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAvalkitqty")
	public ResponseEntity<ResponseDTO> getAvalkitqty(@RequestParam Long warehouseId, @RequestParam String Kitname) {
		String methodName = "getAvalkitqty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> avalKit = new HashSet<>();
		try {
			avalKit = masterService.getAvalKitQty(warehouseId, Kitname);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>> kit = getKitDetails(avalKit);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Available Kit Qty Information get successfully");
			responseObjectsMap.put("Avalkit", kit);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Available Kit Qty Information information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, Object>> getKitDetails(Set<Object[]> avalKit) {
		List<Map<String, Object>> kit = new ArrayList<>();
		for (Object[] w : avalKit) {
			Map<String, Object> kitd = new HashMap<>();
			kitd.put("stockBranch", w[0]);
			kitd.put("kitCode", w[1].toString());
			kitd.put("avlQty", w[2].toString());
			kit.add(kitd);
		}
		return kit;
	}

	@GetMapping("/getAvalkitqtyByBranch")
	public ResponseEntity<ResponseDTO> getAvalkitqtyByBranch(@RequestParam String branch,
			@RequestParam String Kitname) {
		String methodName = "getAvalkitqtyByBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> avalKit = new HashSet<>();
		try {
			avalKit = masterService.getAvalKitQtyByBranch(branch, Kitname);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, Object>> kit = getKitDetailsByBranch(avalKit);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Available Kit Qty Information get successfully");
			responseObjectsMap.put("Avalkit", kit);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Available Kit Qty Information information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, Object>> getKitDetailsByBranch(Set<Object[]> avalKit) {
		List<Map<String, Object>> kit = new ArrayList<>();
		for (Object[] w : avalKit) {
			Map<String, Object> kitd = new HashMap<>();
			kitd.put("stockBranch", w[0]);
			kitd.put("kitCode", w[1].toString());
			kitd.put("avlQty", w[2].toString());
			kit.add(kitd);
		}
		return kit;
	}

	// TermsAndConditions

	@PutMapping("/updateCreateTerms")
	public ResponseEntity<ResponseDTO> updateCreateTerms(@RequestBody TermsAndConditionsDTO termsAndConditionsDTO) {
		String methodName = "updateCreateTerms()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			TermsAndConditionsVO updatedTermsAndConditionsVO = masterService.updateCreateTerms(termsAndConditionsDTO);
			if (updatedTermsAndConditionsVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TermsAndConditionsVO updated successfully");
				responseObjectsMap.put("updatedTermsAndConditions", updatedTermsAndConditionsVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "TermsAndConditionsVO update failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/terms")
	public ResponseEntity<ResponseDTO> getAllTermsByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllTermsByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TermsAndConditionsVO> termsAndConditionsVO = new ArrayList<>();
		try {
			termsAndConditionsVO = masterService.getAllTermsByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "terms information get successfully");
			responseObjectsMap.put("termsAndConditionsVO", termsAndConditionsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "terms information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllTermsById")
	public ResponseEntity<ResponseDTO> getAllTermsById(@RequestParam(required = false) Long termsId) {
		String methodName = "getAllTermsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TermsAndConditionsVO> termsAndConditionsVO = new ArrayList<>();
		try {
			termsAndConditionsVO = masterService.getAllTermsById(termsId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "terms information get successfully");
			responseObjectsMap.put("termsAndConditionsVO", termsAndConditionsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "terms information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// PO
	@PutMapping("/updateCreatePo")
	public ResponseEntity<ResponseDTO> updateCreatePo(@RequestBody PoDTO poDTO) {
		String methodName = "updateCreatePo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PoVO updatedPoVO = masterService.updateCreatePo(poDTO);
			if (updatedPoVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Po updated successfully");
				responseObjectsMap.put("updatedPoVO", updatedPoVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Po update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPoByOrgId")
	public ResponseEntity<ResponseDTO> getPoByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getPoByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PoVO> poVO = new ArrayList<>();
		try {
			poVO = masterService.getPoByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PO information get successfully");
			responseObjectsMap.put("poVO", poVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PO information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllPoByPoId")
	public ResponseEntity<ResponseDTO> getAllPoByPoId(@RequestParam(required = false) Long poId) {
		String methodName = "getAllPoByPoId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PoVO> poVO = new ArrayList<>();
		try {
			poVO = masterService.getAllPoByPoId(poId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Po information get successfully");
			responseObjectsMap.put("poVO", poVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Po information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// Pod

	@PutMapping("/updateCreatePod")
	public ResponseEntity<ResponseDTO> updateCreatePod(@RequestBody PodDTO podDTO) {
		String methodName = "updateCreatePod()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PodVO updatedPodVO = masterService.updateCreatePod(podDTO);
			if (updatedPodVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pod updated successfully");
				responseObjectsMap.put("updatedPodVO", updatedPodVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Pod update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllPodByOrgId")
	public ResponseEntity<ResponseDTO> getAllPodByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllPodByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PodVO> podVO = new ArrayList<>();
		try {
			podVO = masterService.getAllPodByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "POd information get successfully");
			responseObjectsMap.put("PodVO", podVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "POd information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllPodByPodId")
	public ResponseEntity<ResponseDTO> getAllPodByPodId(@RequestParam(required = false) Long podId) {
		String methodName = "getAllPodByPodId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PodVO> podVO = new ArrayList<>();
		try {
			podVO = masterService.getAllPodByPodId(podId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pod information get successfully");
			responseObjectsMap.put("podVO", podVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Pod information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPoNoByCreateAsset")
	public ResponseEntity<ResponseDTO> getPoNoByCreateAsset(@RequestParam(required = false) Long orgId) {
		String methodName = "getPoNoByCreateAsset()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> pono = new HashSet<>();
		try {
			pono = masterService.getPoNoByCreateAsset(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> po = getPoNo(pono);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PO information get successfully");

			responseObjectsMap.put("pono", po);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PO information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	private List<Map<String, String>> getPoNo(Set<Object[]> pono) {
		List<Map<String, String>> po = new ArrayList<>();
		for (Object[] po1 : pono) {
			Map<String, String> po2 = new HashMap<>();
			po2.put("pono", po1[0].toString());
			po2.put("podate", po1[1].toString());
			po.add(po2);
		}
		return po;
	}

	@PostMapping("/uploadFileProofOfDelivery")
	public ResponseEntity<ResponseDTO> uploadFileProofOfDelivery(@RequestParam("file") MultipartFile file,
			@RequestParam String docId, @RequestParam String refNo) {
		String methodName = "uploadFileProofOfDelivery()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = masterService.uploadFileProofOfDelivery(file, docId, refNo);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Proof Of Delivery created successfully");
			responseObjectsMap.put("proofOfDeliveryVO", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Proof Of Delivery creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/createProofOfDelivery")
	public ResponseEntity<ResponseDTO> createProofOfDelivery(@RequestBody ProofOfDeliveryDTO proofOfDeliveryDTO) {
		String methodName = "createProofOfDelivery()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ProofOfDeliveryVO proofOfDeliveryVO = masterService.createProofOfDelivery(proofOfDeliveryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProofOfDelivery created successfully");
			responseObjectsMap.put("proofOfDeliveryVO", proofOfDeliveryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "ProofOfDelivery creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllProofOfDelivery")
	public ResponseEntity<ResponseDTO> getAllProofOfDelivery(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllProofOfDelivery()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProofOfDeliveryVO> proofOfDeliveryVO = new ArrayList<>();
		try {
			proofOfDeliveryVO = masterService.getAllProofOfDelivery(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProofOfDelivery information get successfully");
			responseObjectsMap.put("proofOfDeliveryVO", proofOfDeliveryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ProofOfDelivery information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// BININWARD

	@GetMapping("/getAllotmentNo")
	public ResponseEntity<ResponseDTO> getAllotmentByOrgIdAndEmitterId(@RequestParam Long orgid,
			@RequestParam Long emitterId) {
		String methodName = "getAllotmentByOrgIdAndEmitterId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> partstudy = new HashSet<>();
		try {
			partstudy = masterService.getAllotmentNoByEmitterIdAndOrgId(orgid, emitterId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allotno = findallotno(partstudy);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment No found by ID");
			responseObjectsMap.put("allotno", allotno);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment No not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findallotno(Set<Object[]> partstudy) {
		List<Map<String, String>> allotno = new ArrayList<>();
		for (Object[] ps : partstudy) {
			Map<String, String> part = new HashMap<>();
			part.put("allotNo", ps[0] != null ? ps[0].toString() : "");
			allotno.add(part);
		}
		return allotno;
	}

	@GetMapping("/getAllotmentDetailsByOrgIdAndDocid")
	public ResponseEntity<ResponseDTO> getAllotmentDetailsByOrgIdAndDocid(@RequestParam Long orgid,
			@RequestParam String docid) {
		String methodName = "getAllotmentDetailsByOrgIdAndDocid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> partstudy = new HashSet<>();
		try {
			partstudy = masterService.getAllotmentDetailsByAllotmentNoAndOrgId(orgid, docid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allotDetails = findAllotdetails(partstudy);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment Details found by ID");
			responseObjectsMap.put("allotDetails", allotDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findAllotdetails(Set<Object[]> partstudy) {
		List<Map<String, String>> allotDetails = new ArrayList<>();
		for (Object[] ps : partstudy) {
			Map<String, String> part = new HashMap<>();
			part.put("allotDate", ps[0] != null ? ps[0].toString() : "");
			part.put("reqNo", ps[1] != null ? ps[1].toString() : "");
			part.put("reqDate", ps[2] != null ? ps[2].toString() : "");
			part.put("flow", ps[3] != null ? ps[3].toString() : "");
			part.put("kitCode", ps[4] != null ? ps[4].toString() : "");
			part.put("allotKitQty", ps[5] != null ? ps[5].toString() : "");
			part.put("reqKitQty", ps[6] != null ? ps[6].toString() : "");
			allotDetails.add(part);
		}
		return allotDetails;
	}

	@PutMapping("/updateCreateBinInward")
	public ResponseEntity<ResponseDTO> updateCreateBinInward(@RequestBody BinInwardDTO binInwardDTO) {
		String methodName = "updateCreateBinInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BinInwardVO updatedBinInwardVO = masterService.updateCreateBinInward(binInwardDTO);
			if (updatedBinInwardVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BinInward updated successfully");
				responseObjectsMap.put("BinInwardVO", updatedBinInwardVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "BinInward update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllotmentAssetDetailsByOrgIdAndDocid")
	public ResponseEntity<ResponseDTO> getAllotmentAssetDetailsByOrgIdAndDocid(@RequestParam Long orgid,
			@RequestParam String docid) {
		String methodName = "getAllotmentAssetDetailsByOrgIdAndDocid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> partstudy = new HashSet<>();
		try {
			partstudy = masterService.getAllotmentAssetDetailsByAllotmentNoAndOrgId(orgid, docid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allotAssetDetails = findAllotAssetdetails(partstudy);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment Asset Details found by ID");
			responseObjectsMap.put("allotAssetDetails", allotAssetDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment Asset Details not found",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findAllotAssetdetails(Set<Object[]> partstudy) {
		List<Map<String, String>> allotAssetDetails = new ArrayList<>();
		for (Object[] ps : partstudy) {
			Map<String, String> part = new HashMap<>();
			part.put("asset", ps[0] != null ? ps[0].toString() : "");
			part.put("assetCode", ps[1] != null ? ps[1].toString() : "");
			part.put("rfId", ps[2] != null ? ps[2].toString() : "");
			part.put("tagCode", ps[3] != null ? ps[3].toString() : "");
			part.put("skuQty", ps[4] != null ? ps[4].toString() : "");
			part.put("recQty", ps[4] != null ? ps[4].toString() : "");
			allotAssetDetails.add(part);
		}
		return allotAssetDetails;
	}

	@GetMapping("/getAllBinInwardById")
	public ResponseEntity<ResponseDTO> getAllBinInwardById(@RequestParam Long emitterid, @RequestParam Long orgId) {
		String methodName = "getAllBinInwardById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> binInwardVO = new HashSet<>();
		try {
			binInwardVO = masterService.getAlllBinInwardByEmitterAndOrgId(emitterid, orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> binInwardVos = getAllBinInward(binInwardVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BinInward founded");
			responseObjectsMap.put("binInwardVO", binInwardVos);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "BinInward not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "BinInward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> getAllBinInward(Set<Object[]> binInwardVO) {
		List<Map<String, String>> binInwardVos = new ArrayList<>();
		for (Object[] bininward : binInwardVO) {
			Map<String, String> bininwards = new HashMap<>();
			bininwards.put("docid", bininward[0] != null ? bininward[0].toString() : "");
			bininwards.put("docDate", bininward[1] != null ? bininward[1].toString() : "");
			bininwards.put("allotmentNo", bininward[2] != null ? bininward[2].toString() : "");
			bininwards.put("allotDate", bininward[3] != null ? bininward[3].toString() : "");
			bininwards.put("flow", bininward[4] != null ? bininward[4].toString() : "");
			bininwards.put("kitCode", bininward[5] != null ? bininward[5].toString() : "");
			bininwards.put("reqKitQty", bininward[6] != null ? bininward[6].toString() : "");
			bininwards.put("allotedQty", bininward[6] != null ? bininward[6].toString() : "");
			binInwardVos.add(bininwards);
		}
		return binInwardVos;
	}

	@GetMapping("/getBinInwardById")
	public ResponseEntity<ResponseDTO> getBinInwardById(@RequestParam Long id) {
		String methodName = "getBinInwardById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BinInwardVO binInwardVO = null;
		try {
			binInwardVO = masterService.getBinInwardById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BinInward found by ID");
			responseObjectsMap.put("binInwardVO", binInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "BinInward not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "BinInward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdByBinInward")
	public ResponseEntity<ResponseDTO> getDocIdByBinInward() {
		String methodName = "getDocIdByBinInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String binDocId = null;
		try {
			binDocId = masterService.getDocIdByBinInward();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Inward DocId found success");
			responseObjectsMap.put("binDocId", binDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Inward DocId not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWaitingBinInwardDetailsByEmitterAndOrgId")
	public ResponseEntity<ResponseDTO> getWaitingBinInwardDetailsByEmitterAndOrgId(@RequestParam Long orgId,
			@RequestParam Long emitterid) {
		String methodName = "getWaitingBinInwardDetailsByEmitterAndOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> allot = new HashSet<>();
		try {
			allot = masterService.getWaitingInwardDetailsByEmitterIdandOrgId(orgId, emitterid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allotDetails = findwaitingAllodetails(allot);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment Details found by ID");
			responseObjectsMap.put("allotDetails", allotDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bin Allotment Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findwaitingAllodetails(Set<Object[]> allot) {
		List<Map<String, String>> allotDetails = new ArrayList<>();
		for (Object[] ps : allot) {
			Map<String, String> part = new HashMap<>();
			part.put("allotNo", ps[0] != null ? ps[0].toString() : "");
			part.put("allotDate", ps[1] != null ? ps[1].toString() : "");
			part.put("flow", ps[2] != null ? ps[2].toString() : "");
			part.put("kitCode", ps[3] != null ? ps[3].toString() : "");
			part.put("allotKitQty", ps[4] != null ? ps[4].toString() : "");
			part.put("reqKitQty", ps[5] != null ? ps[5].toString() : "");
			part.put("emitterId", ps[6] != null ? ps[6].toString() : "");
			allotDetails.add(part);
		}
		return allotDetails;
	}

	@GetMapping("/getAllBinInwardByDocid")
	public ResponseEntity<ResponseDTO> getAllBinInwardByDocid(@RequestParam String docid) {
		String methodName = "getAllBinInwardByDocid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BinInwardVO binInwardVO = null;
		try {
			binInwardVO = masterService.getBinInwardByDocid(docid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BinInward found by DocId");
			responseObjectsMap.put("binInwardVO", binInwardVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "BinInward not found for DocId: " + docid;
			responseDTO = createServiceResponseError(responseObjectsMap, "BinInward not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinAllotmentPdfHeaderDetails")
	public ResponseEntity<ResponseDTO> getBinAllotmentPdfHeaderDetails(@RequestParam String docid) {
		String methodName = "getBinAllotmentPdfHeaderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Object[]> header = new ArrayList<>();

		try {
			header = masterService.getBinAllotmentPdfHeaderDetails(docid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allotDetails = findwaitingHeader(header);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PDf Header Details found by ID");
			responseObjectsMap.put("HeaderDetails", allotDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "PDF Header Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> findwaitingHeader(List<Object[]> header) {
		List<Map<String, String>> allotDetails = new ArrayList<>();
		for (Object[] ps : header) {
			Map<String, String> part = new HashMap<>();
			part.put("allotno", ps[0] != null ? ps[0].toString() : "");
			part.put("allotDate", ps[1] != null ? ps[1].toString() : "");
			part.put("binreqno", ps[2] != null ? ps[2].toString() : "");
			part.put("binreqdate", ps[3] != null ? ps[3].toString() : "");
			part.put("senderAddress", ps[4] != null ? ps[4].toString() : "");
			part.put("senderCity", ps[5] != null ? ps[5].toString() : "");
			part.put("senderState", ps[6] != null ? ps[6].toString() : "");
			part.put("senderGst", ps[7] != null ? ps[7].toString() : "");
			part.put("senderName", ps[8] != null ? ps[8].toString() : "");
			part.put("receiverName", ps[9] != null ? ps[9].toString() : "");
			part.put("senderPinCode", ps[10] != null ? ps[10].toString() : "");
			allotDetails.add(part);
		}
		return allotDetails;
	}

	@GetMapping("/getBinAllotmentPdfGridDetails")
	public ResponseEntity<ResponseDTO> getBinAllotmentPdfGridDetails(@RequestParam String docid) {
		String methodName = "getBinAllotmentPdfGridDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Object[]> grid = new ArrayList<>();
		try {
			grid = masterService.getBinAllotmentPdfGridDetails(docid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> allotDetails = getBinAllotmentPdfGridDetails(grid);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PDF Grid Details found by ID");
			responseObjectsMap.put("allotDetails", allotDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "PDF Grid Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> getBinAllotmentPdfGridDetails(List<Object[]> allot) {
		List<Map<String, String>> allotDetails = new ArrayList<>();
		for (Object[] ps : allot) {
			Map<String, String> part = new HashMap<>();
			part.put("kitcode", ps[0] != null ? ps[0].toString() : "");
			part.put("allotkitqty", ps[1] != null ? ps[1].toString() : "");
			part.put("productName", ps[2] != null ? ps[2].toString() : "");
			part.put("productCode", ps[3] != null ? ps[3].toString() : "");
			part.put("productQty", ps[4] != null ? ps[4].toString() : "");
			allotDetails.add(part);
		}
		return allotDetails;
	}

	@PostMapping("/uploadFileCustomerSop")
	public ResponseEntity<ResponseDTO> uploadFileCustomerSop(@RequestParam Long id, @RequestParam String legalname,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadFileCustomerSop()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = masterService.uploadCustomerSop(id, legalname, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer SOP created successfully");
			responseObjectsMap.put("CustomerVO", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customer SOP creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadFileCustomerDocument")
	public ResponseEntity<ResponseDTO> uploadFileCustomerDocument(@RequestParam Long id, @RequestParam String legalname,
			@RequestParam("file") MultipartFile file) {
		String methodName = "uploadFileCustomerDocument()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String result = masterService.uploadCustomerDocument(id, legalname, file);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Document created successfully");
			responseObjectsMap.put("CustomerVO", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customer Document creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getRandomStockAssetDetails")
	public ResponseEntity<ResponseDTO> getRandomStockAssetDetails(@RequestParam String kitCode, @RequestParam int qty,
			@RequestParam String branchCode) {
		String methodName = "getRandomStockAssetDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Object[]> stock = new ArrayList<>();
		try {
			stock = masterService.getRandomAssetDetailsByKitCodeAndAllotQty(kitCode, qty, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> stockdetails = getStockDetails(stock);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Details found by ID");
			responseObjectsMap.put("StockDetails", stockdetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Stock Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> getStockDetails(List<Object[]> stock) {
		List<Map<String, String>> stockdetails = new ArrayList<>();
		for (Object[] ps : stock) {
			Map<String, String> part = new HashMap<>();
			part.put("tagCode", ps[1] != null ? ps[1].toString() : "");
			part.put("asset", ps[2] != null ? ps[2].toString() : "");
			part.put("rfId", ps[3] != null ? ps[3].toString() : "");
			part.put("assetCode", ps[4] != null ? ps[4].toString() : "");
			stockdetails.add(part);
		}
		return stockdetails;
	}
	
	
	@GetMapping("/getCustomizedAllotmentDetails")
	public ResponseEntity<ResponseDTO> getCustomizedAllotmentDetails(@RequestParam(required = false) String kitCode,
			@RequestParam(required = false) String flow, @RequestParam(required = false) String emitter,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startAllotDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endAllotDate) {
		String methodName = "getIssuseRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BinAllotmentNewVO> binAllotmentNewVO = new ArrayList<>();
		try {
			binAllotmentNewVO = masterService.getIssueRequest(kitCode, flow, emitter, startAllotDate, endAllotDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin Allotment Details Get Successfully");
			responseObjectsMap.put("binAllotmentVO", binAllotmentNewVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bin Allotment Details Get Successfully", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
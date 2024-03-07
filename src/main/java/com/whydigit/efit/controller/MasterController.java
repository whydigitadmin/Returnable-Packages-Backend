package com.whydigit.efit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
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
import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.CustomersAddressDTO;
import com.whydigit.efit.dto.CustomersBankDetailsDTO;
import com.whydigit.efit.dto.CustomersDTO;
import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.dto.KitDTO;
import com.whydigit.efit.dto.KitResponseDTO;
import com.whydigit.efit.dto.ResponseDTO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.CustomersAddressVO;
import com.whydigit.efit.entity.CustomersBankDetailsVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.KitVO;
import com.whydigit.efit.entity.ManufacturerProductVO;
import com.whydigit.efit.entity.ManufacturerVO;
import com.whydigit.efit.entity.UnitVO;
import com.whydigit.efit.entity.VenderAddressVO;
import com.whydigit.efit.entity.VenderVO;
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
	public ResponseEntity<ResponseDTO> deleteAsset(@PathVariable int id) {
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

	@DeleteMapping("/assetGroup/{id}")
	public ResponseEntity<ResponseDTO> deleteAssetGroup(@PathVariable int id) {
		String methodName = "deleteAssetGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteAssetGroup(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetGroup deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetGroup deletion failed", errorMsg);
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
		String methodName = "updateAssetGroup()";
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

	@PostMapping("/customersAddress")
	public ResponseEntity<ResponseDTO> createUpdateCustomersAddress(
			@RequestBody CustomersAddressDTO customersAddressDTO) {
		String methodName = "updateCustomersAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String action = StringUtils.EMPTY;
		try {
			action = ObjectUtils.isEmpty(customersAddressDTO.getId()) ? "create" : "update";
			CustomersAddressVO customersCustomersAddressVO = masterService
					.createUpdateCustomersAddress(customersAddressDTO);
			if (customersCustomersAddressVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers Address " + action + " successfully");
				responseObjectsMap.put("customersAddressVO", customersCustomersAddressVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Customers Address " + action + " failed",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers Address " + action + " failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/customersBankDetails")
	public ResponseEntity<ResponseDTO> createUpdateBankDetails(
			@RequestBody CustomersBankDetailsDTO customersBankDetailsDTO) {
		String methodName = "updateCustomersBankDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String action = StringUtils.EMPTY;
		try {
			action = ObjectUtils.isEmpty(customersBankDetailsDTO.getId()) ? "create" : "update";
			CustomersBankDetailsVO customersBankDetailsVO = masterService
					.createUpdateBankDetails(customersBankDetailsDTO);
			if (customersBankDetailsVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"Customers Bank Details " + action + " successfully");
				responseObjectsMap.put("customersBankDetailsVO", customersBankDetailsVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Customers BankDetails " + action + " failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Customers Bank Details" + " update failed",
					errorMsg);
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
	
	@GetMapping("/flow/getFlowByIds")
	public ResponseEntity<ResponseDTO> getFlowByIds(@RequestParam String ids) {
		String methodName = "getFlowByIds()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FlowVO> flowVO = new ArrayList<>();
		try {
			flowVO = masterService.getFlowByIds(ids);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "flow found by IDS");
			responseObjectsMap.put("flowVO", flowVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Flow not found for ID: " + ids;
			responseDTO = createServiceResponseError(responseObjectsMap, "Flow not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

	// vendors

	@GetMapping("/vender")
	public ResponseEntity<ResponseDTO> getAllVender(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllVender()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VenderVO> venderVO = new ArrayList<>();
		try {
			venderVO = masterService.getAllVender(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vender information get successfully");
			responseObjectsMap.put("venderVO", venderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Vender information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/vender/{id}")
	public ResponseEntity<ResponseDTO> getVenderById(@PathVariable int id) {
		String methodName = "getVenderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		VenderVO venderVO = null;
		try {
			venderVO = masterService.getVenderById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VenderVO found by ID");
			responseObjectsMap.put("venderVO", venderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Vender not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Vender not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/vender")
	public ResponseEntity<ResponseDTO> createVender(@RequestBody VenderVO venderVO) {
		String methodName = "createVender()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			VenderVO createdVenderVO = masterService.createVender(venderVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vender created successfully");
			responseObjectsMap.put("venderVO", createdVenderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Vender creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/vender")
	public ResponseEntity<ResponseDTO> updateVendor(@RequestBody VenderVO venderVO) {
		String methodName = "updateVender()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			VenderVO updatedVenderVO = masterService.updateVender(venderVO).orElse(null);
			if (updatedVenderVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vender updated successfully");
				responseObjectsMap.put("venderVO", updatedVenderVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Flow not found for ID: " + venderVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Vender update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Vender" + " update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/vender/{id}")
	public ResponseEntity<ResponseDTO> deleteVende(@PathVariable int id) {
		String methodName = "deleteVender()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteVender(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vender deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Vender deletion failed", errorMsg);
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
	public ResponseEntity<ResponseDTO> getUnitById(@PathVariable int id) {
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
	public ResponseEntity<ResponseDTO> deleteunit(@PathVariable int id) {
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

	// venderAddress
	@GetMapping("/venderAddress")
	public ResponseEntity<ResponseDTO> getAllVenderAddress() {
		String methodName = "getAllVenderAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VenderAddressVO> venderAddressVO = new ArrayList<>();
		try {
			venderAddressVO = masterService.getAllVenderAddress();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VenderAddress information get successfully");
			responseObjectsMap.put("VenderAddress", venderAddressVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "VenderAddress information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/venderAddress/{id}")
	public ResponseEntity<ResponseDTO> getVenderAddressById(@PathVariable int id) {
		String methodName = "getVenderAddressById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		VenderAddressVO venderAddressVO = null;
		try {
			venderAddressVO = masterService.getVenderAddressById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VenderAddress found by ID");
			responseObjectsMap.put("VenderAddress", venderAddressVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "VenderAddress not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "VenderAddress not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/VenderAddress")
	public ResponseEntity<ResponseDTO> createVenderAddress(@RequestBody VenderAddressVO venderAddressVO) {
		String methodName = "createVenderAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			VenderAddressVO createdVenderAddress = masterService.createVenderAddress(venderAddressVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VenderAddress created successfully");
			responseObjectsMap.put("VenderAddress", createdVenderAddress);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "VenderAddress creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/venderAddress")
	public ResponseEntity<ResponseDTO> updateVenderAddress(@RequestBody VenderAddressVO venderAddressVO) {
		String methodName = "updateVenderAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			VenderAddressVO updatedVenderAddress = masterService.updateVenderAddress(venderAddressVO).orElse(null);
			if (updatedVenderAddress != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VenderAddress updated successfully");
				responseObjectsMap.put("venderAddress", updatedVenderAddress);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "VenderAddress not found for ID: " + venderAddressVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "VenderAddress update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "VenderAddress update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/venderAddress/{id}")
	public ResponseEntity<ResponseDTO> deleteVenderAddress(@PathVariable int id) {
		String methodName = "deleteVenderAddress()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			masterService.deleteVenderAddress(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VenderAddress deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "VenderAddress deletion failed", errorMsg);
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
	public ResponseEntity<ResponseDTO> getKitById(@PathVariable String id) {
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
	public ResponseEntity<ResponseDTO> deleteKit(@PathVariable String id) {
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

}

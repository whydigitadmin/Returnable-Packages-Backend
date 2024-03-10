package com.whydigit.efit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.CustomersAddressDTO;
import com.whydigit.efit.dto.CustomersBankDetailsDTO;
import com.whydigit.efit.dto.CustomersDTO;
import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.dto.KitDTO;
import com.whydigit.efit.dto.KitResponseDTO;
import com.whydigit.efit.dto.VendorAddressDTO;
import com.whydigit.efit.dto.VendorBankDetailsDTO;
import com.whydigit.efit.dto.VendorDTO;
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
import com.whydigit.efit.entity.VendorAddressVO;
import com.whydigit.efit.entity.VendorBankDetailsVO;
import com.whydigit.efit.entity.VendorVO;
//import com.whydigit.efit.entity.VenderAddressVO;
//import com.whydigit.efit.entity.VenderVO;
import com.whydigit.efit.exception.ApplicationException;

public interface MasterService {

	List<AssetVO> getAllAsset(Long orgId);

	Optional<AssetVO> getAssetById(int id);

	AssetVO createAsset(AssetVO assetVO);

	Optional<AssetVO> updateAsset(AssetVO assetVO);

	void deleteAsset(int id);

	Map<String, Object> getAllAssetGroup(Long orgId, String assetCategory, String assetName, String assetCodeId,
			String manufacturer);

	Optional<AssetGroupVO> getAssetGroupById(String id);

	AssetGroupVO createAssetGroup(AssetGroupVO assetGroupVO) throws ApplicationException;

	Optional<AssetGroupVO> updateAssetGroup(AssetGroupVO assetGroupVO);

	void deleteAssetGroup(int id);

	// CUstomers

	List<CustomersVO> getAllCustomers(Long orgId);

	CustomersVO getCustomersById(Long id) throws ApplicationException;

	CustomersVO createCustomers(CustomersDTO customersDTO);

	CustomersVO updateCustomers(CustomersDTO customersDTO) throws ApplicationException;

	CustomersBankDetailsVO createUpdateBankDetails(CustomersBankDetailsDTO customersBankDetailsDTO)
			throws ApplicationException;

	void deleteCustomers(Long id);

	void deleteCustomersBankDetails(Long id);

	CustomersAddressVO createUpdateCustomersAddress(CustomersAddressDTO customersAddressDTO)
			throws ApplicationException;

	void deleteCustomersAddress(Long id);

	List<CustomersAddressVO> getCustomerAddressByCustomerId(Long customerId);

	// FLOW

	List<FlowVO> getAllFlow(Long orgId, Long emitterId);

	Optional<FlowVO> getFlowById(long id);

	FlowVO createFlow(FlowDTO flowDTO);

	Optional<FlowVO> updateFlow(FlowVO flowVO);

	void deleteFlow(long id);

	List<ManufacturerVO> getAllManufacturer(Long orgId);

	Optional<ManufacturerVO> getManufacturerById(int id);

	ManufacturerVO createManufacturer(ManufacturerVO manufacturerVO);

	Optional<ManufacturerVO> updateManufacturer(ManufacturerVO manufacturerVO);

	void deleteManufacturer(int id);

	List<ManufacturerProductVO> getAllManufacturerProduct(Long orgId);

	ManufacturerProductVO createManufacturerProduct(ManufacturerProductVO manufacturerProductVO);

	List<AssetCategoryVO> getAllAssetCategory(Long orgId, String assetCategoryName);

	AssetCategoryVO createAssetCategory(AssetCategoryVO assetCategoryVO);

//unit

	List<UnitVO> getAllUnit(Long orgId);

	Optional<UnitVO> getUnitById(int id);

	UnitVO createUnit(UnitVO unitVO);

	Optional<UnitVO> updateUnit(UnitVO unitVO);

	void deleteUnit(int id);

	// Create Kit
	List<KitResponseDTO> getAllKit(Long orgId);

	Optional<KitVO> getKitById(String id);

	KitVO createkit(KitDTO kitDTO) throws ApplicationException;

	Optional<KitVO> updatedKit(KitVO kitVO);

	void deleteKit(String id);

	Map<String, List<CustomersVO>> CustomersType(Long orgId);

	Map<String, Map<String, List<AssetGroupVO>>> getAssetGroupByCategoryType(Long orgId);

	void uploadCustomerAttachmentDoc(MultipartFile[] files, CustomerAttachmentType type, Long customerId)
			throws ApplicationException;

	List<AssetGroupVO> createAssetGroupByCSV(MultipartFile assetFile) throws ApplicationException;

	// Vendor
	List<VendorVO> getAllVendor();

	Optional<VendorVO> getVendorById(Long id);

	VendorVO updateCreateVendor(@Valid VendorDTO vendorDTO) throws ApplicationException;

	List<VendorVO> getVendorByOrgId(Long orgId);

	void deletevendor(long id);

	VendorAddressVO updateCreateVendorAddress(@Valid VendorAddressDTO vendorAddressDTO) throws ApplicationException;

	Optional<VendorAddressVO> getVendorAddressById(Long id);

	void deletevendorAddress(Long id);

	VendorBankDetailsVO updateCreatevendorBankDetails(@Valid VendorBankDetailsDTO vendorBankDetailsDTO)
			throws ApplicationException;

	Optional<VendorBankDetailsVO> getVendorBankDetailsById(Long id);

	void deletevendorBankDetails(Long id);

	List<FlowVO> getFlowByUserId(long userId) throws ApplicationException;

	Set<Object[]> getFlowNameByOrgID(Long orgId, Long emitterId);

}

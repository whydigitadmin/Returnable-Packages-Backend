package com.whydigit.efit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.CustomersAddressDTO;
import com.whydigit.efit.dto.CustomersBankDetailsDTO;
import com.whydigit.efit.dto.CustomersDTO;
import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.dto.KitDTO;
import com.whydigit.efit.dto.KitResponseDTO;
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
import com.whydigit.efit.entity.WarehouseLocationVO;
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

	List<VenderVO> getAllVender(Long orgId);

	Optional<VenderVO> getVenderById(int id);

	VenderVO createVender(VenderVO venderVO);

	Optional<VenderVO> updateVender(VenderVO venderVO);

	void deleteVender(int id);

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

//warehouse location

	List<WarehouseLocationVO> getAllWarehouseLocation(Long orgId);

	Optional<WarehouseLocationVO> getWarehouseLocationById(int id);

	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouselocationVO);

	Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouselocationVO);

	void deleteWarehouseLocation(int id);

	List<AssetGroupVO> createAssetGroupByCSV(MultipartFile assetFile) throws ApplicationException;

//venderAddress

	List<VenderAddressVO> getAllVenderAddress();

	Optional<VenderAddressVO> getVenderAddressById(int id);

	VenderAddressVO createVenderAddress(VenderAddressVO venderAddressVO);

	Optional<VenderAddressVO> updateVenderAddress(VenderAddressVO venderAddressVO);

	void deleteVenderAddress(int id);

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

}

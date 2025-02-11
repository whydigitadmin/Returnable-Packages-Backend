package com.whydigit.efit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.AssetCategoryDTO;
import com.whydigit.efit.dto.AssetDTO;
import com.whydigit.efit.dto.AssetInwardDTO;
import com.whydigit.efit.dto.AssetTaggingDTO;
import com.whydigit.efit.dto.AssetTypeDTO;
import com.whydigit.efit.dto.BinAllotmentDTO;
import com.whydigit.efit.dto.BinInwardDTO;
import com.whydigit.efit.dto.BranchDTO;
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
import com.whydigit.efit.dto.ServiceDTO;
import com.whydigit.efit.dto.StockBranchDTO;
import com.whydigit.efit.dto.TermsAndConditionsDTO;
import com.whydigit.efit.dto.VendorDTO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetInwardVO;
import com.whydigit.efit.entity.AssetTaggingVO;
import com.whydigit.efit.entity.AssetTypeVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinInwardVO;
import com.whydigit.efit.entity.BranchVO;
import com.whydigit.efit.entity.CnoteVO;
import com.whydigit.efit.entity.CustomersAddressVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.DmapVO;
import com.whydigit.efit.entity.FlowVO;
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
//import com.whydigit.efit.entity.VenderAddressVO;
//import com.whydigit.efit.entity.VenderVO;
import com.whydigit.efit.exception.ApplicationException;

public interface MasterService {

	List<AssetVO> getAllAsset(Long orgId);
	
	List<AssetVO> getAllActiveAsset(Long orgId);

	List<AssetVO> getAllAssetByCategory(Long orgId,String category);
	
	Optional<AssetVO> getAssetById(Long id);
	
	

	AssetVO createAsset(AssetVO assetVO) throws ApplicationException;

	AssetVO updateAsset(AssetDTO assetDTO) throws ApplicationException;

	void deleteAsset(Long id);

	Map<String, Object> getAllAssetGroup(Long orgId, String assetCategory, String assetName, String assetCodeId,
			String manufacturer);

	Optional<AssetCategoryVO> getAssetCategoryById(Long id);

	AssetCategoryVO createAssetCategory(AssetCategoryVO assetGroupVO) throws ApplicationException;

	AssetCategoryVO updateAssetCategory(AssetCategoryDTO assetGroupVO) throws ApplicationException;

	// CUstomers
	
	List<CustomersVO> getAllActiveCustomers(Long orgId);

	List<CustomersVO> getAllCustomers(Long orgId);

	CustomersVO getCustomersById(Long id) throws ApplicationException;

	CustomersVO createCustomers(CustomersDTO customersDTO);

	CustomersVO updateCustomers(CustomersDTO customersDTO) throws ApplicationException;


	void deleteCustomers(Long id);

	void deleteCustomersBankDetails(Long id);


	void deleteCustomersAddress(Long id);

	List<CustomersAddressVO> getCustomerAddressByCustomerId(Long customerId);

	// FLOW

	List<FlowVO> getAllFlow(Long orgId, Long emitterId);
	
	List<FlowVO> getAllActiveFlow(Long orgId, Long emitterId);

	Set<Object[]> getKitDetailsByEmitter(Long emitterId, Long orgId);
	
	Optional<FlowVO> getFlowById(long id);

	FlowVO createFlow(FlowDTO flowDTO) throws ApplicationException;

	FlowVO updateFlow(FlowDTO flowDTO) throws ApplicationException;

	void deleteFlow(long id);

	List<ManufacturerVO> getAllManufacturer(Long orgId);

	Optional<ManufacturerVO> getManufacturerById(int id);

	ManufacturerVO createManufacturer(ManufacturerVO manufacturerVO);

	Optional<ManufacturerVO> updateManufacturer(ManufacturerVO manufacturerVO);

	void deleteManufacturer(int id);

	List<ManufacturerProductVO> getAllManufacturerProduct(Long orgId);

	ManufacturerProductVO createManufacturerProduct(ManufacturerProductVO manufacturerProductVO);

	List<AssetTypeVO> getAllAssetType(Long orgId);

	AssetTypeVO createAssetType(AssetTypeVO assetCategoryVO);
	
	AssetTypeVO updateAssetType(AssetTypeDTO assetTypeVO) throws ApplicationException;

//unit

	List<UnitVO> getAllUnit(Long orgId);

	Optional<UnitVO> getUnitById(Long id);

	UnitVO createUnit(UnitVO unitVO) throws ApplicationException;

	Optional<UnitVO> updateUnit(UnitVO unitVO) throws ApplicationException;

	void deleteUnit(Long id);

	// Create Kit
	List<KitResponseDTO> getAllKit(Long orgId);
	
	List<KitResponseDTO> getActiveAllKit(Long orgId);
	
	Optional<KitVO> getKitById(Long id);

	Optional<KitVO> getKitByKitCode(String kitName);

	KitVO createkit(KitDTO kitDTO) throws ApplicationException;

	KitVO updatedKit(KitDTO kitDTO) throws ApplicationException;

	void deleteKit(Long id);

	Map<String, List<CustomersVO>> CustomersType(Long orgId);

	Map<String, Map<String, List<AssetCategoryVO>>> getAssetCategoryAssetType(Long orgId);

	void uploadCustomerAttachmentDoc(MultipartFile[] files, CustomerAttachmentType type, Long customerId)
			throws ApplicationException;

	List<AssetCategoryVO> createAssetCategoryByCSV(MultipartFile assetFile) throws ApplicationException;

	// Vendor
	List<VendorVO> getAllVendor(Long orgId);
	
	List<VendorVO> getAllActiveVendor(Long orgId);

	Optional<VendorVO> getVendorById(Long id);

	VendorVO updateCreateVendor(VendorDTO vendorDTO) throws ApplicationException;

	List<VendorVO> getVendorByOrgId(Long orgId);

	void deletevendor(long id);

//	VendorAddressVO updateCreateVendorAddress(@Valid VendorAddressDTO vendorAddressDTO) throws ApplicationException;

	Optional<VendorAddressVO> getVendorAddressById(Long id);

	void deletevendorAddress(Long id);

//	VendorBankDetailsVO updateCreatevendorBankDetails(@Valid VendorBankDetailsDTO vendorBankDetailsDTO)throws ApplicationException;

	Optional<VendorBankDetailsVO> getVendorBankDetailsById(Long id);

	void deletevendorBankDetails(Long id);

	List<FlowVO> getFlowByUserId(long userId) throws ApplicationException;

	Set<Object[]> getFlowNameByOrgID(Long orgId, Long emitterId);

	int loadKitQty(Long irItemId, Long kitQty);

	DmapVO createDmapVO(DmapDTO dmapDTO);

	// service

	List<ServiceVO> getAllServiceByOrgId(Long OrgId);

	ServiceVO updateCreateService(ServiceDTO serviceDTO) throws ApplicationException;

	CnoteVO updateCreateCnote(CnoteDTO cnoteDTO) throws ApplicationException;

	// Stock Branch

	StockBranchVO createStockBranch(StockBranchDTO stockBranchDTO) throws ApplicationException;

	StockBranchVO updateStockBranch(StockBranchDTO stockBranchDTO) throws ApplicationException;

	List<StockBranchVO> getAllStockBranchByOrgId(Long orgId);
	
	List<StockBranchVO> getAllActiveStockBranchByOrgId(Long orgId);

	AssetInwardVO createAssetInward(AssetInwardDTO assetInwardDTO);

	// Create Asset Tagging

	AssetTaggingVO createTagging(AssetTaggingDTO assetTaggingDTO);

	List<AssetTaggingVO> getAllAsetTaggingByOrgId(Long orgId);
	
	AssetTaggingVO getTaggingById(Long id);

	Set<Object[]> getTagCodeByAsset(String assetcode, String asset, int endno, String category);

	Set<Object[]> getAvalKitQty(Long warehouseId, String Kitname);

	Set<Object[]> getAvalKitQtyByBranch(String branch, String Kitname);

	TermsAndConditionsVO updateCreateTerms(TermsAndConditionsDTO termsAndConditionsDTO) throws ApplicationException;

	List<TermsAndConditionsVO> getAllTermsByOrgId(Long orgId);

	List<TermsAndConditionsVO> getAllTermsById(Long termsId);

	PoVO updateCreatePo(PoDTO poDTO) throws ApplicationException;

	public List<PoVO> getPoByOrgId(Long orgId);

	List<PoVO> getAllPoByPoId(Long poId);

	PodVO updateCreatePod(PodDTO podDTO) throws ApplicationException;

	List<PodVO> getAllPodByPodId(Long podId);

	List<PodVO> getAllPodByOrgId(Long orgId);

	AssetCategoryVO getAssetCategoryByCategoryCode(Long orgId, String categoryCode);

	AssetVO getAssetByOrgId(Long orgId, String assetId);

	List<AssetInwardVO> getAllAssetInwardOrgId(Long orgId);

	Set<Object[]> getPoNoByCreateAsset(Long orgId);

	ProofOfDeliveryVO createProofOfDelivery(ProofOfDeliveryDTO proofOfDeliveryDTO);

	String uploadFileProofOfDelivery(MultipartFile file, String docId, String refNo);

	List<ProofOfDeliveryVO> getAllProofOfDelivery(Long orgId);

	// Bin Allotmentdetails

	BinAllotmentNewVO createBinAllotment(BinAllotmentDTO binAllotmentDTO);

	Set<Object[]> getAllotmentNoByEmitterIdAndOrgId(Long orgId, Long emitterId);

	Set<Object[]> getAllotmentDetailsByAllotmentNoAndOrgId(Long orgId, String docid);


	Set<Object[]> getAlllBinInwardByEmitterAndOrgId(Long emitterid, Long orgId);

	Optional<BinInwardVO> getBinInwardById(Long id);

	Optional<BinInwardVO> getBinInwardByDocid(String docid);

	Set<Object[]> getAllotmentAssetDetailsByAllotmentNoAndOrgId(Long orgId, String docid);

	Set<Object[]> getWaitingInwardDetailsByEmitterIdandOrgId(Long orgId, Long emitterid);

	Set<Object[]> getFlowDetailsByFlowId(Long flowId);

	String getDocIdByAssetTagging();

	String getDocIdByBinInward();

	String getDocIdByAssetInward();//

	// Bin allotment Issue manifest pdf

	Set<Object[]> getBinAllotmentPdfHeaderDetails(String docid);

	List<Object[]> getBinAllotmentPdfGridDetails(String docid);

	AssetInwardVO getAssetInwardByDocId(String docId);

	String uploadCustomerSop(Long id, String legalname, MultipartFile file);

	String uploadCustomerDocument(Long id, String legalname, MultipartFile file);

	List<Object[]> getRandomAssetDetailsByKitCodeAndAllotQty(String kitCode, int qty, String stockbranch);

	// Get Bin Allotment details
	
	List<BinAllotmentNewVO> getCustomizedAllotmentDetails(String kitCode, String flow, String emitter, LocalDate startAllotDate,
			LocalDate endAllotDate);
	
	List<Object[]>availableAllAssetDetails(Long orgId);

	List<FlowVO> getFlowByKitCode(String kitcode);

	List<Object[]> getAvailableKitQtyByEmitter(Long orgId, Long emitterId, String kitId, Long flowId);

	Set<Object[]> getAssetDetailsByAssetForAssetInward(Long orgId, String stockBranch, String sku,int qty);

	Set<Object[]> getAvailAssetDetailsByBranch(Long orgId, String stockBranch, String category);
	
	
	List<BranchVO> getAllBranch(Long orgId);
	
	List<BranchVO> getAllActiveBranch(Long orgId);
	
	Optional<BranchVO> getBranchById(Long id);

	BranchVO createUpdateBranch(BranchDTO branchDTO) throws ApplicationException;
	
	void deleteBranch(Long id);

	Set<Object[]> getEmitterAndReceiverByKitNo(String kitNo);

	Set<Object[]> getBranchLocationByFlow(Long orgId, Long flowId);

	void ExcelUploadForAssetCategory(MultipartFile[] files, CustomerAttachmentType type,Long orgId) throws ApplicationException;

	int getTotalRows();

	int getSuccessfulUploads();


	

	

	

	

	

	


	

	


	

	

	

}
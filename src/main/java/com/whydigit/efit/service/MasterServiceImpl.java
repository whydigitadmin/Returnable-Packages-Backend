
package com.whydigit.efit.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.whydigit.efit.common.CommonConstant;
import com.whydigit.efit.common.CustomerConstant;
import com.whydigit.efit.common.MasterConstant;
import com.whydigit.efit.dto.AssetCategoryDTO;
import com.whydigit.efit.dto.AssetDTO;
import com.whydigit.efit.dto.AssetInwardDTO;
import com.whydigit.efit.dto.AssetInwardDetailDTO;
import com.whydigit.efit.dto.AssetTaggingDTO;
import com.whydigit.efit.dto.AssetTaggingDetailsDTO;
import com.whydigit.efit.dto.AssetTypeDTO;
import com.whydigit.efit.dto.BinAllotmentDTO;
import com.whydigit.efit.dto.BinAllotmentDetailsDTO;
import com.whydigit.efit.dto.BinRetrievalDTO;
import com.whydigit.efit.dto.BinRetrievalDetailsDTO;
import com.whydigit.efit.dto.BranchDTO;
import com.whydigit.efit.dto.CnoteDTO;
import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.CustomersAddressDTO;
import com.whydigit.efit.dto.CustomersBankDetailsDTO;
import com.whydigit.efit.dto.CustomersDTO;
import com.whydigit.efit.dto.DmapDTO;
import com.whydigit.efit.dto.DmapDetailsDTO;
import com.whydigit.efit.dto.FlowDTO;
import com.whydigit.efit.dto.FlowDetailDTO;
import com.whydigit.efit.dto.InvoiceDTO;
import com.whydigit.efit.dto.InvoiceProductLinesDTO;
import com.whydigit.efit.dto.KitAssetDTO;
import com.whydigit.efit.dto.KitDTO;
import com.whydigit.efit.dto.KitResponseDTO;
import com.whydigit.efit.dto.Po1DTO;
import com.whydigit.efit.dto.PoDTO;
import com.whydigit.efit.dto.Pod1DTO;
import com.whydigit.efit.dto.Pod2DTO;
import com.whydigit.efit.dto.PodDTO;
import com.whydigit.efit.dto.ProofOfDeliveryDTO;
import com.whydigit.efit.dto.ServiceDTO;
import com.whydigit.efit.dto.StockBranchDTO;
import com.whydigit.efit.dto.TaxInvoiceDTO;
import com.whydigit.efit.dto.TaxInvoiceKitLineDTO;
import com.whydigit.efit.dto.TaxInvoiceProductLineDTO;
import com.whydigit.efit.dto.TermsAndConditionsDTO;
import com.whydigit.efit.dto.UnitDTO;
import com.whydigit.efit.dto.VendorAddressDTO;
import com.whydigit.efit.dto.VendorBankDetailsDTO;
import com.whydigit.efit.dto.VendorDTO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetInwardDetailVO;
import com.whydigit.efit.entity.AssetInwardVO;
import com.whydigit.efit.entity.AssetItemVO;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.AssetTaggingDetailsVO;
import com.whydigit.efit.entity.AssetTaggingVO;
import com.whydigit.efit.entity.AssetTypeVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.BasicDetailVO;
import com.whydigit.efit.entity.BinAllotmentDetailsVO;
import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinInwardVO;
import com.whydigit.efit.entity.BinRetrievalDetailsVO;
import com.whydigit.efit.entity.BinRetrievalVO;
import com.whydigit.efit.entity.BranchVO;
import com.whydigit.efit.entity.CnoteVO;
import com.whydigit.efit.entity.CustomerAttachmentVO;
import com.whydigit.efit.entity.CustomersAddressVO;
import com.whydigit.efit.entity.CustomersBankDetailsVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.DmapDetailsVO;
import com.whydigit.efit.entity.DmapVO;
import com.whydigit.efit.entity.FlowDetailVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.InvoiceProductLinesVO;
import com.whydigit.efit.entity.InvoiceVO;
import com.whydigit.efit.entity.KitAssetVO;
import com.whydigit.efit.entity.KitVO;
import com.whydigit.efit.entity.ManufacturerProductVO;
import com.whydigit.efit.entity.ManufacturerVO;
import com.whydigit.efit.entity.PoVO;
import com.whydigit.efit.entity.PoVO1;
import com.whydigit.efit.entity.Pod1VO;
import com.whydigit.efit.entity.Pod2VO;
import com.whydigit.efit.entity.PodVO;
import com.whydigit.efit.entity.ProofOfDeliveryVO;
import com.whydigit.efit.entity.ServiceVO;
import com.whydigit.efit.entity.StockBranchVO;
import com.whydigit.efit.entity.TaxInvoiceKitLineVO;
import com.whydigit.efit.entity.TaxInvoiceProductLineVO;
import com.whydigit.efit.entity.TaxInvoiceVO;
import com.whydigit.efit.entity.TermsAndConditionsVO;
import com.whydigit.efit.entity.UnitVO;
import com.whydigit.efit.entity.VendorAddressVO;
import com.whydigit.efit.entity.VendorBankDetailsVO;
import com.whydigit.efit.entity.VendorVO;
import com.whydigit.efit.entity.WarehouseVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AssetCategoryRepo;
import com.whydigit.efit.repo.AssetInwardDetailRepo;
import com.whydigit.efit.repo.AssetInwardRepo;
import com.whydigit.efit.repo.AssetRepo;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
import com.whydigit.efit.repo.AssetTaggingDetailsRepo;
import com.whydigit.efit.repo.AssetTaggingRepo;
import com.whydigit.efit.repo.AssetTypeRepo;
import com.whydigit.efit.repo.BasicDetailRepo;
import com.whydigit.efit.repo.BinAllotmentNewRepo;
import com.whydigit.efit.repo.BinAllotmentRepo;
import com.whydigit.efit.repo.BinInwardRepo;
import com.whydigit.efit.repo.BinOutwardRepo;
import com.whydigit.efit.repo.BinRetrievalRepo;
import com.whydigit.efit.repo.BranchRepo;
import com.whydigit.efit.repo.CnoteRepo;
import com.whydigit.efit.repo.CustomerAttachmentRepo;
import com.whydigit.efit.repo.CustomersAddressRepo;
import com.whydigit.efit.repo.CustomersBankDetailsRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.DmapDetailsRepo;
import com.whydigit.efit.repo.DmapRepo;
import com.whydigit.efit.repo.FlowDetailRepo;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.InvoiceProductLinesRepo;
import com.whydigit.efit.repo.InvoiceRepo;
import com.whydigit.efit.repo.IssueItemRepo;
import com.whydigit.efit.repo.IssueRequestRepo;
import com.whydigit.efit.repo.KitAssetRepo;
import com.whydigit.efit.repo.KitRepo;
import com.whydigit.efit.repo.ManufacturerProductRepo;
import com.whydigit.efit.repo.ManufacturerRepo;
import com.whydigit.efit.repo.Po1Repo;
import com.whydigit.efit.repo.PoRepo;
import com.whydigit.efit.repo.PodRepo;
import com.whydigit.efit.repo.ProofOfDeliveryRepo;
import com.whydigit.efit.repo.ServiceRepo;
import com.whydigit.efit.repo.StockBranchRepo;
import com.whydigit.efit.repo.TaxInvoiceKitLineRepo;
import com.whydigit.efit.repo.TaxInvoiceProductLineRepo;
import com.whydigit.efit.repo.TaxInvoiceRepo;
import com.whydigit.efit.repo.TermsAndConditionsRepo;
import com.whydigit.efit.repo.TransportPickupRepo;
import com.whydigit.efit.repo.UnitRepo;
import com.whydigit.efit.repo.UserRepo;
import com.whydigit.efit.repo.VendorAddressRepo;
import com.whydigit.efit.repo.VendorBankDetailsRepo;
import com.whydigit.efit.repo.VendorRepo;
import com.whydigit.efit.repo.WarehouseRepository;
import com.whydigit.efit.util.CommonUtils;


@Service
public class MasterServiceImpl implements MasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	private static final String STRING = null;

	private static final String NUMERIC = null;

	@Autowired
	AssetRepo assetRepo;
	
	@Autowired
	AssetCategoryRepo assetCategoryRepo;
	
	@Autowired
	TaxInvoiceRepo taxInvoiceRepo;
	
	@Autowired
	TaxInvoiceProductLineRepo taxInvoiceProductLineRepo;
	
	@Autowired
	TaxInvoiceKitLineRepo taxInvoiceKitLineRepo;
	
	@Autowired
	CustomersRepo customersRepo;
	
	@Autowired
	InvoiceRepo invoiceRepo;
	
	@Autowired
	InvoiceProductLinesRepo invoiceProductLinesRepo;

	@Autowired
	WarehouseRepository warehouseRepo;

	@Autowired
	TransportPickupRepo transportPickupRepo;

	@Autowired
	BinRetrievalRepo binRetrievalRepo;

	@Autowired
	FlowDetailRepo flowDetailRepo;

	@Autowired
	BasicDetailRepo basicDetailRepo;

	@Autowired
	FlowRepo flowRepo;
	@Autowired
	VendorRepo vendorRepo;

	@Autowired
	KitAssetRepo kitAssetRepo;

	@Autowired
	AssetTaggingRepo assetTaggingRepo;
	@Autowired
	AssetTaggingDetailsRepo assetTaggingDetailsRepo;
	@Autowired
	ManufacturerRepo manufacturerRepo;
	@Autowired
	ManufacturerProductRepo manufacturerProductRepo;
	@Autowired
	AssetTypeRepo assetTypeRepo;
	@Autowired
	UnitRepo unitRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	VendorAddressRepo vendorAddressRepo;
	@Autowired
	VendorRepo VendorRepo;
	@Autowired
	IssueRequestRepo issueRequestRepo;
	@Autowired
	VendorBankDetailsRepo vendorBankDetailsRepo;
	@Autowired
	KitRepo kitRepo;

	@Autowired
	BinAllotmentRepo binAllotmentRepo;

	@Autowired
	BinAllotmentNewRepo binAllotmentNewRepo;

	@Autowired
	TermsAndConditionsRepo termsAndConditionsRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	AssetStockDetailsRepo assetStockDetailsRepo;

	@Autowired
	CustomersAddressRepo customersAddressRepo;

	@Autowired
	CustomersBankDetailsRepo CustomersBankDetailsRepo;

	@Autowired
	CustomersBankDetailsRepo customersBankDetailsRepo;

	@Autowired
	Environment env;

	@Autowired
	CustomerAttachmentRepo customerAttachmentRepo;

	@Autowired
	BinInwardRepo binInwardRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	IssueItemRepo issueItemRepo;

	@Autowired
	DmapRepo dmapRepo;

	@Autowired
	DmapDetailsRepo dmapdetailsRepo;

	@Autowired
	ServiceRepo serviceRepo;

	@Autowired
	BinOutwardRepo binOutwardRepo;

	@Autowired
	CnoteRepo cnoteRepo;

	@Autowired
	AssetInwardRepo assetInwardRepo;

	@Autowired
	AssetInwardDetailRepo assetInwardDetailRepo;

	@Autowired
	StockBranchRepo stockBranchRepo;

	@Autowired
	PoRepo poRepo;

	@Autowired
	Po1Repo po1Repo;

	@Autowired
	PodRepo podRepo;

	@Autowired
	private ProofOfDeliveryRepo proofOfDeliveryRepo;

	private final String uploadDir = "D:\\Justin"; // Specify the upload directory

	@Override
	public List<AssetVO> getAllAsset(Long orgId) {
		List<AssetVO> assetVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  AssetInformation BY OrgId : {}", orgId);
			assetVO = assetRepo.getAllAssetByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  AssetInformation For All OrgId.");
			assetVO = assetRepo.findAll();
		}
		return assetVO;
	}

	@Override
	public List<AssetVO> getAllActiveAsset(Long orgId) {
		List<AssetVO> assetVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  AssetInformation BY OrgId : {}", orgId);
			assetVO = assetRepo.getAllActiveAssetByOrgId(orgId);
		}
		return assetVO;
	}

	@Override
	public List<AssetVO> getAllAssetByCategory(Long orgId, String category) {
		List<AssetVO> assetVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId) && ObjectUtils.isNotEmpty(category)) {
			LOGGER.info("Successfully Received  Asset Information BY Category : {}", category);
			assetVO = assetRepo.getAllAssetByOrgIdAndCategory(orgId, category);
		} else {
			LOGGER.info("Successfully Received  AssetInformation For All OrgId.");
			assetVO = assetRepo.findAll();
		}
		return assetVO;
	}

	@Override
	public Optional<AssetVO> getAssetById(Long id) {
		return assetRepo.findById(id);
	}

	@Override
	public AssetVO createAsset(AssetVO assetVO) throws ApplicationException {
		if (assetRepo.existsByAssetNameAndOrgId(assetVO.getAssetName(), assetVO.getOrgId())) {
			throw new ApplicationException("Asset Name Already exists.");
		}
		if (assetRepo.existsByAssetCodeIdAndOrgId(assetVO.getAssetCodeId(), assetVO.getOrgId())) {
			throw new ApplicationException("Asset Code Id Already exists.");
		}

		List<AssetItemVO> assetItemVO = new ArrayList<>();
		long skuId = assetVO.getSkuFrom();
		while (skuId <= assetVO.getSkuTo()) {
			AssetItemVO assetItem = new AssetItemVO();
			assetItem.setAssetVO(assetVO);
			assetItem.setAssetName(assetVO.getAssetName());
			assetItem.setSkuId(new StringBuilder(assetVO.getAssetCodeId()).append("-").append(skuId).toString());
			assetItem.setStatus(MasterConstant.ASSET_ITEM_STATUS_INSTOCK);
			assetItemVO.add(assetItem);
			skuId++;
		}
		assetVO.setAssetItemVO(assetItemVO);

		AssetCategoryVO assetCategoryVO = assetCategoryRepo.findByCategoryAndAssetTypeAndOrgId(assetVO.getCategory(),
				assetVO.getAssetType(), assetVO.getOrgId());
		assetCategoryVO.setEflag(true);
		assetCategoryRepo.save(assetCategoryVO);
		return assetRepo.save(assetVO);
	}

	@Override
	public AssetVO updateAsset(AssetDTO assetDTO) throws ApplicationException {
		// Retrieve existing AssetVO by id
		AssetVO existingAsset = assetRepo.findById(assetDTO.getId()).get();

		// Check if assetName is being updated
		if (!existingAsset.getAssetName().equals(assetDTO.getAssetName())) {
			// Check if there's already an entry with the same assetName, category, and
			// orgId
			if (assetRepo.existsByAssetNameAndOrgId(assetDTO.getAssetName(), existingAsset.getOrgId())) {
				throw new ApplicationException("Asset Name Already exists. ");
			}
			// Update assetName if there's no duplicate
			existingAsset.setAssetName(assetDTO.getAssetName());
		}

		// Check if assetCodeId is being updated
		if (!existingAsset.getAssetCodeId().equals(assetDTO.getAssetCodeId())) {
			// Check if there's already an entry with the same assetCodeId, category, and
			// orgId
			if (assetRepo.existsByAssetCodeIdAndOrgId(assetDTO.getAssetCodeId(), existingAsset.getOrgId())) {
				throw new ApplicationException("Asset Code Id Already exists.");
			}
			// Update assetCodeId if there's no duplicate
			existingAsset.setAssetCodeId(assetDTO.getAssetCodeId());
		}

		// Update other fields
		existingAsset.setOrgId(assetDTO.getOrgId());
		existingAsset.setCategory(assetDTO.getCategory());
		existingAsset.setCategoryCode(assetDTO.getCategoryCode());
		existingAsset.setLength(assetDTO.getLength());
		existingAsset.setBreath(assetDTO.getBreadth());
		existingAsset.setBelongsTo(assetDTO.getBelongsTo());
		existingAsset.setMaterialIdentification(assetDTO.getMaterialIdentification());
		existingAsset.setManufacturePartCode(assetDTO.getManufacturePartCode());
		existingAsset.setDesign(assetDTO.getDesign());
		existingAsset.setHeight(assetDTO.getHeight());
		existingAsset.setWeight(assetDTO.getWeight());
		existingAsset.setQuantity(assetDTO.getQuantity());
		existingAsset.setDimUnit(assetDTO.getDimUnit());
		existingAsset.setManufacturer(assetDTO.getManufacturer());
		existingAsset.setChargableWeight(assetDTO.getChargableWeight());
		existingAsset.setBrand(assetDTO.getBrand());
		existingAsset.setEanUpc(assetDTO.getEanUpc());
		existingAsset.setAssetType(assetDTO.getAssetType());
		existingAsset.setExpectedLife(assetDTO.getExpectedLife());
		existingAsset.setMaintanencePeriod(assetDTO.getMaintenancePeriod());
		existingAsset.setExpectedTrips(assetDTO.getExpectedTrips());
		existingAsset.setHsnCode(assetDTO.getHsnCode());
		existingAsset.setTaxRate(assetDTO.getTaxRate());
		existingAsset.setSkuFrom(assetDTO.getSkuFrom());
		existingAsset.setSkuTo(assetDTO.getSkuTo());
		existingAsset.setCostPrice(assetDTO.getCostPrice());
		existingAsset.setSellPrice(assetDTO.getSellPrice());
		existingAsset.setScrapValue(assetDTO.getScrapValue());
		existingAsset.setModifiedby(assetDTO.getModifiedBy());
		existingAsset.setCancelremarks(assetDTO.getCancelRemarks());
		existingAsset.setPoNo(assetDTO.getPoNo());
		existingAsset.setActive(assetDTO.isActive());
		existingAsset.setModifiedby(assetDTO.getModifiedBy());
		return assetRepo.save(existingAsset);
	}

	@Override
	public void deleteAsset(Long id) {
		assetRepo.deleteById(id);

	}

	@Override
	public Map<String, Object> getAllAssetGroup(Long orgId, String assetCategory, String assetName, String assetCodeId,
			String manufacturer) {
		Map<String, Object> assetGroup = new HashMap<>();
		List<AssetCategoryVO> assetGroupVO = assetCategoryRepo.findAll(new Specification<AssetCategoryVO>() {
			@Override
			public Predicate toPredicate(Root<AssetCategoryVO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(orgId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
				}
				if (StringUtils.isNotBlank(assetCategory)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("assetType"), assetCategory)));
				}
				if (StringUtils.isNotBlank(assetName)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("category"), assetName)));
				}
				if (StringUtils.isNotBlank(assetCodeId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("categoryCode"), assetCodeId)));
					assetGroup.put("skuLatestCount", assetRepo.getLatestSkuByAssetCodeId(assetCodeId));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		List<Tuple> tuple = findManufacturerProductsByCriteria(orgId, assetCategory, assetName, assetCodeId,
				manufacturer);
		assetGroup.put("brand", tuple.stream().map(t -> t.get(0, ManufacturerProductVO.class).getBrand()).distinct()
				.collect(Collectors.toList()));
		assetGroup.put("company", tuple.stream().map(t -> t.get(1, ManufacturerVO.class).getCompany()).distinct()
				.collect(Collectors.toList()));
		assetGroup.put("assetGroupVO", assetGroupVO);
		assetGroup.put("assetType",
				assetGroupVO.stream().map(AssetCategoryVO::getAssetType).distinct().collect(Collectors.toList()));
		assetGroup.put("category",
				assetGroupVO.stream().map(AssetCategoryVO::getCategory).distinct().collect(Collectors.toList()));
		assetGroup.put("categoryCode",
				assetGroupVO.stream().map(AssetCategoryVO::getCategoryCode).distinct().collect(Collectors.toList()));
		return assetGroup;
	}

	private List<Tuple> findManufacturerProductsByCriteria(Long orgId, String assetCategory, String assetName,
			String assetCodeId, String manufacturer) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<ManufacturerProductVO> root = cq.from(ManufacturerProductVO.class);
		Join<ManufacturerProductVO, ManufacturerVO> manufacturerJoin = root.join("manufacturerVO");
		List<Predicate> predicates = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			predicates.add(cb.equal(root.get("orgId"), orgId));
		}
		if (StringUtils.isNotBlank(assetCategory)) {
			predicates.add(cb.equal(root.get("assetCategory"), assetCategory));
		}
		if (StringUtils.isNotBlank(assetName)) {
			predicates.add(cb.equal(root.get("assetName"), assetName));
		}
		if (StringUtils.isNotBlank(assetCodeId)) {
			predicates.add(cb.equal(root.get("assetCodeId"), assetCodeId));
		}
		if (StringUtils.isNotBlank(manufacturer)) {
			predicates.add(cb.equal(manufacturerJoin.get("company"), manufacturer));
		}
		cq.multiselect(root, manufacturerJoin);
		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Optional<AssetCategoryVO> getAssetCategoryById(Long id) {
		return assetCategoryRepo.findById(id);
	}

	@Override
	public AssetCategoryVO createAssetCategory(AssetCategoryVO assetGroupVO) throws ApplicationException {
		if (ObjectUtils.isNotEmpty(assetGroupVO) && StringUtils.isNotBlank(assetGroupVO.getCategoryCode())) {
			if (assetCategoryRepo.existsByCategoryAndOrgId(assetGroupVO.getCategory(), assetGroupVO.getOrgId())) {
				throw new ApplicationException("Category Already exists for this Organization.");
			}
			if (assetCategoryRepo.existsByCategoryCodeAndOrgId(assetGroupVO.getCategoryCode(),
					assetGroupVO.getOrgId())) {
				throw new ApplicationException("Category Code Already exists for this  Organization.");
			}

			AssetTypeVO assetTypeVO = assetTypeRepo.findByOrgIdAndAssetType(assetGroupVO.getOrgId(),
					assetGroupVO.getAssetType());
			assetTypeVO.setEflag(true);
			assetTypeRepo.save(assetTypeVO);
			return assetCategoryRepo.save(assetGroupVO);
		} else {
			throw new ApplicationException("Invalid AssetGoup Information.");
		}
	}

	@Override
	public AssetCategoryVO updateAssetCategory(AssetCategoryDTO assetCategoryDTO) throws ApplicationException {
		// Retrieve existing AssetCategoryVO by id
		AssetCategoryVO existingAssetCategory = assetCategoryRepo.findById(assetCategoryDTO.getId()).get();
		// Check if category is being updated
		if (!existingAssetCategory.getCategory().equals(assetCategoryDTO.getCategory())) {
			// Check if there's already an entry with the same category, assetType, and
			// orgId
			if (assetCategoryRepo.existsByCategoryAndOrgId(assetCategoryDTO.getCategory(),
					existingAssetCategory.getOrgId())) {
				throw new ApplicationException("Category Already exists for this Organization.");
			}
			// Update category if there's no duplicate
			existingAssetCategory.setCategory(assetCategoryDTO.getCategory());
		}

		// Check if categoryCode is being updated
		if (!existingAssetCategory.getCategoryCode().equals(assetCategoryDTO.getCategoryCode())) {
			// Check if there's already an entry with the same categoryCode, assetType, and
			// orgId
			if (assetCategoryRepo.existsByCategoryCodeAndOrgId(assetCategoryDTO.getCategoryCode(),
					existingAssetCategory.getOrgId())) {
				throw new ApplicationException("Category Code Already exists for this  Organization.");
			}
			// Update categoryCode if there's no duplicate
			existingAssetCategory.setCategoryCode(assetCategoryDTO.getCategoryCode());
		}
		if (!existingAssetCategory.getAssetType().equals(assetCategoryDTO.getAssetType())) {
			// Check if there's already an entry with the same category, categoryCode, and
			// orgId
			if (assetCategoryRepo.existsByAssetTypeAndCategoryAndCategoryCodeAndOrgId(assetCategoryDTO.getAssetType(),
					existingAssetCategory.getCategory(), existingAssetCategory.getCategoryCode(),
					existingAssetCategory.getOrgId())) {
				throw new ApplicationException("Asset Type Already exists for this Category and Category Code.");
			}
			// Update assetType if there's no duplicate
			existingAssetCategory.setAssetType(assetCategoryDTO.getAssetType());
		}

		// Update other fields
		existingAssetCategory.setActive(assetCategoryDTO.isActive());
		existingAssetCategory.setModifiedby(assetCategoryDTO.getModifiedBy());

		// Save the updated AssetCategoryVO
		return assetCategoryRepo.save(existingAssetCategory);
	}

	@Override
	public List<CustomersVO> getAllActiveCustomers(Long orgId) {
		List<CustomersVO> customersVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  CustomerInformation BY OrgId : {}", orgId);
			customersVO = customersRepo.getAllActiveCustomersByOrgId(orgId);
		}
		return customersVO;
	}

	@Override
	public List<CustomersVO> getAllCustomers(Long orgId) {
		List<CustomersVO> customersVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  CustomerInformation BY OrgId : {}", orgId);
			customersVO = customersRepo.getAllCustomersByOrgId(orgId);
		}
		return customersVO;
	}

	@Override
	public CustomersVO getCustomersById(Long id) throws ApplicationException {
		CustomersVO customersVO = customersRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Customer not found."));
		return customersVO;
	}

	@Override
	public List<CustomersAddressVO> getCustomerAddressByCustomerId(Long customerId) {
		List<CustomersAddressVO> customersAddressVO = new ArrayList<>();

		customersAddressVO = customersAddressRepo.getCustomerAddressByCustomerId(customerId);

		return customersAddressVO;
	}

	@Override
	public CustomersVO createCustomers(CustomersDTO customersDTO) {

		if (customersRepo.existsByEntityLegalNameAndDisplayNameAndOrgId(customersDTO.getEntityLegalName(),
				customersDTO.getDisplayName(), customersDTO.getOrgId())) {
			throw new RuntimeException("The Customer LegalName or DisplayName already exists");
		}
		CustomersVO customersVO = new CustomersVO();
		customersVO.setOrgId(customersDTO.getOrgId());
		customersVO.setCustomerType(customersDTO.getCustomerType());
		customersVO.setEntityLegalName(customersDTO.getEntityLegalName());
		customersVO.setEmail(customersDTO.getEmail());
		customersVO.setCreatedBy(customersDTO.getCreatedBy());
		customersVO.setModifiedBy(customersDTO.getCreatedBy());
		String customerCode = null;
		if (0 == customersDTO.getCustomerType()) {
			int custseq = customersRepo.getCustomerCodeSeq();
			customerCode = "EM" + custseq;
			customersRepo.nextCustomerCode();
		} else {
			int recseq = customersRepo.getRecCodeSeq();
			customerCode = "RC" + recseq;
			customersRepo.nextRecCode();
		}
		customersVO.setCustomerCode(customerCode);
		customersVO.setDisplayName(customersDTO.getDisplayName());
		customersVO.setPhoneNumber(customersDTO.getPhoneNumber());
		customersVO.setCustomerActivatePortal(customersDTO.isCustomerActivatePortal());
		customersVO.setActive(customersDTO.isActive());
//		getCustomersVOFromCustomersDTO(customersDTO, customersVO);

		List<CustomersAddressVO> customersAddressVO = new ArrayList<>();
		if (customersDTO.getCustomerAddressDTO() != null) {
			for (CustomersAddressDTO addressDTO : customersDTO.getCustomerAddressDTO()) {
				CustomersAddressVO custAddress = new CustomersAddressVO();
				custAddress.setGstRegistrationStatus(addressDTO.getGstRegistrationStatus());
				custAddress.setStreet1(addressDTO.getStreet1());
				custAddress.setStreet2(addressDTO.getStreet2());
				custAddress.setPinCode(addressDTO.getPinCode());
				custAddress.setPhoneNumber(addressDTO.getPhoneNumber());
				custAddress.setGstNumber(addressDTO.getGstNumber());
				custAddress.setCity(addressDTO.getCity());
				custAddress.setContactName(addressDTO.getContactName());
				custAddress.setState(addressDTO.getState());
				custAddress.setEmail(addressDTO.getEmail());
				custAddress.setDesignation(addressDTO.getDesignation());
				custAddress.setCustomersVO(customersVO);
				custAddress.setCountry(addressDTO.getCountry());

				customersAddressVO.add(custAddress);
			}
		}
		customersVO.setCustomersAddressVO(customersAddressVO);

		List<CustomersBankDetailsVO> customersBankDetailsVO = new ArrayList<>();
		if (customersDTO.getCustomerBankDetailsDTO() != null) {
			for (CustomersBankDetailsDTO bankDetailsDTO : customersDTO.getCustomerBankDetailsDTO()) {
				CustomersBankDetailsVO bankdetails = new CustomersBankDetailsVO();

				bankdetails.setBank(bankDetailsDTO.getBank());
				bankdetails.setAccountName(bankDetailsDTO.getAccountName());
				bankdetails.setIfscCode(bankDetailsDTO.getIfscCode());
				bankdetails.setBranch(bankDetailsDTO.getBranch());
				bankdetails.setAccountNo(bankDetailsDTO.getAccountNo());
				bankdetails.setCustomersVO(customersVO);

				customersBankDetailsVO.add(bankdetails);
			}
		}
		customersVO.setCustomersBankDetailsVO(customersBankDetailsVO);

		return customersRepo.save(customersVO);

	}

	@Override
	public CustomersVO updateCustomers(CustomersDTO customersDTO) throws ApplicationException {
		CustomersVO customersVO = new CustomersVO();
		if (ObjectUtils.isNotEmpty(customersDTO.getId())) {
			customersVO = customersRepo.findById(customersDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Customer details"));
		}

		getCustomerVOFromCustomerDTO(customersDTO, customersVO);

		// Update customer details excluding customer type and customer code
		// Update or add new address details
		List<CustomersAddressVO> customersAddressVO = new ArrayList<>();
		if (customersDTO.getCustomerAddressDTO() != null) {
			for (CustomersAddressDTO addressDTO : customersDTO.getCustomerAddressDTO()) {
				if (addressDTO.getId() != 0) {
					CustomersAddressVO custAddress = customersAddressRepo.findById(addressDTO.getId()).orElseThrow(
							() -> new ApplicationException("Address details not found for ID: " + addressDTO.getId()));
					custAddress.setGstRegistrationStatus(addressDTO.getGstRegistrationStatus());
					custAddress.setStreet1(addressDTO.getStreet1());
					custAddress.setStreet2(addressDTO.getStreet2());
					custAddress.setPinCode(addressDTO.getPinCode());
					custAddress.setPhoneNumber(addressDTO.getPhoneNumber());
					custAddress.setGstNumber(addressDTO.getGstNumber());
					custAddress.setCity(addressDTO.getCity());
					custAddress.setContactName(addressDTO.getContactName());
					custAddress.setState(addressDTO.getState());
					custAddress.setEmail(addressDTO.getEmail());
					custAddress.setDesignation(addressDTO.getDesignation());
					custAddress.setCountry(addressDTO.getCountry());
					custAddress.setCustomersVO(customersVO);
					customersAddressVO.add(custAddress);
				} else {
					CustomersAddressVO custAddress = new CustomersAddressVO();
					custAddress.setGstRegistrationStatus(addressDTO.getGstRegistrationStatus());
					custAddress.setStreet1(addressDTO.getStreet1());
					custAddress.setStreet2(addressDTO.getStreet2());
					custAddress.setPinCode(addressDTO.getPinCode());
					custAddress.setPhoneNumber(addressDTO.getPhoneNumber());
					custAddress.setGstNumber(addressDTO.getGstNumber());
					custAddress.setCity(addressDTO.getCity());
					custAddress.setContactName(addressDTO.getContactName());
					custAddress.setState(addressDTO.getState());
					custAddress.setEmail(addressDTO.getEmail());
					custAddress.setDesignation(addressDTO.getDesignation());
					custAddress.setCountry(addressDTO.getCountry());
					custAddress.setCustomersVO(customersVO);
					customersAddressVO.add(custAddress);
				}
			}
		}
		customersVO.setCustomersAddressVO(customersAddressVO);

		// Update or add new bank details
		List<CustomersBankDetailsVO> customersBankDetailsVO = new ArrayList<>();
		if (customersDTO.getCustomerBankDetailsDTO() != null) {
			for (CustomersBankDetailsDTO bankDetailsDTO : customersDTO.getCustomerBankDetailsDTO()) {
				if (bankDetailsDTO.getId() != 0) {
					CustomersBankDetailsVO bankdetails = customersBankDetailsRepo.findById(bankDetailsDTO.getId())
							.orElseThrow(() -> new ApplicationException(
									"Bank details not found for ID: " + bankDetailsDTO.getId()));
					bankdetails.setBank(bankDetailsDTO.getBank());
					bankdetails.setAccountName(bankDetailsDTO.getAccountName());
					bankdetails.setIfscCode(bankDetailsDTO.getIfscCode());
					bankdetails.setBranch(bankDetailsDTO.getBranch());
					bankdetails.setAccountNo(bankDetailsDTO.getAccountNo());
					bankdetails.setCustomersVO(customersVO);
					customersBankDetailsVO.add(bankdetails);
				} else {
					CustomersBankDetailsVO bankdetails = new CustomersBankDetailsVO();
					bankdetails.setBank(bankDetailsDTO.getBank());
					bankdetails.setAccountName(bankDetailsDTO.getAccountName());
					bankdetails.setIfscCode(bankDetailsDTO.getIfscCode());
					bankdetails.setBranch(bankDetailsDTO.getBranch());
					bankdetails.setAccountNo(bankDetailsDTO.getAccountNo());
					bankdetails.setCustomersVO(customersVO);
					customersBankDetailsVO.add(bankdetails);
				}
			}
		}
		customersVO.setCustomersBankDetailsVO(customersBankDetailsVO);
		return customersRepo.save(customersVO);
	}

	private void getCustomerVOFromCustomerDTO(CustomersDTO customersDTO, CustomersVO customersVO)
			throws ApplicationException {
		CustomersVO existingCustomer = customersRepo.findById(customersDTO.getId())
				.orElseThrow(() -> new ApplicationException("Customer with ID " + customersDTO.getId() + " not found"));

		if (!existingCustomer.getEntityLegalName().equals(customersDTO.getEntityLegalName())) {
			// Check if there's already an entry with the same Entity Name and orgId
			if (customersRepo.existsByEntityLegalNameAndOrgId(customersDTO.getEntityLegalName(),
					existingCustomer.getOrgId())) {
				throw new ApplicationException("Entity Legal Name Already exists");
			}
			// Update Entity Name if there's no duplicate
			customersVO.setEntityLegalName(customersDTO.getEntityLegalName());
		}

		if (!existingCustomer.getDisplayName().equals(customersDTO.getDisplayName())) {
			// Check if there's already an entry with the same Display Name and orgId
			if (customersRepo.existsByDisplayNameAndOrgId(customersDTO.getDisplayName(), existingCustomer.getOrgId())) {
				throw new ApplicationException("Display Name Already exists");
			}
			// Update Display Name if there's no duplicate
			customersVO.setDisplayName(customersDTO.getDisplayName());
		}

		customersVO.setOrgId(customersDTO.getOrgId());
		customersVO.setEmail(customersDTO.getEmail());
		customersVO.setPhoneNumber(customersDTO.getPhoneNumber());
		customersVO.setCreatedBy(customersDTO.getCreatedBy());
		customersVO.setModifiedBy(customersDTO.getCreatedBy());
		customersVO.setCustomerActivatePortal(customersDTO.isCustomerActivatePortal());

		customersVO.setActive(customersDTO.isActive());
	}

	@Override
	public void deleteCustomers(Long id) {
		customersRepo.deleteById(id);
	}

	@Override
	public List<FlowVO> getAllFlow(Long orgId, Long emitterId) {
		List<FlowVO> flowVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isEmpty(emitterId))) {
			LOGGER.info("Successfully Received Flow BY OrgId : {}", orgId);
			flowVO = flowRepo.findByOrgId(orgId);
		} else if (ObjectUtils.isEmpty(orgId) && (ObjectUtils.isNotEmpty(emitterId))) {
			LOGGER.info("Successfully Received Flow BY EmitterId : {}", emitterId);
			flowVO = flowRepo.findByEmitterId(emitterId);
		} else if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isNotEmpty(emitterId))) {
			LOGGER.info("Successfully Received Flow BY EmitterId : {} orgId : {}", emitterId, orgId);
			flowVO = flowRepo.findByOrgIdAndEmitterId(orgId, emitterId);
		}
		return flowVO;
	}

	@Override
	public List<FlowVO> getAllActiveFlow(Long orgId, Long emitterId) {
		List<FlowVO> flowVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isEmpty(emitterId))) {
			LOGGER.info("Successfully Received Flow BY OrgId : {}", orgId);
			flowVO = flowRepo.getActiveAllFlow(orgId);
		} else if (ObjectUtils.isEmpty(orgId) && (ObjectUtils.isNotEmpty(emitterId))) {
			LOGGER.info("Successfully Received Flow BY EmitterId : {}", emitterId);
			flowVO = flowRepo.findActiveByEmitterId(emitterId);
		} else if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isNotEmpty(emitterId))) {
			LOGGER.info("Successfully Received Flow BY EmitterId : {} orgId : {}", emitterId, orgId);
			flowVO = flowRepo.findActiveByOrgIdAndEmitterId(orgId, emitterId);
		}
		return flowVO;
	}

	@Override
	public List<FlowVO> getAllReceiverActiveFlow(Long orgId, Long receiverId) {
		List<FlowVO> flowVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isEmpty(receiverId))) {
			LOGGER.info("Successfully Received Flow BY OrgId : {}", orgId);
			flowVO = flowRepo.getActiveAllFlow(orgId);
		} else if (ObjectUtils.isEmpty(orgId) && (ObjectUtils.isNotEmpty(receiverId))) {
			LOGGER.info("Successfully Received Flow BY ReceiverId : {}", receiverId);
			flowVO = flowRepo.findActiveByReceiverId(receiverId);
		} else if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isNotEmpty(receiverId))) {
			LOGGER.info("Successfully Received Flow BY ReceiverId : {} orgId : {}", receiverId, orgId);
			flowVO = flowRepo.findActiveByOrgIdAndReceiverId(orgId, receiverId);
		}
		return flowVO;
	}

	@Override
	public Optional<FlowVO> getFlowById(long id) {
		return flowRepo.findById(id);
	}

	@Override
	public FlowVO createFlow(FlowDTO flowDTO) throws ApplicationException {

		FlowVO flowVO = createFlowVOByFlowDTO(flowDTO);
		flowVO.setCreatedBy(flowDTO.getCreatedBy());
		flowVO.setModifiedBy(flowDTO.getCreatedBy());
		flowVO.setActive(flowDTO.isActive());
		flowVO.setEmitter(flowRepo.findEmiterbyId(flowVO.getEmitterId()));
		flowVO.setWarehouseLocation(flowRepo.getWarehouseLocationByLocationId(flowDTO.getWarehouseId()));
		flowVO.setRetrievalWarehouseLocation(
				flowRepo.getWarehouseLocationByLocationId(flowDTO.getRetrievalWarehouseId()));
		flowVO.setReceiver(flowRepo.getReceiverByReceiverId(flowDTO.getReceiverId()));

		return flowRepo.save(flowVO);
	}

	private FlowVO createFlowVOByFlowDTO(FlowDTO flowDTO) throws ApplicationException {
		if (flowRepo.existsByFlowNameAndOrgId(flowDTO.getFlowName(), flowDTO.getOrgId())) {
			throw new ApplicationException("Flow Name Already exists.");
		}
		WarehouseVO warehouseVO = warehouseRepo.findById(flowDTO.getWarehouseId())
				.orElseThrow(() -> new ApplicationException("Warehouse not found"));
		warehouseVO.setEflag(true);
		warehouseRepo.save(warehouseVO);

		WarehouseVO warehouseVO1 = warehouseRepo.findById(flowDTO.getRetrievalWarehouseId())
				.orElseThrow(() -> new ApplicationException("Warehouse not found"));
		warehouseVO1.setEflag(true);
		warehouseRepo.save(warehouseVO1);

		List<FlowDetailVO> flowDetailVOList = new ArrayList<>();
		FlowVO flowVO = FlowVO.builder().orgin(flowDTO.getOrgin())
				.retrievalWarehouseId(flowDTO.getRetrievalWarehouseId())
				.retrievalWarehouseLocation(flowDTO.getRetrievalWarehouseLocation())
				.warehouseLocation(flowDTO.getWarehouseLocation()).flowName(flowDTO.getFlowName())
				.receiverId(flowDTO.getReceiverId()).emitterId(flowDTO.getEmitterId()).emitter(flowDTO.getEmitter())
				.createdBy(flowDTO.getCreatedBy()).destination(flowDTO.getDestination()).orgId(flowDTO.getOrgId())
				.warehouseId(flowDTO.getWarehouseId()).modifiedBy(flowDTO.getCreatedBy()).flowDetailVO(flowDetailVOList)
				.build();

		flowDetailVOList = flowDTO.getFlowDetailDTO().stream().map(fdDTO -> {
			KitVO kitVO = kitRepo.findAllByKitNoAndOrgId(fdDTO.getKitNo(), fdDTO.getOrgId());
			kitVO.setEflag(true);
			kitRepo.save(kitVO);
			BasicDetailVO basicDetailVO = basicDetailRepo.findByPartNumberAndOrgId(fdDTO.getPartNumber(),
					fdDTO.getOrgId());
			basicDetailVO.setEflag(true);
			basicDetailRepo.save(basicDetailVO);
			return FlowDetailVO.builder().cycleTime(fdDTO.getCycleTime()).emitterId(flowDTO.getEmitterId())
					.orgId(flowDTO.getOrgId()).partName(fdDTO.getPartName()).kitDesc(fdDTO.getKitDesc())
					.kitNo(fdDTO.getKitNo()).partNumber(fdDTO.getPartNumber())
					.partQty(kitRepo.findPartqty(fdDTO.getKitNo()))
					.emitter(flowRepo.findEmiterbyId(flowVO.getEmitterId())).flowVO(flowVO).build();
		}).collect(Collectors.toList());
		flowVO.setFlowDetailVO(flowDetailVOList);
		return flowVO;
	}

	@Override
	public FlowVO updateFlow(FlowDTO flowDTO) throws ApplicationException {
		FlowVO flowVO = new FlowVO();
		if (ObjectUtils.isNotEmpty(flowDTO.getId())) {
			flowVO = flowRepo.findById(flowDTO.getId())
					.orElseThrow(() -> new ApplicationException("Flow details not found"));
		}

		getFlowVOFromFlowDTO(flowDTO, flowVO);

		List<FlowDetailVO> detailVOs = flowDetailRepo.findByFlowVO(flowVO);
		flowDetailRepo.deleteAll(detailVOs);

		// Update customer details excluding customer type and customer code
		// Update or add new address details
		List<FlowDetailVO> flowDetailVOList = new ArrayList<>();
		if (flowDTO.getFlowDetailDTO() != null) {
			for (FlowDetailDTO flowDetailDTO : flowDTO.getFlowDetailDTO()) {
				FlowDetailVO flowDetailVO1 = new FlowDetailVO();
				flowDetailVO1.setCycleTime(flowDetailDTO.getCycleTime());
				flowDetailVO1.setEmitterId(flowDTO.getEmitterId());
				flowDetailVO1.setOrgId(flowDetailDTO.getOrgId());
				flowDetailVO1.setPartName(flowDetailDTO.getPartName());
				flowDetailVO1.setKitDesc(flowDetailDTO.getKitDesc());
				flowDetailVO1.setKitNo(flowDetailDTO.getKitNo());
				flowDetailVO1.setPartNumber(flowDetailDTO.getPartNumber());
				flowDetailVO1.setPartQty(kitRepo.findPartqty(flowDetailDTO.getKitNo()));
				flowDetailVO1.setEmitter(flowRepo.findEmiterbyId(flowDTO.getEmitterId()));
				flowDetailVO1.setFlowVO(flowVO);
				flowDetailVOList.add(flowDetailVO1);

			}
		}
		flowVO.setFlowDetailVO(flowDetailVOList);
		return flowRepo.save(flowVO);
	}

	private void getFlowVOFromFlowDTO(FlowDTO flowDTO, FlowVO flowVO) throws ApplicationException {
		FlowVO existingFlow = flowRepo.findById(flowDTO.getId())
				.orElseThrow(() -> new ApplicationException("Flow with ID " + flowDTO.getId() + " not found"));

		if (!existingFlow.getFlowName().equals(flowDTO.getFlowName())) {
			// Check if there's already an entry with the same Flow Name and orgId
			if (flowRepo.existsByFlowNameAndOrgId(flowDTO.getFlowName(), existingFlow.getOrgId())) {
				throw new ApplicationException("Flow Name Already exists");
			}
			// Update Flow Name if there's no duplicate
			flowVO.setFlowName(flowDTO.getFlowName());
		}

		// Update other fields
		flowVO.setActive(flowDTO.isActive());
		flowVO.setOrgin(flowDTO.getOrgin());
		flowVO.setRetrievalWarehouseId(flowDTO.getRetrievalWarehouseId());
		flowVO.setWarehouseLocation(flowDTO.getWarehouseLocation());
		flowVO.setReceiverId(flowDTO.getReceiverId());
		flowVO.setEmitterId(flowDTO.getEmitterId());
		flowVO.setModifiedBy(flowDTO.getCreatedBy());
		flowVO.setEmitter(flowRepo.findEmiterbyId(flowDTO.getEmitterId()));
		flowVO.setDestination(flowDTO.getDestination());
		flowVO.setOrgId(flowDTO.getOrgId());
		flowVO.setWarehouseId(flowDTO.getWarehouseId());
		flowVO.setWarehouseLocation(flowRepo.getWarehouseLocationByLocationId(flowDTO.getWarehouseId()));
		flowVO.setRetrievalWarehouseLocation(
				flowRepo.getWarehouseLocationByLocationId(flowDTO.getRetrievalWarehouseId()));
		flowVO.setReceiver(flowRepo.getReceiverByReceiverId(flowDTO.getReceiverId()));

	}

	@Override
	public void deleteFlow(long id) {
		flowRepo.deleteById(id);

	}

	@Override
	public List<ManufacturerVO> getAllManufacturer(Long orgId) {
		List<ManufacturerVO> manufacturerVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ManufacturerInformation BY OrgId : {}", orgId);
			manufacturerVO = manufacturerRepo.getAllManufacturerByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  VenderInformation For All OrgId.");
			manufacturerVO = manufacturerRepo.findAll();
		}
		return manufacturerVO;
	}

	@Override
	public Optional<ManufacturerVO> getManufacturerById(int id) {
		return manufacturerRepo.findById(id);
	}

	@Override
	public ManufacturerVO createManufacturer(ManufacturerVO manufacturerVO) {
		return manufacturerRepo.save(manufacturerVO);
	}

	@Override
	public Optional<ManufacturerVO> updateManufacturer(ManufacturerVO manufacturerVO) {
		if (manufacturerRepo.existsById(manufacturerVO.getId())) {
			return Optional.of(manufacturerRepo.save(manufacturerVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteManufacturer(int id) {
		manufacturerRepo.deleteById(id);

	}

	@Override
	public List<ManufacturerProductVO> getAllManufacturerProduct(Long orgId) {
		List<ManufacturerProductVO> manufacturerProductVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received   Manufacturer Product Information BY OrgId : {}", orgId);
			manufacturerProductVO = manufacturerProductRepo.getAllManufacturerProduct(orgId);
		} else {
			LOGGER.info("Successfully Received  Manufacturer Product Information For All OrgId.");
			manufacturerProductVO = manufacturerProductRepo.findAll();
		}
		return manufacturerProductVO;
	}

	@Override
	public ManufacturerProductVO createManufacturerProduct(ManufacturerProductVO manufacturerProductVO) {
		return manufacturerProductRepo.save(manufacturerProductVO);
	}

	@Override
	public List<AssetTypeVO> getAllAssetType(Long orgId) {
		List<AssetTypeVO> assetCategoryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Asset Type BY OrgId : {}", orgId);
			assetCategoryVO = assetTypeRepo.findByOrgId(orgId);
		}
		return assetCategoryVO;
	}

	@Override
	public AssetTypeVO createAssetType(AssetTypeVO assetCategoryVO) {
		if (assetTypeRepo.existsByAssetTypeAndOrgId(assetCategoryVO.getAssetType().toUpperCase(),
				assetCategoryVO.getOrgId())) {
			throw new RuntimeException("Asset Type already exists for this organization");
		}
		if (assetTypeRepo.existsByTypeCodeAndOrgId(assetCategoryVO.getTypeCode().toUpperCase(),
				assetCategoryVO.getOrgId())) {
			throw new RuntimeException("Asset Type Code already exists for this organization");
		}
		return assetTypeRepo.save(assetCategoryVO);
	}

	@Override
	public AssetTypeVO updateAssetType(AssetTypeDTO assetTypeDTO) throws ApplicationException {
		// Retrieve existing AssetTypeVO by id
		AssetTypeVO existingAssetType = assetTypeRepo.findById(assetTypeDTO.getId()).get();

		// Check if assetType is being updated
		if (!existingAssetType.getAssetType().equals(assetTypeDTO.getAssetType())) {
			// Check if there's already an entry with the same assetType and orgId
			if (assetTypeRepo.existsByAssetTypeAndOrgId(assetTypeDTO.getAssetType(), existingAssetType.getOrgId())) {
				throw new ApplicationException("Asset Type Already exists for this Organization.");
			}
			// Update assetType if there's no duplicate
			existingAssetType.setAssetType(assetTypeDTO.getAssetType());
		}

		// Check if typeCode is being updated
		if (!existingAssetType.getTypeCode().equals(assetTypeDTO.getTypeCode())) {
			// Check if there's already an entry with the same typeCode and orgId
			if (assetTypeRepo.existsByTypeCodeAndOrgId(assetTypeDTO.getTypeCode(), existingAssetType.getOrgId())) {
				throw new ApplicationException("Type Code Already exists for this Organization.");
			}
			// Update typeCode if there's no duplicate
			existingAssetType.setTypeCode(assetTypeDTO.getTypeCode());
		}

		// Update other fields
		existingAssetType.setActive(assetTypeDTO.isActive());
		existingAssetType.setModifiedby(assetTypeDTO.getModifiedby());

		// Save the updated AssetTypeVO
		return assetTypeRepo.save(existingAssetType);
	}

	// Unit

	@Override
	public List<UnitVO> getAllUnit(Long orgId) {

		return unitRepo.findAllUnitByOrgId(orgId);
	}

	@Override
	public Optional<UnitVO> getUnitById(Long id) {
		return unitRepo.findById(id);
	}

	@Override
	public UnitVO createUnit(UnitVO unitVO) throws ApplicationException {

		if (unitRepo.existsByUnitAndOrgId(unitVO.getUnit(), unitVO.getOrgId())) {
			throw new ApplicationException("A Unit already exists for this Organization");
		}
		return unitRepo.save(unitVO);
	}

	@Override
	public UnitVO updateUnit(UnitDTO unitVO) throws ApplicationException {

		UnitVO unitVO2 = unitRepo.findById(unitVO.getId()).get();
		if (unitRepo.existsById(unitVO.getId())) {
			if (!unitVO2.getUnit().equals(unitVO.getUnit())) {
				if (unitRepo.existsByUnitAndOrgId(unitVO.getUnit(), unitVO.getOrgId())) {
					throw new ApplicationException("A Unit already exists for this Organization");
				}
				unitVO2.setUnit(unitVO.getUnit());
			}
			unitVO2.setUpdatedBy(unitVO.getUpdatedBy());
			unitVO2.setActive(unitVO.isActive());
		} else {
			throw new ApplicationException("The Unit ID is Not Found");
		}

		return unitRepo.save(unitVO2);
	}

	@Override
	public void deleteUnit(Long id) {
		// TODO Auto-generated method stub
		unitRepo.deleteById(id);
	}

	@Override
	public List<AssetCategoryVO> createAssetCategoryByCSV(MultipartFile assetFile) throws ApplicationException {
		if (assetFile.isEmpty()) {
			throw new ApplicationException("Invalid CSV File.");
		}
		List<AssetCategoryVO> assetGroups = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new InputStreamReader(assetFile.getInputStream()))) {
			List<String[]> lines = reader.readAll();
			assetGroups = lines.stream().skip(1) // Skip header
					.map(this::createAssetGroupFromCsvLine).filter(Objects::nonNull) // Filter out null values if
																						// validation fails
					.collect(Collectors.toList());
			System.out.println(lines);
		} catch (Exception e) {
			LOGGER.error(CommonConstant.ERR_MSG_FORMAT, "createAssetGroupByCSV()", e.getMessage());
			throw new ApplicationException("Failed to read CSV file.");
		}
		return assetCategoryRepo.saveAll(assetGroups);
	}

	private AssetCategoryVO createAssetGroupFromCsvLine(String[] csvLine) {
		try {
			if (StringUtils.isEmpty(csvLine[1])) {
				LOGGER.warn("Validation failed for CSV line: {}", Arrays.toString(csvLine));
				return null;
			}
			if (assetCategoryRepo.existsById(csvLine[1])) {
				return null;
			}
			return AssetCategoryVO.builder().categoryCode(csvLine[1]).category(csvLine[3]).assetType(csvLine[4])
					.active(Boolean.parseBoolean(csvLine[4])).length(Float.parseFloat(csvLine[5]))
					.breath(Float.parseFloat(csvLine[6])).height(Float.parseFloat(csvLine[7])).dimUnit(csvLine[8])
					.build();
		} catch (Exception e) {
			LOGGER.error("Error processing CSV line: {}", Arrays.toString(csvLine), e);
			return null;
		}
	}

// kit
	@Override
	public List<KitResponseDTO> getAllKit(Long orgId) {
		List<KitResponseDTO> kitResponseDTO = new ArrayList<>();
		List<KitVO> kitVO = new ArrayList<>();
		if (ObjectUtils.isEmpty(orgId)) {
			LOGGER.info("Get All kit information.", orgId);
			kitVO = kitRepo.findAll();
		} else {
			LOGGER.info("Get All kit information by orgID : {}", orgId);
			kitVO = kitRepo.findByOrgId(orgId);
		}
		kitResponseDTO = kitVO.stream().map(kit -> {
			KitResponseDTO KitResponse = new KitResponseDTO();
			KitResponse.setId(kit.getId());
			KitResponse.setDocId(kit.getDocId());
			KitResponse.setKitNo(kit.getKitNo());
			KitResponse.setKitDesc(kit.getKitDesc());
			KitResponse.setPartQty(kit.getPartQty());
			KitResponse.setOrgId(kit.getOrgId());
			KitResponse.setActive(kit.getActive());
			KitResponse.setEflag(kit.isEflag());
			Map<String, List<KitAssetVO>> kitAssetVOByCategory = kit.getKitAssetVO().stream()
					.collect(Collectors.groupingBy(KitAssetVO::getAssetCategory));
			KitResponse.setKitAssetCategory(kitAssetVOByCategory);
			return KitResponse;
		}).collect(Collectors.toList());
		return kitResponseDTO;
	}

	@Override
	public List<KitResponseDTO> getActiveAllKit(Long orgId) {
		List<KitResponseDTO> kitResponseDTO = new ArrayList<>();
		List<KitVO> kitVO = new ArrayList<>();
		if (ObjectUtils.isEmpty(orgId)) {
			LOGGER.info("Get All kit information.", orgId);
			kitVO = kitRepo.findAll();
		} else {
			LOGGER.info("Get All kit information by orgID : {}", orgId);
			kitVO = kitRepo.findActiveByOrgId(orgId);
		}
		kitResponseDTO = kitVO.stream().map(kit -> {
			KitResponseDTO KitResponse = new KitResponseDTO();
			KitResponse.setId(kit.getId());
			KitResponse.setDocId(kit.getDocId());
			KitResponse.setKitNo(kit.getKitNo());
			KitResponse.setKitDesc(kit.getKitDesc());
			KitResponse.setPartQty(kit.getPartQty());
			KitResponse.setOrgId(kit.getOrgId());
			KitResponse.setActive(kit.getActive());
			KitResponse.setEflag(kit.isEflag());
			Map<String, List<KitAssetVO>> kitAssetVOByCategory = kit.getKitAssetVO().stream()
					.collect(Collectors.groupingBy(KitAssetVO::getAssetCategory));
			KitResponse.setKitAssetCategory(kitAssetVOByCategory);
			return KitResponse;
		}).collect(Collectors.toList());
		return kitResponseDTO;
	}

	@Override
	public Optional<KitVO> getKitById(Long id) {
		return kitRepo.findById(id);
	}

	@Override
	public Optional<KitVO> getKitByKitCode(String kitName) {
		return kitRepo.findByKitNo(kitName);
	}

	@Override
	public Set<Object[]> getEmitterAndReceiverByKitNo(String kitNo) {
		return flowRepo.findEmitterAndReceiverAndFlowByKitNo(kitNo);
	}

	@Override
	public KitVO createkit(KitDTO kitDTO) throws ApplicationException {

		int finyr = kitRepo.getFinyr();
		String kit = finyr + "KT" + kitRepo.finddocid();
		List<KitAssetVO> kitAssetVO = new ArrayList<>();
		KitVO kitVO = KitVO.builder().kitDesc(kitDTO.getKitDesc()).kitNo(kitDTO.getKitNo()).docId(kit)
				.active(kitDTO.isActive()).createdBy(kitDTO.getCreatedBy()).modifiedBy(kitDTO.getCreatedBy())
				.orgId(kitDTO.getOrgId()).active(kitDTO.isActive()).partQty(kitDTO.getPartQuantity())
				.kitAssetVO(kitAssetVO).build();
		for (KitAssetDTO kitAsset : kitDTO.getKitAssetDTO()) {
			AssetVO assetVO = assetRepo.findByAssetCodeIdAndOrgId(kitAsset.getAssetCodeId(), kitDTO.getOrgId());
			assetVO.setEflag(true);
			assetRepo.save(assetVO);
			;
			kitAssetVO.add(KitAssetVO.builder().assetType(kitAsset.getAssetType()).belongsTo(kitAsset.getBelongsTo())
					.manufacturePartCode(kitAsset.getManufacturePartCode()).assetCategory(kitAsset.getAssetCategory())
					.categoryCode(kitAsset.getCategoryCode()).assetCodeId(kitAsset.getAssetCodeId())
					.assetName(kitAsset.getAssetDesc()).quantity(kitAsset.getQuantity()).kitVO(kitVO).build());
		}
		kitRepo.updatesequence();

		if (kitRepo.existsByKitNoAndOrgId(kitDTO.getKitNo().toUpperCase(), kitDTO.getOrgId())) {
			throw new ApplicationException("KitNo already exists");
		}
		if (kitRepo.existsByKitDescAndOrgId(kitDTO.getKitDesc().toUpperCase(), kitDTO.getOrgId())) {
			throw new ApplicationException("KitDesc already exists");
		}

		return kitRepo.save(kitVO);
	}

	@Override
	public KitVO updatedKit(KitDTO kitDTO) throws ApplicationException {

		KitVO kitVO = new KitVO();
		if (kitDTO.getId() != null && !kitDTO.getKitNo().isEmpty()) {
			kitVO = kitRepo.findById(kitDTO.getId()).orElseThrow(() -> new ApplicationException("Invalid Kit details"));
		}

		// Update kit details
		getKitVOFromKitDTO(kitDTO, kitVO);
		List<KitAssetVO> assetVOs = kitAssetRepo.findByKitVO(kitVO);
		kitAssetRepo.deleteAll(assetVOs);

		// Update or create kit asset details
		List<KitAssetVO> kitAssetVOList = new ArrayList<>();
		if (kitDTO.getKitAssetDTO() != null) {
			for (KitAssetDTO kitAssetDTO : kitDTO.getKitAssetDTO()) {
				KitAssetVO kitAssetVO = new KitAssetVO();
				kitAssetVO.setAssetType(kitAssetDTO.getAssetType());
				kitAssetVO.setBelongsTo(kitAssetDTO.getBelongsTo());
				kitAssetVO.setManufacturePartCode(kitAssetDTO.getManufacturePartCode());
				kitAssetVO.setAssetCategory(kitAssetDTO.getAssetCategory());
				kitAssetVO.setCategoryCode(kitAssetDTO.getCategoryCode());
				kitAssetVO.setAssetCodeId(kitAssetDTO.getAssetCodeId());
				kitAssetVO.setAssetName(kitAssetDTO.getAssetDesc());
				kitAssetVO.setQuantity(kitAssetDTO.getQuantity());
				kitAssetVO.setKitVO(kitVO);
				kitAssetVOList.add(kitAssetVO);
			}
		}

		kitVO.setKitAssetVO(kitAssetVOList);

		return kitRepo.save(kitVO);
	}

	private void getKitVOFromKitDTO(KitDTO kitDTO, KitVO kitVO) throws ApplicationException {

		if (kitDTO.getKitNo() != null && !kitDTO.getKitNo().isEmpty()) {
			kitVO = kitRepo.findById(kitDTO.getId()).get();
			kitVO.setOrgId(kitDTO.getOrgId());
			kitVO.setKitNo(kitDTO.getKitNo());
			kitVO.setActive(kitDTO.isActive());
			kitVO.setModifiedBy(kitDTO.getCreatedBy());
			kitVO.setKitDesc(kitDTO.getKitDesc());
			kitVO.setPartQty(kitDTO.getPartQuantity());

		} else {
			if (kitRepo.existsByKitNoAndOrgId(kitDTO.getKitNo(), kitDTO.getOrgId())) {
				throw new ApplicationException("KitNo already exists");
			}
			if (kitRepo.existsByKitDescAndOrgId(kitDTO.getKitDesc(), kitDTO.getOrgId())) {
				throw new ApplicationException("KitDesc already exists");
			}
			kitVO.setOrgId(kitDTO.getOrgId());
			kitVO.setKitNo(kitDTO.getKitNo());
			kitVO.setKitDesc(kitDTO.getKitDesc());
			kitVO.setPartQty(kitDTO.getPartQuantity());
		}
	}

	@Override
	public void deleteKit(Long id) {
		kitRepo.deleteById(id);

	}

	@Override
	public Map<String, List<CustomersVO>> CustomersType(Long orgId) {
		List<CustomersVO> customersVO = customersRepo.getAllActiveCustomersByOrgId(orgId);
		Map<String, List<CustomersVO>> customers = new HashMap<>();
		List<CustomersVO> emitterCustomersVO = customersVO.stream()
				.filter(c -> c.getCustomerType() == CustomerConstant.CUSTOMER_TYPE_EMITTER
						|| c.getCustomerType() == CustomerConstant.CUSTOMER_TYPE_EMITTER_AND_RECEIVER)
				.collect(Collectors.toList());
		List<CustomersVO> receiverCustomersVO = customersVO.stream()
				.filter(c -> c.getCustomerType() == CustomerConstant.CUSTOMER_TYPE_RECEIVER
						|| c.getCustomerType() == CustomerConstant.CUSTOMER_TYPE_EMITTER_AND_RECEIVER)
				.collect(Collectors.toList());
		customers.put("emitterCustomersVO", emitterCustomersVO);
		customers.put("receiverCustomersVO", receiverCustomersVO);
		return customers;
	}

	@Override
	public Map<String, Map<String, List<AssetCategoryVO>>> getAssetCategoryAssetType(Long orgId) {
		List<AssetCategoryVO> assetGroupVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Asset Category Information BY OrgId : {}", orgId);
			assetGroupVO = assetCategoryRepo.getAllCategoryByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  Asset Category Information For All OrgId.");
			assetGroupVO = assetCategoryRepo.findAll();
		}
		return assetGroupVO.stream().collect(Collectors.groupingBy(AssetCategoryVO::getAssetType,
				Collectors.groupingBy(AssetCategoryVO::getCategory)));
	}

	@Override
	public void deleteCustomersAddress(Long id) {
		customersAddressRepo.deleteById(id);
	}

	@Override
	public void deleteCustomersBankDetails(Long id) {
		CustomersBankDetailsRepo.deleteById(id);
	}

	@Override
	public void uploadCustomerAttachmentDoc(MultipartFile[] files, CustomerAttachmentType type, Long customerId)
			throws ApplicationException {
		if (files == null || files.length == 0 || StringUtils.isEmpty(type.name()) || ObjectUtils.isEmpty(customerId)) {
			throw new ApplicationException("Invalid customerId Attachment Information.");
		}
		String customerDirPath = env.getProperty("customer.attachment.dir");
		String uploadDirPath = new StringBuilder(customerDirPath).append("/").append(customerId).toString();
		File uploadDir = new File(uploadDirPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_YYYY_HH_mm_ss"));
		int fileCount = 0;
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					String fileName = CommonUtils.constructUniqueFileName(file.getOriginalFilename(), type.name(),
							fileCount, date);
					Path savePath = Paths.get(uploadDirPath, fileName);
					file.transferTo(savePath);
					String attFileName = new StringBuilder(CommonConstant.FORWARD_SLASH).append(type)
							.append(CommonConstant.FORWARD_SLASH).append(fileName).toString();
					customerAttachmentRepo.save(CustomerAttachmentVO.builder().fileName(attFileName).type(type.name())
							.customerId(customerId).build());
				} catch (Exception e) {
					LOGGER.error("Failed to save the file: {} Error : {}", file.getOriginalFilename(), e.getMessage());
				}
			}
			fileCount++;
		}
	}

	@Override
	public List<VendorVO> getAllVendor(Long orgId) {
		List<VendorVO> vendorVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Vendor Information BY OrgId : {}", orgId);
			vendorVO = vendorRepo.getAllVenderByOrgId(orgId);
		}
		return vendorVO;
	}

	@Override
	public List<VendorVO> getAllActiveVendor(Long orgId) {
		List<VendorVO> vendorVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Vendor Information BY OrgId : {}", orgId);
			vendorVO = vendorRepo.getAllActiveVenderByOrgId(orgId);
		}
		return vendorVO;
	}

	@Override
	public Optional<VendorVO> getVendorById(Long id) {
		return VendorRepo.findById(id);
	}

	// Vendor

	@Override
	public VendorVO updateCreateVendor(VendorDTO vendorDTO) throws ApplicationException {

		VendorVO vendorVO = new VendorVO();
		Long vendorId = vendorDTO.getId();

		if (vendorId != null) {
			vendorVO = vendorRepo.findById(vendorId)
					.orElseThrow(() -> new ApplicationException("Invalid Vendor details"));
		}

		// Update vendor details
		getVendorVOFromVendorDTO(vendorDTO, vendorVO);

		// Update or create bank details
		List<VendorBankDetailsVO> vendorBankDetailsVO = new ArrayList<>();
		if (vendorDTO.getVendorBankDetailsDTO() != null) {
			for (VendorBankDetailsDTO vendorbankDetailsDTO : vendorDTO.getVendorBankDetailsDTO()) {
				VendorBankDetailsVO bankDetailsVO;
				if (vendorbankDetailsDTO.getId() != null && vendorbankDetailsDTO.getId() != 0) {
					bankDetailsVO = vendorBankDetailsRepo.findById(vendorbankDetailsDTO.getId())
							.orElse(new VendorBankDetailsVO());
				} else {
					bankDetailsVO = new VendorBankDetailsVO();
				}
				bankDetailsVO.setAccountNo(vendorbankDetailsDTO.getAccountNo());
				bankDetailsVO.setBank(vendorbankDetailsDTO.getBank());
				bankDetailsVO.setBranch(vendorbankDetailsDTO.getBranch());
				bankDetailsVO.setAccountName(vendorbankDetailsDTO.getAccountName());
				bankDetailsVO.setIfscCode(vendorbankDetailsDTO.getIfscCode());
				bankDetailsVO.setVendorVO(vendorVO);
				vendorBankDetailsVO.add(bankDetailsVO);
			}
		}
		vendorVO.setVendorBankDetailsVO(vendorBankDetailsVO);

		// Update or create address details
		List<VendorAddressVO> vendorAddressVO = new ArrayList<>();
		if (vendorDTO.getVendorAddressDTO() != null) {
			for (VendorAddressDTO vendorAddressDTO : vendorDTO.getVendorAddressDTO()) {
				VendorAddressVO vendorAddress;
				if (vendorAddressDTO.getId() != null && vendorAddressDTO.getId() != 0) {
					vendorAddress = vendorAddressRepo.findById(vendorAddressDTO.getId()).orElse(new VendorAddressVO());
				} else {
					vendorAddress = new VendorAddressVO();
				}
				vendorAddress.setGstNumber(vendorAddressDTO.getGstNumber());
				vendorAddress.setStreet1(vendorAddressDTO.getStreet1());
				vendorAddress.setStreet2(vendorAddressDTO.getStreet2());
				vendorAddress.setCity(vendorAddressDTO.getCity());
				vendorAddress.setPinCode(vendorAddressDTO.getPinCode());
				vendorAddress.setContactName(vendorAddressDTO.getContactName());
				vendorAddress.setPhoneNumber(vendorAddressDTO.getPhoneNumber());
				vendorAddress.setDesignation(vendorAddressDTO.getDesignation());
				vendorAddress.setEmail(vendorAddressDTO.getEmail());
				vendorAddress.setGstRegistrationStatus(vendorAddressDTO.getGstRegistrationStatus());
				vendorAddress.setState(vendorAddressDTO.getState());
				vendorAddress.setVendorVO(vendorVO);
				vendorAddress.setCountry(vendorAddressDTO.getCountry());
				vendorAddressVO.add(vendorAddress);
			}
		}
		vendorVO.setVendorAddressVO(vendorAddressVO);

		return vendorRepo.save(vendorVO);
	}

	private void getVendorVOFromVendorDTO(VendorDTO vendorDTO, VendorVO vendorVO) throws ApplicationException {

		Long vendorId = vendorDTO.getId();

		if (vendorId != null && vendorId != 0) {
			VendorVO existingVendor = vendorRepo.findById(vendorId)
					.orElseThrow(() -> new ApplicationException("Vendor with ID " + vendorId + " not found"));

			if (!existingVendor.getEntityLegalName().equals(vendorDTO.getEntityLegalName())) {
				// Check if there's already an entry with the same Entity Legal Name and orgId
				if (vendorRepo.existsByEntityLegalNameAndOrgId(vendorDTO.getEntityLegalName(),
						existingVendor.getOrgId())) {
					throw new ApplicationException("Entity Legal Name already exists");
				}
				// Update Entity Legal Name if there's no duplicate
				vendorVO.setEntityLegalName(vendorDTO.getEntityLegalName());
			}

			if (!existingVendor.getDisplyName().equals(vendorDTO.getDisplyName())) {
				// Check if there's already an entry with the same Display Name and orgId
				if (vendorRepo.existsByDisplyNameAndOrgId(vendorDTO.getDisplyName(), existingVendor.getOrgId())) {
					throw new ApplicationException("Display Name already exists");
				}
				// Update Display Name if there's no duplicate
				vendorVO.setDisplyName(vendorDTO.getDisplyName());
			}

			vendorVO.setOrgId(vendorDTO.getOrgId());
			vendorVO.setVenderType(vendorDTO.getVenderType());
			vendorVO.setPhoneNumber(vendorDTO.getPhoneNumber());
			vendorVO.setEmail(vendorDTO.getEmail());
			vendorVO.setCountry(vendorDTO.getCountry());
			vendorVO.setModifiedBy(vendorDTO.getCreatedBy());
			vendorVO.setVenderActivePortal(vendorDTO.isVenderActivePortal());
			vendorVO.setActive(vendorDTO.isActive());
		} else {
			if (vendorRepo.existsByEntityLegalNameAndOrgId(vendorDTO.getEntityLegalName(), vendorDTO.getOrgId())) {
				throw new ApplicationException("Entity Legal Name already exists");
			}
			if (vendorRepo.existsByDisplyNameAndOrgId(vendorDTO.getDisplyName(), vendorDTO.getOrgId())) {
				throw new ApplicationException("Display Name already exists");
			}
			vendorVO.setOrgId(vendorDTO.getOrgId());
			vendorVO.setVenderType(vendorDTO.getVenderType());
			vendorVO.setDisplyName(vendorDTO.getDisplyName());
			vendorVO.setPhoneNumber(vendorDTO.getPhoneNumber());
			vendorVO.setEntityLegalName(vendorDTO.getEntityLegalName());
			vendorVO.setEmail(vendorDTO.getEmail());
			vendorVO.setCreatedBy(vendorDTO.getCreatedBy());
			vendorVO.setModifiedBy(vendorDTO.getCreatedBy());
			vendorVO.setVenderActivePortal(vendorDTO.isActive());
			vendorVO.setActive(vendorDTO.isActive());
		}
	}

	@Override
	public List<VendorVO> getVendorByOrgId(Long orgId) {
		List<VendorVO> vendorVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  VenderInformation BY OrgId : {}", orgId);
			vendorVO = vendorRepo.getAllVenderByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  VenderInformation For All OrgId.");
			vendorVO = vendorRepo.findAll();
		}
		return vendorVO;
	}

	@Override
	public void deletevendor(long id) {
		VendorRepo.deleteById(id);
	}

	@Override
	public Optional<VendorAddressVO> getVendorAddressById(Long id) {
		return vendorAddressRepo.findById(id);
	}

	@Override
	public void deletevendorAddress(Long id) {
		vendorAddressRepo.deleteById(id);
	}

	@Override
	public Optional<VendorBankDetailsVO> getVendorBankDetailsById(Long id) {
		return vendorBankDetailsRepo.findById(id);
	}

	@Override
	public void deletevendorBankDetails(Long id) {
		vendorBankDetailsRepo.deleteById(id);
	}

	@Override
	public List<FlowVO> getFlowByUserId(long userId) throws ApplicationException {
		String flowIds = userRepo.getFlowIdsByUserId(userId);
		if (StringUtils.isBlank(flowIds)) {
			throw new ApplicationException("Flow not found.");
		}
		return getFlowByIds(flowIds);
	}

	public List<FlowVO> getFlowByIds(String ids) throws ApplicationException {
		List<Long> flowIds = Arrays.stream(StringUtils.split(ids, ",")).map(Long::parseLong)
				.collect(Collectors.toList());
		List<FlowVO> flowVO = flowRepo.findAllById(flowIds);
		if (flowVO.isEmpty()) {
			throw new ApplicationException("Flow not found.");
		}
		return flowVO;
	}

	@Override
	public Set<Object[]> getKitDetailsByEmitter(Long emitterId, Long orgId) {
		return flowRepo.findKitDetailsByEmitter(emitterId, orgId);
	}

	@Override
	public Set<Object[]> getFlowNameByOrgID(Long orgId, Long emitterId) {
		return flowRepo.getFlowNameByOrgID(orgId, emitterId);
	}

	@Override
	public int loadKitQty(Long irItemId, Long kitQty) {
		return issueItemRepo.loadKitQty(irItemId, kitQty);
	}

	@Override
	public DmapVO createDmapVO(DmapDTO dmapDTO) {

		DmapVO dmapVO = new DmapVO();
		dmapVO.setFinYear(dmapDTO.getFinYear());
		dmapVO.setFromDate(dmapDTO.getFromDate());
		dmapVO.setToDate(dmapDTO.getToDate());
		dmapVO.setExtDate(dmapDTO.getExtDate());
		dmapVO.setOrgId(dmapDTO.getOrgId());

		List<DmapDetailsVO> dmapDetailsVO = new ArrayList<>();
		if (dmapDTO.getDmapDetailsDTO() != null) {
			for (DmapDetailsDTO detailsDTO : dmapDTO.getDmapDetailsDTO()) {
				DmapDetailsVO detailsVO = new DmapDetailsVO();
				detailsVO.setScode(detailsDTO.getScode());
				detailsVO.setPrefix(detailsDTO.getPrefix());
				detailsVO.setSequence(detailsDTO.getSequence());
				detailsVO.setSufix(detailsDTO.getSufix());
				detailsVO.setType(detailsDTO.getType());
				detailsVO.setDocIdType(detailsDTO.getPrefix() + dmapDTO.getFinYear() + detailsDTO.getSufix());
				detailsVO.setFinYear(dmapDTO.getFinYear());
				detailsVO.setDmapVO(dmapVO);

				dmapDetailsVO.add(detailsVO);
			}
		}
		dmapVO.setDmapDetailsVO(dmapDetailsVO);
		return dmapRepo.save(dmapVO);
	}

	// service
	@Override
	public ServiceVO updateCreateService(ServiceDTO serviceDTO) throws ApplicationException {
		ServiceVO serviceVO = new ServiceVO();
		if (ObjectUtils.isNotEmpty(serviceDTO.getId())) {
			serviceVO = serviceRepo.findById(serviceDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid service details"));
			serviceVO.setModifiedBy(serviceDTO.getCreatedBy()); // Set modifiedBy only during update
		} else {
			// Set createdBy only during creation
			serviceVO.setCreatedBy(serviceDTO.getCreatedBy());
			serviceVO.setModifiedBy(serviceDTO.getCreatedBy());
		}
		serviceVO.setOrgId(serviceDTO.getOrgid());
		getServiceVOFromServiceDTO(serviceDTO, serviceVO);
		return serviceRepo.save(serviceVO);
	}

	private void getServiceVOFromServiceDTO(@Valid ServiceDTO serviceDTO, ServiceVO serviceVO) {
		serviceVO.setCode(serviceDTO.getCode());
		serviceVO.setDescription(serviceDTO.getDescription());
	}

	@Override
	public CnoteVO updateCreateCnote(CnoteDTO cnoteDTO) throws ApplicationException {
		CnoteVO cnoteVO = new CnoteVO();
		if (ObjectUtils.isNotEmpty(cnoteDTO.getCnoteId())) {
			cnoteVO = cnoteRepo.findById(cnoteDTO.getCnoteId())
					.orElseThrow(() -> new ApplicationException("Invalid cnote details"));
		}

		getCnoteVOFromCnoteDTO(cnoteDTO, cnoteVO);
		return cnoteRepo.save(cnoteVO);
	}

	private void getCnoteVOFromCnoteDTO(CnoteDTO cnoteDTO, CnoteVO cnoteVO) {
		cnoteVO.setCnoteId(cnoteDTO.getCnoteId());
		cnoteVO.setCancelRemarks(cnoteDTO.getCancelRemarks());
		cnoteVO.setCode(cnoteDTO.getCode());
		cnoteVO.setDescription(cnoteDTO.getDescription());
		cnoteVO.setServiceCode(cnoteDTO.getServiceCode());
		cnoteVO.setRcode(cnoteDTO.getRcode());
		cnoteVO.setCcode(cnoteDTO.getCcode());
		cnoteVO.setRlEdger(cnoteDTO.getRlEdger());
		cnoteVO.setClEdger(cnoteDTO.getClEdger());

	}

	@Override
	public AssetInwardVO createAssetInward(AssetInwardDTO assetInwardDTO) {

		AssetInwardVO assetInwardVO = new AssetInwardVO();
		int finyr = assetInwardRepo.findfinyr();
		String assetinward = finyr + "AI" + assetInwardRepo.finddocid();
		assetInwardVO.setDocId(assetinward);
		assetInwardRepo.nextseq();
		assetInwardVO.setDocDate(assetInwardDTO.getDocDate());
		assetInwardVO.setStockBranch(assetInwardDTO.getStockBranch());
		assetInwardVO.setSourceFrom(assetInwardDTO.getSourceFrom());
		assetInwardVO.setOrgId(assetInwardDTO.getOrgId());
		assetInwardVO.setCategory(assetInwardDTO.getCategory());
		assetInwardVO.setAssetCode(assetInwardDTO.getAssetCode());
		assetInwardVO.setQty(assetInwardDTO.getQty()); 
		assetInwardVO.setCreatedBy(assetInwardDTO.getCreatedBy());
		assetInwardVO.setModifiedBy(assetInwardDTO.getCreatedBy());
		List<AssetInwardDetailVO> assetInwardDetailVO = new ArrayList<>();
		if (assetInwardDTO.getAssetInwardDetailDTO() != null) {
			for (AssetInwardDetailDTO AssetInward : assetInwardDTO.getAssetInwardDetailDTO()) {
				AssetInwardDetailVO assetInwardDetail = new AssetInwardDetailVO();
				assetInwardDetail.setBinLocation(AssetInward.getBinLocation());
				assetInwardDetail.setSkuDetail(AssetInward.getSkuDetail());
				assetInwardDetail.setSkuQty(AssetInward.getSkuQty());
				assetInwardDetail.setSkucode(AssetInward.getSkucode());
				assetInwardDetail.setStockLocation(AssetInward.getStockLocation());
				assetInwardDetail.setStockValue(AssetInward.getStockValue());
				assetInwardDetail.setAssetInwardVO(assetInwardVO);
				assetInwardDetail.setTagCode(AssetInward.getTagCode());
				assetInwardDetail.setRfId(AssetInward.getRfId());
				assetInwardDetailVO.add(assetInwardDetail);
			}
		}
		assetInwardVO.setAssetInwardDetailVO(assetInwardDetailVO);
		AssetInwardVO assetInwardVO1 = assetInwardRepo.save(assetInwardVO);
		List<AssetInwardDetailVO> assetInwardDetailVOs = assetInwardVO1.getAssetInwardDetailVO();

		if (assetInwardDetailVOs != null && !assetInwardDetailVOs.isEmpty()) {
			for (AssetInwardDetailVO assetdetails : assetInwardDetailVOs) {

				AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
				assetStockDetailsVO.setStockRef(assetInwardVO1.getDocId());
				assetStockDetailsVO.setStockDate(assetInwardVO1.getDocDate());
				assetStockDetailsVO.setStockBranch(assetInwardVO1.getSourceFrom());
				assetStockDetailsVO.setSku(assetdetails.getSkuDetail());
				assetStockDetailsVO.setCategory(assetInwardVO1.getCategory());
				assetStockDetailsVO.setSkuCode(assetdetails.getSkucode());
				assetStockDetailsVO.setSkuQty(assetdetails.getSkuQty() * -1);
				assetStockDetailsVO.setStockValue(assetdetails.getStockValue());
				assetStockDetailsVO.setStockLocation(assetdetails.getStockLocation());
				assetStockDetailsVO.setBinLocation(assetdetails.getBinLocation());
				assetStockDetailsVO.setSCode(assetInwardVO1.getSCode());
				assetStockDetailsVO.setOrgId(assetInwardVO1.getOrgId());
				assetStockDetailsVO.setScreen("Asset Inward");
				assetStockDetailsVO.setPm("M");
				assetStockDetailsVO.setStatus("S");
				assetStockDetailsVO.setBinLocation("");
				assetStockDetailsVO.setCancelRemarks("");
				assetStockDetailsVO.setStockLocation("");
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setSourceId(assetdetails.getAssetInwardDetailId());
				assetStockDetailsVO.setFinyr(assetInwardVO1.getFinyr());
				assetStockDetailsVO.setTagCode(assetdetails.getTagCode());
				assetStockDetailsVO.setRfId(assetdetails.getRfId());

				assetStockDetailsRepo.save(assetStockDetailsVO);

			}
			for (AssetInwardDetailVO assetdetails : assetInwardDetailVOs) {

				AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
				assetStockDetailsVO.setStockRef(assetInwardVO1.getDocId());
				assetStockDetailsVO.setStockDate(assetInwardVO1.getDocDate());
				assetStockDetailsVO.setStockBranch(assetInwardVO1.getStockBranch());
				assetStockDetailsVO.setSku(assetdetails.getSkuDetail());
				assetStockDetailsVO.setSkuCode(assetdetails.getSkucode());
				assetStockDetailsVO.setSkuQty(assetdetails.getSkuQty());
				assetStockDetailsVO.setCategory(assetInwardVO1.getCategory());
				assetStockDetailsVO.setStockValue(assetdetails.getStockValue());
				assetStockDetailsVO.setStockLocation(assetdetails.getStockLocation());
				assetStockDetailsVO.setBinLocation(assetdetails.getBinLocation());
				assetStockDetailsVO.setSCode(assetInwardVO1.getSCode());
				assetStockDetailsVO.setOrgId(assetInwardVO1.getOrgId());
				assetStockDetailsVO.setScreen("Asset Inward");
				assetStockDetailsVO.setPm("P");
				assetStockDetailsVO.setStatus("S");
				assetStockDetailsVO.setBinLocation("");
				assetStockDetailsVO.setCancelRemarks("");
				assetStockDetailsVO.setStockLocation("");
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setSourceId(assetdetails.getAssetInwardDetailId());
				assetStockDetailsVO.setFinyr(assetInwardVO1.getFinyr());
				assetStockDetailsVO.setTagCode(assetdetails.getTagCode());
				assetStockDetailsVO.setRfId(assetdetails.getRfId());

				assetStockDetailsRepo.save(assetStockDetailsVO);

			}

		}
		return assetInwardRepo.save(assetInwardVO);
	}

	// Stock branch
	@Override
	public StockBranchVO createStockBranch(StockBranchDTO stockBranchDTO) throws ApplicationException {

		if (stockBranchRepo.existsByBranchAndOrgId(stockBranchDTO.getBranch(), stockBranchDTO.getOrgId())) {
			throw new ApplicationException("Branch Already Exist");
		}
		if (stockBranchRepo.existsBybranchCodeAndOrgId(stockBranchDTO.getBranchCode(), stockBranchDTO.getOrgId())) {
			throw new ApplicationException("Branch Code Already Exist");
		}

		StockBranchVO stockBranchVO = new StockBranchVO();
		stockBranchVO.setBranch(stockBranchDTO.getBranch());
		stockBranchVO.setBranchCode(stockBranchDTO.getBranchCode());
		stockBranchVO.setOrgId(stockBranchDTO.getOrgId());
		stockBranchVO.setCreatedBy(stockBranchDTO.getCreatedby());
		stockBranchVO.setModifiedBy(stockBranchDTO.getCreatedby());
		stockBranchVO.setActive(stockBranchDTO.isActive());
		// stockBranchVO.setActive(stockBranchDTO.isActive());
		return stockBranchRepo.save(stockBranchVO);
	}

	// Update Stock Branch
	@Override
	public StockBranchVO updateStockBranch(StockBranchDTO stockBranchDTO) throws ApplicationException {

		if (stockBranchDTO.getId() != 0) {
			StockBranchVO existingStockBranch = stockBranchRepo.findById(stockBranchDTO.getId()).get();
			if (!existingStockBranch.getBranch().equals(stockBranchDTO.getBranch())) {
				if (stockBranchRepo.existsByBranchAndOrgId(stockBranchDTO.getBranch(),
						existingStockBranch.getOrgId())) {
					throw new ApplicationException("Branch Already Exist");
				}
				existingStockBranch.setBranch(stockBranchDTO.getBranch());
			}
			if (!existingStockBranch.getBranchCode().equals(stockBranchDTO.getBranchCode())) {
				if (stockBranchRepo.existsBybranchCodeAndOrgId(stockBranchDTO.getBranchCode(),
						stockBranchDTO.getOrgId())) {
					throw new ApplicationException("Branch Code Already Exist");
				}
				existingStockBranch.setBranchCode(stockBranchDTO.getBranchCode());
			}
			// existingStockBranch.setActive(stockBranchDTO.isActive());
			existingStockBranch.setModifiedBy(stockBranchDTO.getCreatedby());
			existingStockBranch.setActive(stockBranchDTO.isActive());
			return stockBranchRepo.save(existingStockBranch);
		} else {
			throw new NoSuchElementException("StockBranch with ID " + stockBranchDTO.getId() + " not found");
		}
	}

	@Override
	public List<StockBranchVO> getAllStockBranchByOrgId(Long orgId) {

		return stockBranchRepo.findByOrgId(orgId);

	}

	@Override
	public List<StockBranchVO> getAllActiveStockBranchByOrgId(Long orgId) {

		return stockBranchRepo.findActiveBranchByOrgId(orgId);

	}

	// Asset Tagging
	@Override
	public AssetTaggingVO createTagging(AssetTaggingDTO assetTaggingDTO) {

		AssetTaggingVO assetTaggingVO = new AssetTaggingVO();

		int finyr = assetTaggingRepo.getFinyr();
		String assetTagging = finyr + "AT" + assetTaggingRepo.findocid();
		assetTaggingVO.setDocid(assetTagging);
		assetTaggingRepo.nextDocid();
		assetTaggingVO.setDocDate(assetTaggingDTO.getDocDate());
		assetTaggingVO.setCancel(false);
		assetTaggingVO.setCreatedBy(assetTaggingDTO.getCreatedBy());
		assetTaggingVO.setModifiedBy(assetTaggingDTO.getCreatedBy());
		assetTaggingVO.setActive(true);
		assetTaggingVO.setPoNo(assetTaggingDTO.getPoNo());
		assetTaggingVO.setPoDate(assetTaggingDTO.getPoDate());
		assetTaggingVO.setCategory(assetTaggingDTO.getCategory());
		assetTaggingVO.setAsset(assetTaggingDTO.getAsset());
		assetTaggingVO.setAssetCode(assetTaggingDTO.getAssetCode());
		assetTaggingVO.setSeqFrom(assetTaggingDTO.getSeqFrom());
		assetTaggingVO.setSeqTo(assetTaggingDTO.getSeqTo());
		assetTaggingVO.setOrgId(assetTaggingDTO.getOrgId());
		List<AssetTaggingDetailsVO> assetTaggingDetailsVO = new ArrayList<>();
		if (assetTaggingDTO.getTaggingDetailsDTO() != null) {

			for (AssetTaggingDetailsDTO taggingDetailsDTO : assetTaggingDTO.getTaggingDetailsDTO()) {

				boolean isRfidExists = assetTaggingDetailsRepo.existsByRfId(taggingDetailsDTO.getRfId());
				boolean isTagCodeExists = assetTaggingDetailsRepo.existsByTagCode(taggingDetailsDTO.getTagCode());

				// Check if RFID or Tag Code already exists
				if (isRfidExists) {
					throw new RuntimeException("RFID " + taggingDetailsDTO.getRfId() + " already exists.");
				}

				if (isTagCodeExists) {
					throw new RuntimeException("Tag Code " + taggingDetailsDTO.getTagCode() + " already exists.");
				}

				AssetTaggingDetailsVO assetTaggingDetails = new AssetTaggingDetailsVO();
				assetTaggingDetails.setRfId(taggingDetailsDTO.getTagCode());
				assetTaggingDetails.setTaggingDocDd(assetTaggingVO.getDocid());
				assetTaggingDetails.setAssetCode(taggingDetailsDTO.getAssetCode());
				assetTaggingDetails.setAsset(taggingDetailsDTO.getAsset());
				assetTaggingDetails.setOrgId(assetTaggingVO.getOrgId());
				assetTaggingDetails.setTagCode(taggingDetailsDTO.getTagCode());
				assetTaggingDetails.setCategory(taggingDetailsDTO.getCategory());
				assetTaggingDetails.setTaggingVO(assetTaggingVO);
				assetTaggingDetailsVO.add(assetTaggingDetails);

			}
		}
		assetTaggingVO.setTaggingDetails(assetTaggingDetailsVO);
		AssetTaggingVO savedAssetTaggingVO = assetTaggingRepo.save(assetTaggingVO);
		List<AssetTaggingDetailsVO> savedAssetTaggingDetailsVOs = savedAssetTaggingVO.getTaggingDetails();

		if (savedAssetTaggingDetailsVOs != null && !savedAssetTaggingDetailsVOs.isEmpty()) {

			for (AssetTaggingDetailsVO assetTaggingDetails : savedAssetTaggingDetailsVOs) {
				AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
				assetStockDetailsVO.setStockRef(savedAssetTaggingVO.getDocid());
				assetStockDetailsVO.setStockDate(savedAssetTaggingVO.getDocDate());
				assetStockDetailsVO.setSkuCode(assetTaggingDetails.getAssetCode());
				assetStockDetailsVO.setSku(assetTaggingDetails.getAsset());
				assetStockDetailsVO.setSkuQty(1);
				assetStockDetailsVO.setCategory(assetTaggingDetails.getCategory());
				assetStockDetailsVO.setOrgId(savedAssetTaggingVO.getOrgId());
				assetStockDetailsVO.setRfId(assetTaggingDetails.getRfId());
				assetStockDetailsVO.setTagCode(assetTaggingDetails.getTagCode());
				assetStockDetailsVO.setSCode(savedAssetTaggingVO.getScode()); // Assuming getScode() returns the correct
				assetStockDetailsVO.setSourceId(assetTaggingDetails.getId()); // value
				assetStockDetailsVO.setScreen("Asset Tagging");
				assetStockDetailsVO.setPm("P");
				assetStockDetailsVO.setBinLocation("");
				assetStockDetailsVO.setCancelRemarks("");
				assetStockDetailsVO.setStockLocation("");
				assetStockDetailsVO.setStatus("S");
				assetStockDetailsVO.setFinyr(savedAssetTaggingVO.getFinyr());
				assetStockDetailsVO.setStockBranch("AI POOL");
				assetStockDetailsRepo.save(assetStockDetailsVO);
			}
		}
		return savedAssetTaggingVO;
	}

	@Override
	public List<AssetTaggingVO> getAllAsetTaggingByOrgId(Long orgId) {
		return assetTaggingRepo.findAllByOrgId(orgId);
	}

	@Override
	public Set<Object[]> getTagCodeByAsset(String assetcode, String asset, int endno, String category) {

		int finyr = assetTaggingRepo.getFinyr();
		int start = assetTaggingRepo.getstno(assetcode);
		int st = 0;
		int end = 0;
		if (start != 1) {
			st = start + 1;
			end = start + endno;
		} else {
			st = start;
			end = endno;
		}

		return assetTaggingRepo.getTagCodeByAsset(assetcode, asset, st, end, finyr, category);
	}

	@Override
	public Set<Object[]> getAvalKitQty(Long warehouseId, String Kitname) {

		return stockBranchRepo.findAvalKitQty(warehouseId, Kitname);
	}

	@Override
	public Set<Object[]> getAvalKitQtyByBranch(String branch, String Kitname) {

		return stockBranchRepo.findAvalKitQtyByBranch(branch, Kitname);
	}

	@Override
	public TermsAndConditionsVO updateCreateTerms(TermsAndConditionsDTO termsAndConditionsDTO)
			throws ApplicationException {
		TermsAndConditionsVO termsAndConditionsVO = new TermsAndConditionsVO();
		if (ObjectUtils.isNotEmpty(termsAndConditionsDTO.getTermsId())) {
			termsAndConditionsVO = termsAndConditionsRepo.findById(termsAndConditionsDTO.getTermsId())
					.orElseThrow(() -> new ApplicationException("Invalid Terms details"));
		}
		getWarehouseVOFromWarehouseDTO(termsAndConditionsDTO, termsAndConditionsVO);
		return termsAndConditionsRepo.save(termsAndConditionsVO);
	}

	private void getWarehouseVOFromWarehouseDTO(TermsAndConditionsDTO termsAndConditionsDTO,
			TermsAndConditionsVO termsAndConditionsVO) {

		termsAndConditionsVO.setOrgId(termsAndConditionsDTO.getOrgId());
		termsAndConditionsVO.setSCode("TERMS");
		termsAndConditionsVO.setTermsCode(termsAndConditionsDTO.getTermsCode());
		termsAndConditionsVO.setDetails(termsAndConditionsDTO.getDetails());
		termsAndConditionsVO.setPrintRemarks(termsAndConditionsDTO.getPrintRemarks());
		termsAndConditionsVO.setEffectiveFrom(LocalDate.now());
		termsAndConditionsVO.setEffectiveTo(LocalDate.now());

	}

	@Override
	public List<TermsAndConditionsVO> getAllTermsByOrgId(Long orgId) {
		List<TermsAndConditionsVO> termsAndConditionsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Terms BY OrgId : {}", orgId);
			termsAndConditionsVO = termsAndConditionsRepo.getAllTermsByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  Terms For All OrgId.");
			termsAndConditionsVO = termsAndConditionsRepo.findAll();
		}
		return termsAndConditionsVO;
	}

	@Override
	public List<TermsAndConditionsVO> getAllTermsById(Long termsId) {
		List<TermsAndConditionsVO> termsAndConditionsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(termsId)) {
			LOGGER.info("Successfully Received  Terms BY TermsId : {}", termsId);
			termsAndConditionsVO = termsAndConditionsRepo.getAllTermsByTermsId(termsId);
		} else {
			LOGGER.info("Successfully Received  Terms For All TermsId.");
			termsAndConditionsVO = termsAndConditionsRepo.findAll();
		}
		return termsAndConditionsVO;
	}

	@Override
	public PoVO updateCreatePo(PoDTO poDTO) throws ApplicationException {
		PoVO poVO = new PoVO();
		if (ObjectUtils.isNotEmpty(poDTO.getPoId())) {
			poVO = poRepo.findById(poDTO.getPoId()).orElseThrow(() -> new ApplicationException("Invalid po details"));
		}
		//

		getPoVOFromPoDTO(poDTO, poVO);

		List<PoVO1> poVO1 = new ArrayList<>();
		if (poDTO.getPo1DTO() != null) {
			for (Po1DTO po1DTO : poDTO.getPo1DTO()) {
				if (po1DTO.getPo1Id() != null & ObjectUtils.isNotEmpty(po1DTO.getPo1Id())) {
					PoVO1 Po1 = po1Repo.findById(po1DTO.getPo1Id()).get();
					Po1.setItemId(po1DTO.getItemId());
					Po1.setDescription(po1DTO.getDescription());
					Po1.setHsnCode(po1DTO.getHsnCode());
					Po1.setQty(po1DTO.getQty());
					Po1.setRate(po1DTO.getRate());
					Po1.setExRate(po1DTO.getExRate());
					Po1.setAmount(po1DTO.getAmount());
					Po1.setCurrency(po1DTO.getCurrency());
					Po1.setPoVO(poVO);
					poVO1.add(Po1);
				} else {
					PoVO1 Po1 = new PoVO1();
					Po1.setItemId(po1DTO.getItemId());
					Po1.setDescription(po1DTO.getDescription());
					Po1.setHsnCode(po1DTO.getHsnCode());
					Po1.setQty(po1DTO.getQty());
					Po1.setRate(po1DTO.getRate());
					Po1.setExRate(po1DTO.getExRate());
					Po1.setAmount(po1DTO.getAmount());
					Po1.setCurrency(po1DTO.getCurrency());
					Po1.setPoVO(poVO);
					poVO1.add(Po1);
				}
			}
		}
		poVO.setPoVO1(poVO1);
		return poRepo.save(poVO);
	}

	private void getPoVOFromPoDTO(PoDTO poDTO, PoVO poVO) {

		poVO.setOrgId(poDTO.getOrgId());
		poVO.setSCode("PO");
		poVO.setCompany(poDTO.getCompany());
		poVO.setAddress(poDTO.getAddress());
		poVO.setSelfGst(poDTO.getSelfGst());
		poVO.setPoNo(poDTO.getPoNo());
		poVO.setDate(LocalDate.now());
		poVO.setApId(poDTO.getApId());
		poVO.setApGst(poDTO.getApGst());
		poVO.setShipTo(poDTO.getShipTo());
		poVO.setShipToRemarks(poDTO.getShipToRemarks());
		poVO.setGstType(poDTO.getGstType());
		poVO.setIGst(poDTO.getIGst());
		poVO.setCGst(poDTO.getCGst());
		poVO.setSGst(poDTO.getSGst());
		poVO.setTerms(poDTO.getTerms());
		poVO.setCancel(poDTO.isCancel());
		poVO.setActive(poDTO.isActive());
		poVO.setAp(poDTO.getAp());
		poVO.setTotal(poDTO.getTotal());

	}

	@Override
	public List<PoVO> getPoByOrgId(Long orgId) {
		List<PoVO> poVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Po BY OrgId : {}", orgId);
			poVO = poRepo.getAllPoByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  Po For All OrgId.");
			poVO = poRepo.findAll();
		}
		return poVO;
	}

	@Override
	public List<PoVO> getAllPoByPoId(Long poId) {
		List<PoVO> poVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(poId)) {
			LOGGER.info("Successfully Received  Po BY TermsId : {}", poId);
			poVO = poRepo.getAllPoByPoId(poId);
		} else {
			LOGGER.info("Successfully Received  PO For All poId.");
			poVO = poRepo.findAll();
		}
		return poVO;
	}

	@Override
	public PodVO updateCreatePod(PodDTO podDTO) throws ApplicationException {
		PodVO podVO = new PodVO();
		if (ObjectUtils.isNotEmpty(podDTO.getPodId())) {
			podVO = podRepo.findById(podDTO.getPodId())
					.orElseThrow(() -> new ApplicationException("Invalid po details"));
		}
		List<Pod1VO> pod1VO = new ArrayList<>();
		if (podDTO.getPod1DTO() != null) {
			for (Pod1DTO pod1DTO : podDTO.getPod1DTO()) {
				Pod1VO pod1 = new Pod1VO();
				pod1.setAssetCode(pod1DTO.getAssetCode());
				pod1.setDescription(pod1DTO.getDescription());
				pod1.setAllotQty(pod1DTO.getAllotQty());
				pod1.setAcceptQty(pod1DTO.getAcceptQty());
				pod1.setPodVO(podVO);
				pod1VO.add(pod1);
			}
		}
		List<Pod2VO> pod2VO = new ArrayList<>();
		if (podDTO.getPod2DTO() != null) {
			for (Pod2DTO pod2DTO : podDTO.getPod2DTO()) {
				Pod2VO pod2 = new Pod2VO();
				pod2.setAssetCode(pod2DTO.getAssetCode());
				pod2.setDescription(pod2DTO.getDescription());
				pod2.setRejectedQty(pod2DTO.getRejectedQty());
				pod2.setReturnQty(pod2DTO.getReturnQty());
				pod2.setPodVO(podVO);
				pod2VO.add(pod2);
			}
		}
		podVO.setPod2VO(pod2VO);
		podVO.setPod1VO(pod1VO);
		getPodVOFromPodDTO(podDTO, podVO);
		return podRepo.save(podVO);
	}

	private void getPodVOFromPodDTO(PodDTO podDTO, PodVO podVO) {
		podVO.setOrgId(podDTO.getOrgId());
		podVO.setDocId(podDTO.getDocId());
		podVO.setRefNo(podDTO.getRefNo());
		podVO.setKitCode(podDTO.getKitCode());
		podVO.setKitQty(podDTO.getKitQty());
		podVO.setKitRqty(podDTO.getKitRqty());
		podVO.setActive(podDTO.isActive());
		podVO.setCancel(podDTO.isCancel());
		podVO.setRefDate(podDTO.getRefDate());
		podVO.setDocDate(podDTO.getDocdate());
	}

	@Override
	public List<PodVO> getAllPodByPodId(Long podId) {
		List<PodVO> podVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(podId)) {
			LOGGER.info("Successfully Received  Pod BY TermsId : {}", podId);
			podVO = podRepo.getAllPoByPoId(podId);
		} else {
			LOGGER.info("Successfully Received  POd For All poId.");
			podVO = podRepo.findAll();
		}
		return podVO;
	}

	@Override
	public List<PodVO> getAllPodByOrgId(Long orgId) {
		List<PodVO> podVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Pod BY TermsId : {}", orgId);
			podVO = podRepo.getAllPodByPoId(orgId);
		} else {
			LOGGER.info("Successfully Received  POd For All poId.");
			podVO = podRepo.findAll();
		}
		return podVO;
	}

	@Override
	public AssetCategoryVO getAssetCategoryByCategoryCode(Long orgId, String categoryCode) {
		return assetCategoryRepo.findCategoryByCategoryCode(orgId, categoryCode);
	}

	@Override
	public AssetVO getAssetByOrgId(Long orgId, String assetId) {
		return assetRepo.getAssetByOrgId(orgId, assetId);
	}

	@Override
	public List<ServiceVO> getAllServiceByOrgId(Long OrgId) {
		return serviceRepo.findAllByOrgId(OrgId);
	}

	@Override
	public List<AssetInwardVO> getAllAssetInwardOrgId(Long orgId) {
		return assetInwardRepo.findAssetInwardByOrgId(orgId);
	}

	@Override
	public AssetInwardVO getAssetInwardByDocId(String docId) {
		return assetInwardRepo.findAssetInwardByDocId(docId);
	}

	@Override
	public Set<Object[]> getPoNoByCreateAsset(Long orgId) {
		return poRepo.getPoNoByCreateAsset(orgId);
	}

//	private static final String UPLOAD_DIR = "D:\\Justin\\";
	@Value("${proofOfDelivery.upload.dir}")
	private String UPLOAD_DIR;

	public String uploadFileProofOfDelivery(MultipartFile file, String docId, String refNo) {
		String uploadResult = uploadFile(file, docId, refNo); // Call uploadFile method with docId and refNo
		// Create ProofOfDeliveryVO
		ProofOfDeliveryVO vo = createProofOfDeliveryVO(docId, refNo, Paths.get(UPLOAD_DIR));
		// Here you can do further processing or return both results combined
		return uploadResult + "\n" + vo.toString(); // Example: Combining both results into a single string
	}

	public String uploadFile(MultipartFile file, String docId, String refNo) {
		try {

			ProofOfDeliveryVO proofOfDeliveryVO = proofOfDeliveryRepo.findByDocIdAndRfNo(docId, refNo);
			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtension(originalFileName);
			// Customize the filename
			String customizedFileName = getCustomizedFileName(docId, refNo) + fileExtension;
			// Create the directory if it doesn't exist
			File directory = new File(UPLOAD_DIR);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(UPLOAD_DIR, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create ProofOfDeliveryVO and set uploadReceipt
			ProofOfDeliveryVO vo = createProofOfDeliveryVO(docId, refNo, Paths.get(UPLOAD_DIR));
			proofOfDeliveryVO.setUploadReceipt(filePath.toString().replace("\\", "/"));
			proofOfDeliveryRepo.save(proofOfDeliveryVO);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	private String getFileExtension(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return "";
	}

	private String getCustomizedFileName(String docId, String refNo) {
		return docId + "-" + refNo;
	}

	private ProofOfDeliveryVO createProofOfDeliveryVO(String docId, String refNo, Path filePath) {
		ProofOfDeliveryVO vo = new ProofOfDeliveryVO();
		// Set other attributes as needed
		vo.setDocId(docId);
		vo.setRfNo(refNo);
		vo.setUploadReceipt(filePath.toString());
		return vo;
	}

	@Override
	public ProofOfDeliveryVO createProofOfDelivery(ProofOfDeliveryDTO proofOfDeliveryDTO) {
		ProofOfDeliveryVO vo = new ProofOfDeliveryVO();
		vo.setDocId(proofOfDeliveryDTO.getDocId());
		vo.setDocDate(proofOfDeliveryDTO.getDocDate());
		vo.setRfNo(proofOfDeliveryDTO.getRfNo());
		vo.setRfDate(proofOfDeliveryDTO.getRfDate());
		vo.setKitCode(proofOfDeliveryDTO.getKitCode());
		vo.setKitQty(proofOfDeliveryDTO.getKitQty());
		vo.setKitRQty(proofOfDeliveryDTO.getKitRQty());
		vo.setCreatedBy(proofOfDeliveryDTO.getCreatedBy());
		vo.setModifiedBy(proofOfDeliveryDTO.getCreatedBy());
		vo.setOrgId(proofOfDeliveryDTO.getOrgId());

		return proofOfDeliveryRepo.save(vo);
	}

	@Override
	public List<ProofOfDeliveryVO> getAllProofOfDelivery(Long orgId) {
		List<ProofOfDeliveryVO> proofOfDeliveryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  proofOfDelivery BY orgId : {}", orgId);
			proofOfDeliveryVO = proofOfDeliveryRepo.getAllProofOfDeliveryBy(orgId);
		} else {
			LOGGER.info("Successfully Received  proofOfDelivery For All orgId.");
			proofOfDeliveryVO = proofOfDeliveryRepo.findAll();
		}
		return proofOfDeliveryVO;
	}

	@Override
	public Set<Object[]> getAllotmentNoByEmitterIdAndOrgId(Long orgId, Long emitterId) {
		return binAllotmentNewRepo.getAllotmentNoByEmitterIdAndOrgId(orgId, emitterId);
	}

	@Override
	public Set<Object[]> getAllotmentDetailsByAllotmentNoAndOrgId(Long orgId, String docid) {
		return binAllotmentNewRepo.getAllotmentDetailsByAllotmentNoAndOrgId(orgId, docid);
	}

	@Override
	public Set<Object[]> getAllotmentAssetDetailsByAllotmentNoAndOrgId(Long orgId, String docid) {

		return binAllotmentNewRepo.getAllotmentAssetDetailsByAllotmentNoAndOrgId(orgId, docid);
	}

	@Override
	public Set<Object[]> getAlllBinInwardByEmitterAndOrgId(Long emitterid, Long orgId) {

		return binInwardRepo.findAllByEmitterIdAndOrgId(emitterid, orgId);
	}

	@Override
	public Optional<BinInwardVO> getBinInwardById(Long id) {
		if (binInwardRepo.existsById(id)) {
			return binInwardRepo.findById(id);
		} else {
			throw new NoSuchElementException("Bin Inward with ID " + id + " does not exist");
		}

	}

	@Override
	public Set<Object[]> getFlowDetailsByFlowId(Long flowId) {

		return flowRepo.getFlowDetails(flowId);
	}

	@Override
	public Set<Object[]> getWaitingInwardDetailsByEmitterIdandOrgId(Long orgId, Long emitterid) {

		return binAllotmentNewRepo.getWaitingforBinInwardDetailsByEmitterAndOrgId(orgId, emitterid);
	}

	@Override
	public String getDocIdByAssetTagging() {
		int finyr = assetTaggingRepo.getFinyr();
		String assetTagging = finyr + "AT" + assetTaggingRepo.findocid();
		return assetTagging;
	}

	@Override
	public String getDocIdByBinInward() {
		int finyr = binInwardRepo.getFinyr();
		String binInward = finyr + "BI" + binInwardRepo.finddocid();
		return binInward;
	}

	@Override
	public Optional<BinInwardVO> getBinInwardByDocid(String docid) {
		if (binInwardRepo.existsByDocid(docid)) {
			return binInwardRepo.findAllByDocid(docid);
		} else {
			throw new NoSuchElementException("Bin Inward with DocId " + docid + " does not exist");
		}
	}

	// Bin allotment Issue manifest pdf

	@Override
	public List<Map<String, String>> getBinAllotmentPdfHeaderDetails(String docid) {

		Set<Object[]> header = binAllotmentNewRepo.getBinAllotmentHeader(docid);
		return findwaitingHeader(header);
	}

	private List<Map<String, String>> findwaitingHeader(Set<Object[]> header) {
		List<Map<String, String>> allotDetails = new ArrayList<>();
		for (Object[] ps : header) {
			Map<String, String> part = new HashMap<>();
			part.put("binreqno", ps[0] != null ? ps[0].toString() : "");
			part.put("binreqdate", ps[1] != null ? ps[1].toString() : "");
			part.put("senderAddress", ps[2] != null ? ps[2].toString() : "");
			part.put("senderCity", ps[3] != null ? ps[3].toString() : "");
			part.put("senderState", ps[4] != null ? ps[4].toString() : "");
			part.put("senderGst", ps[5] != null ? ps[5].toString() : "");
			part.put("senderName", ps[6] != null ? ps[6].toString() : "");
			part.put("receiverName", ps[7] != null ? ps[7].toString() : "");
			part.put("senderPinCode", ps[8] != null ? ps[8].toString() : "");
			part.put("receiverAddress", ps[9] != null ? ps[9].toString() : "");
			part.put("receiverGstin", ps[10] != null ? ps[10].toString() : "");
			allotDetails.add(part);
		}
		return allotDetails;
	}

	@Override
	public List<Object[]> getBinAllotmentPdfGridDetails(String docid) {
		return binAllotmentNewRepo.getBinAllotmentGrid(docid);
	}

	@Override
	public String getDocIdByAssetInward() {
		int finyr = assetInwardRepo.findfinyr();
		String assetInward = finyr + "AI" + assetInwardRepo.finddocid();
		return assetInward;
	}

	@Value("${customer.sop.upload.dir}")
	private String UPLOAD_DR;

	public String uploadCustomerSop(Long id, String legalname, MultipartFile file) {
		String uploadResult = uploadCustomerFileSOP(id, legalname, file); // Call uploadFile method with docId and refNo
		// Here you can do further processing or return both results combined
		return uploadResult; // Example: Combining both results into a single string
	}

	public String uploadCustomerFileSOP(Long id, String legalname, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionSop(originalFileName);
			// Customize the filename
			String customizedFileName = getCustomizedSopFileName(legalname) + fileExtension;
			// Create the directory if it doesn't exist
			File directory = new File(UPLOAD_DR);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(UPLOAD_DR, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			CustomersVO vo = customersRepo.findById(id).orElse(null);
			vo.setSop(filePath.toString().replace("\\", "/"));
			customersRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	private String getFileExtensionSop(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return "";
	}

	private String getCustomizedSopFileName(String legalname) {
		return legalname;
	}

//document

	@Value("${customer.document.upload.dir}")
	private String UPLOAD;

	public String uploadCustomerDocument(Long id, String legalname, MultipartFile file) {
		String uploadResult = uploadFileCustomerDocument(id, legalname, file); // Call uploadFile method with docId and
																				// refNo
		// Create ProofOfDeliveryVO
		// Here you can do further processing or return both results combined
		return uploadResult; // Example: Combining both results into a single string
	}

	public String uploadFileCustomerDocument(Long id, String legalname, MultipartFile file) {
		try {

			// Get the original file name
			String originalFileName = file.getOriginalFilename();
			// Extract the original file extension
			String fileExtension = getFileExtensionDocument(originalFileName);
			// Customize the filename
			String customizedFileName = getCustomizedDocumentFileName(id, legalname) + fileExtension;
			// Create the directory if it doesn't exist
			File directory = new File(UPLOAD);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the upload directory with the customized filename
			Path filePath = Paths.get(UPLOAD, customizedFileName);
			file.transferTo(filePath);
			System.out.println(filePath);
			// Create CustomerVO and set uploadReceipt
			CustomersVO vo = customersRepo.findById(id).orElse(null);
			vo.setDocument(filePath.toString().replace("\\", "/"));
			customersRepo.save(vo);
			return filePath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file: " + e.getMessage();
		}
	}

	private String getFileExtensionDocument(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return "";
	}

	private String getCustomizedDocumentFileName(Long id, String legalname) {
		return id + "-" + legalname;
	}

	@Override
	public List<Object[]> getRandomAssetDetailsByKitCodeAndAllotQty(String kitCode, int qty, String stockbranch) {

		return binAllotmentNewRepo.RandomAssetDetailsByKitCodeAndAllotQty(kitCode, qty, stockbranch);
	}

	@Override
	public List<BinAllotmentNewVO> getCustomizedAllotmentDetails(String kitCode, String flow, String emitter,
			LocalDate startAllotDate, LocalDate endAllotDate) {
		return binAllotmentNewRepo.findAll(new Specification<BinAllotmentNewVO>() {

			@Override
			public Predicate toPredicate(Root<BinAllotmentNewVO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(kitCode)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("kitCode"), kitCode)));
				}
				if (ObjectUtils.isNotEmpty(flow)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("flow"), flow)));
				}
				if (ObjectUtils.isNotEmpty(startAllotDate) && ObjectUtils.isNotEmpty(endAllotDate)) {
					predicates.add(criteriaBuilder.between(root.get("docDate"), startAllotDate, endAllotDate));
				}
				if (StringUtils.isNoneBlank(emitter)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("emitter"), emitter)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		});
	}

	// get Available Asset Details.
	@Override
	public List<Object[]> availableAllAssetDetails(Long orgId) {
		return assetStockDetailsRepo.getAvailableAssetDetails(orgId);
	}

	@Override
	public List<FlowVO> getFlowByKitCode(String kitcode) {
		List<FlowVO> flowVOs = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(kitcode)) {
			LOGGER.info("Successfully Received Flow BY kitcode : {}", kitcode);
			flowVOs = flowRepo.getFlowByKitCode(kitcode);
		} else {
			LOGGER.info("Successfully Received Flow Information For All.");
			flowVOs = flowRepo.findAll();
		}
		return flowVOs;
	}

	@Override
	public List<Map<String, Object>> getAvailableKitQtyByEmitter(Long orgId, Long emitterId, String kitId,
			Long flowId) {

		FlowVO flowVO = flowRepo.findById(flowId).get();
		Set<Object[]> emitterAvailKitQty = kitRepo.findByAvailableKitQtyByEmitter(orgId, emitterId, kitId,
				flowVO.getFlowName());

		return getAvaikitDetails(emitterAvailKitQty);
	}

	private List<Map<String, Object>> getAvaikitDetails(Set<Object[]> emitterAvailKitQty) {
		List<Map<String, Object>> avlKitQty = new ArrayList<>();
		for (Object[] ps : emitterAvailKitQty) {
			Map<String, Object> part = new HashMap<>();
			part.put("kitCode", ps[0] != null ? ps[0].toString() : "");
			part.put("kitAvailQty", ps[1] != null ? Integer.parseInt(ps[1].toString()) : 0);
			avlKitQty.add(part);
		}
		return avlKitQty;
	}

	@Override
	public Set<Object[]> getAssetDetailsByAssetForAssetInward(Long orgId, String stockBranch, String assetCode,
			int qty) {

		return assetStockDetailsRepo.getAssetDetailsByAssetForAssetInward(orgId, stockBranch, assetCode, qty);
	}

	@Override
	public Set<Object[]> getAvailAssetDetailsByBranch(Long orgId, String stockBranch, String category) {
		return assetStockDetailsRepo.getAvailAssetDetailsByBranch(orgId, stockBranch, category);
	}

	@Override
	public AssetTaggingVO getTaggingById(Long id) {
		return assetTaggingRepo.findById(id).get();
	}

	@Override
	public List<BranchVO> getAllBranch(Long orgId) {
		return branchRepo.findAllBranchByOrgId(orgId);
	}

	@Override
	public List<BranchVO> getAllActiveBranch(Long orgId) {
		return branchRepo.findAllActiveBranch(orgId);
	}

	@Override
	public Optional<BranchVO> getBranchById(Long id) {
		return branchRepo.findById(id);
	}

	@Override
	public BranchVO createUpdateBranch(BranchDTO branchDTO) throws ApplicationException {
		BranchVO branchVO = new BranchVO();
		if (branchDTO.getId() != 0) {
			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Branch details"));
		}
		getBranchVOFromBranchDTO(branchDTO, branchVO);

		return branchRepo.save(branchVO);
	}

	private void getBranchVOFromBranchDTO(BranchDTO branchDTO, BranchVO branchVO) throws ApplicationException {
		if (branchDTO.getId() != 0) {
			BranchVO existingBranch = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch with ID " + branchDTO.getId() + " not found"));

			if (!existingBranch.getBranchName().equals(branchDTO.getBranchName())) {
				if (branchRepo.existsByBranchNameAndOrgId(branchDTO.getBranchName(), existingBranch.getOrgId())) {
					throw new ApplicationException("Branch Name already exists");
				}
				existingBranch.setBranchName(branchDTO.getBranchName());
			}

			if (!existingBranch.getBranchCode().equals(branchDTO.getBranchCode())) {
				if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), existingBranch.getOrgId())) {
					throw new ApplicationException("Branch Code already exists");
				}
				existingBranch.setBranchCode(branchDTO.getBranchCode());
			}

			existingBranch.setAddress1(branchDTO.getAddress1());
			existingBranch.setAddress2(branchDTO.getAddress2());
			existingBranch.setCity(branchDTO.getCity());
			existingBranch.setState(branchDTO.getState());
			existingBranch.setCountry(branchDTO.getCountry());
			existingBranch.setPinCode(branchDTO.getPinCode());
			existingBranch.setPhone(branchDTO.getPhone());
			existingBranch.setModifiedBy(branchDTO.getCreatedBy());
			existingBranch.setGST(branchDTO.getGST());
			existingBranch.setActive(branchDTO.isActive());
			existingBranch.setPan(branchDTO.getPan());
			existingBranch.setOrgId(branchDTO.getOrgId());
			existingBranch.setCurrency(branchDTO.getCurrency());
		} else {
			if (branchRepo.existsByBranchNameAndOrgId(branchDTO.getBranchName(), branchDTO.getOrgId())) {
				throw new ApplicationException("Branch Name already exists");
			}
			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
				throw new ApplicationException("Branch Code already exists");
			}
			branchVO.setBranchName(branchDTO.getBranchName());
			branchVO.setBranchCode(branchDTO.getBranchCode());
			branchVO.setAddress1(branchDTO.getAddress1());
			branchVO.setAddress2(branchDTO.getAddress2());
			branchVO.setCity(branchDTO.getCity());
			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setModifiedBy(branchDTO.getCreatedBy());
			branchVO.setActive(branchDTO.isActive());
			branchVO.setState(branchDTO.getState());
			branchVO.setCountry(branchDTO.getCountry());
			branchVO.setPinCode(branchDTO.getPinCode());
			branchVO.setPhone(branchDTO.getPhone());
			branchVO.setGST(branchDTO.getGST());
			branchVO.setPan(branchDTO.getPan());
			branchVO.setOrgId(branchDTO.getOrgId());
			branchVO.setCurrency(branchDTO.getCurrency());
		}

	}

	@Override
	public void deleteBranch(Long id) {
		branchRepo.deleteById(id);
	}

	// BinAllotment

	@Override
	public BinAllotmentNewVO createBinAllotment(BinAllotmentDTO binAllotmentDTO) {

//		boolean isBinReqNoExist = binAllotmentNewRepo.existsByBinReqNo(binAllotmentDTO.getBinReqNo());
//
//		if (isBinReqNoExist) {
//			throw new RuntimeException("ReqNo " + binAllotmentDTO.getBinReqNo() + " already exists.");
//		}

		BinAllotmentNewVO binAllotmentNewVO = new BinAllotmentNewVO();
		// Set Docid for Bin Allotment
		int finyr = binAllotmentNewRepo.getFinyr();
		String binallotment = finyr + "BA" + binAllotmentNewRepo.finddocid();
		binAllotmentNewVO.setDocId(binallotment);
		binAllotmentNewRepo.nextDocseq();

		binAllotmentNewVO.setDocDate(binAllotmentDTO.getDocDate());
		binAllotmentNewVO.setBinReqNo(binAllotmentDTO.getBinReqNo());
		binAllotmentNewVO.setBinReqDate(binAllotmentDTO.getBinReqDate());
		binAllotmentNewVO.setCreatedBy(binAllotmentDTO.getCreatedby());
		binAllotmentNewVO.setModifiedBy(binAllotmentDTO.getCreatedby());
		binAllotmentNewVO.setOrgId(binAllotmentDTO.getOrgId());
		binAllotmentNewVO.setEmitterId(binAllotmentDTO.getEmitterId());
		CustomersVO customer = customersRepo.findById(binAllotmentDTO.getEmitterId()).get();
		binAllotmentNewVO.setEmitter(customer.getDisplayName());
		binAllotmentNewVO.setPartName(binAllotmentDTO.getPartName());
		binAllotmentNewVO.setPartCode(binAllotmentDTO.getPartCode());
		binAllotmentNewVO.setStockBranch(binAllotmentDTO.getStockBranch());
		binAllotmentNewVO.setFlowId(binAllotmentDTO.getFlowId());
		binAllotmentNewVO.setFlow(binAllotmentDTO.getFlow());
		binAllotmentNewVO.setKitCode(binAllotmentDTO.getKitCode());
		binAllotmentNewVO.setReqKitQty(binAllotmentDTO.getReqKitQty());
		binAllotmentNewVO.setAvlKitQty(binAllotmentDTO.getAvlKitQty());
		binAllotmentNewVO.setAllotkKitQty(binAllotmentDTO.getAllotKitQty());

		List<BinAllotmentDetailsVO> binAllotmentDetailsVO = new ArrayList<>();
		if (binAllotmentDTO.getBinAllotmentDetailsDTO() != null) {
			for (BinAllotmentDetailsDTO binAllotmentDetailsDTO : binAllotmentDTO.getBinAllotmentDetailsDTO()) {
				BinAllotmentDetailsVO binAllotmentDetails = new BinAllotmentDetailsVO();
				binAllotmentDetails.setRfId(binAllotmentDetailsDTO.getRfId());
				binAllotmentDetails.setTagCode(binAllotmentDetailsDTO.getTagCode());
				binAllotmentDetails.setAssetCode(binAllotmentDetailsDTO.getAssetCode());
				binAllotmentDetails.setAsset(binAllotmentDetailsDTO.getAsset());
				binAllotmentDetails.setQty(binAllotmentDetailsDTO.getQty());
				binAllotmentDetails.setBinAllotmentNewVO(binAllotmentNewVO);
				binAllotmentDetailsVO.add(binAllotmentDetails);
			}
		}
		binAllotmentNewVO.setBinAllotmentDetailsVO(binAllotmentDetailsVO);
		BinAllotmentNewVO allotmentNewVO = binAllotmentNewRepo.save(binAllotmentNewVO);
		List<BinAllotmentDetailsVO> allotmentDetailsVO = allotmentNewVO.getBinAllotmentDetailsVO();
		if (allotmentDetailsVO != null && !allotmentDetailsVO.isEmpty()) {
			for (BinAllotmentDetailsVO allotmentDetailsVO2 : allotmentDetailsVO) {

				AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
				assetStockDetailsVO.setStockRef(allotmentNewVO.getDocId());
				assetStockDetailsVO.setStockDate(allotmentNewVO.getDocDate());
				assetStockDetailsVO.setSkuCode(allotmentDetailsVO2.getAssetCode());
				assetStockDetailsVO.setSku(allotmentDetailsVO2.getAsset());
				assetStockDetailsVO.setSkuQty(-1);
				assetStockDetailsVO.setOrgId(allotmentNewVO.getOrgId());
				assetStockDetailsVO.setRfId(allotmentDetailsVO2.getRfId());
				assetStockDetailsVO.setTagCode(allotmentDetailsVO2.getTagCode());
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(allotmentDetailsVO2.getAssetCode()));
				assetStockDetailsVO.setSCode(allotmentNewVO.getScode()); // Assuming getScode() returns the correct
				assetStockDetailsVO.setSourceId(allotmentDetailsVO2.getId()); // value
				assetStockDetailsVO.setScreen("Bin Allotment");
				assetStockDetailsVO.setPm("M");
				assetStockDetailsVO.setStatus("S");
				assetStockDetailsVO.setFinyr(allotmentNewVO.getFinyr());
				assetStockDetailsVO.setStockBranch(allotmentNewVO.getStockBranch());
				assetStockDetailsRepo.save(assetStockDetailsVO);
			}
			for (BinAllotmentDetailsVO allotmentDetailsVO2 : allotmentDetailsVO) {

				Long flow = issueRequestRepo.getFlowIdByrequestId(binAllotmentDTO.getBinReqNo());
				String emitter = flowRepo.findEmiterbyFlowId(flow);
				String orgin = flowRepo.findOrigionbyFlowId(flow);

				AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
				assetStockDetailsVO.setStockRef(allotmentNewVO.getDocId());
				assetStockDetailsVO.setStockDate(allotmentNewVO.getDocDate());
				assetStockDetailsVO.setSkuCode(allotmentDetailsVO2.getAssetCode());
				assetStockDetailsVO.setSku(allotmentDetailsVO2.getAsset());
				assetStockDetailsVO.setSkuQty(1);
				assetStockDetailsVO.setOrgId(allotmentNewVO.getOrgId());
				assetStockDetailsVO.setRfId(allotmentDetailsVO2.getRfId());
				assetStockDetailsVO.setTagCode(allotmentDetailsVO2.getTagCode());
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setBinLocation("");
				assetStockDetailsVO.setCancelRemarks("");
				assetStockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(allotmentDetailsVO2.getAssetCode()));
				assetStockDetailsVO.setStockLocation("");
				assetStockDetailsVO.setSCode(allotmentNewVO.getScode()); // Assuming getScode() returns the correct
				assetStockDetailsVO.setSourceId(allotmentDetailsVO2.getId()); // value
				assetStockDetailsVO.setScreen("Bin Allotment");
				assetStockDetailsVO.setPm("P");
				assetStockDetailsVO.setStatus("M");
				assetStockDetailsVO.setFinyr(allotmentNewVO.getFinyr());
				assetStockDetailsVO.setStockBranch(emitter + "-" + orgin);
				assetStockDetailsRepo.save(assetStockDetailsVO);
			}
		}
		return binAllotmentNewVO;
	}

	@Override
	public Set<Object[]> getBranchLocationByFlow(Long orgId, Long flow) {

		return flowRepo.findByBranchcode(orgId, flow);
	}

	// Excel File Uploads

	private int totalRows = 0; // Initialize totalRows

	private int successfulUploads = 0; // Initialize successfulUploads

	@Override
	@Transactional
	public void ExcelUploadForAssetCategory(MultipartFile[] files, CustomerAttachmentType type, Long orgId,
			String createdBy) throws ApplicationException {
		List<AssetCategoryVO> assetCategoryVOsToSave = new ArrayList<>();
		totalRows = 0; // Reset totalRows for each execution
		successfulUploads = 0; // Reset successfulUploads for each execution

		// Process each uploaded file
		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
				List<String> errorMessages = new ArrayList<>();
				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
				Row headerRow = sheet.getRow(0);
				if (!isHeaderValidAssetCategory(headerRow)) {
					throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
				}

				// Check all rows for validity first
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip header row
					}

					totalRows++; // Increment totalRows

					String assetType = row.getCell(0).getStringCellValue();
					String category = row.getCell(1).getStringCellValue();
					String categoryCode = row.getCell(2).getStringCellValue();

					// Validate each row
					try {
						if (assetCategoryRepo.existsByCategoryAndOrgId(category, orgId)) {
							errorMessages.add("Category " + category + " Already exists for this Organization. Row: "
									+ (row.getRowNum() + 1));
						}
						if (assetCategoryRepo.existsByCategoryCodeAndOrgId(categoryCode, orgId)) {
							errorMessages.add("Category Code " + categoryCode
									+ " Already exists for this Organization. Row: " + (row.getRowNum() + 1));
						}
						AssetTypeVO assetTypeVO = assetTypeRepo.findByOrgIdAndAssetType(orgId, assetType);
						if (assetTypeVO == null) {
							errorMessages.add("Asset Type " + assetType + " not found for orgId: " + orgId
									+ " and assetType: " + assetType + ". Row: " + (row.getRowNum() + 1));
						}
					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// If there are errors, throw ApplicationException and do not save any rows
				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

				// No errors found, now save all rows
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip header row
					}

					String assetType = row.getCell(0).getStringCellValue();
					String category = row.getCell(1).getStringCellValue();
					String categoryCode = row.getCell(2).getStringCellValue();

					// Create AssetCategoryVO and add to list for batch saving
					AssetCategoryVO assetCategoryVO = new AssetCategoryVO();
					assetCategoryVO.setOrgId(orgId);
					assetCategoryVO.setActive(true);
					assetCategoryVO.setAssetType(assetType.toUpperCase());
					assetCategoryVO.setCategory(category.toUpperCase());
					assetCategoryVO.setCategoryCode(categoryCode.toUpperCase());
					assetCategoryVOsToSave.add(assetCategoryVO);
					successfulUploads++; // Increment successfulUploads
				}
			} catch (IOException e) {
				// Handle IO exceptions specific to the file
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}

		// Batch save all AssetCategoryVOs
		assetCategoryRepo.saveAll(assetCategoryVOsToSave);
	}

	private boolean isHeaderValidAssetCategory(Row headerRow) {
		if (headerRow == null) {
			return false;
		}
		int expectedColumnCount = 3;
		if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
			return false;
		}
		return "assetType".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))
				&& "category".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1)))
				&& "categoryCode".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2)));
	}

	private boolean isRowEmpty(Row row) {
		for (Cell cell : row) {
			if (cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	// Method to retrieve total rows processed
	public int getTotalRows() {
		return totalRows;
	}

	// Method to retrieve successful uploads count
	public int getSuccessfulUploads() {
		return successfulUploads;
	}

	private int totalRows2 = 0; // Initialize totalRows

	private int successfulUploads2 = 0; // Initialize successfulUploads

	@Override
	@Transactional
	public void ExcelUploadForUnit(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy)
			throws ApplicationException {
		List<UnitVO> unitVOs = new ArrayList<>();
		totalRows = 0; // Reset totalRows for each execution
		successfulUploads = 0; // Reset successfulUploads for each execution

		// Process each uploaded file
		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet

				List<String> errorMessages = new ArrayList<>();

				Row headerRow = sheet.getRow(0);
				if (!isHeaderValidUnit(headerRow)) {
					throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
				}

				// Check all rows for validity first
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip header row
					}

					totalRows++; // Increment totalRows

					String unit = row.getCell(0).getStringCellValue();

					// Validate each row
					try {
						if (unitRepo.existsByUnitAndOrgId(unit, orgId)) {
							errorMessages.add("unit " + unit + " Already exists for this Organization. Row: "
									+ (row.getRowNum() + 1));
						}

					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// If there are errors, throw ApplicationException and do not save any rows
				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

				// No errors found, now save all rows
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip header row
					}

					String unit = row.getCell(0).getStringCellValue();

					// Create AssetCategoryVO and add to list for batch saving
					UnitVO unitVO = new UnitVO();
					unitVO.setOrgId(orgId);
					unitVO.setActive(true);
					unitVO.setUnit(unit);
					unitVOs.add(unitVO);
					successfulUploads++; // Increment successfulUploads
				}
			} catch (IOException e) {
				// Handle IO exceptions specific to the file
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}

		// Batch save all AssetCategoryVOs
		unitRepo.saveAll(unitVOs);
	}

	private boolean isHeaderValidUnit(Row headerRow) {
		if (headerRow == null) {
			return false;
		}
		int expectedColumnCount = 1;
		if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
			return false;
		}
		return "unit".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)));
	}

	private boolean isRowEmpty2(Row row) {
		for (Cell cell : row) {
			if (cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	private String getStringCellValue2(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	// Method to retrieve total rows processed
	public int getTotalRows2() {
		return totalRows;
	}

	// Method to retrieve successful uploads count
	public int getSuccessfulUploads2() {
		return successfulUploads;
	}

	private int totalRows3 = 0; // Initialize totalRows

	private int successfulUploads3 = 0; // Initialize successfulUploads

	@Override
	@Transactional
	public void ExcelUploadForStockBranch(MultipartFile[] files, CustomerAttachmentType type, Long orgId,
			String createdBy) throws ApplicationException {
		List<StockBranchVO> stockBranchVOs = new ArrayList<>();
		totalRows = 0; // Reset totalRows for each execution
		successfulUploads = 0; // Reset successfulUploads for each execution

		// Process each uploaded file
		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet

				List<String> errorMessages = new ArrayList<>();
				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement

				Row headerRow = sheet.getRow(0);
				if (!isHeaderValidStockBranch(headerRow)) {
					throw new ApplicationException("Invalid Excel format.Please Refer The Sample File");
				}
				// Check all rows for validity first
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip header row
					}

					totalRows++; // Increment totalRows
					String branchName = row.getCell(0).getStringCellValue();
					String branchCode = row.getCell(1).getStringCellValue();
					// Validate each row
					try {
						if (stockBranchRepo.existsByBranchAndOrgId(branchName, orgId)) {
							errorMessages.add("Branch " + branchName + " already exists for this Organization. Row: "
									+ (row.getRowNum() + 1));
						}
						if (stockBranchRepo.existsBybranchCodeAndOrgId(branchCode, orgId)) {
							errorMessages.add("Branch Code " + branchCode
									+ " already exists for this Organization. Row: " + (row.getRowNum() + 1));
						}
					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// If there are errors, throw ApplicationException and do not save any rows
				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

				// No errors found, now save all rows
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip header row
					}

					String branchName = row.getCell(0).getStringCellValue();
					String branchCode = row.getCell(1).getStringCellValue();

					// Create StockBranchVO and add to list for batch saving
					StockBranchVO vo = new StockBranchVO();
					vo.setBranch(branchName);
					vo.setBranchCode(branchCode);
					vo.setOrgId(orgId);

					stockBranchVOs.add(vo); // Add VO to list

					successfulUploads++; // Increment successfulUploads4
				}
			} catch (IOException e) {
				// Handle IO exceptions specific to the file
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}

		// Batch save all AssetCategoryVOs
		stockBranchRepo.saveAll(stockBranchVOs);
	}

	private boolean isHeaderValidStockBranch(Row headerRow) {
		if (headerRow == null) {
			return false;
		}
		int expectedColumnCount = 2;
		if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
			return false;
		}
		return "branchName".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))
				&& "branchCode".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1)));
	}

	private boolean isRowEmpty3(Row row) {
		for (Cell cell : row) {
			if (cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	private String getStringCellValue3(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	// Method to retrieve total rows processed
	public int getTotalRows3() {
		return totalRows;
	}

	// Method to retrieve successful uploads count
	public int getSuccessfulUploads3() {
		return successfulUploads;
	}

	@Override
	public List<Map<String, Object>> getPartNoAndPartName(Long flowId, String kitNo, Long emitterId) {

		Set<Object[]> EmptystockDetails = assetStockDetailsRepo.getPartNameAndPartNoDetails(flowId, kitNo, emitterId);
		return emptyGatheringDetails(EmptystockDetails);
	}

	private List<Map<String, Object>> emptyGatheringDetails(Set<Object[]> emptystockDetails) {
		List<Map<String, Object>> oemEmptyStockdetails = new ArrayList<>();
		for (Object[] ps : emptystockDetails) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", ps[0] != null ? ps[0].toString() : "");
			part.put("partName", ps[1] != null ? ps[1].toString() : "");
			part.put("kitNo", ps[2] != null ? ps[2].toString() : "");
			part.put("partQty", ps[3] != null ? Integer.parseInt(ps[3].toString()) : 0);

			oemEmptyStockdetails.add(part);
		}
		return oemEmptyStockdetails;
	}

	@Override
	public Set<Object[]> getAvalkitqtyByWarehouse(String warehouse, String kitName) {

		return warehouseRepo.getAvalkitqtyByWarehouse(warehouse, kitName);
	}

	@Override
	public BinRetrievalVO createBinRetrieval(BinRetrievalDTO binRetrievalDTO) {

		BinRetrievalVO binRetrievalVO = new BinRetrievalVO();
		String finyr = binRetrievalRepo.findFinyr();
		String binRetrievalDocId = finyr + "BRI" + binRetrievalRepo.finddocid();
		binRetrievalVO.setDocId(binRetrievalDocId);
		binRetrievalRepo.nextSeq();

		binRetrievalVO.setDocDate(binRetrievalDTO.getDocDate());
		binRetrievalVO.setRetrievalWarehouse(binRetrievalDTO.getRetrievalWarehouse());
		binRetrievalVO.setFromStockBranch(binRetrievalDTO.getFromStockBranch());
		binRetrievalVO.setToStockBranch(binRetrievalDTO.getToStockBranch());
		binRetrievalVO.setPickupDocId(binRetrievalDTO.getPickupDocId());
		binRetrievalVO.setPickupDate(binRetrievalDTO.getPickupDate());
		binRetrievalVO.setTransPortDocNo(binRetrievalDTO.getTransPortDocNo());
		binRetrievalVO.setTransPorter(binRetrievalDTO.getTransPorter());
		binRetrievalVO.setDriverName(binRetrievalDTO.getDriverName());
		binRetrievalVO.setHandOverBy(binRetrievalDTO.getHandOverBy());
		binRetrievalVO.setDriverPhoneNo(binRetrievalDTO.getDriverPhoneNo());
		binRetrievalVO.setVechicleNo(binRetrievalDTO.getVechicleNo());
		binRetrievalVO.setCreatedby(binRetrievalDTO.getCreatedby());
		binRetrievalVO.setOrgId(binRetrievalDTO.getOrgId());

		List<BinRetrievalDetailsVO> binRetrievalDetailsVO = new ArrayList<>();
		if (binRetrievalDTO.getBinRetrievalDetailsDTO() != null) {
			for (BinRetrievalDetailsDTO binRetrievalDetailsDTO : binRetrievalDTO.getBinRetrievalDetailsDTO()) {
				BinRetrievalDetailsVO detailsVO = new BinRetrievalDetailsVO();
				detailsVO.setCategory(binRetrievalDetailsDTO.getCategory());
				detailsVO.setAsset(binRetrievalDetailsDTO.getAsset());
				detailsVO.setAssetCode(binRetrievalDetailsDTO.getAssetCode());
				detailsVO.setInvqty(binRetrievalDetailsDTO.getInvqty());
				detailsVO.setRecqty(binRetrievalDetailsDTO.getRecqty());
				detailsVO.setShortQty(binRetrievalDetailsDTO.getShortQty());
				detailsVO.setDamageQty(binRetrievalDetailsDTO.getDamageQty());
				detailsVO.setBinRetrievalVO(binRetrievalVO);

				binRetrievalDetailsVO.add(detailsVO);
			}
		}

		binRetrievalVO.setBinRetrievalDetailsVO(binRetrievalDetailsVO);
		BinRetrievalVO binRetrievalVO2 = binRetrievalRepo.save(binRetrievalVO);
		List<BinRetrievalDetailsVO> binRetrievalDetailsVOs = binRetrievalVO2.getBinRetrievalDetailsVO();
		if (binRetrievalDetailsVOs != null && !binRetrievalDetailsVOs.isEmpty()) {
			for (BinRetrievalDetailsVO retrievalDetailsVO : binRetrievalDetailsVOs) {
				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(binRetrievalVO2.getPickupDocId());
				stockDetailsVO.setStockBranch(binRetrievalVO2.getToStockBranch());
				stockDetailsVO.setStockDate(binRetrievalVO2.getPickupDate());
				stockDetailsVO.setSku(retrievalDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(retrievalDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(retrievalDetailsVO.getRecqty() * -1);
				stockDetailsVO.setOrgId(binRetrievalVO2.getOrgId());
				stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(retrievalDetailsVO.getAssetCode()));
				stockDetailsVO.setStatus("M");
				stockDetailsVO.setScreen(binRetrievalVO2.getScreen());
				stockDetailsVO.setSCode(binRetrievalVO2.getScode());
				stockDetailsVO.setPm("M");
				stockDetailsVO.setStockSource("");
				stockDetailsVO.setBinLocation("");
				stockDetailsVO.setCancelRemarks("");
				stockDetailsVO.setStockLocation("");
				stockDetailsVO.setSourceId(retrievalDetailsVO.getId());
				stockDetailsVO.setFinyr(binRetrievalVO2.getFinYr());
				assetStockDetailsRepo.save(stockDetailsVO);
			}
			for (BinRetrievalDetailsVO retrievalDetailsVO : binRetrievalDetailsVOs) {
				int dmgQty = retrievalDetailsVO.getDamageQty();
				if (dmgQty > 0) {
					AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
					stockDetailsVO.setStockRef(binRetrievalVO2.getDocId());
					stockDetailsVO.setStockBranch(binRetrievalVO2.getRetrievalWarehouse());
					stockDetailsVO.setStockDate(binRetrievalVO2.getDocDate());
					stockDetailsVO.setSku(retrievalDetailsVO.getAsset());
					stockDetailsVO.setSkuCode(retrievalDetailsVO.getAssetCode());
					stockDetailsVO.setSkuQty(dmgQty);
					stockDetailsVO.setOrgId(binRetrievalVO2.getOrgId());
					stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(retrievalDetailsVO.getAssetCode()));
					stockDetailsVO.setStatus("D");
					stockDetailsVO.setScreen(binRetrievalVO2.getScreen());
					stockDetailsVO.setSCode(binRetrievalVO2.getScode());
					stockDetailsVO.setPm("P");
					stockDetailsVO.setStockSource("");
					stockDetailsVO.setBinLocation("");
					stockDetailsVO.setCancelRemarks("");
					stockDetailsVO.setStockLocation("");
					stockDetailsVO.setSourceId(retrievalDetailsVO.getId());
					stockDetailsVO.setFinyr(binRetrievalVO2.getFinYr());
					assetStockDetailsRepo.save(stockDetailsVO);
				}
			}
			for (BinRetrievalDetailsVO retrievalDetailsVO : binRetrievalDetailsVOs) {
				int grnqty = retrievalDetailsVO.getRecqty() - retrievalDetailsVO.getDamageQty();
				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(binRetrievalVO2.getDocId());
				stockDetailsVO.setStockBranch(binRetrievalVO2.getRetrievalWarehouse());
				stockDetailsVO.setStockDate(binRetrievalVO2.getDocDate());
				stockDetailsVO.setSku(retrievalDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(retrievalDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(grnqty);
				stockDetailsVO.setOrgId(binRetrievalVO2.getOrgId());
				stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(retrievalDetailsVO.getAssetCode()));
				stockDetailsVO.setStatus("S");
				stockDetailsVO.setScreen(binRetrievalVO2.getScreen());
				stockDetailsVO.setSCode(binRetrievalVO2.getScode());
				stockDetailsVO.setPm("P");
				stockDetailsVO.setStockSource("");
				stockDetailsVO.setBinLocation("");
				stockDetailsVO.setCancelRemarks("");
				stockDetailsVO.setStockLocation("");
				stockDetailsVO.setSourceId(retrievalDetailsVO.getId());
				stockDetailsVO.setFinyr(binRetrievalVO2.getFinYr());
				assetStockDetailsRepo.save(stockDetailsVO);

			}
		}
		return binRetrievalVO2;
	}

	@Override
	public List<Map<String, Object>> getPendingBinRetrievalTransportPickupDetails(Long orgId, Long userId) {
		Set<Object[]> getDetails = transportPickupRepo.getPendingPickupDetails(orgId, userId);
		return details(getDetails);
	}

	private List<Map<String, Object>> details(Set<Object[]> getDetails) {

		List<Map<String, Object>> det = new ArrayList<>();
		for (Object[] ps : getDetails) {
			Map<String, Object> part = new HashMap<>();
			part.put("pickupNo", ps[0] != null ? ps[0].toString() : "");
			part.put("pickupDate", ps[1] != null ? ps[1].toString() : "");
			part.put("driverName", ps[2] != null ? ps[2].toString() : "");
			part.put("driverPhoneNo", ps[3] != null ? ps[3].toString() : "");
			part.put("transportDocNo", ps[4] != null ? ps[4].toString() : "");
			part.put("vehicleNo", ps[5] != null ? ps[5].toString() : "");
			part.put("fromStockBranch", ps[6] != null ? ps[6].toString() : "");
			part.put("toStockBranch", ps[7] != null ? ps[7].toString() : "");
			part.put("transporter", ps[8] != null ? ps[8].toString() : "");
			part.put("handoverBy", ps[9] != null ? ps[9].toString() : "");
			det.add(part);
		}
		return det;
	}

	@Override
	public List<Map<String, Object>> getTransportPickupDetailsByDocId(Long orgId, String pickupDocId) {
		Set<Object[]> getPickupDetails = transportPickupRepo.getPickupDetails(orgId, pickupDocId);
		return pickDetails(getPickupDetails);
	}

	private List<Map<String, Object>> pickDetails(Set<Object[]> getPickupDetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] ps : getPickupDetails) {
			Map<String, Object> part = new HashMap<>();
			part.put("category", ps[0] != null ? ps[0].toString() : "");
			part.put("assetCode", ps[1] != null ? ps[1].toString() : "");
			part.put("asset", ps[2] != null ? ps[2].toString() : "");
			part.put("invQty", ps[3] != null ? Integer.parseInt(ps[3].toString()) : 0);
			details.add(part);
		}
		return details;
	}

	@Override
	public String getDocIdByBinRetrieval() {
		String finyr = binRetrievalRepo.findFinyr();
		String binRetrievalDocId = finyr + "BRI" + binRetrievalRepo.finddocid();
		return binRetrievalDocId;
	}

	@Override
	public List<BinRetrievalVO> getBinReterivalByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return binRetrievalRepo.findByOrgId(orgId);
	}

	@Override
	public List<BinRetrievalVO> getBinReterivalByDocId(String docId) {
		// TODO Auto-generated method stub
		return binRetrievalRepo.findByDocId(docId);
	}

	@Override
	public List<BinRetrievalVO> getAllBinReterival(Long id) {
		// TODO Auto-generated method stub
		return binRetrievalRepo.getAllBinReterival(id);
	}

	@Override
	public List<Map<String, Object>> getAvilQtyByEmitterBykitWise(Long orgId, Long userId) {

		Set<Object[]> getKitAvilQtyDetails = binInwardRepo.getKitAvilQtyDetails(orgId, userId);
		return kitDetails(getKitAvilQtyDetails);
	}

	private List<Map<String, Object>> kitDetails(Set<Object[]> getKitAvilQtyDetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] kd : getKitAvilQtyDetails) {
			Map<String, Object> part = new HashMap<>();
			part.put("emitterId", kd[0] != null ? Integer.parseInt(kd[0].toString()) : 0);
			part.put("orgId", kd[1] != null ? Integer.parseInt(kd[1].toString()) : 0);
			part.put("emitterName", kd[2] != null ? kd[2].toString() : "");
			part.put("flowName", kd[3] != null ? kd[3].toString() : "");
			part.put("flowID", kd[4] != null ? Integer.parseInt(kd[4].toString()) : 0);
			part.put("kitNo", kd[5] != null ? kd[5].toString() : "");
			part.put("avilQty", kd[6] != null ? Integer.parseInt(kd[6].toString()) : 0);
			details.add(part);
		}
		return details;
	}

	@Override
	public List<String> getActiveAssetcategory(Long orgId, String assetCategory) {
		Set<Object[]> getActiveCategory = binInwardRepo.getActiveCategory(orgId, assetCategory);
		return getActiveCategoryDetails(getActiveCategory);
	}

	private List<String> getActiveCategoryDetails(Set<Object[]> getActiveCategory) {
		List<String> details = new ArrayList<>();
		for (Object[] kd : getActiveCategory) {
			if (kd[0] != null) {
				details.add(kd[0].toString());
			}
		}
		return details;
	}

	@Override
	public Map<String, List<VendorVO>> VendorsType(Long orgId) {
		List<VendorVO> vendorVO = vendorRepo.getAllActiveVendorsByOrgId(orgId);
		Map<String, List<VendorVO>> vendors = new HashMap<>();
		List<VendorVO> transporterVO = vendorRepo.getAllActiveTransportVendorsByOrgId(orgId);
		List<VendorVO> supplierVO = vendorRepo.getAllActiveSupplierVendorsByOrgId(orgId);
		vendors.put("transportVendorVO", transporterVO);
		vendors.put("supplierVendorVO", supplierVO);
		return vendors;
	}

	@Override
	public List<String> getAllActiveAssetcategory(Long orgId) {
		Set<Object[]> getActiveCategory = binInwardRepo.getActiveCategory1(orgId);
		return getActiveCategoryDetails1(getActiveCategory);
	}

	private List<String> getActiveCategoryDetails1(Set<Object[]> getActiveCategory1) {
		List<String> details = new ArrayList<>();
		for (Object[] kd : getActiveCategory1) {
			if (kd[0] != null) {
				details.add(kd[0].toString());
			}
		}
		return details;
	}

	@Override
	public Map<String, Object> createUpdateInvoice(InvoiceDTO invoiceDTO) throws ApplicationException {
		InvoiceVO invoiceVO=new InvoiceVO();
		String message;
		if(ObjectUtils.isEmpty(invoiceDTO.getId()))
		{
			List<InvoiceProductLinesVO>invoiceProductLinesVO= new ArrayList<>();
			if(invoiceDTO.getProductLines()!=null)
			{
				for(InvoiceProductLinesDTO invoiceProductLinesDTO : invoiceDTO.getProductLines())
				{
					InvoiceProductLinesVO invoiceProductLinesVO1= new InvoiceProductLinesVO();
					invoiceProductLinesVO1.setDescription(invoiceProductLinesDTO.getDescription());
					invoiceProductLinesVO1.setQuantity(invoiceProductLinesDTO.getQuantity());
					invoiceProductLinesVO1.setRate(invoiceProductLinesDTO.getRate());
					invoiceProductLinesVO1.setAmount(invoiceProductLinesDTO.getAmount());
					invoiceProductLinesVO1.setInvoiceVO(invoiceVO);
					invoiceProductLinesVO.add(invoiceProductLinesVO1);
				}
			}
			invoiceVO.setProductLines(invoiceProductLinesVO);
			invoiceVO.setCreatedBy(invoiceDTO.getCreatedBy());
			invoiceVO.setModifiedeBy(invoiceDTO.getCreatedBy());
			 String base64Image = invoiceDTO.getLogo();
			if (base64Image != null && base64Image.startsWith("data:image/")) {
	            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
	            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	            invoiceVO.setLogo(imageBytes);
	        }
			mapInvoiceDTOToInvoiceVO(invoiceDTO,invoiceVO);
			
			message="Invoice Created successfully";
		}
		else
		{
			
			invoiceVO=invoiceRepo.findById(invoiceDTO.getId()).get();
			List<InvoiceProductLinesVO>invoiceProductLinesVO2= invoiceProductLinesRepo.findByInvoiceVO(invoiceVO);
	        invoiceProductLinesRepo.deleteAll(invoiceProductLinesVO2);
	        
	        List<InvoiceProductLinesVO>invoiceProductLinesVO= new ArrayList<>();
	        if(invoiceDTO.getProductLines()!=null)
			{
				for(InvoiceProductLinesDTO invoiceProductLinesDTO : invoiceDTO.getProductLines())
				{
					InvoiceProductLinesVO invoiceProductLinesVO1= new InvoiceProductLinesVO();
					invoiceProductLinesVO1.setDescription(invoiceProductLinesDTO.getDescription());
					invoiceProductLinesVO1.setQuantity(invoiceProductLinesDTO.getQuantity());
					invoiceProductLinesVO1.setRate(invoiceProductLinesDTO.getRate());
					invoiceProductLinesVO1.setAmount(invoiceProductLinesDTO.getAmount());
					invoiceProductLinesVO1.setInvoiceVO(invoiceVO);
					invoiceProductLinesVO.add(invoiceProductLinesVO1);
				}
			}
	        invoiceVO.setModifiedeBy(invoiceDTO.getCreatedBy());
	        invoiceVO.setProductLines(invoiceProductLinesVO);
	        mapInvoiceDTOToInvoiceVO(invoiceDTO,invoiceVO);
	        String base64Image = invoiceDTO.getLogo();
	        if (base64Image != null && base64Image.startsWith("data:image/")) {
	            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
	            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	            invoiceVO.setLogo(imageBytes);
	        }
	        message = "Invoice Updated successfully";
			
		}
		invoiceRepo.save(invoiceVO);
		Map<String, Object> response = new HashMap<>();
	    response.put("invoiceVO", invoiceVO);
	    response.put("message", message);
	    return response;
	}

	private void mapInvoiceDTOToInvoiceVO(InvoiceDTO invoiceDTO, InvoiceVO invoiceVO) {
        invoiceVO.setLogoWidth(invoiceDTO.getLogoWidth());
        invoiceVO.setTitle(invoiceDTO.getTitle());
        invoiceVO.setCompanyName(invoiceDTO.getCompanyName());
        invoiceVO.setName(invoiceDTO.getName());
        invoiceVO.setCompanyAddress(invoiceDTO.getCompanyAddress());
        invoiceVO.setCompanyAddress2(invoiceDTO.getCompanyAddress2());
        invoiceVO.setCompanyCountry(invoiceDTO.getCompanyCountry());
        invoiceVO.setBillTo(invoiceDTO.getBillTo());
        invoiceVO.setClientName(invoiceDTO.getClientName());
        invoiceVO.setClientAddress(invoiceDTO.getClientAddress());
        invoiceVO.setClientAddress2(invoiceDTO.getClientAddress2());
        invoiceVO.setClientCountry(invoiceDTO.getClientCountry());
        invoiceVO.setInvoiceTitleLabel(invoiceDTO.getInvoiceTitleLabel());
        invoiceVO.setInvoiceTitle(invoiceDTO.getInvoiceTitle());
        invoiceVO.setInvoiceDateLabel(invoiceDTO.getInvoiceDateLabel());
        invoiceVO.setInvoiceDate(LocalDate.now());
        invoiceVO.setInvoiceDueDateLabel(invoiceDTO.getInvoiceDueDateLabel());
        invoiceVO.setInvoiceDueDate(invoiceDTO.getInvoiceDueDate());
        invoiceVO.setProductLineDescription(invoiceDTO.getProductLineDescription());
        invoiceVO.setProductLineQuantity(invoiceDTO.getProductLineQuantity());
        invoiceVO.setProductLineQuantityRate(invoiceDTO.getProductLineQuantityRate());
        invoiceVO.setProductLineQuantityAmount(invoiceDTO.getProductLineQuantityAmount());
        invoiceVO.setNotesLabel(invoiceDTO.getNotesLabel());
        invoiceVO.setNotes(invoiceDTO.getNotes());
        invoiceVO.setTaxLabel(invoiceDTO.getTaxLabel());
        invoiceVO.setTaxLabel1(invoiceDTO.getTaxLabel1());
        invoiceVO.setTerm(invoiceDTO.getTerm());
        invoiceVO.setTermLabel(invoiceDTO.getTermLabel());
        invoiceVO.setTotalLabel(invoiceDTO.getTotalLabel());
        invoiceVO.setSubTotalLabel(invoiceDTO.getSubTotalLabel());
        invoiceVO.setOrgId(invoiceDTO.getOrgId());
        
        
	}

	@Override
	public List<InvoiceVO> getAllInvoice(Long orgId) {
		
		return invoiceRepo.findAllByOrgId(orgId);
	}

	@Override
	public InvoiceVO getInvoiceById(Long id) {
		// TODO Auto-generated method stub
		return invoiceRepo.findById(id).get();
		}

	@Override
	public Map<String, Object> createUpdateTaxInvoice(TaxInvoiceDTO taxInvoiceDTO) throws ApplicationException {
	    TaxInvoiceVO taxInvoiceVO = new TaxInvoiceVO();
	    String message;

	    if (ObjectUtils.isEmpty(taxInvoiceDTO.getId())) {
	        List<TaxInvoiceProductLineVO> taxInvoiceProductLineVOs = new ArrayList<>();
	        List<TaxInvoiceKitLineVO> taxInvoiceKitLineVOs = new ArrayList<>();

	        if (taxInvoiceDTO.getProductLines() != null) {
	            for (TaxInvoiceProductLineDTO productLineDTO : taxInvoiceDTO.getProductLines()) {
	                TaxInvoiceProductLineVO productLineVO = new TaxInvoiceProductLineVO();
	                productLineVO.setDescription(productLineDTO.getDescription());
	                productLineVO.setQuantity(productLineDTO.getQuantity());
	                productLineVO.setRate(productLineDTO.getRate());
	                productLineVO.setAmount(productLineDTO.getAmount());
	                productLineVO.setTaxInvoiceVO(taxInvoiceVO);
	                taxInvoiceProductLineVOs.add(productLineVO);
	            }
	        }

	        if (taxInvoiceDTO.getKitLines() != null) {
	            for (TaxInvoiceKitLineDTO kitLineDTO : taxInvoiceDTO.getKitLines()) {
	                TaxInvoiceKitLineVO kitLineVO = new TaxInvoiceKitLineVO();
	                kitLineVO.setDate(kitLineDTO.getDate());
	                kitLineVO.setManifestNo(kitLineDTO.getManifestNo());
	                kitLineVO.setEmitter(kitLineDTO.getEmitter());
	                kitLineVO.setLocation(kitLineDTO.getLocation());
	                kitLineVO.setKitNo(kitLineDTO.getKitNo());
	                kitLineVO.setKitQty(kitLineDTO.getKitQty());
	                kitLineVO.setTaxInvoiceVO(taxInvoiceVO);
	                taxInvoiceKitLineVOs.add(kitLineVO);
	            }
	        }

	        taxInvoiceVO.setProductLines(taxInvoiceProductLineVOs);
	        taxInvoiceVO.setKitLines(taxInvoiceKitLineVOs);
	        taxInvoiceVO.setCreatedBy(taxInvoiceDTO.getCreatedBy());
	        taxInvoiceVO.setModifiedBy(taxInvoiceDTO.getCreatedBy());

	        String base64Image = taxInvoiceDTO.getLogo();
	        if (base64Image != null && base64Image.startsWith("data:image/")) {
	            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
	            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	            taxInvoiceVO.setLogo(imageBytes);
	        }

	        mapTaxInvoiceDTOToTaxInvoiceVO(taxInvoiceDTO, taxInvoiceVO);
	        message = "Tax Invoice Created successfully";
	    } else {
	        taxInvoiceVO = taxInvoiceRepo.findById(taxInvoiceDTO.getId()).get();
	       
	        List<TaxInvoiceProductLineVO> existingProductLines = taxInvoiceProductLineRepo.findByTaxInvoiceVO(taxInvoiceVO);
	        taxInvoiceProductLineRepo.deleteAll(existingProductLines);
	        
	        List<TaxInvoiceKitLineVO> existingKitLines = taxInvoiceKitLineRepo.findByTaxInvoiceVO(taxInvoiceVO);
	        taxInvoiceKitLineRepo.deleteAll(existingKitLines);
	        
	        List<TaxInvoiceProductLineVO> taxInvoiceProductLineVOs = new ArrayList<>();
	        List<TaxInvoiceKitLineVO> taxInvoiceKitLineVOs = new ArrayList<>();

	        if (taxInvoiceDTO.getProductLines() != null) {
	            for (TaxInvoiceProductLineDTO productLineDTO : taxInvoiceDTO.getProductLines()) {
	                TaxInvoiceProductLineVO productLineVO = new TaxInvoiceProductLineVO();
	                productLineVO.setDescription(productLineDTO.getDescription());
	                productLineVO.setQuantity(productLineDTO.getQuantity());
	                productLineVO.setRate(productLineDTO.getRate());
	                productLineVO.setAmount(productLineDTO.getAmount());
	                productLineVO.setTaxInvoiceVO(taxInvoiceVO);
	                taxInvoiceProductLineVOs.add(productLineVO);
	            }
	        }

	        if (taxInvoiceDTO.getKitLines() != null) {
	            for (TaxInvoiceKitLineDTO kitLineDTO : taxInvoiceDTO.getKitLines()) {
	                TaxInvoiceKitLineVO kitLineVO = new TaxInvoiceKitLineVO();
	                kitLineVO.setDate(kitLineDTO.getDate());
	                kitLineVO.setManifestNo(kitLineDTO.getManifestNo());
	                kitLineVO.setEmitter(kitLineDTO.getEmitter());
	                kitLineVO.setLocation(kitLineDTO.getLocation());
	                kitLineVO.setKitNo(kitLineDTO.getKitNo());
	                kitLineVO.setKitQty(kitLineDTO.getKitQty());
	                kitLineVO.setTaxInvoiceVO(taxInvoiceVO);
	                taxInvoiceKitLineVOs.add(kitLineVO);
	            }
	        }

	        taxInvoiceVO.setModifiedBy(taxInvoiceDTO.getCreatedBy());
	        taxInvoiceVO.setProductLines(taxInvoiceProductLineVOs);
	        taxInvoiceVO.setKitLines(taxInvoiceKitLineVOs);
	        mapTaxInvoiceDTOToTaxInvoiceVO(taxInvoiceDTO, taxInvoiceVO);

	        String base64Image = taxInvoiceDTO.getLogo();
	        if (base64Image != null && base64Image.startsWith("data:image/")) {
	            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
	            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
	            taxInvoiceVO.setLogo(imageBytes);
	        }

	        message = "Tax Invoice Updated successfully";
	    }

	    taxInvoiceRepo.save(taxInvoiceVO);
	    Map<String, Object> response = new HashMap<>();
	    response.put("taxInvoiceVO", taxInvoiceVO);
	    response.put("message", message);
	    return response;
	}

	private void mapTaxInvoiceDTOToTaxInvoiceVO(TaxInvoiceDTO taxInvoiceDTO, TaxInvoiceVO taxInvoiceVO) {
	    taxInvoiceVO.setLogoWidth(taxInvoiceDTO.getLogoWidth());
	    taxInvoiceVO.setTitle(taxInvoiceDTO.getTitle());
	    taxInvoiceVO.setCompanyName(taxInvoiceDTO.getCompanyName());
	    taxInvoiceVO.setName(taxInvoiceDTO.getName());
	    taxInvoiceVO.setCompanyAddress(taxInvoiceDTO.getCompanyAddress());
	    taxInvoiceVO.setCompanyAddress2(taxInvoiceDTO.getCompanyAddress2());
	    taxInvoiceVO.setCompanyCountry(taxInvoiceDTO.getCompanyCountry());
	    taxInvoiceVO.setBillTo(taxInvoiceDTO.getBillTo());
	    taxInvoiceVO.setClientName(taxInvoiceDTO.getClientName());
	    taxInvoiceVO.setClientAddress(taxInvoiceDTO.getClientAddress());
	    taxInvoiceVO.setClientAddress2(taxInvoiceDTO.getClientAddress2());
	    taxInvoiceVO.setClientCountry(taxInvoiceDTO.getClientCountry());
	    taxInvoiceVO.setShipTo(taxInvoiceDTO.getShipTo());
	    taxInvoiceVO.setSclientName(taxInvoiceDTO.getSclientName());
	    taxInvoiceVO.setSclientAddress(taxInvoiceDTO.getSclientAddress());
	    taxInvoiceVO.setSclientAddress2(taxInvoiceDTO.getSclientAddress2());
	    taxInvoiceVO.setSclientCountry(taxInvoiceDTO.getSclientCountry());
	    taxInvoiceVO.setInvoiceTitleLabel(taxInvoiceDTO.getInvoiceTitleLabel());
	    taxInvoiceVO.setInvoiceTitle(taxInvoiceDTO.getInvoiceTitle());
	    taxInvoiceVO.setInvoiceDateLabel(taxInvoiceDTO.getInvoiceDateLabel());
	    taxInvoiceVO.setInvoiceDate(taxInvoiceDTO.getInvoiceDate());
	    taxInvoiceVO.setInvoiceDueDateLabel(taxInvoiceDTO.getInvoiceDueDateLabel());
	    taxInvoiceVO.setInvoiceDueDate(taxInvoiceDTO.getInvoiceDueDate());
	    taxInvoiceVO.setProductLineDescription(taxInvoiceDTO.getProductLineDescription());
	    taxInvoiceVO.setProductLineQuantity(taxInvoiceDTO.getProductLineQuantity());
	    taxInvoiceVO.setProductLineQuantityRate(taxInvoiceDTO.getProductLineQuantityRate());
	    taxInvoiceVO.setProductLineQuantityAmount(taxInvoiceDTO.getProductLineQuantityAmount());
	    taxInvoiceVO.setKitLineDate(taxInvoiceDTO.getKitLineDate());
	    taxInvoiceVO.setKitLineManifestNo(taxInvoiceDTO.getKitLineManifestNo());
	    taxInvoiceVO.setKitLineEmitter(taxInvoiceDTO.getKitLineEmitter());
	    taxInvoiceVO.setKitLineLocation(taxInvoiceDTO.getKitLineLocation());
	    taxInvoiceVO.setKitLineKitNo(taxInvoiceDTO.getKitLineKitNo());
	    taxInvoiceVO.setKitLineKitQty(taxInvoiceDTO.getKitLineKitQty());
	    taxInvoiceVO.setSubTotalLabel(taxInvoiceDTO.getSubTotalLabel());
	    taxInvoiceVO.setTaxLabel(taxInvoiceDTO.getTaxLabel());
	    taxInvoiceVO.setTaxLabel1(taxInvoiceDTO.getTaxLabel1());
	    taxInvoiceVO.setTotalLabel(taxInvoiceDTO.getTotalLabel());
	    taxInvoiceVO.setCurrency(taxInvoiceDTO.getCurrency());
	    taxInvoiceVO.setNotesLabel(taxInvoiceDTO.getNotesLabel());
	    taxInvoiceVO.setNotes(taxInvoiceDTO.getNotes());
	    taxInvoiceVO.setTermLabel(taxInvoiceDTO.getTermLabel());
	    taxInvoiceVO.setTerm(taxInvoiceDTO.getTerm());
	    taxInvoiceVO.setPayTo(taxInvoiceDTO.getPayTo());
	    taxInvoiceVO.setBankName(taxInvoiceDTO.getBankName());
	    taxInvoiceVO.setAccountName(taxInvoiceDTO.getAccountName());
	    taxInvoiceVO.setAccountNo(taxInvoiceDTO.getAccountNo());
	    taxInvoiceVO.setIFSC(taxInvoiceDTO.getIFSC());
	    taxInvoiceVO.setPayToLabel(taxInvoiceDTO.getPayToLabel());
	    taxInvoiceVO.setBankNameLabel(taxInvoiceDTO.getBankNameLabel());
	    taxInvoiceVO.setAccountNameLabel(taxInvoiceDTO.getAccountNameLabel());
	    taxInvoiceVO.setAccountNoLabel(taxInvoiceDTO.getAccountNoLabel());
	    taxInvoiceVO.setIFSCLabel(taxInvoiceDTO.getIFSCLabel());
	}
	
	@Override
	public List<TaxInvoiceVO> getAllTaxInvoice(Long orgId) {
		
		return taxInvoiceRepo.findAllByOrgId(orgId);
	}

	@Override
	public TaxInvoiceVO getTaxInvoiceById(Long id) {
		// TODO Auto-generated method stub
		return taxInvoiceRepo.findById(id).get();
		}
}

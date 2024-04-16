
package com.whydigit.efit.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.whydigit.efit.dto.AssetInwardDTO;
import com.whydigit.efit.dto.AssetInwardDetailDTO;
import com.whydigit.efit.dto.AssetTaggingDTO;
import com.whydigit.efit.dto.AssetTaggingDetailsDTO;
import com.whydigit.efit.dto.CnoteDTO;
import com.whydigit.efit.dto.CustomerAttachmentType;
import com.whydigit.efit.dto.CustomersAddressDTO;
import com.whydigit.efit.dto.CustomersBankDetailsDTO;
import com.whydigit.efit.dto.CustomersDTO;
import com.whydigit.efit.dto.DmapDTO;
import com.whydigit.efit.dto.DmapDetailsDTO;
import com.whydigit.efit.dto.FlowDTO;
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
import com.whydigit.efit.dto.TermsAndConditionsDTO;
import com.whydigit.efit.dto.VendorAddressDTO;
import com.whydigit.efit.dto.VendorBankDetailsDTO;
import com.whydigit.efit.dto.VendorDTO;
import com.whydigit.efit.entity.AssetCategoryVO;
import com.whydigit.efit.entity.AssetGroupVO;
import com.whydigit.efit.entity.AssetInwardDetailVO;
import com.whydigit.efit.entity.AssetInwardVO;
import com.whydigit.efit.entity.AssetItemVO;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.AssetTaggingDetailsVO;
import com.whydigit.efit.entity.AssetTaggingVO;
import com.whydigit.efit.entity.AssetVO;
import com.whydigit.efit.entity.CnoteVO;
import com.whydigit.efit.entity.CustomerAttachmentVO;
import com.whydigit.efit.entity.CustomersAddressVO;
import com.whydigit.efit.entity.CustomersBankDetailsVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.DmapDetailsVO;
import com.whydigit.efit.entity.DmapVO;
import com.whydigit.efit.entity.FlowDetailVO;
import com.whydigit.efit.entity.FlowVO;
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
import com.whydigit.efit.entity.TermsAndConditionsVO;
import com.whydigit.efit.entity.UnitVO;
import com.whydigit.efit.entity.VendorAddressVO;
import com.whydigit.efit.entity.VendorBankDetailsVO;
import com.whydigit.efit.entity.VendorVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AssetCategoryRepo;
import com.whydigit.efit.repo.AssetGroupRepo;
import com.whydigit.efit.repo.AssetInwardDetailRepo;
import com.whydigit.efit.repo.AssetInwardRepo;
import com.whydigit.efit.repo.AssetRepo;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
import com.whydigit.efit.repo.AssetTaggingDetailsRepo;
import com.whydigit.efit.repo.AssetTaggingRepo;
import com.whydigit.efit.repo.CnoteRepo;
import com.whydigit.efit.repo.CustomerAttachmentRepo;
import com.whydigit.efit.repo.CustomersAddressRepo;
import com.whydigit.efit.repo.CustomersBankDetailsRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.DmapDetailsRepo;
import com.whydigit.efit.repo.DmapRepo;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.IssueItemRepo;
import com.whydigit.efit.repo.KitRepo;
import com.whydigit.efit.repo.ManufacturerProductRepo;
import com.whydigit.efit.repo.ManufacturerRepo;
import com.whydigit.efit.repo.Po1Repo;
import com.whydigit.efit.repo.PoRepo;
import com.whydigit.efit.repo.PodRepo;
import com.whydigit.efit.repo.ProofOfDeliveryRepo;
import com.whydigit.efit.repo.ServiceRepo;
import com.whydigit.efit.repo.StockBranchRepo;
import com.whydigit.efit.repo.TermsAndConditionsRepo;
import com.whydigit.efit.repo.UnitRepo;
import com.whydigit.efit.repo.UserRepo;
import com.whydigit.efit.repo.VendorAddressRepo;
import com.whydigit.efit.repo.VendorBankDetailsRepo;
import com.whydigit.efit.repo.VendorRepo;
import com.whydigit.efit.util.CommonUtils;

@Service
public class MasterServiceImpl implements MasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	AssetRepo assetRepo;
	@Autowired
	AssetGroupRepo assetGroupRepo;
	@Autowired
	CustomersRepo customersRepo;
	@Autowired
	FlowRepo flowRepo;
	@Autowired
	VendorRepo vendorRepo;
	@Autowired
	AssetTaggingRepo assetTaggingRepo;
	@Autowired
	AssetTaggingDetailsRepo assetTaggingDetailsRepo;
	@Autowired
	ManufacturerRepo manufacturerRepo;
	@Autowired
	ManufacturerProductRepo manufacturerProductRepo;
	@Autowired
	AssetCategoryRepo assetCategoryRepo;
	@Autowired
	UnitRepo unitRepo;
	@Autowired
	VendorAddressRepo vendorAddressRepo;
	@Autowired
	VendorRepo VendorRepo;
	@Autowired
	VendorBankDetailsRepo vendorBankDetailsRepo;
	@Autowired
	KitRepo kitRepo;
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
	public Optional<AssetVO> getAssetById(Long id) {
		return assetRepo.findById(id);
	}

	@Override
	public AssetVO createAsset(AssetVO assetVO) {
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
		return assetRepo.save(assetVO);
	}

	@Override
	public Optional<AssetVO> updateAsset(AssetVO assetVO) {
		if (assetRepo.existsById(assetVO.getId())) {
			return Optional.of(assetRepo.save(assetVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteAsset(Long id) {
		assetRepo.deleteById(id);

	}

	@Override
	public Map<String, Object> getAllAssetGroup(Long orgId, String assetCategory, String assetName, String assetCodeId,
			String manufacturer) {
		Map<String, Object> assetGroup = new HashMap<>();
		List<AssetGroupVO> assetGroupVO = assetGroupRepo.findAll(new Specification<AssetGroupVO>() {
			@Override
			public Predicate toPredicate(Root<AssetGroupVO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(orgId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
				}
				if (StringUtils.isNotBlank(assetCategory)) {
					predicates
							.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("assetCategory"), assetCategory)));
				}
				if (StringUtils.isNotBlank(assetName)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("assetName"), assetName)));
				}
				if (StringUtils.isNotBlank(assetCodeId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("assetCodeId"), assetCodeId)));
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
		assetGroup.put("assetCategory",
				assetGroupVO.stream().map(AssetGroupVO::getAssetCategory).distinct().collect(Collectors.toList()));
		assetGroup.put("assetName",
				assetGroupVO.stream().map(AssetGroupVO::getAssetName).distinct().collect(Collectors.toList()));
		assetGroup.put("assetCodeId",
				assetGroupVO.stream().map(AssetGroupVO::getAssetCodeId).distinct().collect(Collectors.toList()));
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
	public Optional<AssetGroupVO> getAssetGroupById(String id) {
		return assetGroupRepo.findById(id);
	}

	@Override
	public AssetGroupVO createAssetGroup(AssetGroupVO assetGroupVO) throws ApplicationException {
		if (ObjectUtils.isNotEmpty(assetGroupVO) && StringUtils.isNotBlank(assetGroupVO.getAssetCodeId())) {
			if (assetGroupRepo.existsById(assetGroupVO.getAssetCodeId())) {
				throw new ApplicationException("AssetGroup already exist. Please try another one.");
			}
			return assetGroupRepo.save(assetGroupVO);
		} else {
			throw new ApplicationException("Invalid AssetGoup Information.");
		}
	}

	@Override
	public Optional<AssetGroupVO> updateAssetGroup(AssetGroupVO assetGroupVO) {
		if (assetGroupRepo.existsById(assetGroupVO.getAssetCodeId())) {
			return Optional.of(assetGroupRepo.save(assetGroupVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CustomersVO> getAllCustomers(Long orgId) {
		List<CustomersVO> customersVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  CustomerInformation BY OrgId : {}", orgId);
			customersVO = customersRepo.getAllCustomersByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  CustomerInformation For All OrgId.");
			customersVO = customersRepo.findAll();
		}
		return customersVO;
	}

	@Override
	public CustomersVO getCustomersById(Long id) throws ApplicationException {
		CustomersVO customersVO = customersRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Customer not found."));
		List<CustomerAttachmentVO> customerAttachmentVO = customerAttachmentRepo.findByCustomerId(id);
		customersVO.setSop(customerAttachmentVO.stream()
				.filter(ca -> ca.getType().equalsIgnoreCase(CustomerAttachmentType.SOP.name()))
				.collect(Collectors.toList()));
		customersVO.setDocument(customerAttachmentVO.stream()
				.filter(ca -> ca.getType().equalsIgnoreCase(CustomerAttachmentType.DOC.name()))
				.collect(Collectors.toList()));
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
		if (ObjectUtils.isNotEmpty(customersDTO) && ObjectUtils.isNotEmpty(customersDTO.getId())) {
			customersVO = customersRepo.findById(customersDTO.getId())
					.orElseThrow(() -> new ApplicationException("Customer information not found."));
		} else {
			throw new ApplicationException("Invalid customer information");
		}
//		getCustomersVOFromCustomersDTO(customersDTO, customersVO);
		return customersRepo.save(customersVO);
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
		} else {
			LOGGER.info("Successfully Received Flow Information For All OrgId.");
			flowVO = flowRepo.findAll();
		}
		return flowVO;
	}

	@Override
	public Optional<FlowVO> getFlowById(long id) {
		return flowRepo.findById(id);
	}

	@Override
	public FlowVO createFlow(FlowDTO flowDTO) {

		FlowVO flowVO = createFlowVOByFlowDTO(flowDTO);
		flowVO.setEmitter(flowRepo.findEmiterbyId(flowVO.getEmitterId()));
//		flowVO.setDublicateFlowName(flowDTO.getOrgId()+flowDTO.getFlowName());
		flowVO.setWarehouseLocation(flowRepo.getWarehouseLocationByLocationId(flowDTO.getWarehouseId()));
		flowVO.setReceiver(flowRepo.getReceiverByReceiverId(flowDTO.getReceiverId()));
		return flowRepo.save(flowVO);
	}

	private FlowVO createFlowVOByFlowDTO(FlowDTO flowDTO) {
		List<FlowDetailVO> flowDetailVOList = new ArrayList<>();
		FlowVO flowVO = FlowVO.builder().active(flowDTO.isActive()).orgin(flowDTO.getOrgin())
				.warehouseLocation(flowDTO.getWarehouseLocation()).flowName(flowDTO.getFlowName())
				.receiverId(flowDTO.getReceiverId()).emitterId(flowDTO.getEmitterId()).emitter(flowDTO.getEmitter())
				.destination(flowDTO.getDestination()).orgId(flowDTO.getOrgId()).warehouseId(flowDTO.getWarehouseId())
				.flowDetailVO(flowDetailVOList).build();
		flowDetailVOList = flowDTO.getFlowDetailDTO().stream()
				.map(fdDTO -> FlowDetailVO.builder().active(fdDTO.isActive()).cycleTime(fdDTO.getCycleTime())
						.emitterId(flowDTO.getEmitterId()).orgId(flowDTO.getOrgId()).partName(fdDTO.getPartName())
						.kitName(fdDTO.getKitName()).partNumber(fdDTO.getPartNumber())
						.partQty(kitRepo.findPartqty(fdDTO.getKitName()))
						.emitter(flowRepo.findEmiterbyId(flowVO.getEmitterId())).flowVO(flowVO).build())
				.collect(Collectors.toList());
		flowVO.setFlowDetailVO(flowDetailVOList);
		return flowVO;
	}

	@Override
	public Optional<FlowVO> updateFlow(FlowVO flowVO) {
		if (flowRepo.existsById(flowVO.getId())) {
			flowVO.setEmitter(flowRepo.findEmiterbyId(flowVO.getEmitterId()));
			return Optional.of(flowRepo.save(flowVO));
		} else {
			return Optional.empty();
		}
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
	public List<AssetCategoryVO> getAllAssetCategory(Long orgId, String assetCategoryName) {
		List<AssetCategoryVO> assetCategoryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isEmpty(assetCategoryName))) {
			LOGGER.info("Successfully Received AssetCategory BY OrgId : {}", orgId);
			assetCategoryVO = assetCategoryRepo.getAllAssetCategory(orgId);
		} else if (ObjectUtils.isEmpty(orgId) && (ObjectUtils.isNotEmpty(assetCategoryName))) {
			LOGGER.info("Successfully Received AssetCategory BY AssetCategoryName : {}", assetCategoryName);
			assetCategoryVO = assetCategoryRepo.findByAssetCategory(assetCategoryName);
		} else if (ObjectUtils.isNotEmpty(orgId) && (ObjectUtils.isNotEmpty(assetCategoryName))) {
			LOGGER.info("Successfully Received AssetCategory BY AssetCategoryName : {} orgId : {}", assetCategoryName,
					orgId);
			assetCategoryVO = assetCategoryRepo.findByAssetCategoryAndOrgId(assetCategoryName, orgId);
		} else {
			LOGGER.info("Successfully Received AssetCategory Information For All OrgId.");
			assetCategoryVO = assetCategoryRepo.findAll();
		}
		return assetCategoryVO;
	}

	@Override
	public AssetCategoryVO createAssetCategory(AssetCategoryVO assetCategoryVO) {
		if (assetCategoryRepo.existsByAssetCategoryAndOrgId(assetCategoryVO.getAssetCategory(),
				assetCategoryVO.getOrgId())) {
			throw new RuntimeException("Asset category already exists for this organization");
		}
		return assetCategoryRepo.save(assetCategoryVO);
	}

	// Unit

	@Override
	public List<UnitVO> getAllUnit(Long orgId) {
		List<UnitVO> unitVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Unit BY OrgId : {}", orgId);
			unitVO = unitRepo.getAllUnit(orgId);
		} else {
			LOGGER.info("Successfully Received Unit Information For All OrgId.");
			unitVO = unitRepo.findAll();
		}
		return unitVO;
	}

	@Override
	public Optional<UnitVO> getUnitById(int id) {
		return unitRepo.findById(id);
	}

	@Override
	public UnitVO createUnit(UnitVO unitVO) {
		return unitRepo.save(unitVO);
	}

	@Override
	public Optional<UnitVO> updateUnit(UnitVO unitVO) {
		if (unitRepo.existsById(unitVO.getId())) {
			return Optional.of(unitRepo.save(unitVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteUnit(int id) {
		// TODO Auto-generated method stub
		unitRepo.deleteById(id);
	}

	@Override
	public List<AssetGroupVO> createAssetGroupByCSV(MultipartFile assetFile) throws ApplicationException {
		if (assetFile.isEmpty()) {
			throw new ApplicationException("Invalid CSV File.");
		}
		List<AssetGroupVO> assetGroups = new ArrayList<>();
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
		return assetGroupRepo.saveAll(assetGroups);
	}

	private AssetGroupVO createAssetGroupFromCsvLine(String[] csvLine) {
		try {
			if (StringUtils.isEmpty(csvLine[0])) {
				LOGGER.warn("Validation failed for CSV line: {}", Arrays.toString(csvLine));
				return null;
			}
			if (assetGroupRepo.existsById(csvLine[0])) {
				return null;
			}
			return AssetGroupVO.builder().assetCodeId(csvLine[0]).assetName(csvLine[1]).assetCategory(csvLine[2])
					.active(Boolean.parseBoolean(csvLine[3])).length(Float.parseFloat(csvLine[4]))
					.breath(Float.parseFloat(csvLine[5])).height(Float.parseFloat(csvLine[6])).dimUnit(csvLine[7])
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
			KitResponse.setKitCode(kit.getKitCode());
			KitResponse.setPartQty(kit.getPartQty());
			KitResponse.setOrgId(kit.getOrgId());
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
		return kitRepo.findByKitCode(kitName);
	}

	@Override
	public KitVO createkit(KitDTO kitDTO) throws ApplicationException {
//		if (StringUtils.isNotEmpty(kitRepo.findKitcode(kitDTO.getKitCode(), kitDTO.getOrgId()))) {
//			throw new ApplicationException("Kit code already exist. Please try with new kit code.");
//		}
		int finyr = kitRepo.getFinyr();
		String kit = finyr + "KT" + kitRepo.finddocid();
		List<KitAssetVO> kitAssetVO = new ArrayList<>();
		KitVO kitVO = KitVO.builder().kitCode(kit).orgId(kitDTO.getOrgId()).partQty(kitDTO.getPartQuantity())
				.kitAssetVO(kitAssetVO).build();
		for (KitAssetDTO kitAsset : kitDTO.getKitAssetDTO()) {
			kitAssetVO.add(KitAssetVO.builder().assetCategory(kitAsset.getAssetCategory())
					.assetCodeId(kitAsset.getAssetCodeId()).assetName(kitAsset.getAssetName())
					.quantity(kitAsset.getQuantity()).kitVO(kitVO).build());
		}
		kitRepo.updatesequence();
		return kitRepo.save(kitVO);
	}

	@Override
	public Optional<KitVO> updatedKit(KitVO kitVO) {
		if (kitRepo.existsById(kitVO.getId())) {
			return Optional.of(kitRepo.save(kitVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteKit(Long id) {
		kitRepo.deleteById(id);

	}

	@Override
	public Map<String, List<CustomersVO>> CustomersType(Long orgId) {
		List<CustomersVO> customersVO = customersRepo.findByOrgId(orgId);
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
	public Map<String, Map<String, List<AssetGroupVO>>> getAssetGroupByCategoryType(Long orgId) {
		List<AssetGroupVO> assetGroupVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  AssetGroupInformation BY OrgId : {}", orgId);
			assetGroupVO = assetGroupRepo.getAllAssetGroupByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  AssetGroupInformation For All OrgId.");
			assetGroupVO = assetGroupRepo.findAll();
		}
		return assetGroupVO.stream().collect(Collectors.groupingBy(AssetGroupVO::getAssetCategory,
				Collectors.groupingBy(AssetGroupVO::getAssetName)));
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
	public List<VendorVO> getAllVendor() {
		return VendorRepo.findAll();
	}

	@Override
	public Optional<VendorVO> getVendorById(Long id) {
		return VendorRepo.findById(id);
	}

	// Vendor

	@Override
	public VendorVO updateCreateVendor(VendorDTO vendorDTO) throws ApplicationException {
		VendorVO vendorVO = new VendorVO();
		vendorVO.setOrgId(vendorDTO.getOrgId());
		vendorVO.setVenderType(vendorDTO.getVenderType());
		vendorVO.setDisplyName(vendorDTO.getDisplyName());
		vendorVO.setPhoneNumber(vendorDTO.getPhoneNumber());
		vendorVO.setEntityLegalName(vendorDTO.getEntityLegalName());
		vendorVO.setEmail(vendorDTO.getEmail());
		vendorVO.setVenderActivePortal(vendorDTO.isActive());
		vendorVO.setActive(vendorDTO.isActive());

		List<VendorBankDetailsVO> vendorBankDetailsVO = new ArrayList<>();
		if (vendorDTO.getVendorBankDetailsDTO() != null) {
			for (VendorBankDetailsDTO vendorbankDetailsDTO : vendorDTO.getVendorBankDetailsDTO()) {
				VendorBankDetailsVO bankDetailsVO = new VendorBankDetailsVO();
				bankDetailsVO.setAccountNo(vendorbankDetailsDTO.getAccountNo());
				bankDetailsVO.setBank(vendorbankDetailsDTO.getBank());
				bankDetailsVO.setBranch(vendorbankDetailsDTO.getBranch());
				bankDetailsVO.setAccountname(vendorbankDetailsDTO.getAccountName());
				bankDetailsVO.setIfscCode(vendorbankDetailsDTO.getIfscCode());
				bankDetailsVO.setVendorVO(vendorVO);
				vendorBankDetailsVO.add(bankDetailsVO);
				;
			}
		}
		vendorVO.setVendorBankDetailsVO(vendorBankDetailsVO);

		List<VendorAddressVO> vendorAddressVO = new ArrayList<>();
		if (vendorDTO.getVendorAddressDTO() != null) {
			for (VendorAddressDTO vendorAddressDTO : vendorDTO.getVendorAddressDTO()) {
				VendorAddressVO vendorAddress = new VendorAddressVO();

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

		return VendorRepo.save(vendorVO);
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
		assetInwardVO.setDocId(assetInwardDTO.getDocId());
		assetInwardVO.setDocDate(assetInwardDTO.getDocDate());
		assetInwardVO.setStockBranch(assetInwardDTO.getStockBranch());
		assetInwardVO.setSourceFrom(assetInwardDTO.getSourceFrom());
		assetInwardVO.setOrgId(assetInwardDTO.getOrgId());
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
				assetStockDetailsVO.setStockBranch(assetInwardVO1.getStockBranch());
				assetStockDetailsVO.setSku(assetdetails.getSkuDetail());
				assetStockDetailsVO.setSkuCode(assetdetails.getSkucode());
				assetStockDetailsVO.setSkuQty(assetdetails.getSkuQty());
				assetStockDetailsVO.setStockValue(assetdetails.getStockValue());
				assetStockDetailsVO.setStockLocation(assetdetails.getStockLocation());
				assetStockDetailsVO.setBinLocation(assetdetails.getBinLocation());
				assetStockDetailsVO.setScreen(assetInwardVO1.getSCode());
				assetStockDetailsVO.setPm("P");
				assetStockDetailsRepo.save(assetStockDetailsVO);

			}

		}
		return assetInwardRepo.save(assetInwardVO);
	}

	// Stock branch
	@Override
	public StockBranchVO createStockBranch(StockBranchDTO stockBranchDTO) {
		StockBranchVO stockBranchVO = new StockBranchVO();
		stockBranchVO.setBranch(stockBranchDTO.getBranch());
		stockBranchVO.setBranchCode(stockBranchDTO.getBranchCode());
		stockBranchVO.setOrgId(stockBranchDTO.getOrgId());
		stockBranchVO.setCreatedBy(stockBranchDTO.getCreatedby());
		stockBranchVO.setModifiedBy(stockBranchDTO.getCreatedby());
		stockBranchVO.setActive(stockBranchDTO.isActive());
		return stockBranchRepo.save(stockBranchVO);
	}

	// Update Stock Branch
	@Override
	public StockBranchVO updateStockBranch(StockBranchDTO stockBranchDTO) throws ApplicationException {

		Optional<StockBranchVO> existingStockBranchOptional = stockBranchRepo.findById(stockBranchDTO.getId());

		if (existingStockBranchOptional.isPresent()) {
			StockBranchVO existingStockBranch = existingStockBranchOptional.get();
			existingStockBranch.setBranch(stockBranchDTO.getBranch());
			existingStockBranch.setBranchCode(stockBranchDTO.getBranchCode());
			existingStockBranch.setModifiedBy(stockBranchDTO.getCreatedby());
			return stockBranchRepo.save(existingStockBranch);
		} else {
			throw new NoSuchElementException("StockBranch with ID " + stockBranchDTO.getId() + " not found");
		}
	}

	@Override
	public List<StockBranchVO> getAllStockBranchByOrgId(Long orgId) {

		return stockBranchRepo.findByOrgId(orgId);

	}

	// Asset Tagging
	@Override
	public AssetTaggingVO createTagging(AssetTaggingDTO assetTaggingDTO) {

		AssetTaggingVO assetTaggingVO = new AssetTaggingVO();
		assetTaggingVO.setDocid(assetTaggingDTO.getDocId());
		assetTaggingVO.setDocDate(assetTaggingDTO.getDocDate());
		assetTaggingVO.setCancel(false);
		assetTaggingVO.setCreatedBy(assetTaggingDTO.getCreatedBy());
		assetTaggingVO.setModifiedBy(assetTaggingDTO.getCreatedBy());
		assetTaggingVO.setActive(true);
		assetTaggingVO.setAsset(assetTaggingDTO.getAsset());
		assetTaggingVO.setAssetCode(assetTaggingDTO.getAssetCode());
		assetTaggingVO.setSeqFrom(assetTaggingDTO.getSeqFrom());
		assetTaggingVO.setSeqTo(assetTaggingDTO.getSeqTo());
		assetTaggingVO.setOrgId(assetTaggingDTO.getOrgId());
		List<AssetTaggingDetailsVO> assetTaggingDetailsVO = new ArrayList<>();
		if (assetTaggingDTO.getTaggingDetailsDTO() != null) {

			for (AssetTaggingDetailsDTO taggingDetailsDTO : assetTaggingDTO.getTaggingDetailsDTO()) {
				AssetTaggingDetailsVO assetTaggingDetails = new AssetTaggingDetailsVO();
				assetTaggingDetails.setRfId(taggingDetailsDTO.getRfId());
				assetTaggingDetails.setTaggingDocDd(assetTaggingVO.getDocid());
				assetTaggingDetails.setAssetCode(taggingDetailsDTO.getAssetCode());
				assetTaggingDetails.setAsset(taggingDetailsDTO.getAsset());
				assetTaggingDetails.setOrgId(assetTaggingVO.getOrgId());
				assetTaggingDetails.setTagCode(taggingDetailsDTO.getTagCode());
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
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setSCode(savedAssetTaggingVO.getScode()); // Assuming getScode() returns the correct
																				// value
				assetStockDetailsVO.setScreen("Asset Tagging");
				assetStockDetailsVO.setPm("P");
				assetStockDetailsVO.setStockBranch("AI POOL");
				assetStockDetailsRepo.save(assetStockDetailsVO);
			}
		}
		return savedAssetTaggingVO;
	}

	@Override
	public Set<Object[]> getTagCodeByAsset(String assetcode, String asset, int startno, int endno) {

		return assetTaggingRepo.getTagCodeByAsset(assetcode, asset, startno, endno);
	}

	@Override
	public Set<Object[]> getAvalKitQty(Long warehouseId, String Kitname) {

		return stockBranchRepo.findAvalKitQty(warehouseId, Kitname);
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

		List<PoVO1> poVO1 = new ArrayList<>();
		if (poDTO.getPo1DTO() != null) {
			for (Po1DTO po1DTO : poDTO.getPo1DTO()) {
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
		poVO.setPoVO1(poVO1);
		getPoVOFromPoDTO(poDTO, poVO);
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
	public AssetGroupVO getAssetGroupByAssetCode(Long orgId, String assetCodeId) {

		return assetGroupRepo.findAssetByAssetCodeId(orgId, assetCodeId);
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
	public Set<Object[]> getPoNoByCreateAsset(Long orgId) {

		return poRepo.getPoNoByCreateAsset(orgId);
	}



}

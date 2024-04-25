package com.whydigit.efit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.whydigit.efit.common.EmitterConstant;
import com.whydigit.efit.dto.BinAllotmentDTO;
import com.whydigit.efit.dto.BinAllotmentDetailsDTO;
import com.whydigit.efit.dto.BinOutwardDTO;
import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.InwardDTO;
import com.whydigit.efit.dto.IssueItemDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.IssueRequestItemApprovelDTO;
import com.whydigit.efit.dto.IssueRequestQtyApprovelDTO;
import com.whydigit.efit.dto.IssueRequestType;
import com.whydigit.efit.dto.OutwardKitDetailsDTO;
import com.whydigit.efit.dto.Role;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.AssetTaggingDetailsVO;
import com.whydigit.efit.entity.BinAllotmentDetailsVO;
import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinOutwardDetailsVO;
import com.whydigit.efit.entity.BinOutwardVO;
import com.whydigit.efit.entity.CustomersVO;
import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;
import com.whydigit.efit.entity.FlowDetailVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.InwardVO;
import com.whydigit.efit.entity.IssueItemVO;
import com.whydigit.efit.entity.IssueRequestApprovedVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.entity.KitVO;
import com.whydigit.efit.entity.MaxPartQtyPerKitVO;
import com.whydigit.efit.entity.OutwardKitDetailsVO;
import com.whydigit.efit.entity.OutwardView;
import com.whydigit.efit.entity.ReturnStockVO;
import com.whydigit.efit.entity.VwEmitterInwardVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
import com.whydigit.efit.repo.AssetTaggingDetailsRepo;
import com.whydigit.efit.repo.BinAllotmentDetailsRepo;
import com.whydigit.efit.repo.BinAllotmentNewRepo;
import com.whydigit.efit.repo.BinAllotmentRepo;
import com.whydigit.efit.repo.BinOutwardDetailsRepo;
import com.whydigit.efit.repo.BinOutwardRepo;
import com.whydigit.efit.repo.CustomersRepo;
import com.whydigit.efit.repo.DmapDetailsRepo;
import com.whydigit.efit.repo.EmitterInwardRepo;
import com.whydigit.efit.repo.EmitterOutwardRepo;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.InwardRepo;
import com.whydigit.efit.repo.IssueItemRepo;
import com.whydigit.efit.repo.IssueRequestRepo;
import com.whydigit.efit.repo.KitAssetRepo;
import com.whydigit.efit.repo.KitRepo;
import com.whydigit.efit.repo.MaxPartQtyPerKitRepo;
import com.whydigit.efit.repo.OutwardKitDetailsRepo;
import com.whydigit.efit.repo.OutwardViewRepo;
import com.whydigit.efit.repo.ReturnStockRepo;
import com.whydigit.efit.repo.UserRepo;
import com.whydigit.efit.repo.VwEmitterInwardRepo;

@Service
public class EmitterServiceImpl implements EmitterService {
	public static final Logger LOGGER = LoggerFactory.getLogger(EmitterServiceImpl.class);
	@Autowired
	IssueRequestRepo issueRequestRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	EmitterInwardRepo emitterInwardRepo;

	@Autowired
	EmitterOutwardRepo emitterOutwardRepo;

	@Autowired
	IssueItemRepo issueItemRepo;

	@Autowired
	InwardRepo inwardRepo;

	@Autowired
	AssetTaggingDetailsRepo taggingDetailsRepo;

	@Autowired
	FlowRepo flowRepo;

	@Autowired
	ReturnStockRepo returnStockRepo;

	@Autowired
	DmapDetailsRepo dmapdetailsRepo;

	@Autowired
	VwEmitterInwardRepo vwEmitterInwardRepo;

	@Autowired
	MaxPartQtyPerKitRepo maxPartQtyPerKitRepo;
	@Autowired
	CustomersRepo customersRepo;

	@Autowired
	KitRepo kitRepo;

	@Autowired
	KitAssetRepo kitAssetRepo;

	@Autowired
	BinAllotmentRepo binAllotmentRepo;

	@Autowired
	BinAllotmentNewRepo binAllotmentNewRepo;

	@Autowired
	BinAllotmentDetailsRepo binAllotmentDetailsRepo;

	@Autowired
	OutwardViewRepo outwardViewRepo;

	@Autowired
	OutwardKitDetailsRepo outwardKitDetailsRepo;

	@Autowired
	AssetStockDetailsRepo assetStockDetailsRepo;

	@Autowired
	BinOutwardRepo binOutwardRepo;

	@Autowired
	BinOutwardDetailsRepo binOutwardDetailsRepo;

	@Override
	public IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO) throws ApplicationException {
		IssueRequestVO issueRequestVO = new IssueRequestVO();
		List<IssueItemVO> issueItemVO = new ArrayList<>();
		FlowVO flowVO = flowRepo.findById(issueRequestDTO.getFlowTo())
				.orElseThrow(() -> new ApplicationException("Invalid flow. Please try again."));
		getIssueRequestVOFromIssueRequestDTO(issueRequestDTO, issueRequestVO);
		for (IssueItemDTO issueItemDTO : issueRequestDTO.getIssueItemDTO()) {
			IssueItemVO issueItem = new IssueItemVO();
			getIssueItemVOFromIssueRequestDTO(issueItemDTO, issueRequestVO, issueItem);
			if (StringUtils.isBlank(issueRequestDTO.getIrType().name())) {
				throw new ApplicationException("Invalid issue request type");
			} else if (issueRequestDTO.getIrType().equals(IssueRequestType.IR_KIT)) {
//				FlowDetailVO flowDetailVO = flowVO.getFlowDetailVO().stream()
//						.filter(fd -> StringUtils.equalsIgnoreCase(fd.getKitName(), issueItemDTO.getKitName()))
//						.findFirst().orElseThrow(() -> new ApplicationException("Flow not Match with kit"));
//				issueItem.setPartName(flowDetailVO.getPartName());
//				issueItem.setPartNo(flowDetailVO.getPartNumber());
			} else if (issueRequestDTO.getIrType().equals(IssueRequestType.IR_PART)) {
				FlowDetailVO flowDetailVO = flowVO.getFlowDetailVO().stream()
						.filter(fd -> StringUtils.equalsIgnoreCase(fd.getPartNumber(), issueItemDTO.getPartNo()))
						.findFirst().orElseThrow(() -> new ApplicationException("Flow not Match with part"));
				issueItem.setKitName(flowDetailVO.getKitName());
				issueItem.setPartName(flowDetailVO.getPartName());
			} else {
				throw new ApplicationException("Invalid issue request type");
			}
			issueItemVO.add(issueItem);
		}
		int finyr = issueRequestRepo.getFinyr();
		String requestnumber = finyr + "BR" + issueRequestRepo.finddocid();
		issueRequestVO.setDocId(requestnumber);
		issueRequestRepo.updatesequence();
		issueRequestVO.setEmitter(customersRepo.findCustomerLegalnameByEmitterId(issueRequestDTO.getEmitterId()));
		issueRequestVO.setEmitterCode(customersRepo.findcustomercodeByEmitterId(issueRequestDTO.getEmitterId()));
		issueRequestVO.setFlowName(flowVO.getFlowName());
		issueRequestVO.setIssueItemVO(issueItemVO);
		issueRequestVO.setTotalIssueItem(issueItemVO.size());
		issueRequestVO.setWarehouseLocationId(issueRequestRepo.findWarehouseLocationId(issueRequestDTO.getFlowTo()));
		issueRequestVO.setWarehouseLocation(issueRequestRepo.findWarehouseLocation(issueRequestDTO.getFlowTo()));
		issueRequestVO = issueRequestRepo.save(issueRequestVO);
		return issueRequestVO;
	}

	private void getIssueItemVOFromIssueRequestDTO(IssueItemDTO issueItemDTO, IssueRequestVO issueRequestVO,
			IssueItemVO issueItem) {
		issueItem.setIssueItemStatus(0);
		issueItem.setKitName(issueItemDTO.getKitName());
		issueItem.setKitQty(issueItemDTO.getKitQty());
		issueItem.setPartNo(issueItemDTO.getPartNo());
		issueItem.setPartName(issueItemDTO.getPartName());
		issueItem.setPartQty(issueItemDTO.getPartQty());
		issueItem.setRemark(issueItemDTO.getRemark());
		issueItem.setIssueRequestVO(issueRequestVO);
		issueItem.setIssueItemStatus(EmitterConstant.ISSUE_REQUEST_STATUS_PENDING);
		issueItem.setIssuedQty(0);
	}

	private void getIssueRequestVOFromIssueRequestDTO(IssueRequestDTO issueRequestDTO, IssueRequestVO issueRequestVO) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		issueRequestVO.setDemandDate(issueRequestDTO.getDemandDate());
		issueRequestVO.setFlowTo(issueRequestDTO.getFlowTo());
		issueRequestVO.setIssueStatus(0);
		issueRequestVO.setRemark(issueRequestDTO.getRemark());
		issueRequestVO.setReqAddressId(issueRequestDTO.getReqAddressId());
		issueRequestVO.setRequestedDate(currentDateTime);
		issueRequestVO.setOrgId(issueRequestDTO.getOrgId());
		issueRequestVO.setEmitterId(issueRequestDTO.getEmitterId());
		issueRequestVO.setCustomerId(issueRequestDTO.getEmitterId());
		issueRequestVO.setTat(
				ChronoUnit.HOURS.between(currentDateTime, issueRequestDTO.getDemandDate().atTime(LocalTime.MAX)));
		issueRequestVO.setIrType(issueRequestDTO.getIrType());
	}

	@Override
	public List<IssueRequestVO> getIssueRequest(Long emitterId, String warehouseLocation, Long orgId,
			LocalDate startDate, LocalDate endDate, Long warehouseLocationId) {

		return issueRequestRepo.findAll(new Specification<IssueRequestVO>() {

			@Override
			public Predicate toPredicate(Root<IssueRequestVO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(emitterId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("emitterId"), emitterId)));
				}
				if (ObjectUtils.isNotEmpty(orgId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
				}
				if (ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)) {
					predicates.add(criteriaBuilder.between(root.get("requestedDate"),
							LocalDateTime.of(startDate, LocalTime.MIDNIGHT),
							LocalDateTime.of(endDate, LocalTime.MIDNIGHT)));
				}
				if (StringUtils.isNoneBlank(warehouseLocation)) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("WarehouseLocation"), warehouseLocation)));
				}
				if (ObjectUtils.isNotEmpty(warehouseLocationId)) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("warehouseLocationId"), warehouseLocationId)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});

	}

	@Override
	public List<EmitterAddressDTO> getEmitterAddress(Long orgId) {
		List<EmitterAddressDTO> emitterAddressList = new ArrayList<>();
		List<Object[]> emitterAddress = userRepo.findByOrgIdAndRole(orgId, Role.ROLE_EMITTER.name());
		for (Object[] ea : emitterAddress) {
			EmitterAddressDTO emitterAddressDTO = new EmitterAddressDTO();
			emitterAddressDTO.setEmitterId(Long.valueOf(ea[0].toString()));
			emitterAddressDTO.setAddressId(Long.valueOf(ea[1].toString()));
			emitterAddressDTO.setFirstName(ea[2].toString());
			emitterAddressDTO.setLastName(ea[3].toString());
			emitterAddressDTO.setLocation(ea[4].toString());
			emitterAddressDTO.setAddress1(ea[5].toString());
			emitterAddressDTO.setAddress2(ea[6].toString());
			emitterAddressDTO.setCountry(ea[7].toString());
			emitterAddressDTO.setState(ea[8].toString());
			emitterAddressDTO.setPin(ea[9].toString());
			emitterAddressList.add(emitterAddressDTO);
		}
		return emitterAddressList;
	}

	@Override
	@Transactional
	public IssueRequestVO issueRequestQtyApprovel(IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO)
			throws ApplicationException {
		IssueRequestVO issueRequestVO = issueRequestRepo.findById(issueRequestQtyApprovelDTO.getIssueRequestId())
				.orElseThrow(() -> new ApplicationException("Invalid issueRequest information."));
		List<AssetStockDetailsVO> assetStockDetailsVO = new ArrayList<>();
		CustomersVO customersVO = customersRepo.findById(issueRequestVO.getEmitterId())
				.orElseThrow(() -> new ApplicationException("Emitter not found for this issueRequestId."));
		for (IssueRequestItemApprovelDTO irItem : issueRequestQtyApprovelDTO.getIssueRequestItemApprovelDTO()) {
			setIssueRequestItemQTY(issueRequestQtyApprovelDTO, issueRequestVO, assetStockDetailsVO, customersVO,
					irItem.getIssuedQty(), irItem.getIssueItemId());
		}
		issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_ISSUED);
//		if (issueRequestVO.getIssueItemVO().stream()
//				.allMatch(flag -> flag.getIssueItemStatus() > EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_INPROGRESS)) {
//			issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_ISSUED);
//		}
//		// Check if any status flag is equal to 1
//		else if (issueRequestVO.getIssueItemVO().stream()
//				.anyMatch(flag -> flag.getIssueItemStatus() == EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_INPROGRESS)) {
//			issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_INPROGRESS);
//		}
//		// If none of the above conditions are met, return 0
//		else {
//			issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_PENDING);
//		}
		issueItemRepo.updateApptovedStatus(issueRequestQtyApprovelDTO.getIssueRequestId());
		issueRequestVO = issueRequestRepo.save(issueRequestVO);
		assetStockDetailsRepo.saveAll(assetStockDetailsVO);
		return issueRequestVO;
	}

	private void setIssueRequestItemQTY(IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO,
			IssueRequestVO issueRequestVO, List<AssetStockDetailsVO> assetStockDetailsVO, CustomersVO customersVO,
			int itemQTY, long itemId) {
		issueRequestVO.getIssueItemVO().stream().filter(item -> item.getId() == itemId).forEach(item -> {
			List<IssueRequestApprovedVO> issueRequestApprovedVO = item.getIssueRequestApprovedVO();
			IssueRequestApprovedVO issueRequestApproved = new IssueRequestApprovedVO();
			issueRequestApproved.setApprovedDate(LocalDateTime.now());
			issueRequestApproved.setApprovedId(issueRequestQtyApprovelDTO.getApprovedId());
			issueRequestApproved.setApproverName(issueRequestQtyApprovelDTO.getApproverName());
			issueRequestApproved.setIssueItemVO(item);
			issueRequestApproved.setQuantity(itemQTY);
			int qty = issueRequestApprovedVO.stream().mapToInt(IssueRequestApprovedVO::getQuantity).sum() + itemQTY;
			int ct = issueRequestApprovedVO.size() == 0 && qty == item.getKitQty() ? 0 : 1;
			issueRequestApproved.setIrApprovedId(
					new StringBuilder(Long.toString(issueRequestQtyApprovelDTO.getIssueRequestId())).append("-")
							.append(itemId).append("-").append(issueRequestApprovedVO.size() + ct).toString());
			item.setDocDate(LocalDate.now());
			int finyr = issueItemRepo.getFinyr();
			String binallotment = finyr + "BA" + issueItemRepo.finddocid();
			item.setDocId(binallotment);
			issueItemRepo.nextDocseq();
			item.setIssuedQty(qty);
			item.setIssueItemStatus(getItemStatus(qty, item.getKitQty()));
			issueRequestApprovedVO.add(issueRequestApproved);
			item.setIssueRequestApprovedVO(issueRequestApprovedVO);
			if (ObjectUtils.isEmpty(item.getInwardVO())) {
				InwardVO inwardVO = new InwardVO();
				inwardVO.setIssueItemVO(item);
				item.setInwardVO(inwardVO);
			}
			assetStockDetailsVO.addAll(getAssetStockDetailsVO(item, itemQTY, issueRequestQtyApprovelDTO));
		});
	}

	private List<AssetStockDetailsVO> getAssetStockDetailsVO(IssueItemVO issueItemVO, int issuedQty,
			IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO) {
		KitVO kitVO = kitRepo.findByKitCode(issueItemVO.getKitName()).get();

		return kitVO.getKitAssetVO().stream().map(kavo -> {
			AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
			assetStockDetailsVO.setActive(true);
			assetStockDetailsVO.setCancel(false);
			assetStockDetailsVO.setCreatedBy(issueRequestQtyApprovelDTO.getCreatedBy());
			assetStockDetailsVO.setModifiedBy(issueRequestQtyApprovelDTO.getModifiedBy());
			assetStockDetailsVO.setStockBranch(
					issueRequestRepo.findStockBranchByIssurequestid(issueRequestQtyApprovelDTO.getIssueRequestId()));
			assetStockDetailsVO.setStockRef(issueRequestRepo.findDocid(issueRequestQtyApprovelDTO.getIssueRequestId()));
			assetStockDetailsVO.setSkuCode(kavo.getAssetCodeId());
			assetStockDetailsVO.setSkuQty(kavo.getQuantity() * issuedQty * -1);
			assetStockDetailsVO.setSku(kavo.getAssetName());
			assetStockDetailsVO.setPm("M");
			assetStockDetailsVO.setScreen("BIN_ALLOT");
			assetStockDetailsVO.setSCode("BNREQ");
			return assetStockDetailsVO;
		}).collect(Collectors.toList());
	}

	private int getItemStatus(int issuedQty, int reqQty) {
		int balQty = reqQty - issuedQty;
		int status = EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_PENDING;
		if (balQty == 0) {
			status = EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_ISSUED_ALL;
		} else if (balQty != 0) {
			status = EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_INPROGRESS;
		}
		return status;
	}

	// emitter inward
	public List<EmitterInwardVO> getAllEmitterInward(Long orgId) {
		List<EmitterInwardVO> emitterInwardVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  EmitterInward Information BY OrgId : {}", orgId);
			emitterInwardVO = emitterInwardRepo.getAllEmitterInwardByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  EmitterInward Information For All OrgId.");
			emitterInwardVO = emitterInwardRepo.findAll();
		}
		return emitterInwardVO;
	}

	@Override
	public Optional<EmitterInwardVO> getEmitterInwardById(int id) {
		return emitterInwardRepo.findById(id);
	}

	@Override
	public EmitterInwardVO createEmitterInward(EmitterInwardVO emitterInwardVO) {
		return emitterInwardRepo.save(emitterInwardVO);
	}

	@Transactional
	@Override
	public InwardVO updateEmitterInward(InwardDTO inwardDTO) throws ApplicationException {
		IssueItemVO issueItemVO = new IssueItemVO();
		InwardVO inwardVO = new InwardVO();
//		MovementStockVO movementStockVO = new MovementStockVO();
		if (ObjectUtils.isNotEmpty(inwardDTO) && ObjectUtils.isNotEmpty(inwardDTO.getId())) {
			inwardVO = inwardRepo.findById(inwardDTO.getId())
					.orElseThrow(() -> new ApplicationException("Emitter inward information not found."));
			if (ObjectUtils.isNotEmpty(inwardDTO.getIssueItemId())) {
				issueItemVO = issueItemRepo.findById(inwardDTO.getIssueItemId())
						.orElseThrow(() -> new ApplicationException(" information not found."));
				CustomersVO customersVO = customersRepo.findById(issueItemVO.getIssueRequestVO().getEmitterId())
						.orElseThrow(() -> new ApplicationException("Custimer not found."));
//				movementStockVO = getMovementStock(issueItemVO, (int) inwardDTO.getNetQtyRecieved(),
//						MovementType.OUTWARD, customersVO);
			}

		} else {
			throw new ApplicationException("Invalid Emitter inward information.");
		}
		inwardVO.setIssueItemVO(issueItemVO); // Mapping
		getInwardVOFromInwardDTO(inwardDTO, inwardVO);

//		issueItemVO.setApprovedStatus(true);  

		inwardVO = inwardRepo.save(inwardVO);
		EmitterOutwardVO outwardVO = new EmitterOutwardVO();
		outwardVO = emitterOutwardRepo.findByIssueItemId(issueItemVO.getId()).orElse(new EmitterOutwardVO());
		outwardVO.setIssueItemVO(issueItemVO);
		outwardVO.setKitQty(inwardVO.getNetQtyRecieved());
		outwardVO.setActive(true);
		outwardVO.setOrgId(issueItemVO.getIssueRequestVO().getOrgId());
		outwardVO.setInwardConfirmDate(LocalDate.now());
		emitterOutwardRepo.save(outwardVO);
//		movementStockRepo.save(movementStockVO);
		return inwardVO;
	}

	private void getInwardVOFromInwardDTO(InwardDTO inwardDTO, InwardVO inwardVO) {

		ReturnStockVO returnStockVO = new ReturnStockVO();

		inwardVO.setNetQtyRecieved(inwardDTO.getNetQtyRecieved());
		inwardVO.setReturnQty(inwardDTO.getReturnQty());
		inwardVO.setStatus(inwardDTO.getStatus());
		inwardVO.setNetRecAcceptStatus(true);

		returnStockVO.setQty(inwardDTO.getNetQtyRecieved());
		returnStockVO.setIssue_item_id(inwardDTO.getIssueItemId());
		returnStockRepo.save(returnStockVO);

	}

	@Override
	public void deleteEmitterInward(int id) {
		emitterInwardRepo.deleteById(id);

	}

	// emitter outward
	public List<EmitterOutwardVO> getAllEmitterOutward(Long orgId) {
		List<EmitterOutwardVO> emitterOutwardVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  EmitterOutward Information BY OrgId : {}", orgId);
			emitterOutwardVO = emitterOutwardRepo.getAllEmitterOutwardByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  EmitterOutward Information For All OrgId.");
			emitterOutwardVO = emitterOutwardRepo.findAll();
		}
		return emitterOutwardVO;
	}

	@Override
	public Optional<EmitterOutwardVO> getEmitterOutwardById(long id) {
		return emitterOutwardRepo.findById(id);
	}

	@Override
	public EmitterOutwardVO createEmitterOutward(EmitterOutwardVO emitterOutwardVO) {
		return emitterOutwardRepo.save(emitterOutwardVO);
	}

	@Override
	public Optional<EmitterOutwardVO> updateEmitterOutward(EmitterOutwardVO emitterOutwardVO) {
		if (emitterOutwardRepo.existsById(emitterOutwardVO.getId())) {
			return Optional.of(emitterOutwardRepo.save(emitterOutwardVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteEmitterOutward(long id) {
		emitterOutwardRepo.deleteById(id);
	}

	@Override
	public void cancelIssueRequest(Long issueRequestId, Long issueRequestItemId) throws ApplicationException {
		if (ObjectUtils.isNotEmpty(issueRequestItemId)) {
			LOGGER.info("Cancel Issue Requested By IssueRequestItemId : {}", issueRequestItemId);
			if (issueItemRepo.cancelIssueRequestByIssueRequestItemId(issueRequestItemId,
					EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_CANCELLED) < 1) {
				throw new ApplicationException("Failed To Cancel The Issue Requst Item");
			}
			if (issueRequestRepo.cancelIssueRequestByIssueRequestId(issueRequestId,
					EmitterConstant.ISSUE_REQUEST_STATUS_CANCELLED) < 1) {
				throw new ApplicationException("Failed To Cancel The Issue Requst");
			}
		} else if (ObjectUtils.isNotEmpty(issueRequestId)) {
			LOGGER.info("Cancel Issue Requested By IssueRequestId : {}", issueRequestId);
			if (issueItemRepo.cancelIssueRequestByIssueRequestId(issueRequestId,
					EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_CANCELLED) < 1) {
				throw new ApplicationException("Failed To Cancel The Issue Requst");
			}
			if (issueRequestRepo.cancelIssueRequestByIssueRequestId(issueRequestId,
					EmitterConstant.ISSUE_REQUEST_STATUS_CANCELLED) < 1) {
				throw new ApplicationException("Failed To Cancel The Issue Requst");
			}
		} else {
			throw new ApplicationException("Invalid cancel issue request. Failed To Cancel The Issue Requst.");
		}
	}

	@Override
	public List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndEmtId(Long orgId, Long emitterId) {
		return vwEmitterInwardRepo.findAllByOrgId(orgId, emitterId);
	}

	@Override
	public List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndEmtIdAndFlow(Long orgId, Long emitterId, Long flowid) {
		return vwEmitterInwardRepo.findAllByOrgIdFlow(orgId, emitterId, flowid);
	}

	@Override
	public List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndWarehouse(Long orgId, Long warehouseid) {
		return vwEmitterInwardRepo.findAllByOrgIdAndWarehosue(orgId, warehouseid);
	}

	@Override
	public Map<String, Object> getAllViewEmitterInward(Long orgId, Long emitterId, Long flowId,
			Long warehouseLocationId) {
		Map<String, Object> vwEmitterInward = new HashMap<>();
		List<VwEmitterInwardVO> vwEmitterInwardVO = vwEmitterInwardRepo.findAll(new Specification<VwEmitterInwardVO>() {
			@Override
			public Predicate toPredicate(Root<VwEmitterInwardVO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (ObjectUtils.isNotEmpty(orgId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
				}
				if (ObjectUtils.isNotEmpty(emitterId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("emitterId"), emitterId)));
				}
				if (ObjectUtils.isNotEmpty(flowId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("flowId"), flowId)));
				}
				if (ObjectUtils.isNotEmpty(warehouseLocationId)) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("warehouseLocationId"), warehouseLocationId)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		// Further processing based on basicDetailVO if needed
		vwEmitterInward.put("vwEmitterInwardVO", vwEmitterInwardVO);
		return vwEmitterInward;
	}

	// getEmitter by Warehouseid
	@Override
	public Set<Object[]> getEmitterByWarehouseId(Long orgId, Long warehouseId) {

		return flowRepo.findEmitterByWarehouseId(orgId, warehouseId);
	}

	@Override
	public Map<String, Object> getAllMaxPartQtyPerKit(Long orgId, Long emitterId, Long flowId, String partNumber) {
		Map<String, Object> maxPrtQty = new HashMap<>();
		List<MaxPartQtyPerKitVO> maxPartQtyPerKitVO = maxPartQtyPerKitRepo
				.findAll(new Specification<MaxPartQtyPerKitVO>() {
					@Override
					public Predicate toPredicate(Root<MaxPartQtyPerKitVO> root, CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						List<Predicate> predicates = new ArrayList<>();
						if (ObjectUtils.isNotEmpty(orgId)) {
							predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("orgId"), orgId)));
						}
						if (ObjectUtils.isNotEmpty(emitterId)) { // Corrected from orgId to emitterId
							predicates
									.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("emitterId"), emitterId)));
						}
						if (ObjectUtils.isNotEmpty(flowId)) { // Corrected from orgId to flowId
							predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("flowId"), flowId)));
						}
						if (StringUtils.isNotBlank(partNumber)) {
							predicates.add(
									criteriaBuilder.and(criteriaBuilder.equal(root.get("partNumber"), partNumber)));
						}
						return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				});
		maxPrtQty.put("MaxPartQtyPerKitVO", maxPartQtyPerKitVO);
		return maxPrtQty;
	}

	@Override
	public List<OutwardView> getAllEmitterOutwardView(Long orgId, Long flowId) {
		List<OutwardView> outwardView = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId) && ObjectUtils.isNotEmpty(flowId)) {
			LOGGER.info("Successfully Received  EmitterOutward Information BY OrgId : {} flowId : {}", orgId, flowId);
			outwardView = outwardViewRepo.getOutwardViewByOrgIdAndFlowId(orgId, flowId);
		} else if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  EmitterOutward Information BY OrgId : {}", orgId);
			outwardView = outwardViewRepo.getOutwardViewByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  EmitterOutward Information For All OrgId.");
			outwardView = outwardViewRepo.findAll();
		}
		return outwardView;
	}

	@Override
	public OutwardKitDetailsVO updateOutwardKitQty(OutwardKitDetailsDTO outwardKitDetailsDTO)
			throws ApplicationException {

//		OutwardKitDetailsVO outwardKitDetailVO = new OutwardKitDetailsVO();
//		outwardKitDetailVO.setKitNO(outwardKitDetailsDTO.getKitNO());
//		outwardKitDetailVO.setKitQty(outwardKitDetailsDTO.getKitQty());
//		EmitterOutwardVO emitterOutwardVO = emitterOutwardRepo.findById(outwardKitDetailsDTO.getEmitterOutwarId())
//				.orElseThrow(() -> new ApplicationException("EmitterId not found."));
//		outwardKitDetailVO.setEmitterOutwardVO(emitterOutwardVO); 
//		emitterOutwardVO.setKitNo(outwardKitDetailsDTO.getKitNO());
//		emitterOutwardVO.setKitReturnqty(outwardKitDetailsDTO.getKitQty());

		OutwardKitDetailsVO outwardKitDetailVO = new OutwardKitDetailsVO();
		outwardKitDetailVO.setKitNO(outwardKitDetailsDTO.getKitNO());
		outwardKitDetailVO.setKitQty(outwardKitDetailsDTO.getKitQty());
		EmitterOutwardVO emitterOutwardVO = emitterOutwardRepo
				.findOutwardByIssueItemId(outwardKitDetailsDTO.getIssueItemId());
		outwardKitDetailVO.setEmitterOutwardVO(emitterOutwardVO);
		outwardKitDetailsRepo.save(outwardKitDetailVO);
		int reqturnqty = outwardKitDetailsRepo.getReturnQty(emitterOutwardVO.getId());
		emitterOutwardVO.setKitReturnqty(reqturnqty);
		emitterOutwardRepo.save(emitterOutwardVO);
		ReturnStockVO returnStockVO = new ReturnStockVO();
		returnStockVO.setIssue_item_id(emitterOutwardVO.getIssueItemVO().getId());
		returnStockVO.setQty(outwardKitDetailsDTO.getKitQty() * -1);
		returnStockRepo.save(returnStockVO);

		return outwardKitDetailsRepo.save(outwardKitDetailVO);

	}

	@Override
	public BinAllotmentNewVO createBinAllotment(BinAllotmentDTO binAllotmentDTO) {

		boolean isBinReqNoExist = binAllotmentNewRepo.existsByBinReqNo(binAllotmentDTO.getBinReqNo());

		if (isBinReqNoExist) {
			throw new RuntimeException("ReqNo " + binAllotmentDTO.getBinReqNo() + " already exists.");
		}

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
				assetStockDetailsVO.setRfId(allotmentDetailsVO2.getRfId());
				assetStockDetailsVO.setTagCode(allotmentDetailsVO2.getTagCode());
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setSCode(allotmentNewVO.getScode()); // Assuming getScode() returns the correct
				assetStockDetailsVO.setSourceId(allotmentDetailsVO2.getId()); // value
				assetStockDetailsVO.setScreen("BIN ALLOTMENT");
				assetStockDetailsVO.setPm("M");
				assetStockDetailsVO.setStatus("S");
				assetStockDetailsVO.setFinyr(allotmentNewVO.getFinyr());
				assetStockDetailsVO.setStockBranch(allotmentNewVO.getStockBranch());
				assetStockDetailsRepo.save(assetStockDetailsVO);
			}
			for (BinAllotmentDetailsVO allotmentDetailsVO2 : allotmentDetailsVO) {
				AssetStockDetailsVO assetStockDetailsVO = new AssetStockDetailsVO();
				assetStockDetailsVO.setStockRef(allotmentNewVO.getDocId());
				assetStockDetailsVO.setStockDate(allotmentNewVO.getDocDate());
				assetStockDetailsVO.setSkuCode(allotmentDetailsVO2.getAssetCode());
				assetStockDetailsVO.setSku(allotmentDetailsVO2.getAsset());
				assetStockDetailsVO.setSkuQty(1);
				assetStockDetailsVO.setRfId(allotmentDetailsVO2.getRfId());
				assetStockDetailsVO.setTagCode(allotmentDetailsVO2.getTagCode());
				assetStockDetailsVO.setStockSource("");
				assetStockDetailsVO.setSCode(allotmentNewVO.getScode()); // Assuming getScode() returns the correct
				assetStockDetailsVO.setSourceId(allotmentDetailsVO2.getId()); // value
				assetStockDetailsVO.setScreen("BIN ALLOTMENT");
				assetStockDetailsVO.setPm("P");
				assetStockDetailsVO.setStatus("M");
				assetStockDetailsVO.setFinyr(allotmentNewVO.getFinyr());
				assetStockDetailsVO.setStockBranch(allotmentNewVO.getEmitter() + "-" + allotmentNewVO.getStockBranch());
				assetStockDetailsRepo.save(assetStockDetailsVO);
			}
		}
		return binAllotmentNewVO;
	}

	@Override
	public Set<Object[]> getReqDetailsByOrgId(Long orgId) {
		return binAllotmentNewRepo.findReqDetailsByOrgId(orgId);
	}

	@Override
	public List<BinAllotmentNewVO> getAllBinAllotment(Long orgId) {
		List<BinAllotmentNewVO> binAllotmentNewVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  BinAllotment BY orgId : {}", orgId);
			binAllotmentNewVO = binAllotmentNewRepo.getAllBinAllotment(orgId);
		} else {
			LOGGER.info("Successfully Received  BinAllotment For All orgId.");
			binAllotmentNewVO = binAllotmentNewRepo.findAll();
		}
		return binAllotmentNewVO;
	}

	@Override
	public Optional<AssetTaggingDetailsVO> getTaggingDetailsByRfId(String rfId) {

		return taggingDetailsRepo.findByRfId(rfId);
	}

	@Override
	public Optional<AssetTaggingDetailsVO> getTaggingDetailsByTagCode(String tagCode) {

		return taggingDetailsRepo.findByTagCode(tagCode);
	}

	// Bin Outward

	@Override
	public BinOutwardVO createBinOutward(BinOutwardDTO binOutwardDTO) {

		BinOutwardVO binOutwardVO = createBinOutwardVOByBinOutwardDTO(binOutwardDTO);
		int finyr = binOutwardRepo.findfinyr();
		String binoutward = finyr + "BO" + binOutwardRepo.finddocid();
		binOutwardVO.setDocId(binoutward);
		binOutwardRepo.nextseq();
		binOutwardRepo.save(binOutwardVO);
		BinOutwardVO savedBinOutwardVO = binOutwardRepo.save(binOutwardVO);

		List<BinOutwardDetailsVO> binOutwardDetailsVOLists = savedBinOutwardVO.getBinOutwardDetails();
		if (binOutwardDetailsVOLists != null && !binOutwardDetailsVOLists.isEmpty())
			for (BinOutwardDetailsVO binOutwardDetailsVO : binOutwardDetailsVOLists) {

				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(savedBinOutwardVO.getDocId());
				stockDetailsVO.setStockDate(savedBinOutwardVO.getDocDate());
				stockDetailsVO.setSku(binOutwardDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(binOutwardDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(-binOutwardDetailsVO.getQty());
				stockDetailsVO.setSCode(savedBinOutwardVO.getScode());
				stockDetailsVO.setPm(savedBinOutwardVO.getPm());
				stockDetailsVO.setScreen(savedBinOutwardVO.getScreen());
				stockDetailsVO.setSourceId(binOutwardDetailsVO.getId());
				stockDetailsVO.setFinyr(savedBinOutwardVO.getFinyr());
				assetStockDetailsRepo.save(stockDetailsVO);
			}
		return binOutwardVO;
	}

	private BinOutwardVO createBinOutwardVOByBinOutwardDTO(BinOutwardDTO binOutwardDTO) {
		List<BinOutwardDetailsVO> binOutwardDetailsVOList = new ArrayList<>();
		BinOutwardVO binOutwardVO = BinOutwardVO.builder().docDate(binOutwardDTO.getDocDate())
				.flow(binOutwardDTO.getFlow()).orgId(binOutwardDTO.getOrgId()).createdby(binOutwardDTO.getCreatedBy())
				.modifiedby(binOutwardDTO.getCreatedBy()).kit(binOutwardDTO.getKit())
				.outwardKitQty(binOutwardDTO.getOutwardKitQty()).binOutwardDetails(binOutwardDetailsVOList).build();

		binOutwardDetailsVOList = binOutwardDTO.getBinOutwardDetails().stream()
				.map(binoutward -> BinOutwardDetailsVO.builder().asset(binoutward.getAsset())
						.assetCode(binoutward.getAssetCode()).qty(binoutward.getQty()).binOutwardVO(binOutwardVO)
						.build())
				.collect(Collectors.toList());
		binOutwardVO.setBinOutwardDetails(binOutwardDetailsVOList);
		return binOutwardVO;
	}

	@Override
	public List<BinAllotmentNewVO> getAllAllotmentById(String docId) {
		List<BinAllotmentNewVO> binAllotmentNewVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(docId)) {
			LOGGER.info("Successfully Received  Bin ALlotment BY docId : {}", docId);
			binAllotmentNewVO = binAllotmentNewRepo.getAllAssetByOrgId(docId);
		} else {
			LOGGER.info("Successfully Received  Bin ALlotment For All docId.");
			binAllotmentNewVO = binAllotmentNewRepo.findAll();
		}
		return binAllotmentNewVO;
	}

	@Override
	public Set<Object[]> getkitAssetDetailsByKitId(String kitCode, int quantity) {
		return kitAssetRepo.getAssetDetails(kitCode, quantity);
	}

}
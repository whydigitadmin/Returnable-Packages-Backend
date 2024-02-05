package com.whydigit.efit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.whydigit.efit.common.EmitterConstant;
import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.IssueItemDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.Role;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.IssueItemVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.IssueRequestRepo;
import com.whydigit.efit.repo.UserRepo;

@Service
public class EmitterServiceImpl implements EmitterService {
	public static final Logger LOGGER = LoggerFactory.getLogger(EmitterServiceImpl.class);
	@Autowired
	IssueRequestRepo issueRequestRepo;
	@Autowired
	UserRepo userRepo;

	@Autowired
	FlowRepo flowRepo;
	
	@Override
	public IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO) throws ApplicationException {
		IssueRequestVO issueRequestVO = new IssueRequestVO();
		List<IssueItemVO> issueItemVO = new ArrayList<>();
		FlowVO flowVO = flowRepo.findById(null)
				.orElseThrow(() -> new ApplicationException("Invalid flow. Please try again."));
		getIssueRequestVOFromIssueRequestDTO(issueRequestDTO, issueRequestVO);
		for (IssueItemDTO issueItemDTO : issueRequestDTO.getIssueItemDTO()) {
			IssueItemVO issueItem = new IssueItemVO();
			getIssueIemVOFromIssueRequestDTO(issueItemDTO, issueRequestVO, issueItem);
			issueItemVO.add(issueItem);
		}
		issueRequestVO.setFlowName(flowVO.getFlowName());
		issueRequestVO.setIssueItemVO(issueItemVO);
		issueRequestVO.setTotalIssueItem(issueItemVO.size());
		issueRequestVO = issueRequestRepo.save(issueRequestVO);
		return issueRequestVO;
	}

	private void getIssueIemVOFromIssueRequestDTO(IssueItemDTO issueItemDTO, IssueRequestVO issueRequestVO,
			IssueItemVO issueItem) {
		issueItem.setIssueItemStatus(0);
		issueItem.setKitNo(issueItemDTO.getKitNo());
		issueItem.setKitQty(issueItemDTO.getKitQty());
		issueItem.setPartNo(issueItemDTO.getPartNo());
		issueItem.setPartQty(issueItemDTO.getPartQty());
		issueItem.setRemark(issueItemDTO.getRemark());
		issueItem.setIssueRequestVO(issueRequestVO);
		issueItem.setIssueItemStatus(EmitterConstant.ISSUE_REQUEST_STATUS_PENDING);
		issueItem.setIssuedQty(0);
	}

	private void getIssueRequestVOFromIssueRequestDTO(IssueRequestDTO issueRequestDTO, IssueRequestVO issueRequestVO) {
		LocalDateTime currentDateTime=LocalDateTime.now();
		issueRequestVO.setDemandDate(issueRequestDTO.getDemandDate());
		issueRequestVO.setFlowTo(issueRequestDTO.getFlowTo());
		issueRequestVO.setIssueStatus(0);
		issueRequestVO.setRemark(issueRequestDTO.getRemark());
		issueRequestVO.setReqAddressId(issueRequestDTO.getReqAddressId());
		issueRequestVO.setRequestedDate(currentDateTime);
		issueRequestVO.setOrgId(issueRequestDTO.getOrgId());
		issueRequestVO.setEmitterId(issueRequestDTO.getEmitterId());
		issueRequestVO.setTat(ChronoUnit.HOURS.between(currentDateTime,issueRequestDTO.getDemandDate().atStartOfDay()));
	}

	@Override
	public List<IssueRequestVO> getIssueRequest(Long emitterId, Long orgId, LocalDate startDate, LocalDate endDate) {

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
	public IssueRequestVO updateIssueQty(Long issueRequestId, Long issueItemId, int issuedQty)
			throws ApplicationException {
		IssueRequestVO issueRequestVO = issueRequestRepo.findById(issueRequestId)
				.orElseThrow(() -> new ApplicationException("Invalid issueRequest information."));
		issueRequestVO.getIssueItemVO().stream().filter(item -> item.getId() == issueItemId).forEach(item -> {
			item.setIssuedQty(issuedQty);
			item.setIssueItemStatus(getItemStatus(issuedQty, item.getKitQty()));
		});
		if (issueRequestVO.getIssueItemVO().stream()
				.allMatch(flag -> flag.getIssueItemStatus() > EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_INPROGRESS)) {
			issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_ISSUED);
		}
		// Check if any status flag is equal to 1
		else if (issueRequestVO.getIssueItemVO().stream()
				.anyMatch(flag -> flag.getIssueItemStatus() == EmitterConstant.ISSUE_REQUEST_ITEM_STATUS_INPROGRESS)) {
			issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_INPROGRESS);
		}
		// If none of the above conditions are met, return 0
		else {
			issueRequestVO.setIssueStatus(EmitterConstant.ISSUE_REQUEST_STATUS_PENDING);
		}
		return issueRequestRepo.save(issueRequestVO);
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
}

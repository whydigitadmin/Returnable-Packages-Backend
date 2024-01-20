package com.whydigit.efit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.IssueItemDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.entity.IssueItemVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.repo.IssueRequestRepo;

@Service
public class EmitterServiceImpl implements EmitterService {
	public static final Logger LOGGER = LoggerFactory.getLogger(EmitterServiceImpl.class);
	@Autowired
	IssueRequestRepo issueRequestRepo;

	@Override
	public IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO) {
		IssueRequestVO issueRequestVO = new IssueRequestVO();
		List<IssueItemVO> issueItemVO = new ArrayList<>();
		getIssueRequestVOFromIssueRequestDTO(issueRequestDTO, issueRequestVO);
		for (IssueItemDTO issueItemDTO : issueRequestDTO.getIssueItemDTO()) {
			IssueItemVO issueItem = new IssueItemVO();
			getIssueIemVOFromIssueRequestDTO(issueItemDTO, issueRequestVO, issueItem);
			issueItemVO.add(issueItem);
		}
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
	}

	private void getIssueRequestVOFromIssueRequestDTO(IssueRequestDTO issueRequestDTO, IssueRequestVO issueRequestVO) {
		issueRequestVO.setDemandDate(issueRequestDTO.getDemandDate());
		issueRequestVO.setFlowTo(issueRequestDTO.getFlowTo());
		issueRequestVO.setIssueStatus(0);
		issueRequestVO.setRemark(issueRequestDTO.getRemark());
		issueRequestVO.setReqAddressId(issueRequestDTO.getReqAddressId());
		issueRequestVO.setRequestedDate(LocalDateTime.now());
		issueRequestVO.setReqUserId(issueRequestDTO.getReqUserId());
	}

}

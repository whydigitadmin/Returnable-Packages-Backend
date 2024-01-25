package com.whydigit.efit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.entity.IssueRequestVO;

@Service
public interface EmitterService {

	IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO);

	public List<IssueRequestVO> getIssueRequest(Long emitterId, Long orgId, LocalDate startDate, LocalDate endDate) ;
}

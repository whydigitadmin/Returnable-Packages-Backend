package com.whydigit.efit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface EmitterService {

	IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO) throws ApplicationException;

	public List<IssueRequestVO> getIssueRequest(Long emitterId, Long orgId, LocalDate startDate, LocalDate endDate) ;

	List<EmitterAddressDTO> getEmitterAddress(Long orgId);

	IssueRequestVO updateIssueQty(Long issueRequestId, Long issueItemId, int issuedQty) throws ApplicationException;
}

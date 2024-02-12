
package com.whydigit.efit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.IssueRequestQtyApprovelDTO;
import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface EmitterService {

	IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO) throws ApplicationException;

	public List<IssueRequestVO> getIssueRequest(Long emitterId, Long orgId, LocalDate startDate, LocalDate endDate);

	List<EmitterAddressDTO> getEmitterAddress(Long orgId);

	// emitter inward
	List<EmitterInwardVO> getAllEmitterInward(Long orgId);

	Optional<EmitterInwardVO> getEmitterInwardById(int id);

	EmitterInwardVO createEmitterInward(EmitterInwardVO emitterInwardVO);

	Optional<EmitterInwardVO> updateEmitterInward(EmitterInwardVO emitterInwardVO);

	void deleteEmitterInward(int id);

	// emitter outward
	List<EmitterOutwardVO> getAllEmitterOutward(Long orgId);

	Optional<EmitterOutwardVO> getEmitterOutwardById(int id);

	EmitterOutwardVO createEmitterOutward(EmitterOutwardVO emitterOutwardVO);

	Optional<EmitterOutwardVO> updateEmitterOutward(EmitterOutwardVO emitterOutwardVO);

	void deleteEmitterOutward(int id);

	IssueRequestVO issueRequestQtyApprovel(IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO) throws ApplicationException;

	void cancelIssueRequest(Long issueRequestId, Long issueRequestItemId) throws ApplicationException;

}

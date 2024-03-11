
package com.whydigit.efit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.InwardDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.IssueRequestQtyApprovelDTO;
import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.InwardVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.entity.VwEmitterInwardVO;
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

	void deleteEmitterInward(int id);

	// emitter outward
	List<EmitterOutwardVO> getAllEmitterOutward(Long orgId);

	Optional<EmitterOutwardVO> getEmitterOutwardById(int id);

	EmitterOutwardVO createEmitterOutward(EmitterOutwardVO emitterOutwardVO);

	Optional<EmitterOutwardVO> updateEmitterOutward(EmitterOutwardVO emitterOutwardVO);

	void deleteEmitterOutward(int id);

	IssueRequestVO issueRequestQtyApprovel(IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO) throws ApplicationException;

	void cancelIssueRequest(Long issueRequestId, Long issueRequestItemId) throws ApplicationException;

	InwardVO updateEmitterInward(InwardDTO inwardDTO) throws ApplicationException;

	List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndEmtId(Long orgId, Long emitterId);

	List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndEmtIdAndFlow(Long orgId, Long emitterId, Long flowid);

	List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndWarehouse(Long orgId, Long warehouseid);

	Map<String, Object> getAllViewEmitterInward(Long orgId,Long emitterId, Long flowId,
			Long warehouseLocationId);
	
	Set<Object[]>getEmitterByWarehouseId(Long orgId,Long warehouseId);

}

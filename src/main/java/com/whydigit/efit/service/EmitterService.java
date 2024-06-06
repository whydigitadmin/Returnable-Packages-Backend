
package com.whydigit.efit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.efit.dto.BinAllotmentDTO;
import com.whydigit.efit.dto.BinInwardDTO;
import com.whydigit.efit.dto.BinOutwardDTO;
import com.whydigit.efit.dto.DispatchDTO;
import com.whydigit.efit.dto.EmitterAddressDTO;
import com.whydigit.efit.dto.InwardDTO;
import com.whydigit.efit.dto.IssueRequestDTO;
import com.whydigit.efit.dto.IssueRequestQtyApprovelDTO;
import com.whydigit.efit.dto.OutwardKitDetailsDTO;
import com.whydigit.efit.entity.AssetTaggingDetailsVO;
import com.whydigit.efit.entity.BinAllotmentNewVO;
import com.whydigit.efit.entity.BinInwardVO;
import com.whydigit.efit.entity.BinOutwardVO;
import com.whydigit.efit.entity.DispatchVO;
import com.whydigit.efit.entity.EmitterInwardVO;
import com.whydigit.efit.entity.EmitterOutwardVO;
import com.whydigit.efit.entity.InwardVO;
import com.whydigit.efit.entity.IssueRequestVO;
import com.whydigit.efit.entity.OutwardKitDetailsVO;
import com.whydigit.efit.entity.OutwardView;
import com.whydigit.efit.entity.VwEmitterInwardVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface EmitterService {

	IssueRequestVO createIssueRequest(IssueRequestDTO issueRequestDTO) throws ApplicationException;

//	public List<IssueRequestVO> getIssueRequest(Long emitterId, Long orgId, LocalDate startDate, LocalDate endDate,
//			Long warehouseLocationId, String warehouseLocation);

	List<EmitterAddressDTO> getEmitterAddress(Long orgId);

	// emitter inward
	List<EmitterInwardVO> getAllEmitterInward(Long orgId);

	Optional<EmitterInwardVO> getEmitterInwardById(int id);

	EmitterInwardVO createEmitterInward(EmitterInwardVO emitterInwardVO);
	
	List<Object[]> getEmitterDispatchByFlowId(Long orgId , Long flowId , Long emitterId);

	void deleteEmitterInward(int id);

	// emitter outward
	List<BinOutwardVO> getAllBinOutward(Long orgId, Long emitterId);
	
	List<BinOutwardVO> getAllBinOutwardByDocId(String docId);

	Optional<EmitterOutwardVO> getEmitterOutwardById(long id);

	EmitterOutwardVO createEmitterOutward(EmitterOutwardVO emitterOutwardVO);

	Optional<EmitterOutwardVO> updateEmitterOutward(EmitterOutwardVO emitterOutwardVO);

	void deleteEmitterOutward(long id);

	IssueRequestVO issueRequestQtyApprovel(IssueRequestQtyApprovelDTO issueRequestQtyApprovelDTO)
			throws ApplicationException;

	void cancelIssueRequest(Long issueRequestId, Long issueRequestItemId) throws ApplicationException;

	InwardVO updateEmitterInward(InwardDTO inwardDTO) throws ApplicationException;

	List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndEmtId(Long orgId, Long emitterId);

	List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndEmtIdAndFlow(Long orgId, Long emitterId, Long flowid);

	List<VwEmitterInwardVO> getVwEmtInwardByOrgIdAndWarehouse(Long orgId, Long warehouseid);

	Map<String, Object> getAllViewEmitterInward(Long orgId, Long emitterId, Long flowId, Long warehouseLocationId);

	Set<Object[]> getEmitterByWarehouseId(Long orgId, Long warehouseId);

	Map<String, Object> getAllMaxPartQtyPerKit(Long orgId, Long emitterId, Long flowId, String partNumber);

	List<IssueRequestVO> getIssueRequest(Long emitterId, String warehouseLocation, Long orgId, LocalDate startDate,
			LocalDate endDate, Long warehouseLoacationId);

	List<OutwardView> getAllEmitterOutwardView(Long orgId, Long flowId);

	OutwardKitDetailsVO updateOutwardKitQty(OutwardKitDetailsDTO outwardKitDetailsDTO) throws ApplicationException;

	// get Issue request

//	List<BinAllotmentVO> getBinRequest(Long emitterId, String warehouseLocation, Long orgId, LocalDate startDate,
//			LocalDate endDate, Long warehouseLoacationId);

	// Bin Allotment

	Set<Object[]> getReqDetailsByOrgId(Long orgId, String reqNo);

	Optional<AssetTaggingDetailsVO> getTaggingDetailsByRfId(String rfId);

	Optional<AssetTaggingDetailsVO> getTaggingDetailsByTagCode(String tagCode);

	List<BinAllotmentNewVO> getAllBinAllotment(Long orgId);

	// Bin Outward
	
	BinOutwardVO createBinOutward(BinOutwardDTO binOutwardDTO);
	
	List<BinAllotmentNewVO> getAllAllotmentById(String docId);

	Set<Object[]> getkitAssetDetailsByKitId(String kitCode, int quantity);

	Set<Object[]> getIssueRequestreportByOrgId(Long orgId, Long userId);


	String uploadPodFilePath(MultipartFile file, String allotNo);


	String getDocIdByBinallotment();

	String getDocIdByBinOutward();
	
	

	List<BinAllotmentNewVO> getCustomizedAllotmentDetailsByEmitter(String kitCode, String flow, Long emitterId,
			LocalDate startAllotDate, LocalDate endAllotDate);
	
	DispatchVO createDispatch(DispatchDTO dispatchDTO);
	
	List<DispatchVO>getAllDispatchVO(Long emitterId);
	
	DispatchVO getDispatchById(Long id);
	


	BinInwardVO updateCreateBinInward(BinInwardDTO binInwardDTO) throws ApplicationException;
	
	

	


}

package com.whydigit.efit.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.GatheringEmptyDTO;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.RetreivalDTO;
import com.whydigit.efit.dto.TransportPickupDTO;
import com.whydigit.efit.entity.GatheringEmptyVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.entity.RetreivalVO;
import com.whydigit.efit.entity.TransportPickupVO;

@Service
public interface OemService {

//	OEM Bin Inward
	OemBinInwardVO createOemBinInward(OemBinInwardDTO oemBinInwardDTO);

//	OEM Bin Outward
	OemBinOutwardVO createOemBinOutward(OemBinOutwardDTO binOutwardDTO);

	Set<Object[]> getFlowByUserId(Long userId, Long orgId);

	Set<Object[]> getOutwardDetailsByFlow(Long flowId, Long orgId);

	List<OemBinInwardVO> getAllOemBinInward(Long orgId);

	OemBinInwardVO getAllOemBinInwardByDocId(String docId);

	String getDocIdByOemBinInward();

	List<Map<String, Object>> getOemStockBranchByUserId(Long orgId, Long userId);
	
	List<Map<String, Object>> getOemStockDeatilsForOemOutward(String stockBranch);
	
	String getDocIdByOemBinOutward();

	GatheringEmptyVO createGatheringEmpty(GatheringEmptyDTO gatheringEmptyDTO);
	
	String getDocIdByGatheringEmpty();

	List<GatheringEmptyVO> getAllGathering(Long orgId);

	List<OemBinOutwardVO> getAllOemBinOutward(Long orgId);
	
	List<Map<String, Object>> getOemEmptyDeatilsForEmptyGathering(String stockBranch,Long orgId);

	
	// Oem Outward Stock details for Retreival
	List<Map<String, Object>> getOemOutwardDeatilsForRetreival(Long orgId, Long receiverId, String stockBranch);
	
	
	
	RetreivalVO CreateRetreival(RetreivalDTO retreivalDTO);

	String getDocIdByRetreival();

	List<Map<String, Object>> getRetrievalDeatilsForPendingPickup(Long orgId, Long receiverId);

	List<Map<String, Object>> getRetrievalDeatilsByRmNoforPickupFillgrid(Long orgId, String rmNo);
	
	TransportPickupVO createTransPickup(TransportPickupDTO transportPickupDTO);

	String getDocIdByTransportPickup();

	List<OemBinInwardVO> getAllOemBinInwardByReceiverId(Long receiverId);

	List<OemBinOutwardVO> getAllOemBinOutwardByReceiverId(Long receiverId);

	List<GatheringEmptyVO> getAllGatheringEmptyByReceiverId(Long receiverId);

	List<RetreivalVO> getAllReterivalByReceiverId(Long receiverId);

	List<TransportPickupVO> getAllTranportPickupByReceiverId(Long receiverId);
}

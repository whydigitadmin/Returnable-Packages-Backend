package com.whydigit.efit.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.GatheringEmptyDTO;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.entity.GatheringEmptyVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardVO;

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

	List<GatheringEmptyVO> getAllGathering(Long orgId);

	List<OemBinOutwardVO> getAllOemBinOutward(Long orgId);
	
	List<Map<String, Object>> getOemEmptyDeatilsForEmptyGathering(String stockBranch,Long orgId);


}

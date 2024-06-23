package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.GathereingEmptyDetailsDTO;
import com.whydigit.efit.dto.GatheringEmptyDTO;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinInwardDetailsDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDetailsDTO;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.BinOutwardDetailsVO;
import com.whydigit.efit.entity.BinOutwardVO;
import com.whydigit.efit.entity.DispatchVO;
import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.GathereingEmptyDetailsVO;
import com.whydigit.efit.entity.GatheringEmptyVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardDetailsVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.repo.AssetRepo;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
import com.whydigit.efit.repo.BinOutwardRepo;
import com.whydigit.efit.repo.DispatchRepository;
import com.whydigit.efit.repo.FlowRepo;
import com.whydigit.efit.repo.GatheringEmptyRepo;
import com.whydigit.efit.repo.OemBinInwardDetailsRepo;
import com.whydigit.efit.repo.OemBinInwardRepo;
import com.whydigit.efit.repo.OemBinOutwardDetailsRepo;
import com.whydigit.efit.repo.OemBinOutwardRepo;
import com.whydigit.efit.repo.UserRepo;

@Service
public class OemServiceImpl implements OemService {
	public static final Logger LOGGER = LoggerFactory.getLogger(OemServiceImpl.class);

	@Autowired
	AssetStockDetailsRepo assetStockDetailsRepo;

	@Autowired
	OemBinInwardRepo oemBinInwardRepo;

	@Autowired
	OemBinInwardDetailsRepo oemBinInwardDetailsRepo;

	@Autowired
	OemBinOutwardRepo oemBinOutwardRepo;
	
	@Autowired
	DispatchRepository dispatchRepository;

	@Autowired
	OemBinOutwardDetailsRepo oemBinOutwardDetailsRepo;

	@Autowired
	AssetRepo assetRepo;

	@Autowired
	FlowRepo flowRepo;

	@Autowired
	BinOutwardRepo binOutwardRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	GatheringEmptyRepo gatheringEmptyRepo;

	@Override
	public OemBinInwardVO createOemBinInward(OemBinInwardDTO oemBinInwardDTO) {

		OemBinInwardVO oemBinInwardVO = new OemBinInwardVO();
		String finyr = oemBinInwardRepo.findFinyr();
		String binoutward = finyr + "OBI" + oemBinInwardRepo.finddocid();
		oemBinInwardVO.setDocId(binoutward);
		oemBinInwardRepo.nextseq();

		oemBinInwardVO.setDocId(oemBinInwardDTO.getDocId());
		oemBinInwardVO.setDocDate(oemBinInwardDTO.getDocDate());
		oemBinInwardVO.setFlowId(oemBinInwardDTO.getFlowId());
		FlowVO flowVO = flowRepo.findById(oemBinInwardDTO.getFlowId()).get();
		oemBinInwardVO.setFlow(flowVO.getFlowName());
		
		oemBinInwardVO.setCreatedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setModifiedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setDispatchId(oemBinInwardDTO.getDispatchId());
		oemBinInwardVO.setInvoiceNo(oemBinInwardDTO.getInvoiceNo());
		DispatchVO dispatchVO=dispatchRepository.findByInvoiceNoAndOrgId(oemBinInwardDTO.getInvoiceNo(),oemBinInwardDTO.getOrgId());
		dispatchVO.setOemInwardNo(oemBinInwardDTO.getOemInwardNo());
		dispatchVO.setOemInwardDate(oemBinInwardDTO.getOemInwardDate());
		dispatchRepository.save(dispatchVO);
		oemBinInwardVO.setOrgId(oemBinInwardDTO.getOrgId());
		oemBinInwardVO.setCreatedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setOemInwardNo(oemBinInwardDTO.getOemInwardNo());
		oemBinInwardVO.setOemInwardDate(oemBinInwardDTO.getOemInwardDate());
		oemBinInwardVO.setInvoiceDate(oemBinInwardDTO.getInvoiceDate());
		oemBinInwardVO.setModifiedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setEmitterId(oemBinInwardDTO.getEmitterId());
		List<OemBinInwardDetailsVO> oemBinInwardDetailsVOs = new ArrayList<>();
		if (oemBinInwardDTO.getOemBinInwardDetails() != null) {
			for (OemBinInwardDetailsDTO oemBinInwardDetailsDTO : oemBinInwardDTO.getOemBinInwardDetails()) {
				OemBinInwardDetailsVO oemBinInwardDetailsVO = new OemBinInwardDetailsVO();
				oemBinInwardDetailsVO.setOutwardDocDate(oemBinInwardDetailsDTO.getOutwardDocDate());
				oemBinInwardDetailsVO.setOutwardDocId(oemBinInwardDetailsDTO.getOutwardDocId());
				oemBinInwardDetailsVO.setPartName(oemBinInwardDetailsDTO.getPartName());
				oemBinInwardDetailsVO.setPartNo(oemBinInwardDetailsDTO.getPartNo());
				oemBinInwardDetailsVO.setAllotedQty(oemBinInwardDetailsDTO.getAllotedQty());
				oemBinInwardDetailsVO.setKitNo(oemBinInwardDetailsDTO.getKitNo());
				oemBinInwardDetailsVO.setReceivedKitQty(oemBinInwardDetailsDTO.getReceivedKitQty());
				oemBinInwardDetailsVO.setOemBinInwardVO(oemBinInwardVO);
				oemBinInwardDetailsVOs.add(oemBinInwardDetailsVO);
				
				BinOutwardVO binOutwardVO = binOutwardRepo.findByDocId(oemBinInwardDetailsDTO.getOutwardDocId());
				List<BinOutwardDetailsVO> binOutwardDetailsVOLists = binOutwardVO.getBinOutwardDetails();
				if (binOutwardDetailsVOLists != null && !binOutwardDetailsVOLists.isEmpty())
					for (BinOutwardDetailsVO binOutwardDetailsVO : binOutwardDetailsVOLists) {
						AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
						stockDetailsVO.setStockRef(oemBinInwardVO.getDispatchId());
						stockDetailsVO.setStockBranch(binOutwardVO.getReceiver() + "-" + binOutwardVO.getDestination());
						stockDetailsVO.setStockDate(oemBinInwardVO.getDocDate());
						stockDetailsVO.setSku(binOutwardDetailsVO.getAsset());
						stockDetailsVO.setSkuCode(binOutwardDetailsVO.getAssetCode());
						stockDetailsVO.setSkuQty(binOutwardDetailsVO.getQty() * -1);
						stockDetailsVO.setOrgId(binOutwardVO.getOrgId());
						stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(binOutwardDetailsVO.getAssetCode()));
						stockDetailsVO.setStatus("M");
						stockDetailsVO.setScreen("Dispatch");
						stockDetailsVO.setSCode(dispatchVO.getScode());
						stockDetailsVO.setPm("M");
						stockDetailsVO.setStockSource("");
						stockDetailsVO.setBinLocation("");
						stockDetailsVO.setCancelRemarks("");
						stockDetailsVO.setStockLocation("");
						stockDetailsVO.setSourceId(binOutwardDetailsVO.getId());
						stockDetailsVO.setFinyr(binOutwardVO.getFinyr());
						assetStockDetailsRepo.save(stockDetailsVO);
					}

				for (BinOutwardDetailsVO binOutwardDetailsVO : binOutwardDetailsVOLists) {
					AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
					stockDetailsVO.setStockRef(oemBinInwardVO.getDocId());
					stockDetailsVO.setStockBranch(binOutwardVO.getReceiver() + "-" + binOutwardVO.getDestination());
					stockDetailsVO.setStockDate(oemBinInwardVO.getDocDate());
					stockDetailsVO.setSku(binOutwardDetailsVO.getAsset());
					stockDetailsVO.setSkuCode(binOutwardDetailsVO.getAssetCode());
					stockDetailsVO.setSkuQty(binOutwardDetailsVO.getQty());
					stockDetailsVO.setOrgId(binOutwardVO.getOrgId());
					stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(binOutwardDetailsVO.getAssetCode()));
					stockDetailsVO.setStatus("S");
					stockDetailsVO.setScreen(oemBinInwardVO.getScreen());
					stockDetailsVO.setSCode(oemBinInwardVO.getScode());
					stockDetailsVO.setPm("P");
					stockDetailsVO.setStockSource("");
					stockDetailsVO.setBinLocation("");
					stockDetailsVO.setCancelRemarks("");
					stockDetailsVO.setStockLocation("");
					stockDetailsVO.setSourceId(binOutwardDetailsVO.getId());
					stockDetailsVO.setFinyr(binOutwardVO.getFinyr());
					assetStockDetailsRepo.save(stockDetailsVO);
				}
			}
		}
		oemBinInwardVO.setOemBinInwardDetails(oemBinInwardDetailsVOs);
		return oemBinInwardRepo.save(oemBinInwardVO);
	}

	@Override
	public Set<Object[]> getFlowByUserId(Long userId, Long orgId) {
		return oemBinInwardDetailsRepo.findFlowFromOemByOrgId(userId, orgId);
	}

	@Override
	public Set<Object[]> getOutwardDetailsByFlow(Long flowId, Long orgId) {
		return oemBinInwardDetailsRepo.findgetOutwardDetailsByFlow(flowId, orgId);
	}

	@Override
	public List<OemBinInwardVO> getAllOemBinInward(Long orgId) {
		return oemBinInwardRepo.findAllOemBinInwardByOrgIdAndUserId(orgId);
	}

	@Override
	public OemBinOutwardVO createOemBinOutward(OemBinOutwardDTO oemBinOutwardDTO) {

		OemBinOutwardVO oemBinOutwardVO = new OemBinOutwardVO();
		String finyr = oemBinOutwardRepo.findFinyr();
		String binoutward = finyr + "OBO" + oemBinInwardRepo.finddocid();
		oemBinOutwardVO.setDocId(binoutward);
		oemBinOutwardRepo.nextSeq();
		oemBinOutwardVO.setDocDate(oemBinOutwardDTO.getDocDate());
		oemBinOutwardVO.setOrgId(oemBinOutwardDTO.getOrgId());
		oemBinOutwardVO.setCreatedby(oemBinOutwardDTO.getCreatedby());
		oemBinOutwardVO.setModifiedby(oemBinOutwardDTO.getCreatedby());
		oemBinOutwardVO.setModifiedby(oemBinOutwardDTO.getCreatedby());
		oemBinOutwardVO.setOrgId(oemBinOutwardDTO.getOrgId());
		oemBinOutwardVO.setEmitterId(oemBinOutwardDTO.getEmitterId());
		oemBinOutwardVO.setStockBranch(oemBinOutwardDTO.getStockBranch());

		List<OemBinOutwardDetailsVO> oemBinOutwardDetailsVOs = new ArrayList<>();
		if (oemBinOutwardDTO.getOemBinOutwardDetails() != null) {
			for (OemBinOutwardDetailsDTO oemBinOutwardDetailsDTO : oemBinOutwardDTO.getOemBinOutwardDetails()) {
				OemBinOutwardDetailsVO oemBinOutwardDetailsVO = new OemBinOutwardDetailsVO()   ;
				oemBinOutwardDetailsVO.setCategory(oemBinOutwardDetailsDTO.getCategory());
				oemBinOutwardDetailsVO.setAsset(oemBinOutwardDetailsDTO.getAsset());
				oemBinOutwardDetailsVO.setAssetCode(oemBinOutwardDetailsDTO.getAssetCode());
				oemBinOutwardDetailsVO.setAvailqty(oemBinOutwardDetailsDTO.getAvailqty());
				oemBinOutwardDetailsVO.setOutQty(oemBinOutwardDetailsDTO.getOutQty());
				oemBinOutwardDetailsVO.setOemBinOutwardVO(oemBinOutwardVO);
				oemBinOutwardDetailsVOs.add(oemBinOutwardDetailsVO);
			}
		}
		oemBinOutwardVO.setOemBinOutwardDetails(oemBinOutwardDetailsVOs);
		OemBinOutwardVO savedOemBinOutwardVO = oemBinOutwardRepo.save(oemBinOutwardVO);
		List<OemBinOutwardDetailsVO> oembinOutwardDetailsVOLists = savedOemBinOutwardVO.getOemBinOutwardDetails();
		if (oembinOutwardDetailsVOLists != null && !oembinOutwardDetailsVOLists.isEmpty())
			for (OemBinOutwardDetailsVO oembinOutwardDetailsVO : oembinOutwardDetailsVOLists) {
				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(savedOemBinOutwardVO.getDocId());
				stockDetailsVO.setStockBranch(savedOemBinOutwardVO.getStockBranch());
				stockDetailsVO.setStockDate(savedOemBinOutwardVO.getDocDate());
				stockDetailsVO.setSku(oembinOutwardDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(oembinOutwardDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(oembinOutwardDetailsVO.getOutQty() * -1);
				stockDetailsVO.setOrgId(savedOemBinOutwardVO.getOrgId());
				stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(oembinOutwardDetailsVO.getAssetCode()));
				stockDetailsVO.setStatus("E");
				stockDetailsVO.setScreen(savedOemBinOutwardVO.getScreen());
				stockDetailsVO.setSCode(savedOemBinOutwardVO.getScode());
				stockDetailsVO.setPm("M");
				stockDetailsVO.setStockSource("");
				stockDetailsVO.setBinLocation("");
				stockDetailsVO.setCancelRemarks("");
				stockDetailsVO.setStockLocation("");
				stockDetailsVO.setSourceId(oembinOutwardDetailsVO.getId());
				stockDetailsVO.setFinyr(savedOemBinOutwardVO.getFinYr());
				assetStockDetailsRepo.save(stockDetailsVO);
			}

		for (OemBinOutwardDetailsVO oembinOutwardDetailsVO : oembinOutwardDetailsVOLists) {
			AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
			stockDetailsVO.setStockRef(savedOemBinOutwardVO.getDocId());
			stockDetailsVO.setStockBranch(savedOemBinOutwardVO.getStockBranch());
			stockDetailsVO.setStockDate(savedOemBinOutwardVO.getDocDate());
			stockDetailsVO.setSku(oembinOutwardDetailsVO.getAsset());
			stockDetailsVO.setSkuCode(oembinOutwardDetailsVO.getAssetCode());
			stockDetailsVO.setSkuQty(oembinOutwardDetailsVO.getOutQty());
			stockDetailsVO.setOrgId(savedOemBinOutwardVO.getOrgId());
			stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(oembinOutwardDetailsVO.getAssetCode()));
			stockDetailsVO.setStatus("R");
			stockDetailsVO.setScreen(savedOemBinOutwardVO.getScreen());
			stockDetailsVO.setSCode(savedOemBinOutwardVO.getScode());
			stockDetailsVO.setPm("P");
			stockDetailsVO.setStockSource("");
			stockDetailsVO.setBinLocation("");
			stockDetailsVO.setCancelRemarks("");
			stockDetailsVO.setStockLocation("");
			stockDetailsVO.setSourceId(oembinOutwardDetailsVO.getId());
			stockDetailsVO.setFinyr(savedOemBinOutwardVO.getFinYr());
			assetStockDetailsRepo.save(stockDetailsVO);
		}
		return savedOemBinOutwardVO; 
	}

	@Override
	public OemBinInwardVO getAllOemBinInwardByDocId(String docId) {

		return oemBinInwardRepo.findOemInwardByDocId(docId);
	}

	@Override
	public String getDocIdByOemBinInward() {
		String finyr = oemBinInwardRepo.findFinyr();
		String binoutward = finyr + "OBI" + oemBinInwardRepo.finddocid();
		return binoutward;
	}

	@Override
	public List<Map<String, Object>> getOemStockBranchByUserId(Long orgId,Long userId) {

		Set<Object[]> stockbranch =userRepo.getOemStockBranchByOrgIdAndUserId(orgId,userId);

		return getOemStockbranch(stockbranch);
	}

	private List<Map<String, Object>> getOemStockbranch(Set<Object[]> stockbranch) {
		List<Map<String, Object>> stockbr = new ArrayList<>();
        for (Object[] ps : stockbranch) {
            Map<String, Object> part = new HashMap<>();
            part.put("stockBranch", ps[0] != null ? ps[0].toString() : "");
            part.put("destination", ps[1] != null ? ps[1].toString() : "");
            stockbr.add(part);
        }
        return stockbr;
	}
	
	@Override
	public List<Map<String, Object>> getOemStockDeatilsForOemOutward(String stockBranch) {
		
		Set<Object[]> stockDetails =assetStockDetailsRepo.getOemStockDetailsForOemBinOutward(stockBranch);
		
		return getOemStockDeatils(stockDetails);
	}

	private List<Map<String, Object>> getOemStockDeatils(Set<Object[]> stockDetails) {
		List<Map<String, Object>> oemStockdetails = new ArrayList<>();
        for (Object[] ps : stockDetails) {
            Map<String, Object> part = new HashMap<>();
            part.put("stockBranch", ps[0] != null ? ps[0].toString() : "");
            part.put("category", ps[1] != null ? ps[1].toString() : "");
            part.put("assetName", ps[2] != null ? ps[2].toString() : "");
            part.put("assetCode", ps[3] != null ? ps[3].toString() : "");
            part.put("availQty", ps[4] != null ? ps[4].toString() : "");
            oemStockdetails.add(part);
        }
        return oemStockdetails;
	}

	@Override
	public String getDocIdByOemBinOutward() {
		String finyr = oemBinOutwardRepo.findFinyr();
		String binoutward = finyr + "OBO" + oemBinOutwardRepo.finddocid();
		return binoutward;
	}

	@Override
	public GatheringEmptyVO createGatheringEmpty(GatheringEmptyDTO gatheringEmptyDTO) {
		GatheringEmptyVO gatheringEmptyVO = new GatheringEmptyVO();
		String finyr = gatheringEmptyRepo.findFinyr();
		String binoutward = finyr + "GEMT" + gatheringEmptyRepo.finddocid();
		gatheringEmptyVO.setDocId(binoutward);
		gatheringEmptyRepo.nextseq();
		
		gatheringEmptyVO.setDocId(gatheringEmptyDTO.getDocId());
		gatheringEmptyVO.setOrgId(gatheringEmptyDTO.getOrgId());
		gatheringEmptyVO.setStockBranch(gatheringEmptyDTO.getStockBranch());
		gatheringEmptyVO.setCreatedBy(gatheringEmptyDTO.getCreatedBy());
		gatheringEmptyVO.setModifiedby(gatheringEmptyDTO.getCreatedBy());

		List<GathereingEmptyDetailsVO> gathereingEmptyDetailsVO = new ArrayList<>();
		if (gatheringEmptyDTO.getGathereingEmptyDetailsDTO() != null) {
			for (GathereingEmptyDetailsDTO gathereingEmptyDetailsDTO : gatheringEmptyDTO.getGathereingEmptyDetailsDTO()) {
				GathereingEmptyDetailsVO gathereingEmptyDetailsVOs = new GathereingEmptyDetailsVO();
				gathereingEmptyDetailsVOs.setAssetName(gathereingEmptyDetailsDTO.getAssetName());
				gathereingEmptyDetailsVOs.setAssetCode(gathereingEmptyDetailsDTO.getAssetCode());
				gathereingEmptyDetailsVOs.setCategory(gathereingEmptyDetailsDTO.getCategory());
				gathereingEmptyDetailsVOs.setEmptyQty(gathereingEmptyDetailsDTO.getEmptyQty());
				gathereingEmptyDetailsVOs.setGatheringEmptyVO(gatheringEmptyVO);
				gathereingEmptyDetailsVO.add(gathereingEmptyDetailsVOs);
			}
		}
		gatheringEmptyVO.setGathereingEmptyDetailsVO(gathereingEmptyDetailsVO);
		 
		GatheringEmptyVO emptyVO = gatheringEmptyRepo.save(gatheringEmptyVO);
		 
		List<GathereingEmptyDetailsVO> gathereingEmptyDetailsVOs = emptyVO.getGathereingEmptyDetailsVO();
		if (gathereingEmptyDetailsVOs != null && !gathereingEmptyDetailsVOs.isEmpty())
			for (GathereingEmptyDetailsVO emptydetails : gathereingEmptyDetailsVOs) {
				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(emptyVO.getDocId());
				stockDetailsVO.setStockBranch(emptyVO.getStockBranch());
				stockDetailsVO.setStockDate(emptyVO.getDocDate());
				stockDetailsVO.setSku(emptydetails.getAssetName());
				stockDetailsVO.setSkuCode(emptydetails.getAssetCode());
				stockDetailsVO.setSkuQty(emptydetails.getEmptyQty() * -1);
				stockDetailsVO.setOrgId(emptyVO.getOrgId());
				stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(emptydetails.getAssetCode()));
				stockDetailsVO.setStatus("S");
				stockDetailsVO.setScreen(emptyVO.getScreen());
				stockDetailsVO.setSCode(emptyVO.getScode());
				stockDetailsVO.setPm("M");
				stockDetailsVO.setStockSource("");
				stockDetailsVO.setBinLocation("");
				stockDetailsVO.setCancelRemarks("");
				stockDetailsVO.setStockLocation("");
				stockDetailsVO.setSourceId(emptydetails.getId());
				stockDetailsVO.setFinyr(emptyVO.getFinyr());
				assetStockDetailsRepo.save(stockDetailsVO);
			}

		for (GathereingEmptyDetailsVO emptydetails : gathereingEmptyDetailsVOs) {
			AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
			stockDetailsVO.setStockRef(emptyVO.getDocId());
			stockDetailsVO.setStockBranch(emptyVO.getStockBranch());
			stockDetailsVO.setStockDate(emptyVO.getDocDate());
			stockDetailsVO.setSku(emptydetails.getAssetName());
			stockDetailsVO.setSkuCode(emptydetails.getAssetCode());
			stockDetailsVO.setSkuQty(emptydetails.getEmptyQty());
			stockDetailsVO.setOrgId(emptyVO.getOrgId());
			stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(emptydetails.getAssetCode()));
			stockDetailsVO.setStatus("E");
			stockDetailsVO.setScreen(emptyVO.getScreen());
			stockDetailsVO.setSCode(emptyVO.getScode());
			stockDetailsVO.setPm("P");
			stockDetailsVO.setStockSource("");
			stockDetailsVO.setBinLocation("");
			stockDetailsVO.setCancelRemarks("");
			stockDetailsVO.setStockLocation("");
			stockDetailsVO.setSourceId(emptydetails.getId());
			stockDetailsVO.setFinyr(emptyVO.getFinyr());
			assetStockDetailsRepo.save(stockDetailsVO);
		}
		 return  emptyVO;
		
	}
	
	@Override
	public List<GatheringEmptyVO> getAllGathering(Long orgId) {
		List<GatheringEmptyVO> gatheringEmptyVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  GatheringEmpty BY OrgId : {}", orgId);
			gatheringEmptyVO = gatheringEmptyRepo.getAllGatheringEmptyByOrgId(orgId);
		} 
		return gatheringEmptyVO;
	}

	@Override
	public List<OemBinOutwardVO> getAllOemBinOutward(Long orgId) {
		List<OemBinOutwardVO> oemBinOutwardVOs=new ArrayList<OemBinOutwardVO>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  OemBinOutward BY OrgId : {}", orgId);
			oemBinOutwardVOs = oemBinOutwardRepo.getAllOemBinOutwardByOrgId(orgId);
		}else {
			LOGGER.info("Successfully Received  OemBinOutward For All.");
			oemBinOutwardVOs = oemBinOutwardRepo.findAll();
		}
		return oemBinOutwardVOs;
		}

	@Override
	public List<Map<String, Object>> getOemEmptyDeatilsForEmptyGathering(String stockBranch,Long orgId) {
		
		Set<Object[]> EmptystockDetails =assetStockDetailsRepo.getOemEmptyStockDetailsForGathering(stockBranch,orgId);
		return emptyGatheringDetails(EmptystockDetails);
	}

	private List<Map<String, Object>> emptyGatheringDetails(Set<Object[]> emptystockDetails) {
		List<Map<String, Object>> oemEmptyStockdetails = new ArrayList<>();
        for (Object[] ps : emptystockDetails) {
            Map<String, Object> part = new HashMap<>();
            part.put("stockBranch", ps[0] != null ? ps[0].toString() : "");
            part.put("category", ps[1] != null ? ps[1].toString() : "");
            part.put("assetName", ps[2] != null ? ps[2].toString() : "");
            part.put("assetCode", ps[3] != null ? ps[3].toString() : "");
            part.put("availQty", ps[4] != null ? ps[4].toString() : "");
            oemEmptyStockdetails.add(part);
        }
        return oemEmptyStockdetails;
	}

	@Override
	public String getDocIdByGatheringEmpty() {
		String finyr = gatheringEmptyRepo.findFinyr();
		String docid = finyr + "GEMT" + gatheringEmptyRepo.finddocid();
		return docid;
	}

	
}
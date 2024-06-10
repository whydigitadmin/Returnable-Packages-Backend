package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinInwardDetailsDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDetailsDTO;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.BinOutwardVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardDetailsVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.repo.AssetRepo;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
import com.whydigit.efit.repo.BinOutwardRepo;
import com.whydigit.efit.repo.OemBinInwardDetailsRepo;
import com.whydigit.efit.repo.OemBinInwardRepo;
import com.whydigit.efit.repo.OemBinOutwardDetailsRepo;
import com.whydigit.efit.repo.OemBinOutwardRepo;

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
	OemBinOutwardDetailsRepo oemBinOutwardDetailsRepo;

	@Autowired
	AssetRepo assetRepo;

	@Autowired
	BinOutwardRepo binOutwardRepo;

	@Override
	public OemBinInwardVO createOemBinInward(OemBinInwardDTO oemBinInwardDTO) {

		OemBinInwardVO oemBinInwardVO = new OemBinInwardVO();
		String finyr = oemBinInwardRepo.findFinyr();
		String binoutward = finyr + "OBI" + oemBinInwardRepo.finddocid();
		oemBinInwardVO.setDocId(binoutward);
		oemBinInwardRepo.nextseq();  

		oemBinInwardVO.setDocDate(oemBinInwardDTO.getDocDate());
		oemBinInwardVO.setOrgId(oemBinInwardDTO.getOrgId());
		oemBinInwardVO.setCreatedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setModifiedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setKitNo(oemBinInwardDTO.getKitNo());
		oemBinInwardVO.setRecievedKitQty(oemBinInwardDTO.getRecievedKitQty());
		oemBinInwardVO.setCreatedby(oemBinInwardDTO.getCreatedBy());
		oemBinInwardVO.setModifiedby(oemBinInwardDTO.getCreatedBy()); 
		oemBinInwardVO.setGrnNo(oemBinInwardDTO.getGrnNo());
		oemBinInwardVO.setGrnDate(oemBinInwardDTO.getGrnDate());
		oemBinInwardVO.setOutwardDocId(oemBinInwardDTO.getOutwardDocId());

		List<OemBinInwardDetailsVO> oemBinInwardDetailsVOs = new ArrayList<>();
		if (oemBinInwardDTO.getOemBinInwardDetails() != null) {
			for (OemBinInwardDetailsDTO oemBinInwardDetailsDTO : oemBinInwardDTO.getOemBinInwardDetails()) {
				OemBinInwardDetailsVO oemBinInwardDetailsVO = new OemBinInwardDetailsVO();
				oemBinInwardDetailsVO.setAsset(oemBinInwardDetailsDTO.getAsset());
				oemBinInwardDetailsVO.setAssetCode(oemBinInwardDetailsDTO.getAssetCode());
				oemBinInwardDetailsVO.setRecievedQty(oemBinInwardDetailsDTO.getRecievedQty());
				oemBinInwardDetailsVO.setOemBinInwardVO(oemBinInwardVO);
				oemBinInwardDetailsVOs.add(oemBinInwardDetailsVO);
			}
		}
		oemBinInwardVO.setOemBinInwardDetails(oemBinInwardDetailsVOs);

		OemBinInwardVO savedoemBinInwardVO = oemBinInwardRepo.save(oemBinInwardVO);
		List<OemBinInwardDetailsVO> binInwardDetailsVOs = savedoemBinInwardVO.getOemBinInwardDetails();
		if (binInwardDetailsVOs != null && !binInwardDetailsVOs.isEmpty())
		{
			for (OemBinInwardDetailsVO binInwardDetailsVO : binInwardDetailsVOs) {

				BinOutwardVO emitterOutwardVO = binOutwardRepo.findByDocId(savedoemBinInwardVO.getOutwardDocId());

				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(savedoemBinInwardVO.getOutwardDocId());
				stockDetailsVO.setStockBranch(emitterOutwardVO.getReceiver() + "-" + emitterOutwardVO.getDestination());
				stockDetailsVO.setStockDate(savedoemBinInwardVO.getDocDate());
				stockDetailsVO.setSku(binInwardDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(binInwardDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(binInwardDetailsVO.getRecievedQty() * -1);
				stockDetailsVO.setOrgId(savedoemBinInwardVO.getOrgId());
				stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(binInwardDetailsVO.getAssetCode()));
				stockDetailsVO.setStatus("M");
				stockDetailsVO.setScreen("Bin Outward");
				stockDetailsVO.setSCode(savedoemBinInwardVO.getScode());
				stockDetailsVO.setPm("M");
				stockDetailsVO.setStockSource("");
				stockDetailsVO.setBinLocation("");
				stockDetailsVO.setCancelRemarks("");
				stockDetailsVO.setStockLocation("");
				stockDetailsVO.setSourceId(binInwardDetailsVO.getId());
				stockDetailsVO.setFinyr(savedoemBinInwardVO.getFinyr());
				assetStockDetailsRepo.save(stockDetailsVO);
			}

		for (OemBinInwardDetailsVO binInwardDetailsVO : binInwardDetailsVOs) {

			BinOutwardVO emitterOutwardVO = binOutwardRepo.findByDocId(savedoemBinInwardVO.getOutwardDocId());

			AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
			stockDetailsVO.setStockRef(savedoemBinInwardVO.getDocId());
			stockDetailsVO.setStockBranch(emitterOutwardVO.getReceiver() + "-" + emitterOutwardVO.getDestination());
			stockDetailsVO.setStockDate(savedoemBinInwardVO.getDocDate());
			stockDetailsVO.setSku(binInwardDetailsVO.getAsset());
			stockDetailsVO.setSkuCode(binInwardDetailsVO.getAssetCode());
			stockDetailsVO.setSkuQty(binInwardDetailsVO.getRecievedQty());
			stockDetailsVO.setOrgId(savedoemBinInwardVO.getOrgId());
			stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(binInwardDetailsVO.getAssetCode()));
			stockDetailsVO.setStatus("S");
			stockDetailsVO.setScreen("Bin Outward");
			stockDetailsVO.setSCode(savedoemBinInwardVO.getScode());
			stockDetailsVO.setPm("P");
			stockDetailsVO.setStockSource("");
			stockDetailsVO.setBinLocation("");
			stockDetailsVO.setCancelRemarks("");
			stockDetailsVO.setStockLocation("");
			stockDetailsVO.setSourceId(binInwardDetailsVO.getId());
			stockDetailsVO.setFinyr(savedoemBinInwardVO.getFinyr());
			assetStockDetailsRepo.save(stockDetailsVO);
		}
	
	}
		return oemBinInwardVO; 
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
		String binoutward = finyr + "OBI" + oemBinInwardRepo.finddocid();
		oemBinOutwardVO.setDocId(binoutward);
		oemBinOutwardRepo.nextSeq();

		oemBinOutwardVO.setDocDate(oemBinOutwardDTO.getDocDate());
		oemBinOutwardVO.setOrgId(oemBinOutwardDTO.getOrgId());
		oemBinOutwardVO.setCreatedby(oemBinOutwardDTO.getCreatedBy());
		oemBinOutwardVO.setModifiedby(oemBinOutwardDTO.getCreatedBy());
		oemBinOutwardVO.setKit(oemBinOutwardDTO.getKit());
		oemBinOutwardVO.setOutwardKitQty(oemBinOutwardDTO.getOutwardKitQty());
		oemBinOutwardVO.setModifiedby(oemBinOutwardDTO.getCreatedBy());
		oemBinOutwardVO.setOrgId(oemBinOutwardDTO.getOrgId());
		oemBinOutwardVO.setOutwardDocId(oemBinOutwardDTO.getOutwardDocId());

		List<OemBinOutwardDetailsVO> oemBinOutwardDetailsVOs = new ArrayList<>();
		if (oemBinOutwardDTO.getOemBinOutwardDetails() != null) {
			for (OemBinOutwardDetailsDTO oemBinOutwardDetailsDTO : oemBinOutwardDTO.getOemBinOutwardDetails()) {
				OemBinOutwardDetailsVO oemBinOutwardDetailsVO = new OemBinOutwardDetailsVO();
				oemBinOutwardDetailsVO.setAsset(oemBinOutwardDetailsDTO.getAsset());
				oemBinOutwardDetailsVO.setAssetCode(oemBinOutwardDetailsDTO.getAssetCode());
				oemBinOutwardDetailsVO.setQty(oemBinOutwardDetailsDTO.getQty());
				oemBinOutwardDetailsVO.setOemBinOutwardVO(oemBinOutwardVO);
				oemBinOutwardDetailsVOs.add(oemBinOutwardDetailsVO);
			}
		}
		oemBinOutwardVO.setOemBinOutwardDetails(oemBinOutwardDetailsVOs);

		OemBinOutwardVO savedOemBinOutwardVO = oemBinOutwardRepo.save(oemBinOutwardVO);
		List<OemBinOutwardDetailsVO> binOutwardDetailsVOs = savedOemBinOutwardVO.getOemBinOutwardDetails();
		if (binOutwardDetailsVOs != null && !binOutwardDetailsVOs.isEmpty())
		{
			for (OemBinOutwardDetailsVO binOutwardDetailsVO : binOutwardDetailsVOs) {

				BinOutwardVO emitterOutwardVO = binOutwardRepo.findByDocId(savedOemBinOutwardVO.getOutwardDocId());

				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(savedOemBinOutwardVO.getOutwardDocId());
				stockDetailsVO.setStockBranch(emitterOutwardVO.getReceiver() + "-" + emitterOutwardVO.getDestination());
				stockDetailsVO.setStockDate(savedOemBinOutwardVO.getDocDate());
				stockDetailsVO.setSku(binOutwardDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(binOutwardDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(binOutwardDetailsVO.getQty() * -1);
				stockDetailsVO.setOrgId(savedOemBinOutwardVO.getOrgId());
				stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(binOutwardDetailsVO.getAssetCode()));
				stockDetailsVO.setStatus("M");
				stockDetailsVO.setScreen("Bin Outward");
				stockDetailsVO.setSCode(savedOemBinOutwardVO.getScode());
				stockDetailsVO.setPm("M");
				stockDetailsVO.setStockSource("");
				stockDetailsVO.setBinLocation("");
				stockDetailsVO.setCancelRemarks("");
				stockDetailsVO.setStockLocation("");
				stockDetailsVO.setSourceId(savedOemBinOutwardVO.getId());
				stockDetailsVO.setFinyr(savedOemBinOutwardVO.getFinYr());
				assetStockDetailsRepo.save(stockDetailsVO);
			}

			for (OemBinOutwardDetailsVO binOutwardDetailsVO : binOutwardDetailsVOs) {

				BinOutwardVO emitterOutwardVO = binOutwardRepo.findByDocId(savedOemBinOutwardVO.getOutwardDocId());

			AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
			stockDetailsVO.setStockRef(savedOemBinOutwardVO.getDocId());
			stockDetailsVO.setStockBranch(emitterOutwardVO.getReceiver() + "-" + emitterOutwardVO.getDestination());
			stockDetailsVO.setStockDate(savedOemBinOutwardVO.getDocDate());
			stockDetailsVO.setSku(binOutwardDetailsVO.getAsset());
			stockDetailsVO.setSkuCode(binOutwardDetailsVO.getAssetCode());
			stockDetailsVO.setSkuQty(binOutwardDetailsVO.getQty());
			stockDetailsVO.setOrgId(savedOemBinOutwardVO.getOrgId());
			stockDetailsVO.setCategory(assetRepo.getCategoryByAssetCodeId(binOutwardDetailsVO.getAssetCode()));
			stockDetailsVO.setStatus("S");
			stockDetailsVO.setScreen("Bin Outward");
			stockDetailsVO.setSCode(savedOemBinOutwardVO.getScode());
			stockDetailsVO.setPm("P");
			stockDetailsVO.setStockSource("");
			stockDetailsVO.setBinLocation("");
			stockDetailsVO.setCancelRemarks("");
			stockDetailsVO.setStockLocation("");
			stockDetailsVO.setSourceId(binOutwardDetailsVO.getId());
			stockDetailsVO.setFinyr(savedOemBinOutwardVO.getFinYr());
			assetStockDetailsRepo.save(stockDetailsVO);
		}
	
	}   
		return oemBinOutwardVO; 
	}

	@Override
	public OemBinInwardVO getAllOemBinInwardByDocId(String docId) {
		
		return oemBinInwardRepo.findOemInwardByDocId(docId);
	}
	
	@Override
	public String getDocIdByOemBinInward() {
		String finyr = oemBinOutwardRepo.findFinyr();
		String binoutward = finyr + "OBI" + oemBinInwardRepo.finddocid();
		return binoutward;
	}
	
}
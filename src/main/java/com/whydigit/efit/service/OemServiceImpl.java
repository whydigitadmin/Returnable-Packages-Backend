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
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.BinInwardDetailsVO;
import com.whydigit.efit.entity.BinOutwardVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.exception.ApplicationException;
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
		oemBinInwardDTO.setGrnNo(oemBinInwardDTO.getGrnNo());
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
	public OemBinOutwardVO updateCreateOemBinOutward(OemBinOutwardDTO oemBinOutWardDTO) throws ApplicationException {
		return null;
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

}
package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.BinInwardDTO;
import com.whydigit.efit.dto.BinInwardDetailsDTO;
import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDetailsDTO;
import com.whydigit.efit.dto.Po1DTO;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.BinInwardDetailsVO;
import com.whydigit.efit.entity.BinInwardVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardDetailsVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.entity.PoVO;
import com.whydigit.efit.entity.PoVO1;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
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

	@Override
	public OemBinInwardVO createOemBinInward(OemBinInwardDTO oemBinInwardDTO) {

		OemBinInwardVO oemBinInwardVO = createOemBinInwardVOByOemBinInwardDTO(oemBinInwardDTO);
		int finyr = oemBinInwardRepo.findfinyr();
		String bininward = finyr + "OBI" + oemBinInwardRepo.finddocid();
		oemBinInwardVO.setDocId(bininward);
		oemBinInwardRepo.nextseq();
		oemBinInwardRepo.save(oemBinInwardVO);
		OemBinInwardVO savedBinInwardVO = oemBinInwardRepo.save(oemBinInwardVO);

		List<OemBinInwardDetailsVO> binInwardDetailsVOLists = savedBinInwardVO.getOemBinInwardDetails();
		if (binInwardDetailsVOLists != null && !binInwardDetailsVOLists.isEmpty())
			for (OemBinInwardDetailsVO binInwardDetailsVO : binInwardDetailsVOLists) {

				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(savedBinInwardVO.getDocId());
				stockDetailsVO.setStockDate(savedBinInwardVO.getDocDate());
				stockDetailsVO.setSku(binInwardDetailsVO.getAsset());
				stockDetailsVO.setSkuCode(binInwardDetailsVO.getAssetCode());
				stockDetailsVO.setSkuQty(binInwardDetailsVO.getRecievedQty());
				stockDetailsVO.setSCode(savedBinInwardVO.getScode());
				stockDetailsVO.setPm(savedBinInwardVO.getPm());
				stockDetailsVO.setScreen(savedBinInwardVO.getScreen());
				stockDetailsVO.setSourceId(binInwardDetailsVO.getId());
				stockDetailsVO.setFinyr(savedBinInwardVO.getFinyr());
				;
				assetStockDetailsRepo.save(stockDetailsVO);
			}
		return oemBinInwardVO;
	}

	private OemBinInwardVO createOemBinInwardVOByOemBinInwardDTO(OemBinInwardDTO oemBinInwardDTO) {
		List<OemBinInwardDetailsVO> binInwardDetailsVOList = new ArrayList<>();
		OemBinInwardVO binInwardVO = OemBinInwardVO.builder().docDate(oemBinInwardDTO.getDocDate())
				.recievedKitQty(oemBinInwardDTO.getRecievedKitQty()).createdBy(oemBinInwardDTO.getCreatedBy())
				.orgId(oemBinInwardDTO.getOrgId()).modifiedBy(oemBinInwardDTO.getCreatedBy())
				.oemBinInwardDetails(binInwardDetailsVOList).build();

		binInwardDetailsVOList = oemBinInwardDTO.getOemBinInwardDetails().stream()
				.map(bininward -> OemBinInwardDetailsVO.builder().asset(bininward.getAsset())
						.assetCode(bininward.getAssetCode()).recievedQty(bininward.getRecievedQty())
						.oemBinInwardVO(binInwardVO).build())
				.collect(Collectors.toList());
		binInwardVO.setOemBinInwardDetails(binInwardDetailsVOList);
		return binInwardVO;
	}

	//OEM BIN OUTWARD
	
	@Override
	public OemBinOutwardVO updateCreateOemBinOutward(OemBinOutwardDTO oemBinOutwardDTO) throws ApplicationException {
		OemBinOutwardVO oemBinOutwardVO = new OemBinOutwardVO();
		if (ObjectUtils.isNotEmpty(oemBinOutwardDTO.getId())) {
			oemBinOutwardVO = oemBinOutwardRepo.findById(oemBinOutwardDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid OEM BinOutward details"));
		}
		List<OemBinOutwardDetailsVO> oemBinOutwardDetailsVO = new ArrayList<>();
		if (oemBinOutwardDTO.getOemBinOutwardDetails() != null) {
			for (OemBinOutwardDetailsDTO oemBinOutwardDetailsDTO : oemBinOutwardDTO.getOemBinOutwardDetails()) {
				OemBinOutwardDetailsVO oemBinOutwardDetails = new OemBinOutwardDetailsVO();
				oemBinOutwardDetails.setAsset(oemBinOutwardDetailsDTO.getAsset());
				oemBinOutwardDetails.setAssetCode(oemBinOutwardDetailsDTO.getAssetCode());
				oemBinOutwardDetails.setQty(oemBinOutwardDetails.getQty());

				oemBinOutwardDetailsVO.add(oemBinOutwardDetails);
			}
		}
		oemBinOutwardVO.setOemBinOutwardDetails(oemBinOutwardDetailsVO);
		OemBinOutwardVO savedOemBinOutwardVO = oemBinOutwardRepo.save(oemBinOutwardVO);
		
		getOemBinOutwardVOFromOemBinOutwardDTO(oemBinOutwardDTO, oemBinOutwardVO);
		
		
		List<OemBinOutwardDetailsVO> savedOemBinOutwardDetailsVO = savedOemBinOutwardVO.getOemBinOutwardDetails();

		if (savedOemBinOutwardDetailsVO != null && !savedOemBinOutwardDetailsVO.isEmpty()) {

			for (OemBinOutwardDetailsVO oemBinOutwardDetails : savedOemBinOutwardDetailsVO) {
				AssetStockDetailsVO stockDetailsVO = new AssetStockDetailsVO();
				stockDetailsVO.setStockRef(savedOemBinOutwardVO.getDocId());
				stockDetailsVO.setStockDate(savedOemBinOutwardVO.getDocDate());
				stockDetailsVO.setSCode(savedOemBinOutwardVO.getScode());
				stockDetailsVO.setPm(savedOemBinOutwardVO.getPm());
				stockDetailsVO.setScreen(savedOemBinOutwardVO.getScreen());
				stockDetailsVO.setFinyr(savedOemBinOutwardVO.getFinYr());
				stockDetailsVO.setSku(oemBinOutwardDetails.getAsset());
				stockDetailsVO.setSkuCode(oemBinOutwardDetails.getAssetCode());
				stockDetailsVO.setSkuQty(-oemBinOutwardDetails.getQty());
				stockDetailsVO.setSourceId(oemBinOutwardDetails.getId());
				stockDetailsVO.setSCode(savedOemBinOutwardVO.getScode());
				stockDetailsVO.setScreen(savedOemBinOutwardVO.getScreen());
				stockDetailsVO.setPm(savedOemBinOutwardVO.getPm());
				assetStockDetailsRepo.save(stockDetailsVO);
			}
		}
		
		
		return oemBinOutwardVO;
	}

	private void getOemBinOutwardVOFromOemBinOutwardDTO(OemBinOutwardDTO oemBinOutwardDTO, OemBinOutwardVO oemBinOutwardVO) {
		
		int finyr = oemBinOutwardRepo.findFinyr();
		String binoutward = finyr + "OBO" + oemBinOutwardRepo.finddocid();
		oemBinOutwardVO.setDocId(binoutward);
		oemBinOutwardRepo.nextSeq();
		oemBinOutwardVO.setCreatedBy(oemBinOutwardDTO.getCreatedBy());
		oemBinOutwardVO.setModifiedBy(oemBinOutwardDTO.getCreatedBy());
		oemBinOutwardVO.setOrgId(oemBinOutwardDTO.getOrgId());
		oemBinOutwardVO.setDocDate(oemBinOutwardDTO.getDocDate());
		oemBinOutwardVO.setKit(oemBinOutwardDTO.getKit());
		oemBinOutwardVO.setOutwardKitQty(oemBinOutwardDTO.getOutwardKitQty());

	}

}

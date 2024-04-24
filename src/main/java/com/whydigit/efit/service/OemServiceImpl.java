package com.whydigit.efit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.entity.AssetStockDetailsVO;
import com.whydigit.efit.entity.OemBinInwardDetailsVO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.repo.AssetStockDetailsRepo;
import com.whydigit.efit.repo.OemBinInwardDetailsRepo;
import com.whydigit.efit.repo.OemBinInwardRepo;

@Service
public class OemServiceImpl implements OemService {
	public static final Logger LOGGER = LoggerFactory.getLogger(OemServiceImpl.class);

	@Autowired
	AssetStockDetailsRepo assetStockDetailsRepo;

	@Autowired
	OemBinInwardRepo oemBinInwardRepo;

	@Autowired
	OemBinInwardDetailsRepo oemBinInwardDetailsRepo;

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

}

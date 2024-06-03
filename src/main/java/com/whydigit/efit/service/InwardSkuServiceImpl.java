package com.whydigit.efit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.InwardSkuDTO;
import com.whydigit.efit.entity.InwardSkuVO;
import com.whydigit.efit.exception.ApplicationException;
import com.whydigit.efit.repo.InwardSkuRepo;

@Service
public class InwardSkuServiceImpl implements InwardSkuService {

	@Autowired
	private InwardSkuRepo inwardSkuRepo;

	@Override
	public InwardSkuVO createInwardSku(InwardSkuDTO inwardSkuDTO) {
		InwardSkuVO inwardSkuVO = new InwardSkuVO();
		getInwardSkuVOFromInwardSkuDTO(inwardSkuDTO, inwardSkuVO);
		return inwardSkuRepo.save(inwardSkuVO);
	}

	private void getInwardSkuVOFromInwardSkuDTO(InwardSkuDTO inwardSkuDTO, InwardSkuVO inwardSkuVO) {
		inwardSkuVO.setRmDate(LocalDateTime.now());
		inwardSkuVO.setRetManifestNo(inwardSkuDTO.getRetManifestNo());
		inwardSkuVO.setFlowName(inwardSkuDTO.getFlowName());
		inwardSkuVO.setTransporter(inwardSkuDTO.getTransporter());
		inwardSkuVO.setWayBillNo(inwardSkuDTO.getWayBillNo());
		inwardSkuVO.setDehireQty(inwardSkuDTO.getDehireQty());
		inwardSkuVO.setNetRecdQty(inwardSkuDTO.getNetRecdQty());
		inwardSkuVO.setRepair(inwardSkuDTO.getRepair());
		inwardSkuVO.setScrap(inwardSkuDTO.getScrap());
		inwardSkuVO.setTat(inwardSkuDTO.getTat());
		inwardSkuVO.setPartName(inwardSkuDTO.getPartName());
		inwardSkuVO.setPartNo(inwardSkuDTO.getPartNo());
	}

	@Override
	public InwardSkuVO updateInwardSku(InwardSkuDTO inwardSkuDTO) throws ApplicationException {
		InwardSkuVO inwardSkuVO = new InwardSkuVO();
		if (ObjectUtils.isNotEmpty(inwardSkuDTO) && ObjectUtils.isNotEmpty(inwardSkuDTO.getId())) {
			inwardSkuVO = inwardSkuRepo.findById(inwardSkuDTO.getId())
					.orElseThrow(() -> new ApplicationException("InwardSku information not found."));
		} else {
			throw new ApplicationException("Invalid InwardSku information");
		}
		getInwardSkuVOFromInwardSkuDTO(inwardSkuDTO, inwardSkuVO);
		return inwardSkuRepo.save(inwardSkuVO);
	}


	@Override
	public Optional<InwardSkuVO> getInwardSkuById(Long id) {
		// TODO Auto-generated method stub
		return inwardSkuRepo.findById(id);
	}


	@Override
	public List<InwardSkuVO> getAllInwardSku() {
		// TODO Auto-generated method stub
		return inwardSkuRepo.findAll();
	}
}

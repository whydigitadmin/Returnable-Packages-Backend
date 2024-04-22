package com.whydigit.efit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.InwardSkuDTO;
import com.whydigit.efit.entity.InwardSkuVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface InwardSkuService {

	InwardSkuVO createInwardSku(InwardSkuDTO inwardSkuDTO);

	InwardSkuVO updateInwardSku(InwardSkuDTO inwardSkuDTO) throws ApplicationException;

	Optional<InwardSkuVO> getInwardSkuById(Long id);

	List<InwardSkuVO> getAllInwardSku();
	
	

}

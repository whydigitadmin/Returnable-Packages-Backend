package com.whydigit.efit.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.efit.dto.OemBinInwardDTO;
import com.whydigit.efit.dto.OemBinOutwardDTO;
import com.whydigit.efit.entity.OemBinInwardVO;
import com.whydigit.efit.entity.OemBinOutwardVO;
import com.whydigit.efit.exception.ApplicationException;

@Service
public interface OemService {

//	OEM Bin Inward
	OemBinInwardVO createOemBinInward(OemBinInwardDTO oemBinInwardDTO);
	

//	OEM Bin Outward
	OemBinOutwardVO updateCreateOemBinOutward(OemBinOutwardDTO oemBinOutWardDTO) throws ApplicationException;


	Set<Object[]> getFlowByUserId(Long userId, Long orgId);


	Set<Object[]> getOutwardDetailsByFlow(Long flowId, Long orgId);
	
}
